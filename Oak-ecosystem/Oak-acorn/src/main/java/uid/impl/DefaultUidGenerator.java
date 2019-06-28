package uid.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uid.UidGenerator;
import uid.exception.UidGenerateException;
import uid.utils.BitsAllocator;
import uid.utils.DateUtils;
import uid.worker.WorkerIdAssigner;

import javax.sql.DataSource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Represents an implementation of {@link }
 * <p>
 * The unique id has 64bits (long), default allocated as blow:<br>
 * <li>sign: The highest bit is 0
 * <li>delta seconds: The next 28 bits, represents delta seconds since a
 * customer epoch(2016-05-20 00:00:00.000). Supports about 8.7 years until to
 * 2024-11-20 21:24:16
 * <li>worker id: The next 22 bits, represents the worker's id which assigns
 * based on database, max id is about 420W
 * <li>sequence: The next 13 bits, represents a sequence within the same second,
 * max for 8192/s<br>
 * <br>
 * <p>
 * The {@link DefaultUidGenerator#parseUID(long)} is a tool method to parse the
 * bits
 * <p>
 * 
 * <pre>
 * {@code
 * +------+----------------------+----------------+-----------+
 * | sign |     delta seconds    | worker node id | sequence  |
 * +------+----------------------+----------------+-----------+
 *   1bit          28bits              22bits         13bits
 * }
 * </pre>
 * <p>
 * You can also specified the bits by Spring property setting.
 * <li>timeBits: default as 28
 * <li>workerBits: default as 22
 * <li>seqBits: default as 13
 * <li>epochStr: Epoch date string format 'yyyy-MM-dd'. Default as '2018-06-30'
 * <p>
 * <p>
 * <b>Note that:</b> The total bits must be 64 -1
 * Created by Oak on 2018/7/19.
 * Description:
 */
public class DefaultUidGenerator implements UidGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUidGenerator.class);

	/**
	 * Bits allocate
	 */
	private final int timeBits = 28;
	private final int workerBits = 22;
	private final int seqBits = 13;

	/**
	 * Customer epoch, unit as second. For example 2016-05-20 (ms: 1463673600000)
	 */
	private final String epochStr = "2018-06-30";
	private final long epochSeconds = TimeUnit.MILLISECONDS.toSeconds(DateUtils.parseByDayPattern(epochStr).getTime());;

	/**
	 * Stable fields after spring bean initializing
	 */
	private final BitsAllocator bitsAllocator = new BitsAllocator(timeBits, workerBits, seqBits);
	private long workerId;

	/**
	 * Volatile fields caused by nextId()
	 */
	private long sequence = 0L;
	private long lastSecond = -1L;

	/**
	 * Spring property
	 */
	private WorkerIdAssigner workerIdAssigner;

	public DefaultUidGenerator(DataSource dataSource) {
		this(dataSource, null, null);
	}

	public DefaultUidGenerator(DataSource dataSource, String port, String appName) {
		// initialize bits allocator
		workerIdAssigner = new WorkerIdAssigner(dataSource, port, appName);
		// initialize worker id
		workerId = workerIdAssigner.assignWorkerId();
		if (workerId > bitsAllocator.getMaxWorkerId()) {
			throw new RuntimeException("Worker id " + workerId + " exceeds the max " + bitsAllocator.getMaxWorkerId()
					+ ", Execute SQL: truncate table ZUID_WORKER_NODE.");
		}
		LOGGER.info("Initialized bits(1, {}, {}, {}) for workerID:{}", workerBits, workerBits, seqBits, workerId);
	}

	public long getUID() throws UidGenerateException {
		try {
			return nextId();
		} catch (Exception e) {
			// LOGGER.error("Generate unique id exception. ", e);
			throw new UidGenerateException(e);
		}
	}

	public String parseUID(long uid) {
		// parse UID
		long[] vs = bitsAllocator.resolve(uid);
		long deltaSeconds = vs[0];
		long workerId = vs[1];
		long sequence = vs[2];
		//
		Date thatTime = new Date(TimeUnit.SECONDS.toMillis(epochSeconds + deltaSeconds));
		String thatTimeStr = DateUtils.formatByDateTimePattern(thatTime);
		// format as string
		return String.format("{\"UID\":\"%d\",\"timestamp\":\"%s\",\"workerId\":\"%d\",\"sequence\":\"%d\"}", uid,
				thatTimeStr, workerId, sequence);
	}

	/**
	 * Get UID
	 *
	 * @return UID
	 * @throws UidGenerateException in the case: Clock moved backwards; Exceeds the
	 *                              max timestamp
	 */
	private synchronized long nextId() {
		long currentSecond = getCurrentSecond();

		// Clock moved backwards, refuse to generate uid
		if (currentSecond < lastSecond) {
			long refusedSeconds = lastSecond - currentSecond;
			throw new UidGenerateException("Clock moved backwards. Refusing for %d seconds", refusedSeconds);
		}

		// At the same second, increase sequence
		if (currentSecond == lastSecond) {
			sequence = (sequence + 1) & bitsAllocator.getMaxSequence();
			// Exceed the max sequence, we wait the next second to generate uid
			if (sequence == 0) {
				currentSecond = getNextSecond(lastSecond);
			}
			// At the different second, sequence restart from zero
		} else {
			sequence = 0L;
		}

		lastSecond = currentSecond;

		// Allocate bits for UID
		return bitsAllocator.allocate(currentSecond - epochSeconds, workerId, sequence);
	}

	/**
	 * Get next millisecond
	 */
	private long getNextSecond(long lastTimestamp) {
		long timestamp = getCurrentSecond();
		while (timestamp <= lastTimestamp) {
			timestamp = getCurrentSecond();
		}

		return timestamp;
	}

	/**
	 * Get current second
	 */
	private long getCurrentSecond() {
		long currentSecond = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
		if (currentSecond - epochSeconds > bitsAllocator.getMaxDeltaSeconds()) {
			throw new UidGenerateException("Timestamp bits is exhausted. Refusing UID generate. Now: " + currentSecond);
		}
		return currentSecond;
	}

}
