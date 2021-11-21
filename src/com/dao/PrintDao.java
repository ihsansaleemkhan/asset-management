package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beans.Print;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class PrintDao {

	/*
	 * get All delivered/dispatched card to user 
	 * @param startdate from when card has dispatched
	 * @param enddate till when card has dispatched
	 * @param school schoolId (schoolId 100 will be sent if data from all schools is required, orelse respective schoolId)
	 * return String which has all details of delivered cards.
	 * 
	 */
	public String getDataToPrint(String startDate, String endDate, String school, Integer draw, Integer length, Integer start) throws SQLException {
		
	
		
		List<Map<String, String>> schedules = new ArrayList<>();
		Map<String, String> schedule;
		schedule = new HashMap<String, String>();
		List<Print> printList = new ArrayList<Print>();
		JsonObject jsonOBJ = new JsonObject();
		String schedulesJSON = "";
		if(endDate.equals("")){
			LocalDate localDate = LocalDate.now(ZoneId.of("GMT+02:00"));
			String month = Integer.toString(localDate.getMonthValue());
			String date = Integer.toString(localDate.getDayOfMonth());
			if(month.length() == 1)
				month = "0" + month;
			if(date.length() == 1)
				date = "0" + date;
			endDate = localDate.getYear() + "-" + month + "-" + date;
		}
		String sql = "select box_group, card_serial_no, Date(printed_date) printed_date, delivered_cards, delivered_person, returned_cards ,date(delivered_date) delivered_date, 200-card_ready_count damaged_card, name from card_box cb"
				+ "	join box_info bi on cb.box_id = bi.box_id"
				+ " join schools sc on cb.school_id = sc.id"
				+ " WHERE cb.status=3 ";
		
		if(!startDate.equals("")){
			sql+=" AND bi.delivered_date between '"+startDate+"' ";
		}
		if(!endDate.equals("")) {
			sql+=" AND DATE(DATE_ADD('"+endDate+"', INTERVAL 1 DAY))";
		}
		//System.out.println("length"+ length);	
		if(!school.equals("100"))
			sql+= " and school_id = " +school;
		if(length>0)
			sql+= " order by delivered_date LIMIT " + length + " OFFSET " + start;
		
		System.out.println("sql :" + sql);
		Integer totalDeliveredBox = fetchTotalDeliveredBoxCount(school, startDate, endDate);
		try (Connection conn = DB.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql);) {
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				if(length>0) {
					Print print =new Print();
					schedule = new HashMap<String, String>();
					print.setBox_group(rs.getString("box_group"));
					print.setCard_serial_no(rs.getString("card_serial_no"));
					print.setPrintedDate(rs.getString("printed_date"));
					print.setDelivered_cards(rs.getString("delivered_cards"));
					print.setDeliveredDate(rs.getString("delivered_date"));
					print.setDelivered_person(rs.getString("delivered_person"));// change to delivered person when column																				// is created
					print.setReturnedCard(rs.getString("returned_cards"));
					print.setDamaged_card(rs.getString("damaged_card"));
					print.setSchool(rs.getString("name"));
					printList.add(print);
				}else {
					schedule = new HashMap<String, String>();
					schedule.put("box_group", rs.getString("box_group"));
					schedule.put("card_serial_no", rs.getString("card_serial_no"));
					schedule.put("printed_date", rs.getString("printed_date"));
					schedule.put("delivered_cards", rs.getString("delivered_cards"));
					schedule.put("delivered_date", rs.getString("delivered_date"));
					schedule.put("delivered_person", rs.getString("delivered_person"));// change to delivered person when column																				// is created
					schedule.put("returned_cards", rs.getString("returned_cards"));
					schedule.put("damaged_card", rs.getString("damaged_card"));
					schedule.put("school", rs.getString("name"));
					schedules.add(schedule);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(length>0) {
			//System.out.println(printList);
			jsonOBJ.addProperty("recordsTotal", totalDeliveredBox);
			jsonOBJ.addProperty("recordsFiltered", totalDeliveredBox);
			jsonOBJ.addProperty("draw", new Gson().toJson(draw));
			jsonOBJ.addProperty("data", new Gson().toJson(printList));
			return new Gson().toJson(jsonOBJ);
		}
		else {
			schedulesJSON = new Gson().toJson(schedules);
			//System.out.println("schedulesJSON:"+schedulesJSON);
			return schedulesJSON;
		}
		
	}
	
	
	
	/**
	 * Get count of Total delivered Box
	 * 
	 * @return Integer[count]
	 * @throws SQLException
	 */
	public Integer fetchTotalDeliveredBoxCount(String school_id, String start_date, String end_date) throws SQLException {
		Integer totalDeliveredBox = 0;
		
		String sql = "SELECT COUNT(*) FROM card_box cb"
				+ " JOIN box_info bi ON cb.box_id = bi.box_id"
				+ " join schools sc on cb.school_id = sc.id"
				+ " WHERE cb.status=3";
			
		    	if(!start_date.equals("") && !end_date.equals("")){
					sql+=" AND bi.delivered_date between '"+start_date+"' AND DATE(DATE_ADD('"+end_date+"', INTERVAL 1 DAY))";
				}
				if(!school_id.equals("100"))
					sql+= " AND school_id = " +school_id;
				System.out.println("count sql ---------"+sql);
				System.out.println("2 ---------"+start_date + "end date"+end_date);
		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();

			totalDeliveredBox = rs.getInt(1);

		} catch (SQLException e) {
			System.out.println("Error on String fetchTotalDeliveredBoxCount method: " + e);
		} finally {
			conn.close();
		}

		return totalDeliveredBox;
	}

}
