package com.etaap.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;

import com.etaap.common.util.CommonUtil;
import com.etaap.common.util.LogUtil;
import com.etaap.core.config.DBConfig;
import com.etaap.core.exception.ExceptionListener;
import com.etaap.core.exception.HandleException;
import com.etaap.core.exception.handler.ExceptionHandler;

/**
 * This Class helps to get connection for Databases Right now, it is using JDBC
 * for Database connection.
 */
public class DBManager implements ExceptionListener {

	/** The log. */
	static Log log = LogUtil.getLog(DBManager.class);

	/** The db config. */
	DBConfig dbConfig;

	/**
	 * Setter for DBConfig.
	 * 
	 * @param dbConfig
	 *            the new db config
	 */
	public void setDbConfig(DBConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	/**
	 * Create a new database connection and acts as a getter for the same.
	 * 
	 * @return the connection
	 */
	@HandleException(expected = { ClassNotFoundException.class, SQLException.class })
	public Connection getConnection() {
		Connection con = null;

		try {
			Class.forName(dbConfig.getDbClass());
			con = DriverManager.getConnection(dbConfig.getDbUrl(), dbConfig.getUserName(), dbConfig.getPassword());
		} catch (ClassNotFoundException | SQLException e) {
			ExceptionHandler ex = new ExceptionHandler();
			ex.handleit(this, e);

		}

		return con;
	}

	/**
	 * Execute query based on the given sql.
	 * 
	 * @param sql
	 *            the sql
	 * @return the int
	 * @throws SQLException
	 */
	public int executeQuery(String sql) throws SQLException {

		int resultCount = 0;

		Connection con = getConnection();
		// if the connection or sql is null then resultCount will be null
		if ((con != null) && (sql != null)) {
			try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					resultCount = rs.getInt(1);
					CommonUtil.sop("Result Count from DB: " + resultCount);
				}
			}
		}
		return resultCount;
	}
}
