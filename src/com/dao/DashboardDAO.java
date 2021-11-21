package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beans.Shipment;

import org.apache.log4j.Logger;

public class DashboardDAO {
	
	
	static Logger logger = Logger.getLogger(SummaryDAO.class);
	
	
	/**
	 * Get Shipment List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<Shipment> getShipment() throws SQLException {
	
		ArrayList<Shipment> shipments = new ArrayList<Shipment>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Shipment shipment;
		
		String sql = "SELECT carton_id,shipment FROM card_carton GROUP BY shipment";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		/*	 System.out.println("-----------    "+ rs.getFetchSize());*/
			while (rs.next()) {
				shipment = new Shipment();
			
				shipment.setCarton_id(rs.getInt("carton_id"));
				shipment.setShipment(rs.getString("shipment"));
		
				shipments.add(shipment);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<Cartonbox> getShipment() method: " + e);
		} finally {
			conn.close();
		}
/*		System.out.println("-------------------------------shipment ---------------- "+new Gson().toJson(shipments));*/
		return shipments;
	}
	
	/**
	 * Get card boxes counts
	 * 
	 * @param shipment
	 * @return
	 * @throws SQLException
	 */
	public int getCardboxesCountsByShipment(String Shipment) throws SQLException {
		int totalCount = 0;
		
		String sql = "SELECT COUNT(*)  FROM card_box WHERE card_box.carton_id IN (SELECT card_carton.carton_id FROM card_carton WHERE shipment = '"+Shipment +"' )";

		System.out.println("----------------- sql tatal count -----------: "+sql);

		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			totalCount = rs.getInt(1);
	/*		System.out.println("return count : "+totalCount);*/
		} catch (SQLException e) {

			logger.error("Error on int getCardboxesCountsByShipment(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		return totalCount;
	}
	
	/**
	 * Get New boxes counts
	 * 
	 * @param shipment
	 * @return
	 * @throws SQLException
	 */
	public int getNewboxesCountsByShipment(String Shipment) throws SQLException {
		int newBoxesCount = 0;
		
		String sql = "SELECT COUNT(*) FROM card_box WHERE card_box.carton_id IN (SELECT card_carton.carton_id FROM card_carton WHERE shipment =  '" +Shipment + "' ) AND card_box.status = 1 ";


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			newBoxesCount = rs.getInt(1);
		} catch (SQLException e) {

			logger.error("Error on int getNewboxesCountsByShipment(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		return newBoxesCount;
	}
	
	/**
	 * Get card boxes counts
	 * 
	 * @param shipment
	 * @return
	 * @throws SQLException
	 */
	public int getPendingboxesCountsByShipment(String Shipment) throws SQLException {
		int pendingBoxesCount = 0;
		
		String sql = "SELECT COUNT(*) FROM card_box WHERE card_box.carton_id IN (SELECT card_carton.carton_id FROM card_carton WHERE shipment =  '" +Shipment + "' ) AND card_box.status = 2 ";


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			pendingBoxesCount = rs.getInt(1);
		} catch (SQLException e) {

			logger.error("Error on int getPendingboxesCountsByShipment(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		return pendingBoxesCount;
	}
	
	/**
	 * Get card boxes counts
	 * 
	 * @param shipment
	 * @return counet
	 * @throws SQLException
	 */
	public int getDeliveredboxesCountsByShipment(String shipment) throws SQLException {
		int deliveredBoxesCount = 0;
		
		String sql = "SELECT COUNT(*) FROM card_box WHERE card_box.carton_id IN (SELECT card_carton.carton_id FROM card_carton WHERE shipment =  '" +shipment + "' ) AND card_box.status = 3 ";


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			deliveredBoxesCount = rs.getInt(1);
		} catch (SQLException e) {

			logger.error("Error on getDeliveredboxesCountsByShipment(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		return deliveredBoxesCount;
	}
	
	
	/**
	 * Get Applicant delivered card box counts by school
	 * 
	 * @param shipment
	 * @return box_count, school_name
	 * @throws SQLException
	 */
	
	public Map<String, String> getdeliveredBoxCountbySchool(String shipment) throws SQLException {
	   
		Map<String, String> count = new HashMap<String, String>();

		List<Integer> countList = new ArrayList<Integer>();
		List<String> schoolList = new ArrayList<String>();

		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;

		String sql = "SELECT COUNT(box_info.delivered_cards) box_count, schools.name FROM box_info "
				+ "LEFT JOIN card_box ON card_box.box_id = box_info.box_id "
				+ "LEFT JOIN schools ON schools.id = card_box.school_id WHERE card_box.carton_id IN (SELECT card_carton.carton_id FROM card_carton WHERE shipment =  '" + shipment + "') AND card_box.status = 3 "
				+ "GROUP BY school_id ";

		try {

			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				countList.add(rs.getInt("box_count"));
				schoolList.add(rs.getString("schools.name"));
			  }

		} catch (SQLException e) {
			System.out.print("Error on getNumApplicantsPerSchoolForChart method: " + e);
		} finally {
			conn.close();
		}

		count.put("countList", countList.toString());
		count.put("schoolList", schoolList.toString());

		System.out.println("------------------count----"+count.get("countList"));
		System.out.println("------------------schoolList----"+count.get("schoolList"));
		return count;
	}
	
	
	
	
	/**
	 * Get Damaged Cards Count
	 * 
	 * @param shipment
	 * @return
	 * @throws SQLException
	 */
	public int getDamagedCardsCount(String shipment) throws SQLException {
		int damagedCardCount = 0;
		
		String sql = "SELECT COUNT(*) FROM damaged_cards db" + 
				     " LEFT JOIN card_box cb ON cb.box_id = db.box_id"+ 
				     " LEFT JOIN card_carton cc ON cc.carton_id = cb.carton_id WHERE shipment =  '" + shipment + "' ";


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			damagedCardCount = rs.getInt(1);
		} catch (SQLException e) {

			logger.error("Error on int getDamagedCardsCount(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		return damagedCardCount;
	}
	
	
	/**
	 * Get Returned Cards Count
	 * 
	 * @param shipment
	 * @return
	 * @throws SQLException
	 */
	public int getReturnedCardsCount(String shipment) throws SQLException {
		int returnedCardCount = 0;
		
		String sql = "SELECT COUNT(*) FROM returned_cards rc" + 
				     " LEFT JOIN schools sc ON sc.id = rc.school_id"+ 
				     " LEFT JOIN card_box cb ON cb.box_id = rc.box_id"+ 
				     " LEFT JOIN card_carton cc ON cc.carton_id = cb.carton_id WHERE shipment = '" + shipment + "' ";


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			returnedCardCount = rs.getInt(1);
		} catch (SQLException e) {

			logger.error("Error on int getReturnedCardsCount(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		return returnedCardCount;
	}
	
	/**
	 * Get Ready Cards Count
	 * 
	 * @param shipment
	 * @return
	 * @throws SQLException
	 */
	public int getReadyCardsCount(String shipment) throws SQLException {
		int readyCardCount = 0;
		
		String sql = "SELECT SUM(card_ready_count) FROM card_box cb" + 
				     " LEFT JOIN schools sc ON cb.school_id = sc.id"+ 
				     " LEFT JOIN card_carton cc ON cb.carton_id = cc.carton_id WHERE cb.status = 2 AND shipment = '" + shipment + "' ";


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			readyCardCount = rs.getInt(1);
		} catch (SQLException e) {

			logger.error("Error on int getReadyCardsCount(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		return readyCardCount;
	}
	
	/**
	 * Get Delivered Cards Count
	 * 
	 * @param shipment
	 * @return
	 * @throws SQLException
	 */
	public int getDeliveredCardsCount(String shipment) throws SQLException {
		int deliveredCardCount = 0;
		
		String sql = "SELECT SUM(card_ready_count) FROM card_box cb" + 
				     " LEFT JOIN schools sc ON cb.school_id = sc.id"+ 
				     " LEFT JOIN card_carton cc ON cb.carton_id = cc.carton_id"+
				     " LEFT JOIN box_info bi ON cb.box_id = bi.box_id WHERE cb.status = 3 AND shipment = '" + shipment + "' ";


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			deliveredCardCount = rs.getInt(1);
		} catch (SQLException e) {

			logger.error("Error on int getDeliveredCardsCount(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		return deliveredCardCount;
	}
	
	/**
	 * Get New Cards Count
	 * 
	 * @param shipment
	 * @return
	 * @throws SQLException
	 */
	public int getNewCardsCount(String shipment) throws SQLException {
		int newCardCount = 0;
		
		String sql = " SELECT SUM(total_cards) FROM card_box WHERE card_box.carton_id IN "
				+ " (SELECT card_carton.carton_id FROM card_carton "
				+ " WHERE shipment =  '" + shipment + "' ) AND card_box.status = 1;";


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			newCardCount = rs.getInt(1);
		} catch (SQLException e) {

			logger.error("Error on int getNewCardsCount(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		return newCardCount;
	}
	
}