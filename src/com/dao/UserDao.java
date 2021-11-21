package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.beans.EmpInfo;
import com.beans.User;
import com.beans.Users;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class UserDao {
	
	public boolean verifyLoginCredential(String username, String password, Integer role) {
		String sql="select count(user_id) from user join role on user.role_id = role.role_id " + 
				"	where user.username = ? and user.password=? and user.role_id=? ";
		ResultSet rs = null;
		boolean status = false;
		try(Connection conn = DB.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setInt(3, role);
			rs = preparedStatement.executeQuery();
			if(rs.next())
				status = (rs.getInt(1)==1);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return status;
	}
	
	/**
	 * Get get User List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<Users> getUsersList() throws SQLException {
	
		ArrayList<Users> users = new ArrayList<Users>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Users user;
		
		String sql = "SELECT user_id, username, password, role FROM user u" + 
				" JOIN role r ON u.role_id = r.role_id";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		/*	 System.out.println("-----------    "+ rs.getFetchSize());*/
			while (rs.next()) {
				user = new Users();
			
				user.setUserId(rs.getInt("user_id"));
				user.setUserName(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
		
				users.add(user);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<Users> getUsersList() method: " + e);
		} finally {
			conn.close();
		}
		/*System.out.println("-------------------------------Users List ---------------- "+new Gson().toJson(users));*/
		return users;
	}
	
	
	/**
	 * check available user name
	 * @param un
	 * @return [true/false]
	 * @throws SQLException
	 */
	public boolean checkUsername(String un) throws SQLException {

		boolean isValidUser = false;
		Connection conn = null;
		Statement stm = null;
		ResultSet res = null;

		String st = "select * from user where username='" + un + "'";
		conn = DB.getConnection();

		try {
			stm = conn.createStatement();
			res = stm.executeQuery(st);

			if (res.next()) {
				isValidUser = true;
			}

		} catch (SQLException e) {
			System.out.println("Error on boolean checkUsername(String un) method: "+ e);
		} finally {
			conn.close();
		}

		return isValidUser;
	}
	
	/**
	 * Add user
	 * @param user
	 * @return [true/false]
	 * @throws SQLException
	 */
	public boolean addUser(User user) throws SQLException {
		Connection conn = null;
		boolean isDone = false;

		String sql = "insert into user (username,password,role_id, dash_board,material,dts_card,vehicle,hardwares,manage_user,add_asset,add_employee,emp_info_id) values (?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			conn = DB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, user.getUser_name());
			pst.setString(2, user.getPassword());
			pst.setInt(3, user.getRold_id());
			pst.setBoolean(4, user.isHaveDashBoardAccess());
			pst.setBoolean(5, user.isHaveMaterialAccess());
			pst.setBoolean(6, user.isHaveDtsCardAccess());
			pst.setBoolean(7, user.isHaveVehicleAccess());
			pst.setBoolean(8, user.isHaveHardwareAccess());
			pst.setBoolean(9, user.isHaveManageUsersAccess());
			pst.setBoolean(10, user.isHaveAddAssetAccess());
			pst.setBoolean(11, user.isHaveAddEmployeeAccess());
			pst.setInt(12, user.getEmployee_id());
			int affected = pst.executeUpdate();

			if (affected == 1) {
				isDone = true;
				}else {
					 isDone = false;
				 }
			}catch (Exception e) {
				System.out.println("Error on boolean addReturnCard(String card_serial_no, int school_id,String reason,String recieved_by) method: ");
			} finally {
				conn.close();
			}

			return isDone;
	}
	
	/**
	 * Get User details
	 * 
	 * @param user_id
	 * @return Json data
	 * @throws SQLException
	 */
	public String getUserDeatil(Integer user_id) throws SQLException {
		
		ArrayList<User> user_detail = new ArrayList<User>();
		String userJSON = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		User userbyID;
		
		String sql = "SELECT * FROM user u" + 
				" JOIN role r ON u.role_id = r.role_id WHERE user_id = "+user_id;
	/*	System.out.println(sql);*/
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				 //System.out.println("-----------    "+ rs.getFetchSize());
			 
		while (rs.next()) {
			userbyID = new User();
			  
			userbyID.setId(rs.getInt("user_id"));
			userbyID.setUser_name(rs.getString("username"));
			userbyID.setPassword(rs.getString("password"));
			userbyID.setRold_id(rs.getInt("role_id"));
			userbyID.setRold_name(rs.getString("role"));
			userbyID.setHaveDashBoardAccess(rs.getBoolean("dash_board"));
			userbyID.setHaveMaterialAccess(rs.getBoolean("material"));
			userbyID.setHaveDtsCardAccess(rs.getBoolean("dts_card"));
			userbyID.setHaveVehicleAccess(rs.getBoolean("vehicle"));
			userbyID.setHaveHardwareAccess(rs.getBoolean("hardwares"));
			userbyID.setHaveManageUsersAccess(rs.getBoolean("manage_user"));
			userbyID.setHaveAddAssetAccess(rs.getBoolean("add_asset"));
			userbyID.setHaveAddEmployeeAccess(rs.getBoolean("add_employee"));
			userbyID.setEmployee_id(rs.getInt("emp_info_id"));
			user_detail.add(userbyID);
			  
		  }
           }catch (SQLException e) {
			 
			 System.out.println("Error on String getUserDeatil method: " + e.getLocalizedMessage());
			 e.printStackTrace();
		 } finally {
			 conn.close();
		 }
		
		 userJSON = new Gson().toJson(user_detail);
	  /*   System.out.println("card box json 1111111111111"+userJSON);*/
		return userJSON;
	}
	
	/**
	 * Update User
	 * @param User_id,username,password,role
	 * @param 
	 * @return [true/false]
	 * @throws SQLException
	 */
	public boolean updateUser(User user) throws SQLException {
		boolean isDone = false;
		Connection conn = DB.getConnection();
		String sql = "UPDATE user" + 
				         " SET" + 
				           " username = '" + user.getUser_name() + 
				           "', password = '" + user.getPassword() +
				           "', role_id = " + user.getRold_id() +
				           ", emp_info_id = " + user.getEmployee_id() +
				           ", dash_board = " + user.isHaveDashBoardAccess() +
				           ", material = " + user.isHaveMaterialAccess() +
				           ", dts_card =" + user.isHaveDtsCardAccess() +
				           ", vehicle = " + user.isHaveVehicleAccess() +
				           ", hardwares =" + user.isHaveHardwareAccess() + 
				           ", manage_user = " + user.isHaveManageUsersAccess() +
				           ", add_asset = " + user.isHaveAddAssetAccess() +
				           ", add_employee = " + user.isHaveAddEmployeeAccess() + 
				     " WHERE user_id = " + user.getId();

		try {
			//System.out.println("sql :::::"+sql);
			PreparedStatement pst = conn.prepareStatement(sql);
			int affected = pst.executeUpdate();
			//System.out.println("affected salk "+affected);

			if (affected == 1) {
				isDone = true;
			} else {
				isDone = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on boolean updateUser(Users user) method: ");
		} finally {
			conn.close();
		}
		return isDone;
	}
	
	
	/**
	 * Delete User
	 * @param user_id
	 * @param 
	 * @return [true/false]
	 * @throws SQLException
	 */
	public boolean deleteUser(int user_id) throws SQLException {
		boolean isDone = false;
		Connection conn = DB.getConnection();
		String sql = "DELETE FROM user WHERE user_id = " + user_id;

		try {
			//System.out.println("sql :::::"+sql);
			PreparedStatement pst = conn.prepareStatement(sql);
			int affected = pst.executeUpdate();
			//System.out.println("affected salk "+affected);

			if (affected == 1) {
				isDone = true;
			} else {
				isDone = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on boolean deleteUser(int user_id) method: ");
		} finally {
			conn.close();
		}
		return isDone;
	}
	
	
	public User getUser(String user_name,String password , int role_id) {
		User user = null;
		String sql = "SELECT * FROM user where username = ? AND password = ? AND role_id = ?  ";
		ResultSet rs = null;
		try(Connection conn = DB.getConnection();PreparedStatement pstm = conn.prepareStatement(sql)){
			pstm.setString(1, user_name);
			pstm.setString(2, password);
			pstm.setInt(3, role_id);
			
			rs  = pstm.executeQuery();
			if(rs.next()) {
				user = new User();
				user.setUser_name(rs.getString("username"));
				user.setRold_id(rs.getInt("role_id"));
				user.setHaveDashBoardAccess(rs.getBoolean("dash_board"));
				user.setHaveMaterialAccess(rs.getBoolean("material"));
				user.setHaveDtsCardAccess(rs.getBoolean("dts_card"));
				user.setHaveVehicleAccess(rs.getBoolean("vehicle"));
				user.setHaveHardwareAccess(rs.getBoolean("hardwares"));
				user.setHaveManageUsersAccess(rs.getBoolean("manage_user"));
				user.setHaveAddAssetAccess(rs.getBoolean("add_asset"));
				user.setHaveAddEmployeeAccess(rs.getBoolean("add_employee"));
				user.setHaveManageDeliveryPersonAccess(rs.getBoolean("manage_delivery_person"));
			}
		}catch(Exception e) {
			System.out.println("Exception in UserDao getUser --->  " + e);
			e.printStackTrace();
		}
		return user ;
	}
	
	public ArrayList<EmpInfo> getEmployeeInfoList() {
		
		ArrayList<EmpInfo> employeeInfoList = new ArrayList<EmpInfo>();
		
		ResultSet rs = null;
		EmpInfo employeeInfo;
		
		String sql = "SELECT * FROM emp_info";
		
		try(Connection conn = DB.getConnection();Statement stm = conn.createStatement()) {
			rs = stm.executeQuery(sql);
			
			while (rs.next()) {
				employeeInfo = new EmpInfo();
				
				employeeInfo.setEmp_id(rs.getInt("emp_id"));
				employeeInfo.setFirst_name(rs.getString("first_name"));
				employeeInfo.setLast_name(rs.getString("last_name"));
				employeeInfo.setEmail(rs.getString("email"));
				employeeInfo.setMobile_no(rs.getString("mobile_no"));
				employeeInfo.setDepartment(rs.getString("department"));
				
			
		
				employeeInfoList.add(employeeInfo);
			
			}
		
		} catch(Exception e) {
			System.out.println("Exception in UserDao getEmployeeInfoList --->  " + e);
			e.printStackTrace();
		}
		/*System.out.println("-------------------------------Users List ---------------- "+new Gson().toJson(users));*/
		return employeeInfoList;
	}
	
	
	public boolean addEmployee(Map<String,String> params) {
		boolean result = false ;
		String sql = "insert into emp_info(first_name,last_name,email,mobile_no,department) values (?,?,?,?,?)";
		try(Connection conn = DB.getConnection();PreparedStatement pstm = conn.prepareStatement(sql)){
			pstm.setString(1, params.get("firstName"));
			pstm.setString(2, params.get("lastName"));
			pstm.setString(3, params.get("email"));
			pstm.setString(4, params.get("mobileNumber"));
			pstm.setString(5, params.get("department"));
			int rowEffected = pstm.executeUpdate();
			if(rowEffected >= 1) {
				result = true;
			}
		}catch(Exception e) {
			System.out.println("Exception in UserDao addEmployee --->  " + e);
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean updateEmployee(Map<String,String> params) {
		boolean result = false ;
		String sql = "update emp_info set first_name = ? , last_name = ? , email = ? , mobile_no = ? , department = ? where emp_id = ?";
		try(Connection conn = DB.getConnection();PreparedStatement pstm = conn.prepareStatement(sql)){
			pstm.setString(1, params.get("firstName"));
			pstm.setString(2, params.get("lastName"));
			pstm.setString(3, params.get("email"));
			pstm.setString(4, params.get("mobileNumber"));
			pstm.setString(5, params.get("department"));
			pstm.setString(6, params.get("employeeId"));
			int rowEffected = pstm.executeUpdate();
			if(rowEffected >= 1) {
				result = true;
			}
		}catch(Exception e) {
			System.out.println("Exception in UserDao addEmployee --->  " + e);
			e.printStackTrace();
		}
		
		return result;
	}

	public boolean deleteEmployee(int emp_id) {
		
		boolean result = false ;
		String sql = "delete from emp_info where emp_id = ?";
		try(Connection conn = DB.getConnection();PreparedStatement pstm = conn.prepareStatement(sql)){
			pstm.setInt(1,emp_id);
		
			int rowEffected = pstm.executeUpdate();
			if(rowEffected >= 1) {
				result = true;
			}
		}catch(Exception e) {
			System.out.println("Exception in UserDao addEmployee --->  " + e);
			e.printStackTrace();
		}
		
		return result;
	}

	public List<EmpInfo> searchEmployee(String name_email_id) {
		List<EmpInfo> empInfoList = new ArrayList<EmpInfo>();
		EmpInfo empInfo = null;
		String sql = "select * from emp_info where emp_id like \"%"+name_email_id+"%\" OR email like \"%"+name_email_id+"%\" OR first_name like \"%"+name_email_id+"%\" OR last_name like \"%"+name_email_id+"%\" LIMIT 10";
		ResultSet rs = null;
		try(Connection conn = DB.getConnection();Statement stm = conn.createStatement()){
			rs = stm.executeQuery(sql);
			while(rs.next()) {
				empInfo = new EmpInfo();
				empInfo.setEmp_id(rs.getInt("emp_id"));
				empInfo.setEmail(rs.getString("email"));
				
				empInfoList.add(empInfo);
			}
		}catch(Exception e) {
			System.out.println("Exception in UserDao searchEmployee --->  " + e);
			e.printStackTrace();
		}
				
		
		return empInfoList;
	}
}
