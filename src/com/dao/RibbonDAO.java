package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.beans.Barcode;
import com.beans.DeliveredInk;
import com.beans.DispatchedRibbon;
import com.beans.Printerink;
import com.beans.RequestInk;
import com.beans.Ribbon;
import com.beans.RibbonBarcode;
import com.beans.Ribbonreq;
import com.google.gson.Gson;

public class RibbonDAO {
	
	static Logger logger = Logger.getLogger(PrinterinkDAO.class);
	
	/**
	 * Insert Requested Ribbon Details
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static boolean addRequestedRibbon(int order_no, String model, int qty, String cat, String req_date, String order_recieved, String via, String supplier, String req_by) throws SQLException {
		
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "INSERT INTO ribbon_req (order_no,model,qty,category,requested_date,order_recieved_person,order_via,supplier,requested_by) "
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
				logger.error("Error on boolean addRequestedRibbon() method: ",e);
			} finally {
				conn.close();
			}

		return isDone;
	}
	
	/**
	 * Get Requested Ribbon Order
	 * 
	 * @param order_no
	 * @return Json data
	 * @throws SQLException
	 */
	public static String getReqOrderRibbon(Integer id) throws SQLException {
		
		ArrayList<Ribbon> reqOrderDettail = new ArrayList<Ribbon>();
		String reqOrderJSON = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Ribbon reqOrderByOrderNo;
		
		String sql = "SELECT id,order_no,model,qty,category,requested_date,order_recieved_person,order_via,supplier,requested_by"
				   + " FROM ribbon_req"
				   + " WHERE id = "+ id;
		
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
			 
		while (rs.next()) {
			reqOrderByOrderNo = new Ribbon();
			  
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
	 * Get Next order no for Request order Ribbon
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
			String sql = "SELECT order_no FROM ribbon_req ORDER BY id DESC LIMIT 1";
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
	 * Get All Ribbon Requested order details
	 * 
	 * @return Json data
	 * @throws SQLException
	 */
	public ArrayList<Ribbonreq> getAllOrderRequestedRibbonList() throws SQLException {
		
		ArrayList<Ribbonreq> reqOrderRibbonList = new ArrayList<Ribbonreq>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Ribbonreq ribbon;
		
		String sql = "SELECT order_no, model, qty, category, requested_date, order_recieved_person, order_via, supplier, requested_by FROM ribbon_req";
	/*	System.out.println(sql);*/
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				 //System.out.println("-----------    "+ rs.getFetchSize());
			 
		while (rs.next()) {
			 ribbon = new Ribbonreq();
			
			 ribbon.setOrder_no(rs.getInt("order_no")); 
			 ribbon.setModel(rs.getString("model"));
			 ribbon.setQty(rs.getInt("qty"));
			 ribbon.setCat(rs.getString("category"));
			 ribbon.setReq_date(rs.getString("requested_date"));
			 ribbon.setOrder_recieved_person(rs.getString("order_recieved_person"));
			 ribbon.setOrder_via(rs.getString("order_via"));
			 ribbon.setSupplier(rs.getString("supplier"));
			 ribbon.setReq_by(rs.getString("requested_by"));
			 
			 reqOrderRibbonList.add(ribbon);
		  }
           }catch (SQLException e) {
			 
			 System.out.println("Error on String getAllOrderRequestedRibbonList method: " + e.getLocalizedMessage());
			 e.printStackTrace();
		 } finally {
			 conn.close();
		 }
		
		/* printerinkJSON = new Gson().toJson(reqOrderRibbonList);*/
		return reqOrderRibbonList;
	}
	
	
	/**
	 * Get Order No List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<Ribbonreq> getOrderNoList() throws SQLException {
	
		ArrayList<Ribbonreq> order_list = new ArrayList<Ribbonreq>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Ribbonreq ribbon_req;
		
		String sql = "SELECT id,order_no FROM ribbon_req ORDER BY id ASC";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		/*	 System.out.println("-----------    "+ rs.getFetchSize());*/
			while (rs.next()) {
				ribbon_req = new Ribbonreq();
				
				ribbon_req.setId(rs.getInt("id"));
				ribbon_req.setOrder_no(rs.getInt("order_no"));
		
				order_list.add(ribbon_req);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<Ribbonreq> getOrderNoList() method: " + e);
		} finally {
			conn.close();
		}
		
		return order_list;
	}
	
