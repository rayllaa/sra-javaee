package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoConnection {
	
	//private static final DaoConnection dao = new DaoConnection();

	private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mariadb://localhost:3306/sra"; 
	private static final String USER = "root";
	private static final String PASS = "";
	private static Connection c;
	
	public DaoConnection() {
		conexao();
	}
	
	public Connection getConnection() {
		return c;
	}
	
	private static void conexao() {

		try {
			Class.forName(JDBC_DRIVER);

			c = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Banco conectado com sucesso!");
			
		} catch (SQLException se) {
			System.out.println("Exception: SQLException");
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception: Exception");
			e.printStackTrace();
		}
		
	}
	
}
