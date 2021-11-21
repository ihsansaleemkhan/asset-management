package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.beans.Cardbox;
import com.google.gson.Gson;

public class SearchDao {

	public String getCardDetail(String card_serial_no) {
		ResultSet rs = null, rs1=null , rs2=null;
		int enteredSerialNo = Integer.parseInt(card_serial_no);
		String serialNoRange = convertedSerialNo(enteredSerialNo);
		String sql="select box_group, box_type, schools.name, box_info.delivered_cards, box_info.delivered_date, box_info.delivered_person, status from card_box   " + 
				" join box_info on card_box.box_id = box_info.box_id " + 
				" join schools on card_box.school_id = schools.id " + 
				" where card_serial_no = '"+ serialNoRange +"'";
		String sqlDamagedCards = "select box_group, box_type, schools.name, box_info.delivered_cards, box_info.delivered_date, box_info.delivered_person   from card_box " + 
				" join schools on  card_box.school_id = schools.id " + 
				" join box_info on card_box.box_id = box_info.box_id " + 
				" join damaged_cards on card_box.box_id=damaged_cards.box_id " + 
				" where damaged_cards.card_serial_no = '"+ enteredSerialNo +"'";
		String sqlDefault = "select box_group,box_type, status from card_box where card_serial_no = '"
				+ serialNoRange +"' limit 1";
		int damagedCards = 0;
		boolean damageCardExist = false;
		Cardbox cardbox = new Cardbox();
		try (Connection conn = DB.getConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sqlDamagedCards);) {
			rs = preparedStatement.executeQuery();
			damagedCards = rs.getFetchSize();
			
			while (rs.next()) {
				
				damageCardExist = true;
				cardbox.setBox_group(rs.getString("box_group"));
				cardbox.setCard_serial_no(serialNoRange);
				cardbox.setBox_type(rs.getString("box_type"));
				cardbox.setSchool(rs.getString("schools.name"));
				cardbox.setDelivery_date(rs.getString("delivered_date"));
				cardbox.setDelivery_person(rs.getString("delivered_person"));
				cardbox.setDeliverdCardCount(rs.getInt("delivered_cards"));
				cardbox.setEnteredCardSerialNo(card_serial_no);
				cardbox.setStatus(4);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!damageCardExist){
			
			try (Connection conn1 = DB.getConnection(); PreparedStatement preparedStatement1 = conn1.prepareStatement(sql);) {
				
				rs1 = preparedStatement1.executeQuery();
				
				while (rs1.next()) {
					damageCardExist = true;
					cardbox.setBox_group(rs1.getString("box_group"));
					cardbox.setCard_serial_no(serialNoRange);
					cardbox.setBox_type(rs1.getString("box_type"));
					cardbox.setSchool(rs1.getString("schools.name"));
					cardbox.setDelivery_date(rs1.getString("delivered_date"));
					cardbox.setDelivery_person(rs1.getString("delivered_person"));
					cardbox.setDeliverdCardCount(rs1.getInt("delivered_cards"));
					cardbox.setEnteredCardSerialNo(card_serial_no);
					cardbox.setStatus(rs1.getInt("status"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!damageCardExist){
		/*	System.out.println("sqlDefault: "+ sqlDefault);*/
			try (Connection conn1 = DB.getConnection(); PreparedStatement preparedStatement1 = conn1.prepareStatement(sqlDefault);) {
				/*System.out.println("entered");*/
				rs2 = preparedStatement1.executeQuery();
		/*		System.out.println("after rs2");*/
				while (rs2.next()) {
					damageCardExist = true;
			/*		System.out.println("11");
					System.out.println("Nope");*/
					cardbox.setBox_group(rs2.getString("box_group"));
					cardbox.setCard_serial_no(serialNoRange);
					cardbox.setBox_type(rs2.getString("box_type"));
					cardbox.setEnteredCardSerialNo(card_serial_no);
					cardbox.setStatus(rs2.getInt("status"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("cardbox:::::::"+cardbox);
		return new Gson().toJson(cardbox);
	}

	public String convertedSerialNo(int enteredSerialNo) {
		int remainder = enteredSerialNo%200 ;
		int min = enteredSerialNo-199;
		int maxaddtion = 0;
		if(remainder!=0) {
			min = (enteredSerialNo - remainder) + 1 ;
			maxaddtion = 200-remainder;
		}
		int max = (enteredSerialNo + maxaddtion)  ;
		return min+" - "+max;
	}
	
	public Integer getBoxidByserialNo(int enteredSerialNo) {
		int remainder = enteredSerialNo%200 ;
		int min = (enteredSerialNo - remainder) + 1 ;
	    int box_id = ((min - 1010001)/200) + 1 ;
		
		return box_id;
	}

}
