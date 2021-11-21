package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;

import com.beans.*;
import com.google.gson.Gson;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.CollectionUtils;

/**
 * @author ihsan
 */
public class CardboxDAO {
	
	static Logger logger = Logger.getLogger(CardboxDAO.class);
	

	private Connection conn = null;
	private Statement stm = null;
	private ResultSet res = null;
	
	/**
	 * Add new carton box
	 * 
	 * @param carton_no
	 * @return boolean[true/false]
	 * @throws SQLException
	 */
	public boolean addCartonbox(int carton_no, int no_of_boxes, String Shipment, String ordered_date, String stock_in_date) throws SQLException {
		Connection conn = DB.getConnection();
		boolean isDone = false;
        
		String sql = "INSERT INTO card_carton (carton_no, carton_group, stock_in_date, updated_at, total_boxes, total_cards, status, shipment, ordered_date) VALUES (?,?,?,now(),?,?,?,?,?)";
		  try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
		  int total_cards = carton_no*no_of_boxes;	  
          // Insert records
          for (int i = 1; i <= carton_no; i++) {
             stmt.setInt(1, carton_no);
             stmt.setString(2, i+"/"+carton_no);
             stmt.setString(3, stock_in_date);
             stmt.setInt(4, no_of_boxes);
             stmt.setInt(5, total_cards);
             stmt.setInt(6, 1);
             stmt.setString(7, Shipment);
             stmt.setString(8, ordered_date);
             //Add statement to batch
             stmt.addBatch();
          }
          //execute batch
          stmt.executeBatch();
          
          ResultSet gen_keys = stmt.getGeneratedKeys();
          Integer gen_key = 0;
          Integer box_group_num = 1;
          while(gen_keys.next()) {
        
        	  gen_key = gen_keys.getInt(1);
        	  insertCardBoxDetail(gen_key,carton_no, box_group_num,no_of_boxes);
        	  box_group_num+=no_of_boxes;
          }
          
          isDone = true;
          
       } catch (SQLException e) {
          e.printStackTrace();
          if (conn != null) {
             try {
                conn.rollback();
             } catch (Exception ex) {
                ex.printStackTrace();
             }
          }
       } catch (Exception e) {
			logger.error("Error on boolean addCatonbox(int box_no) method: ", e);
		} finally {
			conn.close();
		}

