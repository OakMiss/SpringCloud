package uid.worker;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uid.exception.UidGenerateException;
import uid.utils.DockerUtils;
import uid.utils.NetUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;

/**
 * Represents a worker id assigner for {}
 * Created by Oak on 2018/7/19.
 * Description:
 */
@AllArgsConstructor
@RequiredArgsConstructor
public class WorkerIdAssigner {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkerIdAssigner.class);

	@NonNull
	private DataSource dataSource;

	private String port;

	private String appName;

	/**
	 * Assign worker id base on database.
	 * <p>
	 * If there is host name & port in the environment, we considered that the node
	 * runs in Docker container<br>
	 * Otherwise, the node runs on an actual machine.
	 * 
	 * @return assigned worker id
	 */
	public long assignWorkerId() {
		// build worker node entity
		WorkerNodeEntity workerNodeEntity = buildWorkerNode();
		LOGGER.info("Add worker node:" + workerNodeEntity);
		// add worker node for new (ignore the same IP + PORT)
		return addWorkerNode(workerNodeEntity);
	}

	private long addWorkerNode(WorkerNodeEntity workerNodeEntity) {
		Connection conn = null;
		PreparedStatement stsm = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO ZUID_WORKER_NODE (HOST_NAME,PORT,NODE_TYPE,APP_NAME,MODIFIED,CREATED) VALUES (?,?,?,?,?,?);";
			stsm = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stsm.setString(1, workerNodeEntity.getHostName()); // 主机名
			stsm.setString(2, workerNodeEntity.getPort()); // 端口
			stsm.setInt(3, workerNodeEntity.getNodeType()); // 服务类型 ACTUAL or CONTAINER
			stsm.setString(4, workerNodeEntity.getAppName()); // APP名称
			stsm.setTimestamp(5, new Timestamp(workerNodeEntity.getModified().getTime())); // 更新时间
			stsm.setTimestamp(6, new Timestamp(workerNodeEntity.getCreated().getTime())); // 创建时间
			stsm.executeUpdate();
			rs = stsm.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			throw new UidGenerateException(e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				LOGGER.error("build worker node error");
			}
			try {
				if (stsm != null) {
					stsm.close();
				}
			} catch (SQLException e) {
				LOGGER.error("build worker node error");
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				LOGGER.error("build worker node error");
			}
		}
		throw new UidGenerateException();
	}

	/**
	 * Build worker node entity by IP and PORT
	 */
	private WorkerNodeEntity buildWorkerNode() {
		WorkerNodeEntity workerNodeEntity = new WorkerNodeEntity();
		if (DockerUtils.isDocker()) {
			workerNodeEntity.setNodeType(WorkerNodeType.CONTAINER.value());
			workerNodeEntity.setHostName(DockerUtils.getDockerHost());
			workerNodeEntity.setPort(DockerUtils.getDockerPort());
		} else {
			workerNodeEntity.setNodeType(WorkerNodeType.ACTUAL.value());
			workerNodeEntity.setHostName(NetUtils.getLocalAddress());
			workerNodeEntity.setPort(port);
		}
		workerNodeEntity.setAppName(appName);
		//
		if (workerNodeEntity.getPort() == null) {
			workerNodeEntity.setPort("N/A");
		}
		if (workerNodeEntity.getAppName() == null) {
			workerNodeEntity.setAppName("N/A");
		}
		if (workerNodeEntity.getCreated() == null) {
			workerNodeEntity.setCreated(new Date());
		}
		if (workerNodeEntity.getModified() == null) {
			workerNodeEntity.setModified(new Date());
		}
		return workerNodeEntity;
	}
}
