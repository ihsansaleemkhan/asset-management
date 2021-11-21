package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.beans.School;

public class UtilDao {
	
	public List<School> getAllSchool(){
		String getAllSchool = "select * from db_assets.schools";
		List<School> schoolArray = new ArrayList<School>();
		School school = null;
		try(Connection conn = DB.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(getAllSchool);){
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				school = new School();
				school.setId(rs.getInt("id"));
				school.setSchoolName(rs.getString("name"));
				schoolArray.add(school);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return schoolArray;
	}

}
