package com.dao;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DB {
	
	public static Connection getConnection() throws SQLException {
		  
		   Connection conn=null;
		   Properties prop = new Properties();
		   InputStream inputStream=null;
		   try {
		              inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
		              
		              prop.load(inputStream);
		              
		              String url = prop.getProperty("DB_URL");
		              String driver=prop.getProperty("DB_DRIVER_CLASS");
		              String db_username=prop.getProperty("DB_USERNAME");
		              String db_password=prop.getProperty("DB_PASSWORD");
		              
		              inputStream.close();
		              
		              Class.forName(driver).newInstance();
		              
		              conn=DriverManager.getConnection(url, db_username, db_password);
		          	
		  			/*System.out.println("DB connected getconnection");
*/
		    } catch(Exception e) {
		    	System.out.println((new StringBuilder("Exception is : ")).append(e));
		    	
		    }finally {
		    	
		    }
		   
          return conn;
		}
	
	public static Connection getDataBaseConnection() throws SQLException{
		
		Connection conn=null;
		Properties prop = new Properties();
		InputStream inputStream=null;
		try {
			inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties");
			
			prop.load(inputStream);
			
			String url = prop.getProperty("DB_URL1");
			String driver=prop.getProperty("DB_DRIVER_CLASS1");
			String db_username=prop.getProperty("DB_USERNAME1");
			String db_password=prop.getProperty("DB_PASSWORD1");
			
			inputStream.close();
			
			Class.forName(driver).newInstance();
			
			conn=DriverManager.getConnection(url,db_username,db_password);
			
			/*System.out.println("DB connected getDatabaseConnection");*/
			
		}catch (Exception e) {
			System.out.println((new StringBuilder("Exception is : ")).append(e).toString());
		}finally {
			
		}
		return conn;
	}


}
