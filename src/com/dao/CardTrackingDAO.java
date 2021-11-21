package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.beans.DeliveredCardInfo;
import com.beans.DeliveryPerson;
import com.beans.Trainer;
import com.beans.Vehicle;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * @author Nawed Alam
 *
 */
public class CardTrackingDAO {

	static Logger LOGGER = Logger.getLogger(CardTrackingDAO.class);


	public String fetchTrainersByCardStatus(Map<String, String> filters, Integer draw, Integer length, Integer start)
			throws SQLException {

		String trainersCardStatusJSON = "";

		Integer totalTrainersByCardStatus = fetchTrainersByCardStatusCount(filters);

		List<Trainer> trainersByCardStatus = new ArrayList<Trainer>();

		JsonObject jsonOBJ = new JsonObject();
		jsonOBJ.addProperty("draw", draw);

		String sql = " select trainer.id, trainer.name, school.name AS school, isCardPrinted, tags.delivery_status, tags.id AS tagId from drivingq_school.trainer"
				+ " LEFT JOIN tags ON tags.status=1 AND trainer.id=tags.user_id AND tags.user_type='T'"
				+ " left join drivingq_school.school on trainer.sch_id = school.id"
				+ " where isActive=1 and isApproved = 1 ";

		if (filters.size() > 0) {

			if (filters.get("search_filter") != null) {
				if (filters.get("search_filter").matches("[0-9]+")) {
					sql += " and trainer.id LIKE '%" + filters.get("search_filter") + "%'";
				} else {
					sql += " and trainer.name LIKE '%" + filters.get("search_filter") + "%'";
				}
			}
		}
		sql += " ORDER BY trainer.id ASC LIMIT " + length + " OFFSET " + start;

		try (Connection connection = DB.getDataBaseConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			final ResultSet rs = preparedStatement.executeQuery();

//			Process the ResultSet object.
			while (rs.next()) {
				final Long id = rs.getLong("id");
				String name = rs.getString("name");
				String school = rs.getString("school");
				String isCardPrinted = rs.getString("isCardPrinted");
				String delivery_status = rs.getString("delivery_status");
				String tagId = rs.getString("tagId");
				trainersByCardStatus.add(new Trainer(id, name, school, isCardPrinted, delivery_status, tagId));
			}
			jsonOBJ.addProperty("recordsTotal", totalTrainersByCardStatus);
			jsonOBJ.addProperty("recordsFiltered", totalTrainersByCardStatus);
			jsonOBJ.addProperty("data", new Gson().toJson(trainersByCardStatus));

			trainersCardStatusJSON = new Gson().toJson(jsonOBJ);

		} catch (SQLException e) {
			LOGGER.error("Error on String fetchTrainersByCardStatus method: " + e);
			printSQLException(e);
		}
		return trainersCardStatusJSON;
	}

	private static Integer fetchTrainersByCardStatusCount(Map<String, String> filters) {
		Integer totalTrainersByCardStatus = 0;

		String sql = "select COUNT(*) from drivingq_school.trainer"
				+ " LEFT JOIN tags ON tags.status=1 AND trainer.id=tags.user_id AND tags.user_type='T'"
				+ " left join drivingq_school.school on trainer.sch_id = school.id"
				+ " where isActive=1 and isApproved = 1 ";

		if (filters.get("search_filter") != null) {
			if (filters.get("search_filter").matches("[0-9]+")) {
				sql += " AND trainer.id LIKE '%" + filters.get("search_filter") + "%'";
			} else {
				sql += " AND trainer.name LIKE '%" + filters.get("search_filter") + "%'";
			}
		}

		try (Connection connection = DB.getDataBaseConnection(); Statement statement = connection.createStatement();) {

			final ResultSet rs = statement.executeQuery(sql);
			rs.next();
			totalTrainersByCardStatus = rs.getInt(1);

		} catch (SQLException e) {
			LOGGER.error("Error on Integer fetchTrainersByCardStatusCount method: " + e);
			printSQLException(e);
		}

		return totalTrainersByCardStatus;
	}

