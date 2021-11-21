package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.beans.Barcode;
import com.beans.Cardbox;
import com.beans.DeliveredInk;
import com.beans.Printerink;
import com.beans.RequestInk;
import com.beans.Schools;
import com.google.gson.Gson;

public class PrinterinkDAO {
	
	static Logger logger = Logger.getLogger(PrinterinkDAO.class);
	

	/**
	 * Add Printer ink to Stock
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static boolean addPrinterInk(String[] barcodes, Integer req_id, String model, String cat, String date, Integer qty, String supplier, String shipment, String received_by, String status) throws SQLException {
		
		Connection conn = null;
		boolean isDone = false;
		boolean isBarcodeDone = false;
		String sql = "INSERT INTO printer_ink (model, catergory, date, qty, supplier, shipment, received_by, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		String sql2 = "DELETE FROM req_ink_order WHERE id = "+req_id;
		String sql3 = "INSERT INTO ink_barcodes (printer_ink_id,barcode) values (? ,?)";
		
		System.out.println("Inside DAO : "+ barcodes);
		
		try {
			conn = DB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			PreparedStatement pst2 = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
				
			pst.setString(1, model);
			pst.setString(2, cat);
			pst.setString(3, date);
			pst.setInt(4, qty);
			pst.setString(5, supplier);
			pst.setString(6, shipment);
			pst.setString(7, received_by);
			pst.setString(8, status);
			
			int affected = pst.executeUpdate();
			int affected2 = pst2.executeUpdate();
		   
			try (PreparedStatement stmt = conn.prepareStatement(sql3, PreparedStatement.RETURN_GENERATED_KEYS);) {
				int key = 0;
				// Get generated key.
		        ResultSet rs = pst.getGeneratedKeys();
		        if (rs.next()) {
		            key = rs.getInt(1);
		            System.out.println("key : "+key);
		            
		        }
				  
		          // Insert Barcodes
		          for (int count = 0; count < barcodes.length; count++) {
		        	 
		        	 
		        	  stmt.setInt(1, key);
					  stmt.setString(2, barcodes[count]);
		             
		             //Add statement to batch
		             stmt.addBatch();
		          }
		          //execute batch
		          stmt.executeBatch();
		          
		          isBarcodeDone = true;
		          
		       } catch (SQLException e) {
		          e.printStackTrace();
		          if (conn != null) {
		             try {
		                conn.rollback();
		             } catch (Exception ex) {
		                ex.printStackTrace();
		             }
		          }
		       } 
			
			 if(affected == 1 && affected2 == 1 && isBarcodeDone) {
				 isDone = true;
			 }else {
				 isDone = false;
			 }
		}catch (Exception e) {
			System.out.println("SQLException e"+e);
			logger.error("Error on boolean addPrinterInk(String model, String cat, String date, Integer qty, String supplier, double cost, String shipment, String received_by, double total, String stats) method: ",e);
		} finally {
			conn.close();
		}

		return isDone;
	}
	
	
	/**
	 * Get printer ink stock details
	 * 
	 * @return Json data
	 * @throws SQLException
	 */
	public ArrayList<Printerink> getPritnerInkStockDetails() throws SQLException {
		
		ArrayList<Printerink> printerinklist = new ArrayList<Printerink>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Printerink printerink;
		
		String sql = "SELECT * FROM printer_ink";
	/*	System.out.println(sql);*/
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				 //System.out.println("-----------    "+ rs.getFetchSize());
			 
		while (rs.next()) {
			printerink = new Printerink();
			
			 printerink.setId(rs.getInt("id")); 
			 printerink.setModel(rs.getString("model"));
			 printerink.setCat(rs.getString("catergory"));
			 printerink.setDate(rs.getString("date"));
			 printerink.setQty(rs.getInt("qty"));
			 printerink.setSupplier(rs.getString("supplier"));
			 printerink.setShipment(rs.getString("shipment"));
			 printerink.setReceived_by(rs.getString("received_by"));
			 printerink.setStatus(rs.getString("status"));
			 
			 printerinklist.add(printerink);
		  }
           }catch (SQLException e) {
			 
			 System.out.println("Error on String getPritnerInkStockDetails method: " + e.getLocalizedMessage());
			 e.printStackTrace();
		 } finally {
			 conn.close();
		 }
		