	/**
	 * Get Ribbon stock details
	 * 
	 * @return Json data
	 * @throws SQLException
	 */
	public ArrayList<Ribbon> getPritnerRibbonStockDetails() throws SQLException {
		
		ArrayList<Ribbon> ribbonList = new ArrayList<Ribbon>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Ribbon ribbon;
		
		String sql = "SELECT * FROM ribbon_stock";
		
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				 //System.out.println("-----------    "+ rs.getFetchSize());
			 
		while (rs.next()) {
			 ribbon = new Ribbon();
			
			 ribbon.setId(rs.getInt("id")); 
			 ribbon.setModel(rs.getString("model"));
			 ribbon.setCat(rs.getString("catergory"));
			 ribbon.setDate(rs.getString("date"));
			 ribbon.setQty(rs.getInt("qty"));
			 ribbon.setSupplier(rs.getString("supplier"));
			 ribbon.setShipment(rs.getString("shipment"));
			 ribbon.setReceived_by(rs.getString("received_by"));
			 ribbon.setStatus(rs.getString("status"));
			 
			 ribbonList.add(ribbon);
		  }
           }catch (SQLException e) {
			 
			 System.out.println("Error on String getPritnerRibbonStockDetails method: " + e.getLocalizedMessage());
			 e.printStackTrace();
		 } finally {
			 conn.close();
		 }
		 
