package uid.worker;

import lombok.Data;

import java.util.Date;

/**
 * Entity for M_WORKER_NODE
 * Created by Oak on 2018/7/19.
 * Description:
 */
@Data
public class WorkerNodeEntity {

	/**
	 * Entity unique id (table unique)
	 */
	private long id;

	/**
	 * Type of CONTAINER: HostName, ACTUAL : IP.
	 */
	private String hostName;

	/**
	 * Type of CONTAINER: Port, ACTUAL : Timestamp + Random(0-10000)
	 */
	private String port;

	/**
	 * type of {@link WorkerNodeType}
	 */
	private int nodeType;

	/**
	 * Application name
	 */
	private String appName;

	/**
	 * Created time
	 */
	private Date created;

	/**
	 * Last modified
	 */
	private Date modified;

	/**
	 * Getters & Setters
	 */

}
