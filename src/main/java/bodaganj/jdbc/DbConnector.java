package bodaganj.jdbc;


import bodaganj.engine.ProjectLogger;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.fest.assertions.api.Assertions.fail;

/**
 * Created by bogdan on 14.05.16.
 * Test framework
 */
public final class DbConnector {

	private static final Logger LOG = ProjectLogger.getLogger(DbConnector.class.getSimpleName());
	private static final String SERVER = System.getProperty("db.bind_address");
	private static final String DB_NAME = System.getProperty("db.db_name");
	private static final String USER = System.getProperty("db.username");
	private static final String PASSWORD = System.getProperty("db.pwd");
	private static final String MYSQL_PREFIX = "jdbc:mysql://";
	private static final String MYSQL_PORT = "3306";
	private static DbConnector connector;

	private DbConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			LOG.debug("Started to get connection through JDBC driver to MySQL DB. Details:");
			LOG.debug(" server: {}", SERVER);
			LOG.debug(" dbName: {}", DB_NAME);
			LOG.debug(" user: {}", USER);
			LOG.debug(" password: {}", PASSWORD);
		} catch (final ClassNotFoundException exc) {
			LOG.error("com.mysql.jdbc.Driver isn't loaded correct");
			LOG.error(exc.getMessage(), exc);
		}
	}

	public static DbConnector getInstance() {
		if (connector == null) {
			connector = new DbConnector();
		}
		return connector;
	}

	/**
	 * Connect to DB specified in properties
	 */
	public Connection getConnection() {
		return getConnection(SERVER, DB_NAME, USER, PASSWORD);
	}

	/**
	 * Connect to certain DB
	 */
	public Connection getConnection(final String server, final String dbName, final String user, final String
			password) {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(MYSQL_PREFIX + server + ":" + MYSQL_PORT + "/" + dbName, user,
					password);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		if (connection == null) {
			fail("Cannot establish connection, please check connection settings");
		}
		return connection;
	}
}