	public String fetchVehiclesByCardStatus(Map<String, String> filters, Integer draw, Integer length, Integer start)
			throws SQLException {
		String vehiclesCardStatusJSON = "";

		Integer totalVehiclesByCardStatus = fetchVehiclesByCardStatusCount(filters);

		List<Vehicle> vehiclesByCardStatus = new ArrayList<Vehicle>();

		JsonObject jsonOBJ = new JsonObject();
		jsonOBJ.addProperty("draw", draw);

		String sql = "SELECT vehicle.Id, vehicle.plate_no, vehicle_type,school.name AS school, isCardPrinted, delivery_status, tags.id AS tagId FROM drivingq_school.vehicle"
				+ " LEFT JOIN tags ON tags.status=1 AND vehicle.plate_no=tags.user_id AND tags.user_type='V' "
				+ "LEFT JOIN school ON  school.id = vehicle.school_id LEFT JOIN vehicle_type ON vehicle_type.id = vehicleTypeId "
				+ "WHERE isActive=1 AND isApproved=1 ";

		if (filters.size() > 0) {

			if (filters.get("search_filter") != null) {
				if (filters.get("search_filter").matches("[0-9]+")) {
					sql += " and vehicle.plate_no LIKE '%" + filters.get("search_filter") + "%'";
				} else {
					sql += " and vehicle_type.vehicle_type LIKE '%" + filters.get("search_filter") + "%'";
				}
			}
		}
		sql += " ORDER BY vehicle.id ASC LIMIT " + length + " OFFSET " + start;
		
		System.out.println("fetchVehiclesByCardStatus " +sql);
		
		try (Connection connection = DB.getDataBaseConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			final ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				final Long id = rs.getLong("Id");
				String name = rs.getString("plate_no");
				String vehicle_type = rs.getString("vehicle_type");
				String school = rs.getString("school");
				String isCardPrinted = rs.getString("isCardPrinted");
				String delivery_status = rs.getString("delivery_status");
				String tagId = rs.getString("tagId");

				vehiclesByCardStatus
						.add(new Vehicle(id, name, vehicle_type, school, isCardPrinted, delivery_status, tagId));
			}

			jsonOBJ.addProperty("recordsTotal", totalVehiclesByCardStatus);
			jsonOBJ.addProperty("recordsFiltered", totalVehiclesByCardStatus);
			jsonOBJ.addProperty("data", new Gson().toJson(vehiclesByCardStatus));

			vehiclesCardStatusJSON = new Gson().toJson(jsonOBJ);
		} catch (SQLException e) {
			LOGGER.error("Error on Integer fetchTrainersByCardStatusCount method: " + e);
			printSQLException(e);
		}

		return vehiclesCardStatusJSON;
	}

	private Integer fetchVehiclesByCardStatusCount(Map<String, String> filters) throws SQLException {

		Integer totalVehiclesByCardStatus = 0;

		String sql = "SELECT COUNT(*) FROM drivingq_school.vehicle"
				+ " LEFT JOIN tags ON tags.status=1 AND vehicle.plate_no=tags.user_id AND tags.user_type='V' "
				+ " LEFT JOIN school ON  school.id = vehicle.school_id LEFT JOIN vehicle_type ON vehicle_type.id = vehicleTypeId "
				+ " WHERE isActive=1 AND isApproved=1 ";
		
		if (filters.get("search_filter") != null) {
			if (filters.get("search_filter").matches("[0-9]+")) {
				sql += " AND vehicle.plate_no LIKE '%" + filters.get("search_filter") + "%'";
			} else {
				sql += " AND vehicle_type.vehicle_type LIKE '%" + filters.get("search_filter") + "%'";
			}
			
		}
		
		System.out.println("Counrt " +sql);
		
		try (Connection connection = DB.getDataBaseConnection(); Statement statement = connection.createStatement();) {
			final ResultSet rs = statement.executeQuery(sql);
			rs.next();
				totalVehiclesByCardStatus = rs.getInt(1);
		} catch (SQLException e) {
			LOGGER.error("Error on Integer fetchVehiclesByCardStatusCount method: " + e);
			printSQLException(e);
		}
		return totalVehiclesByCardStatus;
	}

	public String UpdateTagsStatus(String[] tag_ids) {

		String ids = Arrays.toString(tag_ids).replaceAll("\\[", "").replaceAll("\\]", "");
		Map<String, String> resultMap = new HashMap<String, String>();

		String sql = "UPDATE tags SET delivery_status = 1 WHERE id IN (" + ids + ")";

		try (Connection connection = DB.getDataBaseConnection(); Statement statement = connection.createStatement()) {

			if (statement.executeUpdate(sql) > 0) {
				resultMap.put("title", "success!");
				resultMap.put("type", "success");
				resultMap.put("msg", "Successfully Send Traffic Department");
			} else {
				resultMap.put("title", "error");
				resultMap.put("type", "error");
				resultMap.put("msg", "Failed to Update Send Traffic Department");
			}

		} catch (Exception e) {
			resultMap.put("title", "error");
			resultMap.put("type", "error");
			resultMap.put("msg", "something.went.wrong");
			LOGGER.error("Error on Update Trainers Card Status(String tag_id) method: " + e);
		}
		return new Gson().toJson(resultMap);
	}

