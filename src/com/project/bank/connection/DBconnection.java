package com.project.bank.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
public class DBconnection {
	
	


	
	private static Properties loadProperties() {
		 Properties prop = new Properties();
		   
		   try {
			prop.load(new FileInputStream("resources/driver.properties"));
		  } catch (FileNotFoundException e) {
			e.printStackTrace();
		  } catch (IOException e) {
		 	e.printStackTrace();
		 }
		return prop;
	}
	
	
	public static Connection getConnection () {
		
		   Connection conn=null;
		   Properties prop = loadProperties();
		
		 	 
			try {
				//드라이버로딩
				Class.forName(prop.getProperty("driver"));
				
				//접속할 DB정보
				
				conn=DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("id"),prop.getProperty("password"));
				conn.setAutoCommit(false);
			
			} catch (ClassNotFoundException e) {
				System.out.println("sql정보를 찾을수 없습니다.");
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("sql접속오류");
				e.printStackTrace();
			}
	  return conn;
		
	 }
	
	
	//커밋 처리해주는 메소드
	
	
	 //2.commit처리해주는 메소드 (Connection객체 전달 받아서)
		public static void commit(Connection conn) {
			try {
				
				if(conn!=null&&!conn.isClosed()) {//conn이 반납이 되지 않았거나,null이 아닐때
					conn.commit();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	  public static void rollback(Connection conn) {
			try {
				
				if(conn!=null&&!conn.isClosed()) {//conn이 반납이 되지 않았거나,null이 아닐때
					conn.rollback();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		//JDBC용 객체들을 전달받아서 반납처리해주는 메소드
		//.Statement관련 객체를 전달받아서 반납시켜주는 메소드들
		
		
		
		public static void close(Statement stmt) {
			try {
				
				if(stmt!=null&&!stmt.isClosed()) {//conn이 반납이 되지 않았거나,null이 아닐때
					stmt.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		public static void close(ResultSet rest) {
			try {
				
				if(rest!=null&&!rest.isClosed()) {//conn이 반납이 되지 않았거나,null이 아닐때
					rest.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		public static void close(Connection con) {
			try {
				
				if(con!=null&&!con.isClosed()) {//conn이 반납이 되지 않았거나,null이 아닐때
					con.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	
	
	
}