		return isDone;
	}
	
	/**
	 * Add card box details
	 * 
	 * @param gen_key, carton_no, box_group_num
	 * @return boolean[true/false]
	 * @throws SQLException
	 */
	public void insertCardBoxDetail(Integer gen_key, Integer carton_no, Integer box_group_num, int no_of_boxes) throws SQLException {
		Connection conn = DB.getConnection();
		
		String cardbox_sql = "INSERT INTO card_box (carton_id, box_no, box_group, completed_date, total_cards, card_serial_no, printed_date, school_id, status)"
				           + " VALUES ('"+gen_key+"',?,?,null,?,?,now(),null,?)";
		
		try {
			 
		PreparedStatement stmt = conn.prepareStatement(cardbox_sql);;
		
		int box_no = carton_no*no_of_boxes;		
		
        // Insert records
		    int intialValue = getInitialValue();
            for (int i = 1; i <= no_of_boxes; i++) {
            	
        		int fixed_value = intialValue;
            	int min = ((box_group_num-1)*box_no) + fixed_value +1;
            	int max = (box_group_num*box_no) + fixed_value;
            	String serial_no = min +" - "+ max;  
          
	           stmt.setInt(1, box_no);
	           stmt.setString(2, box_group_num+"/"+box_no);
	           stmt.setInt(3, box_no);
	           stmt.setString(4, serial_no);
	           stmt.setInt(5, 1);
	           //Add statement to batch
	           stmt.addBatch();
	           box_group_num++;
	           
	         }
       
        //execute batch
        stmt.executeBatch();
        
		}catch(SQLException e){
			System.out.println("Error on insertCardBoxDetail(Integer gen_key, Integer carton_no, Integer box_group_num) "+ e);
		} finally {
			conn.close();
		}
	}
	
	/**
	 * Get carton box by status
	 * 
	 * @param carton_id
	 * @return Array
	 * @throws SQLException
	 */
	public ArrayList<Cartonbox> getCartonboxByStatus(String shipmentId) throws Exception {
			ArrayList<Cartonbox> arrList = new ArrayList<Cartonbox>();
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;
			Cartonbox cartonboxByStatus;

			try {
				String sql = "SELECT * FROM card_carton WHERE card_carton.status = 1 and shipment = '" + shipmentId +"'";
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				while (rs.next()) {
					  cartonboxByStatus = new Cartonbox();
					  cartonboxByStatus.setCarton_id(rs.getInt("carton_id"));
					  cartonboxByStatus.setCartonNo(rs.getInt("carton_no"));
					  cartonboxByStatus.setCartonGroup(rs.getString("carton_group"));
					  cartonboxByStatus.setStockinDate(rs.getString("stock_in_date"));
					  cartonboxByStatus.setShipment(rs.getString("shipment"));
					  cartonboxByStatus.setNew_box_count(getCountNewBoxBycartonId(rs.getInt("carton_id")));
					  cartonboxByStatus.setPending_box_count(getCountPendingBoxBycartonId(rs.getInt("carton_id")));
					  cartonboxByStatus.setDeliveredCount(getCountDeliveredBoxBycartonId(rs.getInt("carton_id")));
					arrList.add(cartonboxByStatus);
					
				}
			} catch (SQLException e) {

				logger.error("Error on ArrayList<Cartonbox> getCartonboxByStatus() ", e);
			} finally {
				conn.close();
			}
			 
			return arrList;
		
	}
	
	
	/**
	 * Get count of pending box by carton id
	 * 
	 * @param carton_id
	 * @return Array
	 * @throws SQLException
	 */
	private int getCountNewBoxBycartonId(int carton_id) {
		int new_count = 0;
		
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		

		try {
			String sql = "SELECT COUNT(*) FROM card_box WHERE carton_id="+carton_id+"  AND status=1";
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if(rs.next()) {
				new_count = rs.getInt(1);
			}
		 } catch (SQLException e) {
				} finally {
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		return new_count;
	}
	
	/**
	 * Get count of pending box by carton id
	 * 
	 * @param carton_id
	 * @return Array
	 * @throws SQLException
	 */
	private int getCountPendingBoxBycartonId(int carton_id) {
		int pending_count = 0;
		
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		

		try {
			String sql = "SELECT COUNT(*) FROM card_box WHERE carton_id="+carton_id+"  AND status=2";
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if(rs.next()) {
				pending_count = rs.getInt(1);
			}
		 } catch (SQLException e) {
				} finally {
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		return pending_count;
	}
	
	/**
	 * Get count of Delivered box by carton id
	 * 
	 * @param carton_id
	 * @return Array
	 * @throws SQLException
	 */
	private int getCountDeliveredBoxBycartonId(int carton_id) {
		int delivered_count = 0;

		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		

		try {
			String sql = "SELECT COUNT(*) FROM card_box WHERE carton_id="+carton_id+"  AND status=3";
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if(rs.next()) {
				delivered_count = rs.getInt(1);
			}
		 } catch (SQLException e) {
				} finally {
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		return delivered_count;
	}

	
	/**
	 * Get List of carton box
	 * 
	 * @param carton_id
	 * @return Array
	 * @throws SQLException
	 */
	public ArrayList<Cartonbox> getCartonboxDeatils(Integer carton_id) throws SQLException {
		
		ArrayList<Cartonbox> cartonbox_details = new ArrayList<Cartonbox>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Cartonbox cartonboxBycartonID;
		
		String sql = "SELECT * FROM card_carton  WHERE carton_id = "+carton_id;
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				cartonboxBycartonID = new Cartonbox();
				
				cartonboxBycartonID.setCarton_id(rs.getInt("carton_id"));
				cartonboxBycartonID.setCartonNo(rs.getInt("carton_no"));
				cartonboxBycartonID.setCartonGroup(rs.getString("carton_group"));
				cartonboxBycartonID.setStockinDate(rs.getString("stock_in_date"));
				cartonboxBycartonID.setStockOutDate(rs.getDate("updated_at"));
				cartonboxBycartonID.setTotal_boxes(rs.getInt("total_boxes"));
				cartonboxBycartonID.setTotal_cards(rs.getInt("total_cards"));
				cartonboxBycartonID.setStatus(rs.getInt("status"));
		
				
				cartonbox_details.add(cartonboxBycartonID);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on String getCardboxDeatils method: " + e);
		} finally {
			conn.close();
		}
		return cartonbox_details;
	}
	
	/**
	 * Get cardbox List
	 * 
	 * @param carton_id
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<Cardbox> getCardboxDeatils(Integer carton_id) throws SQLException {
	
		ArrayList<Cardbox> cardbox_details = new ArrayList<Cardbox>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Cardbox cardboxBycartonID;
		
		String sql = "SELECT * FROM card_carton"
				+ " LEFT JOIN card_box ON card_box.carton_id = card_carton.carton_id"
				+ " LEFT JOIN schools ON card_box.school_id = schools.id "
				+ " WHERE card_carton.carton_id = "+carton_id;
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				cardboxBycartonID = new Cardbox();
				
				cardboxBycartonID.setBox_id(rs.getInt("box_id"));
				cardboxBycartonID.setCarton_id(rs.getInt("carton_id"));
				cardboxBycartonID.setBox_no(rs.getInt("box_no"));
				cardboxBycartonID.setBox_group(rs.getString("box_group"));
				cardboxBycartonID.setBox_type(rs.getString("completed_date"));
				cardboxBycartonID.setTotal_cards(rs.getInt("total_cards"));
				cardboxBycartonID.setCard_serial_no(rs.getString("card_serial_no"));
				cardboxBycartonID.setPrinted_date(rs.getDate("printed_date"));
				cardboxBycartonID.setSchool_id(rs.getInt("school_id"));
				cardboxBycartonID.setSchool(rs.getString("name"));
				cardboxBycartonID.setStatus(rs.getInt("card_box.status"));
				
				
				cardbox_details.add(cardboxBycartonID);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on String getCardboxDeatils method: " + e);
		} finally {
			conn.close();
		}
		return cardbox_details;
	}
	
	/**
	 * Get cardbox details
	 * 
	 * @param box_id
	 * @return Json data
	 * @throws SQLException
	 */
	public String getCardboxDeatil(Integer box_id) throws SQLException {
		
		ArrayList<Cardbox> cardbox_detail = new ArrayList<Cardbox>();
		String cardboxJSON = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Cardbox cardboxByBoxID;
		
		String sql = "SELECT * FROM card_box"
				+ " LEFT JOIN box_info ON card_box.box_id = box_info.box_id "
				+ " LEFT JOIN schools ON card_box.school_id = schools.id where card_box.box_id = "+ box_id;
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
			 
		while (rs.next()) {
			  cardboxByBoxID = new Cardbox();
			  
			  cardboxByBoxID.setBox_id(rs.getInt("box_id"));
			  cardboxByBoxID.setCarton_id(rs.getInt("carton_id"));
			  cardboxByBoxID.setBox_no(rs.getInt("box_no"));
			  cardboxByBoxID.setBox_group(rs.getString("box_group"));
			  cardboxByBoxID.setBox_type(rs.getString("completed_date"));
			  cardboxByBoxID.setTotal_cards(rs.getInt("total_cards"));
			  cardboxByBoxID.setCard_serial_no(rs.getString(""+ ""));
			  cardboxByBoxID.setPrinted_date(rs.getDate("printed_date"));
			  cardboxByBoxID.setSchool_id(rs.getInt("school_id"));
			  cardboxByBoxID.setNote(rs.getString("note"));
			  cardboxByBoxID.setReady_count(rs.getInt("card_ready_count"));
			  cardboxByBoxID.setStatus(rs.getInt("status"));
			  cardboxByBoxID.setSchool(rs.getString("name"));
			  cardboxByBoxID.setDelivery_date(rs.getString("delivered_date"));
			  cardboxByBoxID.setDelivery_person(rs.getString("delivered_person"));
			  
			  cardbox_detail.add(cardboxByBoxID);
			  
		  }
           }catch (SQLException e) {
			 System.out.println("Error on String getCardboxDeatil method: " + e.getLocalizedMessage());
			 e.printStackTrace();
		 } finally {
			 conn.close();
		 }
		
		 cardboxJSON = new Gson().toJson(cardbox_detail);
		return cardboxJSON;
	}
	
	
	/**
	 * Get cartonID
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Integer getCartonID() throws SQLException {
		Integer carton_id = 0;

		try {

			String sql = "SELECT carton_id FROM card_carton WHERE carton_group = \"1/50\" and status = 1 ";

			conn = DB.getConnection();
			stm = conn.createStatement();
			res = stm.executeQuery(sql);

			res.next();

			carton_id = res.getInt(1);

		} catch (SQLException e) {
			logger.error("Error on Integer getCartonID() method: ",e);
		} finally {
			conn.close();
		}

		return carton_id;
	}
	
	/**
	 * Update cardbox info
	 * @param name
	 * @param 
	 * @return [true/false]
	 * @throws SQLException
	 */
	public boolean updateCardbox(int box_id, String Completed_date, String card_sn, int school_id, String note, Integer card_count_ready) throws SQLException {
		boolean isDone = false;
		Connection conn = DB.getConnection();
		String sql = "UPDATE card_box" + 
				         " SET" + 
				           " completed_date = '" + Completed_date + 
				           "', card_serial_no = '" + card_sn +
				           "', school_id = " + school_id + 
				           ", status = 2" +
				           ", note = '" + note +
				           "', card_ready_count = " + card_count_ready + 
				     " WHERE box_id = " + box_id;

		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			int affected = pst.executeUpdate();

			if (affected == 1) {
				isDone = true;
			} else {
				isDone = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return isDone;
	}
	
	
	/**
	 * Add new carton box
	 * 
	 * @param carton_no
	 * @return boolean[true/false]
	 * @throws SQLException
	 */
	public boolean addDamagedCard(JSONArray jsonArray) throws SQLException {
		Connection conn = DB.getConnection();
		boolean isDone = false;
		
		String sql = "insert into damaged_cards (box_id, card_serial_no, reason) values (? ,? ,?)";
		try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
			  
	          // Insert records
	          for (int count = 0; count < jsonArray.length(); count++) {
	        	  
	        	 JSONObject obj = jsonArray.getJSONObject(count);
	        	 
	             stmt.setInt(1, Integer.parseInt(obj.get("box_id").toString()));
	             stmt.setString(2, obj.get("SerialNo").toString());
	             stmt.setString(3, obj.get("Reason").toString());
	             
	             //Add statement to batch
	             stmt.addBatch();
	          }
	          //execute batch
	          stmt.executeBatch();
	          
	          isDone = true;
	          
	       } catch (SQLException e) {
	          e.printStackTrace();
	          if (conn != null) {
	             try {
	                conn.rollback();
	             } catch (Exception ex) {
	                ex.printStackTrace();
	             }
	          }
	       } catch (Exception e) {
	    	   System.out.println(e);
			} finally {
				conn.close();
			}

			return isDone;
		
	}
	
	/**
	 * Update damaged card info while update card box
	 * @param id, box_id, serial_no, reason
	 * @param 
	 * @return [true/false]
	 * @throws SQLException
	 */
	
	public boolean updateDamagedCard(String box_id, String reason, String serial_no) throws SQLException {
		Connection conn = null;
		boolean isDone = false;

		String sql = "INSERT INTO damaged_cards (box_id, card_serial_no, reason) VALUES (?, ?, ?)";
		   System.out.println("sql"+sql);
		try {
			conn = DB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
			pst.setString(1, box_id);
			pst.setString(2, serial_no);
			pst.setString(3, reason);
			
			int affected = pst.executeUpdate();
			
			 if(affected == 1) {
				 isDone = true;
			 }else {
				 isDone = false;
			 }
		}catch (Exception e) {
			logger.error("Error on boolean updateDamagedCard(String box_id, String reason, String serial_no) method: ",e);
		} finally {
			conn.close();
		}

		return isDone;
	}
	
	/**
	 * Update damaged card info
	 * @param id, box_id, serial_no, reason
	 * @param 
	 * @return [true/false]
	 * @throws SQLException
	 */
	
	public boolean editDamagedCard(int id, int box_id, String reason, String serial_no) throws SQLException {
		boolean isDone = false;
		Connection conn = DB.getConnection();
		
		String sql = "UPDATE  damaged_cards" + 
			           " SET" + 
			           " card_serial_no = '" + serial_no + 
			           "', reason = '" + reason + "' "+
			           " WHERE id = "+id+" AND box_id = " + box_id;

		try {
			PreparedStatement pst = conn.prepareStatement(sql);
			int affected = pst.executeUpdate();

			if (affected == 1) {
				isDone = true;
			} else {
				isDone = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return isDone;
	}
	
	/**
	 * Delete Damaged Card
	 * 
	 * @param id
	 * @return boolean[true/false]
	 * @throws SQLException
	 */
	public boolean deleteDamagedCard(int id) throws SQLException {
		boolean isDone = false;
		Connection conn = DB.getConnection();
		System.out.println("id,--------------"+id);
		String sql = "DELETE FROM damaged_cards WHERE id = " + id;

		try {
			//System.out.println("sql :::::"+sql);
			PreparedStatement pst = conn.prepareStatement(sql);
			int affected = pst.executeUpdate();
			System.out.println("affected delete Damaged Card "+affected);

			if (affected == 1) {
				isDone = true;
			} else {
				isDone = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error on boolean deleteDamagedCard(int id) method: ");
		} finally {
			conn.close();
		}
		return isDone;
		
	}
	
	/**
	 * Get cardbox List
	 * 
	 * @param carton_id
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<Schools> getSchools() throws SQLException {
	
		ArrayList<Schools> Schools = new ArrayList<Schools>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Schools school;
		
		String sql = "SELECT * FROM schools";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			
			while (rs.next()) {
				school = new Schools();
				
				school.setId(rs.getInt("id"));
				school.setSchool_name(rs.getString("name"));
		
				Schools.add(school);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<Schools> getSchools() method: " + e);
		} finally {
			conn.close();
		}
		
		return Schools;
	}
	
	
	
	
	/**
	 * Add delivered box_info
	 * 
	 * @param box_id
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public boolean addDeliveredboxInfo(int box_id, int delivered_cards, String delivery_date, String delivery_person) throws SQLException {
		
		Connection conn = null;
		boolean isDone = false;

		String sql1 = "INSERT INTO box_info VALUES (?, ?, ?, ?, ?, ?, now(), ?)";
		String sql2 = "UPDATE card_box SET status = 3 WHERE box_id = " + box_id;
		   
		try {
			conn = DB.getConnection();
			PreparedStatement pst1 = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
			PreparedStatement pst2 = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
				
			pst1.setInt(1, box_id);
			pst1.setInt(2, delivered_cards);
			pst1.setInt(3, 200 - delivered_cards);
			pst1.setInt(4, 200);
			pst1.setInt(5, 0);
			pst1.setString(6, delivery_date);
			pst1.setString(7, delivery_person);
			
			int affected1 = pst1.executeUpdate();
			int affected2 = pst2.executeUpdate();
			
			 if(affected1 == 1 && affected2 == 1) {
				 isDone = true;
			 }else {
				 isDone = false;
			 }
		}catch (Exception e) {
			logger.error("Error on boolean addDeliveredboxInfo(int box_id, int delivered_cards, String delivery_date, String delivery_person) method: ",e);
		} finally {
			conn.close();
		}

		return isDone;
	}
	
	/**
	 * Add Returned card Details
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public boolean addReturnCard(String card_serial_no, int school_id,String reason,String recieved_by,int box_id) throws SQLException {
		
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "INSERT INTO returned_cards(card_serial_no,school_id,reason,recieved_by,box_id)  VALUES (?,?,?,?,?)";
		   
		try {
			conn = DB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
			pst.setString(1, card_serial_no);
			pst.setInt(2, school_id);
			pst.setString(3, reason);
			pst.setString(4, recieved_by);
			pst.setInt(5, box_id);
			
			int affected = pst.executeUpdate();
			
			 if(affected == 1 ) {
				 isDone = true;
			 }else {
				 isDone = false;
			 }
		}catch (Exception e) {
			logger.error("Error on boolean addReturnCard(String card_serial_no, int school_id,String reason,String recieved_by) method: ",e);
		} finally {
			conn.close();
		}

		return isDone;
	}
	
	
	/**
	 * Get cartonID
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Integer getInitialValue() throws SQLException {
		Integer initialValue = 0;

		try {

			String sql = "SELECT card_serial_no FROM card_box ORDER BY box_id DESC LIMIT 1";

			conn = DB.getConnection();
			stm = conn.createStatement();
			res = stm.executeQuery(sql);

			res.next();

		    String serial_no = res.getString(1);
		    
		    String[] box_serial_no = serial_no.split(" - ");
		    
		    initialValue = Integer.parseInt(box_serial_no[1]);

		} catch (SQLException e) {
			System.out.println("Error on Integer getCartonID() method: "+e);
		} finally {
			conn.close();
		}

		return initialValue;
	}
	
	
	/*
	 * Validate Shipment 
	 * @return boolean
	 * @throws SQLException
	 * 
	 */
	public boolean validateShipment(String shipment) throws SQLException{
		
		boolean validateShipment = false;
        int resultsCounter = 0;
        
    	Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
        
    	try {
			String sql = "SELECT COUNT(*) FROM card_carton WHERE shipment = '"+ shipment +"' ";
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if(rs.next()) {
				resultsCounter = rs.getInt(1);
			}
            
            System.out.println("Result Counter : "+resultsCounter);
            
            if (resultsCounter == 0) {
                // containg null result
            	validateShipment = true;
            	System.out.println("true------------------------");
            }
            else{
                //This conains the result you want
            	validateShipment = false;
            	System.out.println("false------------------------");
            }
            
        }catch (Exception e) {
			logger.error("Error on boolean validateShipment(String shipment) method: ",e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return validateShipment;
    }
	
	/**
	 * Get Damage Cards counts By box_id
	 * 
	 * @param box_id
	 * @return
	 * @throws SQLException
	 */
	public int getDamageCardCountsByBoxId(Integer box_id) throws SQLException {
		int damagedCardsCount = 0;
		
		String sql = "SELECT COUNT(*) FROM damaged_cards WHERE box_id = "+box_id;


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			damagedCardsCount = rs.getInt(1);
		} catch (SQLException e) {

			logger.error("Error on int getDeliveredBoxesCountsByGroup(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		System.out.println("damagedCardsCount by Group  :"+damagedCardsCount);
		return damagedCardsCount;
	}
	
	/**
	 * Get Damaged Card List By box_id
	 * 
	 * @param box_id
	 * @return Array list
	 * @throws SQLException
	 */
	public String getDamagedCardListByBoxId(Integer box_id) throws SQLException {
		String damagedCardJSON = "";
		ArrayList<Damagedcard> damaged_card_details = new ArrayList<Damagedcard>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Damagedcard damagedCard;
		
		String sql = "SELECT id,box_id,card_serial_no,reason FROM damaged_cards WHERE box_id = "+ box_id;
		
		System.out.println("SQL --->"+sql);
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				damagedCard = new Damagedcard();
				
				damagedCard.setId(rs.getInt("id"));
				damagedCard.setBox_id(rs.getInt("box_id"));
				damagedCard.setCard_serial_no(rs.getString("card_serial_no"));
				damagedCard.setReason(rs.getString("reason"));
				
				
				damaged_card_details.add(damagedCard);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on String getDamagedCardListByBoxId(Integer box_id) method: " + e);
		} finally {
			conn.close();
		}
		
		damagedCardJSON = new Gson().toJson(damaged_card_details);
		System.out.println("damaged Cards Array :"+damagedCardJSON);
	    return damagedCardJSON;
	}

	public int validateReturnedCardStatus(Integer enteredCardSerialNo) throws SQLException {
		int status = 0;
		String SerialNoRange = convertedSerialNo(enteredCardSerialNo);
		String sql = "SELECT status FROM db_assets.card_box WHERE card_serial_no = '"+SerialNoRange+"' ";


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			status = rs.getInt(1);
		} catch (SQLException e) {
			logger.error("Error on int validateReturnedCardStatus(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		System.out.println("Status  :"+status);
		return status;
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
	
	public Integer getBoxIdByserialNo(int enteredSerialNo) {
		int remainder = enteredSerialNo%200 ;
		int min = (enteredSerialNo - remainder) + 1 ;
	    int box_id = ((min - 1010001)/200) + 1 ;
		
		return box_id;
	}

	public int getSchoolBySerialNo(Integer enteredCardSerialNo) throws SQLException {
		
		Integer school_id = 0;
		Integer box_id = getBoxIdByserialNo(enteredCardSerialNo);
		String sql = "SELECT school_id FROM card_box WHERE box_id = "+box_id;


		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			school_id = rs.getInt(1);
		} catch (SQLException e) {
			logger.error("Error on int getSchoolBySerialNo(String Shipment) method: ", e);
		} finally {
			conn.close();
		}
		System.out.println("Status  :"+school_id);
		
		return school_id;
	}
	
	
	/*
	 * Validate DamagedCards 
	 * @return boolean
	 * @throws SQLException
	 * 
	 */
	public boolean validateDamagedCard(String card_serial_no) throws SQLException{
		 
		boolean validateDamagedCard = false;
        int resultsCounter = 0;
        
    	Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
        
    	try {
			String sql = "SELECT COUNT(*) FROM damaged_cards WHERE card_serial_no = '"+card_serial_no+"' ";
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if(rs.next()) {
				resultsCounter = rs.getInt(1);
			}
            
            System.out.println("Result Counter : "+resultsCounter);
            
            if (resultsCounter == 0) {
                // containg null result
            	validateDamagedCard = true;
            	System.out.println("true------------------------");
            }
            else{
                //This conains the result you want
            	validateDamagedCard = false;
            	System.out.println("false------------------------");
            }
            
        }catch (Exception e) {
			logger.error("Error on boolean validateDamagedCard(String card_serial_no) method: ",e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return validateDamagedCard;
    }

	/*
	 * Validate ReturnCard 
	 * @return boolean
	 * @throws SQLException
	 * 
	 */
	public boolean validateReturnCard(String card_serial_no) throws SQLException{
		 
		boolean validateReturnCard = false;
        int resultsCounter = 0;
        
    	Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
        
    	try {
			String sql = "SELECT COUNT(*) FROM db_assets.returned_cards WHERE card_serial_no = '"+card_serial_no+"' ";
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if(rs.next()) {
				resultsCounter = rs.getInt(1);
			}
            
            System.out.println("Result Counter : "+resultsCounter);
            
            if (resultsCounter == 0) {
                // containg null result
            	validateReturnCard = true;
            	System.out.println("true------------------------");
            }
            else{
                //This conains the result you want
            	validateReturnCard = false;
            	System.out.println("false------------------------");
            }
            
        }catch (Exception e) {
			logger.error("Error on boolean validateReturnCard(String card_serial_no) method: ",e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return validateReturnCard;
    }

}