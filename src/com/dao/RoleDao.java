package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.beans.Role;

public class RoleDao {
	
	public List<Role> getRole() {
		String sql = "select role_id, role from role";
		ResultSet rs = null;
		List<Role> roleList = new ArrayList<Role>();
		
		try(Connection conn = DB.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			rs = (ResultSet) preparedStatement.executeQuery();
			while(rs.next()) {
				Role role = new Role();
				role.setRoleId(rs.getInt("role_id"));
				role.setRole(rs.getString("role"));
				roleList.add(role);
			}			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return roleList;
	}

}
