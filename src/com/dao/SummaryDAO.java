package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.beans.Shipment;
import com.beans.Damagedcard;
import com.beans.Returnedcard;
import com.beans.Summary;
import com.google.gson.Gson;

import org.apache.log4j.Logger;

public class SummaryDAO {
	
	
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
	 * @return
	 * @throws SQLException
	 */
	public int getDeliveredboxesCountsByShipment(String Shipment) throws SQLException {
		int deliveredBoxesCount = 0;
		
		String sql = "SELECT COUNT(*) FROM card_box WHERE card_box.carton_id IN (SELECT card_carton.carton_id FROM card_carton WHERE shipment =  '" +Shipment + "' ) AND card_box.status = 3 ";


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
	 * Get getDamagedCards List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<Damagedcard> getDamagedCards() throws SQLException {
	
		ArrayList<Damagedcard> damagedcards = new ArrayList<Damagedcard>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Damagedcard damagedcard;
		
		String sql = "SELECT cc.shipment, cc.carton_group, box_group, cb.card_serial_no as box_serial_no, db.card_serial_no, db.reason FROM damaged_cards db" + 
				" LEFT JOIN card_box cb ON cb.box_id = db.box_id"+ 
				" LEFT JOIN card_carton cc ON cc.carton_id = cb.carton_id";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		/*	 System.out.println("-----------    "+ rs.getFetchSize());*/
			while (rs.next()) {
				damagedcard = new Damagedcard();
				
				damagedcard.setShipment(rs.getString("shipment"));
				damagedcard.setCarton_group(rs.getString("carton_group"));
				damagedcard.setBox_group(rs.getString("box_group"));
				damagedcard.setBox_serial_no(rs.getString("box_serial_no"));
				damagedcard.setCard_serial_no(rs.getString("card_serial_no"));
				damagedcard.setReason(rs.getString("reason"));
		
				damagedcards.add(damagedcard);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<Damagedcard> getDamagedCards() method: " + e);
		} finally {
			conn.close();
		}
	/*	System.out.println("-------------------------------Damaged Cards ---------------- "+new Gson().toJson(damagedcards));*/
		return damagedcards;
	}
	
	/**
	 * Get get Returned card List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<Returnedcard> getReturnedCards() throws SQLException {
	
		ArrayList<Returnedcard> returnedcards = new ArrayList<Returnedcard>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Returnedcard returnedcard;
		
		String sql = " SELECT cc.shipment, cc.carton_group, box_group, cb.card_serial_no as box_serial_no, rc.card_serial_no, sc.name as school, rc.reason, rc.recieved_by FROM returned_cards rc"
				+ " LEFT JOIN schools sc ON sc.id = rc.school_id"
				+ " LEFT JOIN card_box cb ON cb.box_id = rc.box_id"
				+ " LEFT JOIN card_carton cc ON cc.carton_id = cb.carton_id";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		/*	 System.out.println("-----------    "+ rs.getFetchSize());*/
			while (rs.next()) {
				returnedcard = new Returnedcard();
				
				returnedcard.setShipment(rs.getString("shipment"));
				returnedcard.setCarton_group(rs.getString("carton_group"));
				returnedcard.setBox_group(rs.getString("box_group"));
				returnedcard.setBox_serial_no(rs.getString("box_serial_no"));
				returnedcard.setCard_serial_no(rs.getString("card_serial_no"));
				returnedcard.setReason(rs.getString("reason"));
				returnedcard.setRecived_by(rs.getString("recieved_by"));
				returnedcard.setSchool_name(rs.getString("school"));
		
				returnedcards.add(returnedcard);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<Returnedcard> getReturnedCards() method: " + e);
		} finally {
			conn.close();
		}
		/*System.out.println("-------------------------------Returned Cards ---------------- "+new Gson().toJson(returnedcards));*/
		return returnedcards;
	}
	
	
	/**
	 * Get ready to deliver card boxes List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<Summary> getReadyToDeliverCardBoxes() throws SQLException {
	
		ArrayList<Summary> ready_to_deliver_cards = new ArrayList<Summary>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Summary ready_to_deliver_card;
		
		String sql = "SELECT cc.shipment, box_group, completed_date, card_serial_no, printed_date, note, card_ready_count, name as school, carton_group, updated_at FROM card_box cb"
				+ " LEFT JOIN schools sc ON cb.school_id = sc.id"
				+ " LEFT JOIN card_carton cc ON cb.carton_id = cc.carton_id WHERE cb.status = 2";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		/*	 System.out.println("-----------    "+ rs.getFetchSize());*/
			while (rs.next()) {
				ready_to_deliver_card = new Summary();
			
				ready_to_deliver_card.setShipment(rs.getString("shipment"));
				ready_to_deliver_card.setBoxGroup(rs.getString("box_group"));
				ready_to_deliver_card.setCompletedDate(rs.getString("completed_date"));
				ready_to_deliver_card.setCardSerialNo(rs.getString("card_serial_no"));
				ready_to_deliver_card.setPrintedDate(rs.getString("printed_date"));
				ready_to_deliver_card.setNote(rs.getString("note"));
				ready_to_deliver_card.setCardReadyCount(rs.getInt("card_ready_count"));
				ready_to_deliver_card.setSchool(rs.getString("school"));
				ready_to_deliver_card.setCartonGroup(rs.getString("carton_group"));
				ready_to_deliver_card.setStockOutDate(rs.getString("updated_at"));
		
				ready_to_deliver_cards.add(ready_to_deliver_card);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<Summary> getReadyToDeliverCards() method: " + e);
		} finally {
			conn.close();
		}
/*		System.out.println("-------------------------------Ready To Delivery ---------------- "+new Gson().toJson(ready_to_deliver_cards));*/
		return ready_to_deliver_cards;
	}
	
	/**
	 * Get delivered card boxes List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<Summary> getDeliveredCardBoxes() throws SQLException {
	
		ArrayList<Summary> delivered_cards = new ArrayList<Summary>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Summary delivered_card;
		
		String sql = "SELECT cc.shipment, box_group, completed_date, card_serial_no, printed_date, note, card_ready_count, name as school, carton_group, updated_at, delivered_date, delivered_person FROM card_box cb"
				+ " LEFT JOIN schools sc ON cb.school_id = sc.id"
				+ " LEFT JOIN card_carton cc ON cb.carton_id = cc.carton_id"
				+ " LEFT JOIN box_info bi ON cb.box_id = bi.box_id WHERE cb.status = 3 order by delivered_date desc";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		/*	 System.out.println("-----------    "+ rs.getFetchSize());*/
			while (rs.next()) {
				delivered_card = new Summary();
			
				delivered_card.setShipment(rs.getString("shipment"));
				delivered_card.setBoxGroup(rs.getString("box_group"));
				delivered_card.setCompletedDate(rs.getString("completed_date"));
				delivered_card.setCardSerialNo(rs.getString("card_serial_no"));
				delivered_card.setPrintedDate(rs.getString("printed_date"));
				delivered_card.setNote(rs.getString("note"));
				delivered_card.setCardReadyCount(rs.getInt("card_ready_count"));
				delivered_card.setSchool(rs.getString("school"));
				delivered_card.setCartonGroup(rs.getString("carton_group"));
				delivered_card.setStockOutDate(rs.getString("updated_at"));
				delivered_card.setDeliveredDate(rs.getString("delivered_Date"));
				delivered_card.setDeliveredPerson(rs.getString("delivered_person"));
		
				delivered_cards.add(delivered_card);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<Summary> getDeliveredCardBoxes() method: " + e);
		} finally {
			conn.close();
		}
		/*System.out.println("-------------------------------Delivered ---------------- "+new Gson().toJson(delivered_cards));*/
		return delivered_cards;
	}
	
	
	/**
	 * Get New boxes counts By Carton Group
	 * 
	 * @param carton_id
	 * @return
	 * @throws SQLException
	 */
	public int getNewboxesCountsByGroup(Integer carton_id) throws SQLException {
		int newBoxesCount = 0;
		
		String sql = "SELECT COUNT(*) FROM card_box WHERE card_box.carton_id IN (SELECT card_carton.carton_id FROM card_carton WHERE carton_id =  '" +carton_id + "' ) AND card_box.status = 1 ";


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			newBoxesCount = rs.getInt(1);
		} catch (SQLException e) {

			logger.error("Error on int getNewboxesCountsByGroup(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		System.out.println("newBoxesCount by Group  :"+newBoxesCount);
		return newBoxesCount;
	}
	
	/**
	 * Get Ready boxes counts By Carton Group
	 * 
	 * @param cartion_id
	 * @return
	 * @throws SQLException
	 */
	public int getReadyBoxesCountsByGroup(Integer carton_id) throws SQLException {
		int readyBoxesCount = 0;
		
		String sql = "SELECT COUNT(*) FROM card_box WHERE card_box.carton_id IN (SELECT card_carton.carton_id FROM card_carton WHERE carton_id =  '" +carton_id + "' ) AND card_box.status = 2 ";


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			readyBoxesCount = rs.getInt(1);
		} catch (SQLException e) {

			logger.error("Error on int getReadyBoxesCountsByGroup(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		System.out.println("readyBoxesCount by Group  :"+readyBoxesCount);
		return readyBoxesCount;
	}
	
	/**
	 * Get Delivered boxes counts By Carton Group
	 * 
	 * @param carton_id
	 * @return
	 * @throws SQLException
	 */
	public int getDeliveredBoxesCountsByGroup(Integer carton_id) throws SQLException {
		int deliveredBoxesCount = 0;
		
		String sql = "SELECT COUNT(*) FROM card_box WHERE card_box.carton_id IN (SELECT card_carton.carton_id FROM card_carton WHERE carton_id =  '" +carton_id + "' ) AND card_box.status = 3 ";


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			deliveredBoxesCount = rs.getInt(1);
		} catch (SQLException e) {

			logger.error("Error on int getDeliveredBoxesCountsByGroup(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		System.out.println("deliveredBoxesCount by Group  :"+deliveredBoxesCount);
		return deliveredBoxesCount;
	}
	
	
}