		/* printerinkJSON = new Gson().toJson(printerinklist);*/
		return printerinklist;
	}
	
	
	/**
	 * Get Next order no for Request order ink
	 * 
	 * @return order_no
	 * @throws SQLException
	 */
	
	
	public int getOrderNo() {
		int order_no = 0;
		
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		

		try {
			String sql = "SELECT order_no FROM req_ink_order ORDER BY id DESC LIMIT 1";
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if(rs.next()) {
				order_no = rs.getInt(1);
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
		return order_no;
	}
	
	/**
	 * Insert Requested PrinterInk Details
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static boolean addRequestedInk(int order_no, String model, int qty, String cat, String req_date, String order_recieved, String via, String supplier, String req_by) throws SQLException {
		
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "INSERT INTO req_ink_order (order_no,model,qty,category,requested_date,order_recieved_person,order_via,supplier,requested_by) "
				+ " VALUES (?,?,?,?,?,?,?,?,?)";
		   
		try {
				conn = DB.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					
				pst.setInt(1, order_no);
				pst.setString(2, model);
				pst.setInt(3, qty);
				pst.setString(4, cat);
				pst.setString(5, req_date);
				pst.setString(6, order_recieved);
				pst.setString(7, via);
				pst.setString(8, supplier);
				pst.setString(9, req_by);
			
				
				int affected = pst.executeUpdate();
				
				 if(affected == 1 ) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				logger.error("Error on boolean addRequestedInk() method: ",e);
			} finally {
				conn.close();
			}

		return isDone;
	}
	
	
	/**
	 * Get Order No List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<RequestInk> getOrderNoList() throws SQLException {
	
		ArrayList<RequestInk> order_list = new ArrayList<RequestInk>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		RequestInk reqink;
		
		String sql = "SELECT id,order_no FROM req_ink_order ORDER BY id ASC";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		/*	 System.out.println("-----------    "+ rs.getFetchSize());*/
			while (rs.next()) {
				reqink = new RequestInk();
				
				reqink.setId(rs.getInt("id"));
				reqink.setOrder_no(rs.getInt("order_no"));
		
				order_list.add(reqink);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<Printerink> getOrderNoList() method: " + e);
		} finally {
			conn.close();
		}
		
		return order_list;
	}
	
	/**
	 * Get Requested Ink Order
	 * 
	 * @param order_no
	 * @return Json data
	 * @throws SQLException
	 */
	public static String getReqOrderInk(Integer id) throws SQLException {
		
		ArrayList<Printerink> reqOrderDettail = new ArrayList<Printerink>();
		String reqOrderJSON = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Printerink reqOrderByOrderNo;
		
		String sql = "SELECT id,order_no,model,qty,price,category,requested_date,order_recieved_person,order_via,supplier,requested_by"
				   + " FROM req_ink_order"
				   + " WHERE id = "+ id;
	/*	System.out.println(sql);*/
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				 //System.out.println("-----------    "+ rs.getFetchSize());
			 
		while (rs.next()) {
			reqOrderByOrderNo = new Printerink();
			  
			reqOrderByOrderNo.setId(rs.getInt("id"));
			reqOrderByOrderNo.setOrder_no(rs.getInt("order_no"));
			reqOrderByOrderNo.setModel(rs.getString("model"));
			reqOrderByOrderNo.setQty(rs.getInt("qty"));
			reqOrderByOrderNo.setCat(rs.getString("category"));
			reqOrderByOrderNo.setReq_date(rs.getString("requested_date"));
			reqOrderByOrderNo.setOrder_recieved_person(rs.getString("order_recieved_person"));
			reqOrderByOrderNo.setOrder_via(rs.getString("order_via"));
			reqOrderByOrderNo.setSupplier(rs.getString("supplier"));
			reqOrderByOrderNo.setReq_by(rs.getString("requested_by"));
			  
			reqOrderDettail.add(reqOrderByOrderNo);
			  
		  }
         }catch (SQLException e) {
			 System.out.println("Error on String getReqOrderInk(Integer order_no) method: " + e.getLocalizedMessage());
			 e.printStackTrace();
		 } finally {
			 conn.close();
		 }
		
		 reqOrderJSON = new Gson().toJson(reqOrderDettail);
		// System.out.println("card box json 1111111111111"+cardboxJSON);
		return reqOrderJSON;
	}
	
	/**
	 * Get All printer ink Requested order details
	 * 
	 * @return Json data
	 * @throws SQLException
	 */
	public ArrayList<RequestInk> getAllOrderRequestedInkList() throws SQLException {
		
		ArrayList<RequestInk> reqOrderInkList = new ArrayList<RequestInk>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		RequestInk reqOrderInk;
		
		String sql = "SELECT order_no, model, qty, price, category, requested_date, order_recieved_person, order_via, supplier, requested_by FROM req_ink_order";
	/*	System.out.println(sql);*/
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				 //System.out.println("-----------    "+ rs.getFetchSize());
			 
		while (rs.next()) {
			reqOrderInk = new RequestInk();
			
			 reqOrderInk.setOrder_no(rs.getInt("order_no")); 
			 reqOrderInk.setModel(rs.getString("model"));
			 reqOrderInk.setQty(rs.getInt("qty"));
			 reqOrderInk.setCat(rs.getString("category"));
			 reqOrderInk.setReq_date(rs.getString("requested_date"));
			 reqOrderInk.setOrder_recieved_person(rs.getString("order_recieved_person"));
			 reqOrderInk.setOrder_via(rs.getString("order_via"));
			 reqOrderInk.setSupplier(rs.getString("supplier"));
			 reqOrderInk.setReq_by(rs.getString("requested_by"));
			 
			 reqOrderInkList.add(reqOrderInk);
		  }
           }catch (SQLException e) {
			 
			 System.out.println("Error on String getAllOrderRequestedInkList method: " + e.getLocalizedMessage());
			 e.printStackTrace();
		 } finally {
			 conn.close();
		 }
		
		/* printerinkJSON = new Gson().toJson(printerinklist);*/
		return reqOrderInkList;
	}
	
	
	/**
	 * Insert Delivered Ink Details
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static boolean addDeliveredInk(int barcode_id, int ink_id, String barcode, String location, String device_no, String delivered_date, String delivered_by,String sent_by) throws SQLException {
		
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "INSERT INTO ink_delivered_info (ink_id, barcode, delivery_date, qty, location, device_no, delivered_person, sent_by) " + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		String sql2 = "UPDATE printer_ink SET qty = qty-1 WHERE id = "+ ink_id;
		String sql3 = "DELETE FROM ink_barcodes WHERE id = "+ barcode_id;
		
		try {
				conn = DB.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement pst2 = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement pst3 = conn.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
				
				pst.setInt(1, ink_id);
				pst.setString(2, barcode);
				pst.setString(3, delivered_date);
				pst.setInt(4, 1);
				pst.setString(5, location);
				pst.setString(6, device_no);
				pst.setString(7, delivered_by);
				pst.setString(8, sent_by);
			    
					
				int affected = pst.executeUpdate();
				int affected2 = pst2.executeUpdate();
				int affected3 = pst3.executeUpdate();
				
				 if(affected == 1 && affected2 == 1 && affected3 == 1) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				System.out.println("Error on boolean addDeliveredInk(int ink_id, ink_id, delivery_date, qty, location, delivered_person, send_by) method: "+e);
			} finally {
				conn.close();
			}

		return isDone;
	}	
	
	/**
	 * Get All printer ink Delivered details
	 * 
	 * @return Json data
	 * @throws SQLException
	 */
	public ArrayList<DeliveredInk> getAllDeliveredInkList() throws SQLException {
		
		ArrayList<DeliveredInk> deliveredInkList = new ArrayList<DeliveredInk>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		DeliveredInk deliveredInk;
		
		String sql = "SELECT shipment, model, catergory, barcode, sent_by, delivered_person, delivery_date, location, ink_delivered_info.device_no FROM db_assets.ink_delivered_info "
		           + " LEFT JOIN printer_ink ON ink_delivered_info.ink_id = printer_ink.id";
	/*	System.out.println(sql);*/
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				 //System.out.println("-----------    "+ rs.getFetchSize());
			 
		while (rs.next()) {
			   deliveredInk = new DeliveredInk();
			
			   deliveredInk.setShipment(rs.getString("shipment"));
			   deliveredInk.setModel(rs.getString("model"));
			   deliveredInk.setCat(rs.getString("catergory"));
			   deliveredInk.setBarcode(rs.getString("barcode"));
			   deliveredInk.setSent_by(rs.getString("sent_by"));
			   deliveredInk.setDelivered_person(rs.getString("delivered_person"));
			   deliveredInk.setDelivery_date(rs.getString("delivery_date"));
			   deliveredInk.setLocation(rs.getString("location"));
			   deliveredInk.setDevice_no(rs.getString("ink_delivered_info.device_no"));
			 
			   deliveredInkList.add(deliveredInk);
		      }
           }catch (SQLException e) {
			 
			 System.out.println("Error on String getAllDeliveredInkList method: " + e.getLocalizedMessage());
			 e.printStackTrace();
		 } finally {
			 conn.close();
		 }
				 System.out.println("Delivered List - >"+deliveredInkList);
		return deliveredInkList;
		
	}
	
	
	
	/**
	 * Get Barcodes for the Ribbon
	 * 
	 * @param id
	 * @return Json data
	 * @throws SQLException
	 */
	public static String getBarcodes(Integer id) throws SQLException {
		
		ArrayList<Barcode> barcodes = new ArrayList<Barcode>();
		String barcodeJSON = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Barcode barcode;
		
		String sql = "SELECT id, printer_ink_id, barcode FROM ink_barcodes WHERE printer_ink_id = "+ id;
		
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
			 
		while (rs.next()) {
			barcode = new Barcode();
			  
			barcode.setId(rs.getInt("id"));
			barcode.setInk_id(rs.getInt("printer_ink_id"));
			barcode.setBarcode(rs.getString("barcode"));
			  
			barcodes.add(barcode);
			  
		  }
         }catch (SQLException e) {
			 
			 System.out.println("Error on String getBarcodes(Integer id) method: " + e.getLocalizedMessage());
			 e.printStackTrace();
		 } finally {
			 conn.close();
		 }
		
		 barcodeJSON = new Gson().toJson(barcodes);
		 
		 return barcodeJSON;
	}
	
	/**
	 * Get Barcode id and printer ink for the deliver ribbon
	 * 
	 * @param barcode
	 * @return Json data
	 * @throws SQLException
	 */
	public static String searchBarcode(String barcode) throws SQLException {
		
		ArrayList<Barcode> barcodes = new ArrayList<Barcode>();
		String barcodeJSON = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Barcode barcode1;
		
		String sql = "SELECT id,printer_ink_id FROM ink_barcodes where barcode = '"+ barcode+"' ";
		
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
			 
		while (rs.next()) {
			barcode1 = new Barcode();
			  
			barcode1.setId(rs.getInt("id"));
			barcode1.setInk_id(rs.getInt("printer_ink_id"));
			  
			barcodes.add(barcode1);
			  
		  }
         }catch (SQLException e) {
			 
			 System.out.println("Error on String searchBarcode(String barcode) method: " + e.getLocalizedMessage());
			 e.printStackTrace();
		 } finally {
			 conn.close();
		 }
		
		 barcodeJSON = new Gson().toJson(barcodes);
		 
		 return barcodeJSON;
	}
}