		return ribbonList;
	}
	
	
	/**
	 * Add Printer Ribbon to Stock
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static boolean addPrinterRibbon(String[] barcodes, Integer req_id, String model, String cat, String date, Integer qty, String supplier, String shipment, String received_by, String status) throws SQLException {
		
		Connection conn = null;
		boolean isDone = false;
		boolean isBarcodeDone = false;
		String sql = "INSERT INTO ribbon_stock (model, catergory, date, qty, supplier, shipment, received_by, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		String sql2 = "DELETE FROM ribbon_req WHERE id = "+req_id;
		String sql3 = "INSERT INTO ribbon_barcodes (ribbon_id,barcode) values (? ,?)";
		
		System.out.println("Inside DAO sql 1: "+ sql);
		System.out.println("Inside DAO sql 2: "+ sql2);
		System.out.println("Inside DAO sql 3: "+ sql3);
		
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
			System.out.println("affected = "+affected+ ", affected2 = "+affected2+", isBarcodeDone = "+isBarcodeDone);
			 if(affected == 1 && affected2 == 1 && isBarcodeDone) {
				 isDone = true;
			 }else {
				 isDone = false;
			 }
		
		}catch (Exception e) {
			logger.error("Error on boolean addPrinterRibbon(String model, String cat, String date, Integer qty, String supplier, double cost, String shipment, String received_by, double total, String stats) method: ",e);
		} finally {
			conn.close();
		}
		
		System.out.println(isDone);
		return isDone;
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
		
		String sql = "SELECT id, ribbon_id, barcode FROM ribbon_barcodes WHERE ribbon_id = "+ id;
		
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
			 
		while (rs.next()) {
			barcode = new Barcode();
			  
			barcode.setId(rs.getInt("id"));
			barcode.setInk_id(rs.getInt("ribbon_id"));
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
	 * Get All Dispatched Ribbon List details
	 * 
	 * @return Json data
	 * @throws SQLException
	 */
	public ArrayList<DispatchedRibbon> getAllDispatchedRibbonList() throws SQLException {
		
		ArrayList<DispatchedRibbon> dispatchedRibbonList = new ArrayList<DispatchedRibbon>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		DispatchedRibbon dispatchedRibbon;
		
		String sql = "SELECT shipment, model, catergory, barcode, sent_by, delivered_person, delivery_date, location FROM ribbon_dispatch "
		           + " LEFT JOIN ribbon_stock ON ribbon_dispatch.ribbon_id = ribbon_stock.id";
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
			 
		while (rs.next()) {
			   dispatchedRibbon = new DispatchedRibbon();
			
			   dispatchedRibbon.setShipment(rs.getString("shipment"));
			   dispatchedRibbon.setModel(rs.getString("model"));
			   dispatchedRibbon.setCat(rs.getString("catergory"));
			   dispatchedRibbon.setBarcode(rs.getString("barcode"));
			   dispatchedRibbon.setSent_by(rs.getString("sent_by"));
			   dispatchedRibbon.setDelivered_person(rs.getString("delivered_person"));
			   dispatchedRibbon.setDelivery_date(rs.getString("delivery_date"));
			   dispatchedRibbon.setLocation(rs.getString("location"));
			 
			   dispatchedRibbonList.add(dispatchedRibbon);
		      }
           }catch (SQLException e) {
			 
			 System.out.println("Error on String getAllDispatchedRibbonList method: " + e.getLocalizedMessage());
			 e.printStackTrace();
		 } finally {
			 conn.close();
		 }
				 
		return dispatchedRibbonList;
	}
	
	/**
	 * Get Barcode id and printer Ribbon for dispatch ribbon
	 * 
	 * @param barcode
	 * @return Json data
	 * @throws SQLException
	 */
	public static String searchBarcode(String barcode) throws SQLException {
		
		ArrayList<RibbonBarcode> barcodes = new ArrayList<RibbonBarcode>();
		String barcodeJSON = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		RibbonBarcode ribbon_barcode;
		
		String sql = "SELECT id,ribbon_id FROM ribbon_barcodes where barcode = '"+ barcode+"' ";
		
		 try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
			 
		while (rs.next()) {
			ribbon_barcode = new RibbonBarcode();
			  
			ribbon_barcode.setId(rs.getInt("id"));
			ribbon_barcode.setRibbon_id(rs.getInt("ribbon_id"));
			  
			barcodes.add(ribbon_barcode);
			  
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
	
	/**
	 * Insert Dispatched Ribbon
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static boolean addDispatchedRibbon(int barcode_id, int ribbon_id, String barcode, String location, String delivered_date, String delivered_by,String sent_by) throws SQLException {
		
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "INSERT INTO ribbon_dispatch (ribbon_id, barcode, delivery_date, qty, location, delivered_person, sent_by) "
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		String sql2 = "UPDATE ribbon_stock SET qty = qty-1 WHERE id = "+ ribbon_id;
		String sql3 = "DELETE FROM ribbon_barcodes WHERE id = "+ barcode_id;
		
		try {
				conn = DB.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement pst2 = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement pst3 = conn.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
				
				pst.setInt(1, ribbon_id);
				pst.setString(2, barcode);
				pst.setString(3, delivered_date);
				pst.setInt(4, 1);
				pst.setString(5, location);
				pst.setString(6, delivered_by);
				pst.setString(7, sent_by);
			    
					
				int affected = pst.executeUpdate();
				int affected2 = pst2.executeUpdate();
				int affected3 = pst3.executeUpdate();
				
				 if(affected == 1 && affected2 == 1 && affected3 == 1) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				System.out.println("Error on boolean addDispatchedRibbon(int ribbon_id, ink_id, delivery_date, qty, location, delivered_person, send_by) method: "+e);
			} finally {
				conn.close();
			}

		return isDone;
	}
	
	
	/**
	 * Get Ribbon counts by Black $ white
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int getRibbonCountBlackWhite() throws SQLException {
		int totalCount = 0;
		
		String sql = "SELECT SUM(qty) FROM ribbon_stock WHERE catergory= "+"'Black & White'";

		System.out.println("----------------- sql tatal count -----------: "+sql);

		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			totalCount = rs.getInt(1);
	
		} catch (SQLException e) {

			logger.error("Error on int getRibbonCountBlackWhite() method: ", e);
		} finally {
			conn.close();
		}
		System.out.println("Ribbon Black and white count---"+totalCount);
		return totalCount;
	}
	
	/**
	 * Get Ribbon counts by Colour
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int getRibbonCountColour() throws SQLException {
		int totalCount = 0;
		
		String sql = "SELECT SUM(qty) FROM ribbon_stock WHERE catergory= "+"'Colour'";

		System.out.println("----------------- sql tatal count -----------: "+sql);

		Connection conn = null;
		try {
			conn = DB.getConnection();
			Statement stm = conn.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			totalCount = rs.getInt(1);
	
		} catch (SQLException e) {

			logger.error("Error on int getRibbonCountColour() method: ", e);
		} finally {
			conn.close();
		}
		return totalCount;
	}

}
