package br.com.jacademy.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcConnectionPool;

public class PoolConnectionJDBC {

	private DataSource dataSource;

	public PoolConnectionJDBC() {
		/**
		 * pode ser diferente pois isso depende do driver ex: JDBCPool poderia
		 * passar user e senha aqui caso vc necessario
		 */
		JdbcConnectionPool pool = JdbcConnectionPool.create("jdbc:h2:file:./bd-data", "sa", "");
		this.dataSource = pool;

	}

	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}

}