//	Common method foe SQL_EXCeption
	private static void printSQLException(final SQLException ex) {
		for (final Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				LOGGER.error("SQLState: " + ((SQLException) e).getSQLState());
				LOGGER.error("Error Code: " + ((SQLException) e).getErrorCode());
				LOGGER.error("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					LOGGER.info("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
	
	/**
	 * Add AddDeliveredPerson Details 
	 * 
	 * @param name, qid, mobile
	 * @return
	 * @throws SQLException
	 */
	public boolean addDeliveryPersonDetails(DeliveryPerson delivery_person) throws SQLException {
		Connection conn = null;
		PreparedStatement statement = null;
		boolean success = false;
		
		try {
		    
			String sql = "INSERT INTO delivery_person(qid,name,mobile) values (?,?,?)";
			System.out.println("SQL--> "+sql);
			conn = DB.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setString(1, delivery_person.getQid());
			statement.setString(2, delivery_person.getName());
			statement.setString(3, delivery_person.getMobile());
			
//			if (delivery_person.getPhoto() != null) {
//
//				InputStream photoInputStream = new ByteArrayInputStream(delivery_person.getPhoto());
//
//				if (photoInputStream != null) {
//					statement.setBinaryStream(4, photoInputStream);
//				}
//
//			} else {
//				statement.setBinaryStream(4, null);
//
//			}
			
			if (statement.executeUpdate() == 1) {
				success = true;
			} else {
				success = false;
			}
			
		}catch (SQLException e) {
			LOGGER.error("Error on boolean AddDeliveredPerson(name, qid, mobile) method: "+ e);
		} finally {
			conn.close();
		}

		return success;
	}

	/**
	 * Check if Delivery person details already exists
	 * 
	 * @param qid
	 * @return boolean[true/false]
	 * @throws SQLException
	 */
	public boolean checkIfQIDExist(String qid) throws SQLException {
		boolean exists = true;
		Connection conn = null;
		Statement stmt = null;
		ResultSet res = null;

		try {
			String sql = "SELECT COUNT(*) FROM delivery_person WHERE qid = " + qid;
			
			conn = DB.getConnection();
			stmt = conn.createStatement();
			res = stmt.executeQuery(sql);

			res.next();

			exists = res.getInt(1) > 0 ? true : false;

		} catch (SQLException e) {
			LOGGER.error("Error on boolean checkIfQIDExist(String qid) method: "+ e);
		} finally {
			conn.close();
		}

		return exists;
	}

	/**
	 * Get list of delivery person's details
	 * 
	 * @return JSON - delivery person's list
	 * @throws SQLException
	 */
	public String getAllDeliveryPersonsList() throws SQLException {
		    
			String personJSON = "";
			List<DeliveryPerson> persons = new ArrayList<DeliveryPerson>();
			DeliveryPerson person = new DeliveryPerson();
			
			String sql = "SELECT id, qid, name, mobile FROM delivery_person ORDER BY id";
			
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;
			
		    try {
		    	conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				while (rs.next()) {
					
					person = new DeliveryPerson();
					
					person.setId(rs.getInt("id"));
					person.setQid(rs.getString("qid"));
					person.setName(rs.getString("name"));
					person.setMobile(rs.getString("mobile"));
					
					persons.add(person);
					
				}
				personJSON = new Gson().toJson(persons);
				
		    } catch (SQLException e) {
				LOGGER.error("Error on String getAllDeliveryPersonsList method: " + e);
			} finally {
				conn.close();
			}
		    
			return personJSON;
	}

	/**
	 * Update specific delivery person's information
	 * 
	 * @param delivry_person details
	 * @param QID
	 * @param langType
	 * @return Message[success/failed]
	 * @throws SQLException
	 */
	public String updateDeliveryPersonDetails(DeliveryPerson delivery_person, String currentQID) throws SQLException {
		
		Connection conn = null;
		PreparedStatement statement = null;

		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			String sql = "UPDATE delivery_person SET qid = ?, name = ?, mobile = ? ";
        
//			if (delivery_person.getPhoto() != null) {
//				sql += ", image = ? ";
//			}

			sql += "WHERE qid = " + currentQID;

			conn = DB.getConnection();
			statement = conn.prepareStatement(sql);

			statement.setString(1, delivery_person.getQid());
			statement.setString(2, delivery_person.getName());
			statement.setString(3, delivery_person.getMobile());

//			if (delivery_person.getPhoto() != null) {
//				InputStream photoInputStream = new ByteArrayInputStream(delivery_person.getPhoto());
//				if (photoInputStream != null) {
//					statement.setBinaryStream(4, photoInputStream);
//				}
//			}
		
			if (statement.executeUpdate() > 0) {
				resultMap.put("title", "Updated Successfully!!");
				resultMap.put("type", "success");
				resultMap.put("msg", "Delivery Person Details Updated Successfully Thank You.");
			} else {
				resultMap.put("title", "Somthing Went Wrong");
				resultMap.put("type", "error");
				resultMap.put("msg", "Delivery Person Details Not Updated!!");
			}

		} catch (SQLException e) {
			resultMap.put("title", "Somthing Went Wrong");
			resultMap.put("type", "error");
			resultMap.put("msg", "Delivery Person Details Not Updated!!");
			e.printStackTrace();
			LOGGER.error("Error on boolean updateDeliveryPersonDetails(DeliveredPerson delivery_person, String qID, String langType) method: "+ e);
		} finally {
			conn.close();
		}

		return new Gson().toJson(resultMap);
	}

	/**
	 * Delete Delivery Person
	 * @param id
	 * @param 
	 * @return [true/false]
	 * @throws SQLException
	 */
	public boolean deleteDeliveryPersonDetail(Integer id) throws SQLException {
		boolean isDone = false;
		Connection conn = DB.getConnection();
		String sql = "DELETE FROM delivery_person WHERE id = " + id;

		try {
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
			System.out.println("Error on boolean deleteDeliveryPersonDetail(int id) method: ");
		} finally {
			conn.close();
		}
		return isDone;
	}
	
	/**
	 * Get list of all Delivery Person
	 * 
	 * @return ArrayList[DeliveryPerson]
	 * @throws SQLException
	 */
	public ArrayList<DeliveryPerson> getAllDeliveryPerson() throws SQLException {

		ArrayList<DeliveryPerson> arrList = new ArrayList<DeliveryPerson>();

		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		DeliveryPerson DeliveryPerson;

		String sql = "SELECT id, qid, name FROM delivery_person ORDER BY id";

		try {

			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				DeliveryPerson = new DeliveryPerson();
				DeliveryPerson.setId(rs.getInt(1));
				DeliveryPerson.setQid(rs.getString(2));
				DeliveryPerson.setName(rs.getString(3));
				arrList.add(DeliveryPerson);
			}
		} catch (SQLException e) {
			LOGGER.error("Error on ArrayList<DeliveryPerson> getAllDeliveryPerson() method: " + e);
		} finally {
			conn.close();
		}

		return arrList;

	}

	/**
	 * Add Add Delivered Card Info Details 
	 * 
	 * @param delivery_person_id, card_type, card_no[] , date,delivered_id
	 * @return
	 * @throws SQLException
	 */
	public boolean addDeliveredCardInfo(DeliveredCardInfo delivery_card_info) throws SQLException {
		
		Connection conn = null;
		PreparedStatement statement = null;
		Statement stm = null;
		ResultSet rs = null;
		boolean success = false;
		
		try {
			int delivered_id = 0;
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			String sqlGetLastId = "SELECT max(delivered_id) as max_value FROM card_delivered_report";
			stm = conn.createStatement();
			rs = stm.executeQuery(sqlGetLastId);
			if(rs.next()) {
				delivered_id = rs.getInt("max_value");
			}
			delivered_id ++;
			List<String> card_idList = delivery_card_info.getCard_no();
			boolean error = false;
			String sql = "INSERT INTO card_delivered_report (delivery_person_id, card_type, card_id, delivered_date, delivered_id) VALUES (?,?,?,?,?)";
			
			statement = conn.prepareStatement(sql);
			for(String cardId : card_idList) {
				statement.setInt(1, delivery_card_info.getDelivery_person_id());
				statement.setString(2, delivery_card_info.getCard_type());
				statement.setString(3, cardId);
				statement.setString(4, delivery_card_info.getDate());
				statement.setInt(5, delivered_id);
				if (statement.executeUpdate() < 1) {
					error = true;
					conn.rollback();
					return false;
				} 
			}
			if(!error) {
				conn.commit();
				return true;
			}
		}catch (SQLException e) {
			LOGGER.error("Error on boolean addDeliveredCardInfo(delivery_card_info) method: "+ e);
		} finally {
			conn.close();
		}
		return success;
	}

	/**
	 * Get list of delivered cards
	 * 
	 * @param filters
	 * @param draw
	 * @param length
	 * @param start
	 * @return JSON - delivered cards list
	 * @throws SQLException
	 */
	public String fetchDeliveredCardReport(Map<String, String> filters, Integer draw, Integer length, Integer start) throws SQLException {
		
		String deliveredCardsJSON = "";
		Integer totalCardDelivery = fetchDeliveredCardReportCount(filters);
		
		List<DeliveredCardInfo> cardDeliveredReportList = new ArrayList<DeliveredCardInfo>();
		DeliveredCardInfo cardDelivererdReport = new DeliveredCardInfo();

		JsonObject jsonOBJ = new JsonObject();
		jsonOBJ.addProperty("draw", draw);
		
		String sql = "SELECT delivered_id, dp.name, dp.qid, dp.mobile, card_type, delivered_date, COUNT(delivered_id) AS card_count  FROM card_delivered_report "
				+ "LEFT JOIN delivery_person dp ON dp.id = card_delivered_report.delivery_person_id WHERE 1=1 ";
		
		if (filters.size() > 0) {
	
			if (filters.get("card_type") != null) {
				sql += " AND card_type = '" + filters.get("card_type") + "'";
			} 
			
			if (filters.get("delivery_person") != null) {
				sql += " AND qid = '" + filters.get("delivery_person") + "'";
			} 
			
			if (filters.get("delivered_from_date") != null) {
				sql += " AND (DATE(delivered_date) BETWEEN '" + filters.get("delivered_from_date") + "' AND '" + filters.get("delivered_to_date") + "')";
			}
			
			if (filters.get("search_filter") != null) {
				if (filters.get("search_filter").matches("[0-9]+")) {
					sql += " AND qid LIKE '%" + filters.get("search_filter") + "%'";
				} else {
					sql += " AND name LIKE '%" + filters.get("search_filter") + "%'";
				}
			}
		}
		
		sql += " GROUP BY delivered_id"
			+  " ORDER BY delivered_date DESC LIMIT " + length + " OFFSET " + start;
		
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				cardDelivererdReport = new DeliveredCardInfo();

				cardDelivererdReport.setId(rs.getInt("delivered_id"));
				cardDelivererdReport.setName(rs.getString("name"));
				cardDelivererdReport.setQid(rs.getString("qid"));
				cardDelivererdReport.setMobile(rs.getString("mobile"));
				cardDelivererdReport.setCard_type(rs.getString("card_type"));
				cardDelivererdReport.setDate(rs.getString("delivered_date"));
				cardDelivererdReport.setCard_count(rs.getInt("card_count"));
				
				cardDeliveredReportList.add(cardDelivererdReport);
			}

			jsonOBJ.addProperty("recordsTotal", totalCardDelivery);
			jsonOBJ.addProperty("recordsFiltered", totalCardDelivery);
			jsonOBJ.addProperty("data", new Gson().toJson(cardDeliveredReportList));

			deliveredCardsJSON = new Gson().toJson(jsonOBJ);

		} catch (SQLException e) {
			LOGGER.error("Error on String fetchDeliveredCardReport method: ", e);
		} finally {
			conn.close();
		}

		return deliveredCardsJSON;
	}

	/**
	 * Get count of Delivered Report to filter
	 * 
	 * @param schoolId
	 * @param filters
	 * @return Integer[count]
	 * @throws SQLException
	 */
	private Integer fetchDeliveredCardReportCount(Map<String, String> filters) throws SQLException {
        Integer totalDeliveredReport = 0;
		
		String sql = "SELECT COUNT(distinct(delivered_id)) FROM card_delivered_report "
				   + "LEFT JOIN delivery_person dp ON dp.id = card_delivered_report.delivery_person_id WHERE 1=1 ";
	
			if (filters.size() > 0) {
				
				if (filters.get("card_type") != null) {
					sql += " AND card_type = '" + filters.get("card_type") + "'";
				} 
				
				if (filters.get("delivery_person") != null) {
					sql += " AND qid = '" + filters.get("delivery_person") + "'";
				} 
				
				if (filters.get("delivered_from_date") != null) {
					sql += " AND (DATE(delivered_date) BETWEEN '" + filters.get("delivered_from_date") + "' AND '" + filters.get("delivered_to_date") + "')";
				}
				
				if (filters.get("search_filter") != null) {
					if (filters.get("search_filter").matches("[0-9]+")) {
						sql += " AND qid LIKE '%" + filters.get("search_filter") + "%'";
					} else {
						sql += " AND name LIKE '%" + filters.get("search_filter") + "%'";
					}
				}
			}
		
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;
			try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				rs.next();
	
				totalDeliveredReport = rs.getInt(1);
	
			} catch (SQLException e) {
				LOGGER.error("Error on Integer fetchDeliveredCardReportCount method: ", e);
			} finally {
				conn.close();
			}
	
			return totalDeliveredReport;
	}

	/**
	 * Get Delivered Card List for the Report
	 * 
	 * @param id
	 * @return Json data
	 * @throws SQLException
	 */
	public static String getDeliveredCardListByDeliveryId(Integer delivery_id) throws SQLException {
		
		ArrayList<DeliveredCardInfo> delivered_cards = new ArrayList<DeliveredCardInfo>();
		String cardsJSON = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		DeliveredCardInfo delivery_card;
		
		String sql = "SELECT id, card_id FROM card_delivered_report WHERE delivered_id = "+ delivery_id;
		
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
			 
		while (rs.next()) {
			delivery_card = new DeliveredCardInfo();
			  
			delivery_card.setId(rs.getInt("id"));
			delivery_card.setCard_id(rs.getString("card_id"));
			  
			delivered_cards.add(delivery_card);
			  
		  }
         }catch (SQLException e) {
			 
			 System.out.println("Error on String getDeliveredCardListByDeliveryId(Integer delivery_id) method: " + e.getLocalizedMessage());
			 e.printStackTrace();
		 } finally {
			 conn.close();
		 }
		
		 cardsJSON = new Gson().toJson(delivered_cards);
		 
		 return cardsJSON;
		 
	}
}
