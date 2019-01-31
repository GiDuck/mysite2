package com.douzone.mvc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

	private static DBManager dbManager = new DBManager();

	private DBManager() {

	}

	public static Connection getConnection() throws SQLException {

		
		if (dbManager == null) {

			synchronized (dbManager) {

				if (dbManager == null) {

					dbManager = new DBManager();
				}
			}

		}
		
		Connection conn = null;

		try {
			// 1. 드라이버 로딩
			Class.forName(AppProps.getProps().getDbName());
			// 2. 연결하기
			String url = AppProps.getProps().getDbUrl();
			conn = DriverManager.getConnection(url, AppProps.getProps().getDbId(), AppProps.getProps().getDbPwd());
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}
	
	public static void disconnect(Connection conn, PreparedStatement pstmt, Statement stmt, ResultSet rs) {
		
		try {
			
			if(rs!=null && !rs.isClosed()) {
				
				rs.close();
			}
			
			
			if(pstmt!=null && !pstmt.isClosed()) {
				pstmt.close();
			}
			
			if(stmt !=null && !stmt.isClosed()) {
				stmt.close();
			}
			
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}

}
