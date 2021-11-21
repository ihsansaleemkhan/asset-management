package com.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;

public class DbUtil {
	  private static java.sql.Connection dbConnection = null;

	  public static java.sql.Connection getConnection() {
	      if (dbConnection != null) {
	          return dbConnection;
	      } else {
	          try {
	              InputStream inputStream = DbUtil.class.getClassLoader()
	                      .getResourceAsStream("db.properties");
	              Properties properties = new Properties();
	              if (properties != null) {
	                  properties.load(inputStream);

	                  String dbDriver = properties.getProperty("dbDriver");
	                  String connectionUrl = properties
	                          .getProperty("connectionUrl");
	                  String userName = properties.getProperty("userName");
	                  String password = properties.getProperty("password");

	                  Class.forName(dbDriver).newInstance();
	                  dbConnection = DriverManager.getConnection(connectionUrl,
	                          userName, password);
	              }
	          } catch (Exception e) {
	              e.printStackTrace();
	          }
	          return dbConnection;
	      }
	  }
	  
	  public static void closeConnection(Connection conn) {
			try {
				conn.close();
			} catch (Exception e) { 
				/* ignored */ 
			}
		}
		
		public static void closeStatement(Statement stmt) {
			try {
				stmt.close();
			} catch (Exception e) { 
				/* ignored */ 
			}
		}
		
		public static void closeStatement(PreparedStatement pstmt) {
			try {
				pstmt.close();
			} catch (Exception e) { 
				/* ignored */ 
			}
		}
		
	}
