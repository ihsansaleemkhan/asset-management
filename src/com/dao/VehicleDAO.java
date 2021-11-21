package com.dao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;

import com.beans.Drivers;
import com.beans.Make;
import com.beans.Model;
import com.beans.Trainer;
import com.beans.VehicleAccident;
import com.beans.VehicleFines;
import com.beans.VehicleServiceType;
import com.beans.VehicleServices;
import com.beans.Vehicleholoteq;
import com.google.gson.Gson;
import com.google.gson.JsonObject;



public class VehicleDAO {

	static Logger logger = Logger.getLogger(VehicleDAO.class);
	
	
	/**
	 * Insert Vehicle Details
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static Boolean addVehicle(String plate_no, String make, String model, String permit_date,
			byte[] permit_img,byte[] permit_img2, byte[] vehicleImg, byte[] vehicleImg2, byte[] vehicleImg3, byte[] vehicleImg4) throws SQLException {
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "INSERT INTO vehicles (plate_no, make, model, permit_img,permit_img_2, permit_date,vehicle_img,vehicle_img_2,vehicle_img_3,vehicle_img_4, created_date) "
				+ "VALUES ( ?, ?, ?,?, ?, ?,?,?,?,? , CURDATE())";

		try {
				conn = DB.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					
				pst.setString(1, plate_no);
				pst.setString(2, make);
				pst.setString(3, model);
				
				if(permit_img != null) {
					InputStream photoInputStream = new ByteArrayInputStream(permit_img);
					if (photoInputStream != null) {
						pst.setBinaryStream(4, photoInputStream);
					}
				}else {
					pst.setBinaryStream(4, null);
				}
				
				if(permit_img2 != null) {
					InputStream photoInputStream2 = new ByteArrayInputStream(permit_img2);
					if (photoInputStream2 != null) {
						pst.setBinaryStream(5, photoInputStream2);
					}
				}else {
					pst.setBinaryStream(5, null);
				}
				
				pst.setString(6, permit_date);
				
				if(vehicleImg != null) {
					InputStream vehicleImgInputStream = new ByteArrayInputStream(vehicleImg);
					if (vehicleImgInputStream != null) {
						pst.setBinaryStream(7, vehicleImgInputStream);
					}
				}else {
					pst.setBinaryStream(7, null);	
				}
				
				if(vehicleImg2 != null) {
					InputStream vehicleImgInputStream2 = new ByteArrayInputStream(vehicleImg2);
					if (vehicleImgInputStream2 != null) {
						pst.setBinaryStream(8, vehicleImgInputStream2);
					}
				}else {
					pst.setBinaryStream(8, null);	
				}
				if(vehicleImg3 != null) {
					InputStream vehicleImgInputStream3 = new ByteArrayInputStream(vehicleImg3);
					if (vehicleImgInputStream3 != null) {
						pst.setBinaryStream(9, vehicleImgInputStream3);
					}
				}else {
					pst.setBinaryStream(9, null);	
				}
				
				if(vehicleImg4 != null) {
					InputStream vehicleImgInputStream4 = new ByteArrayInputStream(vehicleImg4);
					if (vehicleImgInputStream4 != null) {
						pst.setBinaryStream(10, vehicleImgInputStream4);
					}
				}else {
					pst.setBinaryStream(10, null);	
				}
				
				
				int affected = pst.executeUpdate();
				System.out.println("affected ----->"+affected);
			    System.out.println("sql--------------------->"+sql);
				 if(affected == 1 ) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				System.out.println("exception in add vehical dao : " + e.getMessage());
				logger.error("Error on boolean addVehicle() method: ",e);
			} finally {
				conn.close();
			}
		
		return isDone;
	}


	/**
	 * Insert driver Details
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static Boolean addDriver(String driver_name, String license_no, byte[] license_img, String given_date, String vehicle,byte[] profile_img) throws SQLException {
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "INSERT INTO drivers ( vehicle_id, driver_name, license_no, license_img, given_date,photo, created_date) VALUES ( ?, ?, ?, ?, ? ,?,  CURDATE())";
 
		try {
				conn = DB.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				pst.setString(1, vehicle);
				pst.setString(2, driver_name);
				pst.setString(3, license_no);
				InputStream photoInputStream = new ByteArrayInputStream(license_img);
				if (photoInputStream != null) {
					pst.setBinaryStream(4, photoInputStream);
				}
				pst.setString(5, given_date);
				
				InputStream proImgInputStream = new ByteArrayInputStream(profile_img);
				if (proImgInputStream != null) {
					pst.setBinaryStream(6, proImgInputStream);
				}
				
				int affected = pst.executeUpdate();
				System.out.println("affected ----->"+affected);
			    System.out.println("sql--------------------->"+sql);
				 if(affected == 1 ) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				logger.error("Error on boolean addVehicle() method: ",e);
			} finally {
				conn.close();
			}
		
		return isDone;
	}
	
	/**
	 * Get vehicle List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<Vehicleholoteq> getVehicles() throws SQLException {
	
		ArrayList<Vehicleholoteq> vehicles = new ArrayList<Vehicleholoteq>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		byte data[] = null;
		Blob file = null;
		Vehicleholoteq vehicle;
		
		String sql = "SELECT plate_no, make.make, model.model, permit_img, permit_date, driver_name FROM vehicles" 
				+" LEFT JOIN make ON make.id = vehicles.make"
				+" LEFT JOIN model ON model.id = vehicles.model"
				+" LEFT JOIN drivers ON drivers.id = vehicles.driver_id";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		/*	 System.out.println("-----------    "+ rs.getFetchSize());*/
			while (rs.next()) {
				vehicle = new Vehicleholoteq();
				file = rs.getBlob("permit_img");
				data = file.getBytes(1, (int) file.length());
				
				vehicle.setPlate_no(rs.getString("plate_no"));
				vehicle.setMake(rs.getString("make.make"));
				vehicle.setModel(rs.getString("model.model"));
				vehicle.setPermit_img(data);
				vehicle.setPermit_date(rs.getString("permit_date"));
		        vehicle.setDriver_name(rs.getString("driver_name"));
				
				vehicles.add(vehicle);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<Vehicleholoteq> getVehicles() method: " + e);
		} finally {
			conn.close();
		}

		return vehicles;
	}
	
	
	public static String fetchVehicleList() throws SQLException {
		
		ArrayList<Vehicleholoteq> vehicle_list = new ArrayList<Vehicleholoteq>();
		String vehiclesJSON = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Vehicleholoteq vehicle;
		
		String sql = "SELECT vehicles.id, plate_no, make.make, model.model, permit_date, driver_name FROM vehicles"
					+" LEFT JOIN make ON make.id = vehicles.make"
					+" LEFT JOIN model ON model.id = vehicles.model"
				   + " LEFT JOIN drivers ON drivers.id = vehicles.driver_id";
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			
			while(rs.next()) {
				vehicle =  new Vehicleholoteq();
				
				vehicle.setId(rs.getInt("id"));
				vehicle.setPlate_no(rs.getString("plate_no"));
				vehicle.setMake(rs.getString("make.make"));
				vehicle.setModel(rs.getString("model.model"));
				vehicle.setPermit_date(rs.getString("permit_date"));
				
				if(rs.getString("driver_name") == null) {
					vehicle.setDriver_name("Not Assigned");
				}else {
					vehicle.setDriver_name(rs.getString("driver_name"));
				}
				
				vehicle_list.add(vehicle);
			}
			
		} catch (SQLException e) {
			System.out.println("Error on fetchVehicleList method: " + e);
		} finally {
			conn.close();
		}
		vehiclesJSON = new Gson().toJson(vehicle_list);
		
		return vehiclesJSON;
	}
	

	/**
	 * Get number of total vehicle
	 * 
	 * @param filters
	 * @return Integer[total vehicle]
	 * @throws SQLException
	 */
	public Integer getTotalVehicles(Map<String, String> filters) throws SQLException{
		
		Integer totalVehicles = 0;
		
		String sql = "SELECT COUNT(*) FROM vehicles";
		
		if(filters != null) {
			
		}
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			rs.next();

			totalVehicles = rs.getInt(1);

		} catch (SQLException e) {
			logger.error("Error on Integer getTotalVehicles method: " + e);
		} finally {
			conn.close();
		}
		return totalVehicles;
		
	}
	
	
	
	/**
	 * Get Vehicle plate no List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<Vehicleholoteq> getVehiclePlateNo() throws SQLException {
	
		ArrayList<Vehicleholoteq> vehicles = new ArrayList<Vehicleholoteq>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Vehicleholoteq vehicle;
		
		String sql = "SELECT vehicles.id,plate_no,model.model,make.make,driver_id,driver_name from vehicles"
				+" LEFT JOIN make ON make.id = vehicles.make"
				+" LEFT JOIN model ON model.id = vehicles.model"
				+ " left join drivers on drivers.id=driver_id GROUP BY id";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
		/*	 System.out.println("-----------    "+ rs.getFetchSize());*/
			while (rs.next()) {
				vehicle = new Vehicleholoteq();
			
				vehicle.setId(rs.getInt("vehicles.id"));
				vehicle.setDriverId(rs.getInt("driver_id"));
				vehicle.setPlate_no(rs.getString("plate_no"));
				
				vehicle.setModel(rs.getString("model.model"));
				vehicle.setMake(rs.getString("make.make"));
				
				vehicle.setDriver_name(rs.getString("driver_name"));
		
				vehicles.add(vehicle);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<Vehicleholoteq> getVehiclePlateNo() method: " + e);
		} finally {
			conn.close();
		}
		return vehicles;
	}
	
	/**
	 * Get Driver name list
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<Drivers> getDrivername() throws SQLException {
	
		ArrayList<Drivers> drivers = new ArrayList<Drivers>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Drivers driver;
		
		String sql = "SELECT drivers.id, driver_name,vehicle_id,plate_no,model.model,make.make from drivers"				
				+ " left join vehicles on vehicles.id=vehicle_id"
				+" LEFT JOIN make ON make.id = vehicles.make"
				+" LEFT JOIN model ON model.id = vehicles.model"
				+ " GROUP BY id";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			
			while (rs.next()) {
				driver = new Drivers();
			
				driver.setId(rs.getInt("drivers.id"));
				driver.setVehicle_id(rs.getInt("vehicle_id"));
				driver.setPlate_no(rs.getString("plate_no"));
				driver.setVehicle(rs.getString("make.make")+" "+rs.getString("model.model"));
				driver.setDriver_name(rs.getString("driver_name"));
		
				drivers.add(driver);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<Vehicleholoteq> getDrivername() method: " + e);
		} finally {
			conn.close();
		}
		return drivers;
	}
	
	/**
	 * Assign a Driver
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static Boolean assignDriver(int vehicle_id, int driver_id) throws SQLException {
		Connection conn = null;
		boolean isDone = false;
		
		String update_vehicle = "UPDATE vehicles SET driver_id = " + driver_id + " WHERE id = "+vehicle_id;
		String update_driver = "UPDATE drivers SET vehicle_id = "+ vehicle_id +" WHERE id = "+driver_id;
 
		try {
				conn = DB.getConnection();
				PreparedStatement pst_vehicle = conn.prepareStatement(update_vehicle);
				PreparedStatement pst_driver = conn.prepareStatement(update_driver);
					
				
				int vehicle_affected = pst_vehicle.executeUpdate();
				int driver_affected = pst_driver.executeUpdate();
				
		
				 if(vehicle_affected == 1 && driver_affected == 1) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				logger.error("Error on boolean assignDriver() method: ",e);
			} finally {
				conn.close();
			}
		
		return isDone;
	}
	
	/**
	 * Assign a Driver
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static Boolean returnVehicle(int vehicle_id, int driver_id) throws SQLException {
		Connection conn = null;
		boolean isDone = false;
		
		String update_vehicle = "UPDATE vehicles SET driver_id =0 WHERE id = "+vehicle_id;
		String update_driver = "UPDATE drivers SET vehicle_id =0 WHERE id = "+driver_id;
 
		try {
				conn = DB.getConnection();
				PreparedStatement pst_vehicle = conn.prepareStatement(update_vehicle);
				PreparedStatement pst_driver = conn.prepareStatement(update_driver);
					
				
				int vehicle_affected = pst_vehicle.executeUpdate();
				int driver_affected = pst_driver.executeUpdate();
				
		
				 if(vehicle_affected == 1 && driver_affected == 1) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				logger.error("Error on boolean assignDriver() method: ",e);
			} finally {
				conn.close();
			}
		
		return isDone;
	}
	
	
	/**
	 * Assign A Driver
	 * @param vehicle_id
	 * @param driver_id
	 * @return
	 * @throws SQLException
	 */
	public static Boolean assignDriverToVehicle(int vehicle_id, int driver_id) throws SQLException {
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "INSERT INTO vehicle_report (vehicle_id, driver_id, cdate, sdate, status) VALUES ( ?, ?, now(),now(), 1 )";

		try {
				conn = DB.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					
				pst.setInt(1, vehicle_id);
				pst.setInt(2, driver_id);
				
				int affected = pst.executeUpdate();
				System.out.println("affected ----->"+affected);
				 if(affected == 1 ) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				System.out.println("exception in aassignDriverToVehicle : " + e.getMessage());
				logger.error("Error on boolean assignDriverToVehicle() method: ",e);
			} finally {
				conn.close();
			}
		
		return isDone;
	}
	
	/**
	 * Return Vehicle
	 * @param vehicle_id
	 * @param driver_id
	 * @param remark
	 * @return
	 * @throws SQLException
	 */
	public static Boolean returnVehicle(int vehicle_id, int driver_id,String remark) throws SQLException {
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "update vehicle_report set rdate=now(),status=0,remark=? where vehicle_id=? and driver_id=?";

		try {
				conn = DB.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, remark);	
				pst.setInt(2, vehicle_id);
				pst.setInt(3, driver_id);
				
				int affected = pst.executeUpdate();
				System.out.println("affected ----->"+affected);
				 if(affected == 1 ) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				System.out.println("exception in returnVehicle : " + e.getMessage());
				logger.error("Error on boolean returnVehicle() method: ",e);
			} finally {
				conn.close();
			}
		
		return isDone;
	}
	
	
	/**
	 * Fetch Driver List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public static String fetchDriverList() throws SQLException {
		
		ArrayList<Drivers> driver_list = new ArrayList<Drivers>();
		String driversJSON = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Drivers driver;
		
		String sql = "SELECT drivers.id AS driverId, driver_name, license_no, given_date, returned_date, plate_no, penaltyCount.penalty_count, accidentCount.accident_count" 
		              + " FROM drivers"
				      + " LEFT JOIN vehicles ON vehicles.id = drivers.vehicle_id"
				      + " LEFT JOIN ("
				      + " SELECT driver_id, COUNT(driver_id) penalty_count FROM vehicle_fines"
				      + " group by driver_id ) AS penaltyCount ON drivers.id = penaltyCount.driver_id"
				      + " LEFT JOIN ("
				      + " SELECT driver_id, COUNT(driver_id) accident_count FROM vehicle_accidents"
				      + " group by driver_id ) AS accidentCount ON drivers.id = accidentCount.driver_id";
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			
			while(rs.next()) {
				driver =  new Drivers();
				
				driver.setId(rs.getInt("driverId"));
				driver.setDriver_name(rs.getString("driver_name"));
				driver.setLicense_no(rs.getString("license_no"));
				driver.setGiven_date(rs.getString("given_date"));
				
				if(rs.getString("penalty_count") == null) {
					driver.setPenalty_count(0);
				}else {
					driver.setPenalty_count(rs.getInt("penalty_count"));
				}
				
				if(rs.getString("accident_count") == null) {
					driver.setAccident_count(0);
				}else {
					driver.setAccident_count(rs.getInt("accident_count"));
				}
				
				if(rs.getString("returned_date") == null) {
				  driver.setReturned_date("Current Driver");
				}else {
				  driver.setReturned_date(rs.getString("returned_date"));
				}
				
				if(rs.getString("plate_no") == null) {
				 driver.setPlate_no("Not Assigned");
				}else {
				 driver.setPlate_no(rs.getString("plate_no"));
				}
				
				driver_list.add(driver);
			}
			
		} catch (SQLException e) {
			System.out.println("Error on fetchDriverList method: " + e);
		} finally {
			conn.close();
		}
		driversJSON = new Gson().toJson(driver_list);
		
		return driversJSON;
	}
	
	/**
	 * Get Vehicle Isthimara image
	 * @param id
	 * @return byte[image]
	 * @throws SQLException
	 */
	public byte[] getVehicleImages(int id,String imageNo) throws SQLException {
		byte data[] = null;
		String sql =null;
		if(imageNo != null) {
			sql = "SELECT permit_img_"+imageNo+" FROM vehicles WHERE id = " + id;
		}else {
			sql = "SELECT permit_img FROM vehicles WHERE id = " + id;
		}
		
  
		Connection conn = DB.getConnection();
		Statement stm = null;
		ResultSet rs = null;
		Blob file = null;
		
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);

			if (rs.next()) {
				file = rs.getBlob(1);
				data = file.getBytes(1, (int) file.length());
			}

		} catch (Exception e) {
			logger.error("Error on byte[] getComplaintImages(int id)  method: "+ e);
		} finally {
		  conn.close();
		}

		return data;
	}
	
	/**
	 * Get Driver image
	 * @param id
	 * @return byte[image]
	 * @throws SQLException
	 */
	public byte[] getDriverLicenseImages(int id) throws SQLException {
		byte data[] = null;
		String sql = "SELECT license_img FROM drivers WHERE id = " + id;

		Connection conn = DB.getConnection();
		Statement stm = null;
		ResultSet rs = null;
		Blob file = null;
		
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);

			if (rs.next()) {
				file = rs.getBlob(1);
				data = file.getBytes(1, (int) file.length());
			}

		} catch (Exception e) {
			logger.error("Error on byte[] getDriverImages(int id)  method: "+ e);
		} finally {
		  conn.close();
		}

		return data;
	}
	
	
	/**
	 * Get Vehicle Isthimara image
	 * @param id
	 * @return byte[image]
	 * @throws SQLException
	 */
	public byte[] getVehicleProImages(int id,String imageNo) throws SQLException {
		byte data[] = null;
		String sql =null;
		if(imageNo != null) {
			sql = "SELECT vehicle_img_"+imageNo+" FROM vehicles WHERE id = " + id;
		}else {
			sql = "SELECT vehicle_img FROM vehicles WHERE id = " + id;	
		}
		

		Connection conn = DB.getConnection();
		Statement stm = null;
		ResultSet rs = null;
		Blob file = null;
		
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);

			if (rs.next()) {
				file = rs.getBlob(1);
				data = file.getBytes(1, (int) file.length());
			}

		} catch (Exception e) {
			logger.error("Error on byte[] getComplaintImages(int id)  method: "+ e);
		} finally {
		  conn.close();
		}

		return data;
	}
	
	/**
	 * Get Driver image
	 * @param id
	 * @return byte[image]
	 * @throws SQLException
	 */
	public byte[] getDriverImages(int id) throws SQLException {
		byte data[] = null;
		String sql = "SELECT photo FROM drivers WHERE id = " + id;

		Connection conn = DB.getConnection();
		Statement stm = null;
		ResultSet rs = null;
		Blob file = null;
		
		try {
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);

			if (rs.next()) {
				file = rs.getBlob(1);
				data = file.getBytes(1, (int) file.length());
			}

		} catch (Exception e) {
			logger.error("Error on byte[] getDriverImages(int id)  method: "+ e);
		} finally {
		  conn.close();
		}

		return data;
	}

	/**
	 * update Vehicle Details
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static Boolean updateVehicle(int vehicle_id, String plate_no, String permit_date, String make, String model,
										byte[] permit_img,byte[] permit_img2,
										byte[] vehicleImg, byte[] vehicleImg2, byte[] vehicleImg3, byte[] vehicleImg4) throws SQLException {
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "UPDATE vehicles SET plate_no = '"+plate_no+"', make= '"+make+"', model= '"+model+"', permit_date= '"+permit_date+"' ";
		        
		        int param=0;
		        int vImageIndx=0;
		        int v2ImageIndx=0;
		        int v3ImageIndx=0;
		        int v4ImageIndx=0;
		        
		        int pImageIndx=0;
		        int p2ImageIndx=0;
		        if(vehicleImg != null && vehicleImg.length > 0) {
		        	param++;		        	
					sql +=",vehicle_img =?";
					vImageIndx=param;
		        }
		        
		        if(vehicleImg2 != null && vehicleImg2.length > 0) {
		        	param++;		        	
					sql +=",vehicle_img_2 =?";
					v2ImageIndx=param;
		        }
		        
		        if(vehicleImg3 != null && vehicleImg3.length > 0) {
		        	param++;		        	
					sql +=",vehicle_img_3 =?";
					v3ImageIndx=param;
		        }
		        
		        if(vehicleImg4 != null && vehicleImg4.length > 0) {
		        	param++;		        	
					sql +=",vehicle_img_4 =?";
					v4ImageIndx=param;
		        }
		        
		        if(permit_img != null && permit_img.length > 0) {
		        	param++;
					sql+=",permit_img=?";
					pImageIndx=param;
		        }
		        
		        if(permit_img2 != null && permit_img2.length > 0) {
		        	param++;
					sql+=",permit_img_2=?";
					p2ImageIndx=param;
		        }
		        
		       
		        
				
				sql += "WHERE id = "+vehicle_id;
         System.out.println(sql);
		try {
				conn = DB.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql);
				 if(vehicleImg != null && vehicleImg.length > 0) {
			        	InputStream vehicleImgInputStream = new ByteArrayInputStream(vehicleImg);
						if (vehicleImgInputStream != null) {
							pst.setBinaryStream(vImageIndx, vehicleImgInputStream);	
						}
			    }
				 
				 if(vehicleImg2 != null && vehicleImg2.length > 0) {
			        	InputStream vehicleImgInputStream2 = new ByteArrayInputStream(vehicleImg2);
						if (vehicleImgInputStream2 != null) {
							pst.setBinaryStream(v2ImageIndx, vehicleImgInputStream2);	
						}
			    }
				 
				 if(vehicleImg3 != null && vehicleImg3.length > 0) {
			        	InputStream vehicleImgInputStream3 = new ByteArrayInputStream(vehicleImg3);
						if (vehicleImgInputStream3 != null) {
							pst.setBinaryStream(v3ImageIndx, vehicleImgInputStream3);	
						}
			    }
				 
				 if(vehicleImg4 != null && vehicleImg4.length > 0) {
			        	InputStream vehicleImgInputStream4 = new ByteArrayInputStream(vehicleImg4);
						if (vehicleImgInputStream4 != null) {
							pst.setBinaryStream(v4ImageIndx, vehicleImgInputStream4);	
						}
			    }
			        
			    if(permit_img != null && permit_img.length > 0) {
			        	InputStream photoInputStream = new ByteArrayInputStream(permit_img);
						if (photoInputStream != null) {
							pst.setBinaryStream(pImageIndx, photoInputStream);	
						}
			        	
			    }
			    
			    if(permit_img2 != null && permit_img2.length > 0) {
		        	InputStream photoInputStream2 = new ByteArrayInputStream(permit_img2);
					if (photoInputStream2 != null) {
						pst.setBinaryStream(p2ImageIndx, photoInputStream2);	
					}
		        	
			    }
			    
			    
			    
				
				int affected = pst.executeUpdate();
				System.out.println("affected----->"+affected);
		
				 if(affected == 1) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				System.out.println("Error on boolean updateVehicle() method: "+e);
			} finally {
				conn.close();
			}
		System.out.println("update Status in DAO class->"+isDone);
		return isDone;
	}


	/**
	 * Get Vehicle by id
	 * 
	 * @param id
	 * @return Array list
	 * @throws SQLException
	 */
	public static String fetchVehicleById(Integer id) throws SQLException {
		String vehicleJSON = "";
		ArrayList<Vehicleholoteq> vehicle_details = new ArrayList<Vehicleholoteq>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Vehicleholoteq vehicle;
		
		String sql = "SELECT plate_no, make.make, model.model, permit_date FROM vehicles"
				+" LEFT JOIN make ON make.id = vehicles.make"
				+" LEFT JOIN model ON model.id = vehicles.model"
				+ " WHERE vehicles.id = "+ id;
		
		System.out.println("SQL --->"+sql);
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				vehicle = new Vehicleholoteq();
				
				vehicle.setPlate_no(rs.getString("plate_no"));
				vehicle.setMake(rs.getString("make.make"));
				vehicle.setModel(rs.getString("model.model"));
				vehicle.setPermit_date(rs.getString("permit_date"));
				
				vehicle_details.add(vehicle);
			}
		
		} catch (SQLException e) {
			System.out.println("Error on String fetchVehicleById(Integer id) method: " + e);
		} finally {
			conn.close();
		}
		
		vehicleJSON = new Gson().toJson(vehicle_details);
		System.out.println("Vehicles Array JSON-->:"+vehicleJSON);
	    return vehicleJSON;
	}

	/**
	 * Get Driver by id
	 * 
	 * @param id
	 * @return Array list
	 * @throws SQLException
	 */
	public static String fetchDriverById(Integer id) throws SQLException {
		String driverJSON = "";
		ArrayList<Drivers> driver_details = new ArrayList<Drivers>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Drivers driver;
		
		String sql = "SELECT driver_name, license_no, given_date FROM drivers WHERE id = "+ id;
		
		System.out.println("SQL --->"+sql);
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				driver = new Drivers();
				
				driver.setDriver_name(rs.getString("driver_name"));
				driver.setLicense_no(rs.getString("license_no"));
				driver.setGiven_date(rs.getString("given_date"));
				
				driver_details.add(driver);
			}
		
		} catch (SQLException e) {
			System.out.println("Error on String fetchVehicleById(Integer id) method: " + e);
		} finally {
			conn.close();
		}
		
		driverJSON = new Gson().toJson(driver_details);
		System.out.println("Driver Array JSON-->:"+driverJSON);
	    return driverJSON;
	}

	/**
	 * update driver Details
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static Boolean updateDriver(int driver_id, String driver_name, String given_date, String license_no,byte[] license_img, byte[] profileImg) throws SQLException {
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "UPDATE drivers SET driver_name = '"+driver_name+"', license_no = '"+license_no+"', given_date= '"+given_date+"' ";
		 	int param=0;
	        int lImageIndx=0;
	        int pImageIndx=0;
	        if(license_img != null && license_img.length > 0) {
	        	param++;		        	
				sql +=",license_img =?";
				lImageIndx=param;
	        }
	        
	        if(profileImg != null && profileImg.length > 0) {
	        	param++;
				sql+=",photo=?";
				pImageIndx=param;
	        }
	        
		sql+= "WHERE id = "+driver_id;
         System.out.println(sql);
		try {
				conn = DB.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql);
				if(license_img != null && license_img.length > 0) {
		        	InputStream vehicleImgInputStream = new ByteArrayInputStream(license_img);
					if (vehicleImgInputStream != null) {
						pst.setBinaryStream(lImageIndx, vehicleImgInputStream);	
					}
		        }
		        
		        if(profileImg != null && profileImg.length > 0) {
		        	InputStream photoInputStream = new ByteArrayInputStream(profileImg);
					if (photoInputStream != null) {
						pst.setBinaryStream(pImageIndx, photoInputStream);	
					}
		        	
		        }
				int affected = pst.executeUpdate();
				System.out.println("affected----->"+affected);
		
				 if(affected == 1) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				System.out.println("Error on boolean updateDriver() method: "+e);
			} finally {
				conn.close();
			}
		System.out.println("update Status in DAO class->"+isDone);
		return isDone;
	}

	/**
	 * Delete Vehicle Details
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static Boolean deleteVehicle(int vehicle_id, String reason) throws SQLException {
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "INSERT INTO vehicles_history (id, plate_no, make, model, permit_img, permit_date, created_date, driver_id, deleted_reason) "
				     + " SELECT id, plate_no, make, model, permit_img, permit_date, created_date, driver_id, '"+ reason +"' FROM vehicles "
				     + " WHERE id = "+vehicle_id;
		
		String sql_delete = "DELETE FROM vehicles WHERE id = "+vehicle_id;
		try {
				conn = DB.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql);
				PreparedStatement pst_delete = conn.prepareStatement(sql_delete);
					
				int affected = pst.executeUpdate();
				int affected_delete = pst_delete.executeUpdate();
				if(affected_delete == 1) {
					removeDriverVehicle(vehicle_id);
				}		
				 if(affected == 1 && affected_delete == 1) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				System.out.println("Error on boolean deleteVehicle() method: "+e);
			} finally {
				conn.close();
			}
		System.out.println("delete Status in DAO class->"+isDone);
		return isDone;
	}
	
	public static Boolean removeDriverVehicle(int vehicle_id) throws SQLException {
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "update db_assets.drivers set vehicle_id=0 where vehicle_id="+vehicle_id;
 
		try {
				conn = DB.getConnection();
				PreparedStatement updateDriver = conn.prepareStatement(sql);
				
				int result = updateDriver.executeUpdate();

				 if(result==1) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				logger.error("Error on boolean removeDriverVehicle() method: ",e);
			} finally {
				conn.close();
			}
		
		return isDone;
	}

	/**
	 * Delete driver Details
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static Boolean deleteDriver(int driver_id, String reason) throws SQLException {
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "INSERT INTO drivers_history (id, vehicle_id, driver_name, license_no, license_img, given_date, returned_date, created_date, deleted_reason) "
				     + " SELECT id, vehicle_id, driver_name, license_no, license_img, given_date, returned_date, created_date, '"+ reason +"' FROM drivers "
				     + " WHERE id = "+driver_id;
		
		String sql_delete = "DELETE FROM drivers WHERE id = "+driver_id;
         System.out.println(sql);
         System.out.println(sql_delete);
         
		try {
				conn = DB.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql);
				PreparedStatement pst_delete = conn.prepareStatement(sql_delete);
					
				int affected = pst.executeUpdate();
				int affected_delete = pst_delete.executeUpdate();
				
				if(affected_delete ==1) {
					removeVehicleDriver(driver_id);	
				}
		
				 if(affected == 1 && affected_delete == 1) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				System.out.println("Error on boolean deleteDriver() method: "+e);
			} finally {
				conn.close();
			}
		System.out.println("delete Status in DAO class->"+isDone);
		return isDone;
	}
	
	public static Boolean removeVehicleDriver(int driver_id) throws SQLException {
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "update db_assets.vehicles set driver_id=0 where driver_id="+driver_id;
 
		try {
				conn = DB.getConnection();
				PreparedStatement updateVehicle = conn.prepareStatement(sql);
				
				int result = updateVehicle.executeUpdate();

				 if(result==1) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
				logger.error("Error on boolean removeVehicleDriver() method: ",e);
			} finally {
				conn.close();
			}
		
		return isDone;
	}
	
	/*
	 * Validate vehicle plate No 
	 * @return boolean
	 * @throws SQLException
	 * 
	 */
	public static boolean validatePlateNo(String plate_no) throws SQLException{
		 
		boolean validatePlateNo = false;
        int resultsCounter = 0;
        
    	Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
        
    	try {
			String sql = "SELECT COUNT(*) FROM vehicles WHERE plate_no = '"+plate_no+"' ";
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if(rs.next()) {
				resultsCounter = rs.getInt(1);
			}           
            if (resultsCounter == 0) {
                // containg null result
            	validatePlateNo = false;
            }
            else{
                //This conains the result you want
            	validatePlateNo = true;
            }
            
        }catch (Exception e) {
			logger.error("Error on boolean validatePlateNo(String plate_no) method: ",e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return validatePlateNo;
    }
	
	/*
	 * Validate Driver License no 
	 * @return boolean
	 * @throws SQLException
	 * 
	 */
	public static boolean validateLicenseNo(String license_no) throws SQLException{
		 
		boolean validateLicenseNo = false;
        int resultsCounter = 0;
        
    	Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
        
    	try {
			String sql = "SELECT COUNT(*) FROM drivers WHERE license_no = '"+license_no+"' ";
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			if(rs.next()) {
				resultsCounter = rs.getInt(1);
			}
            
            System.out.println("Result Counter : "+resultsCounter);
            
            if (resultsCounter == 0) {
                // containg null result
            	validateLicenseNo = true;
            	System.out.println("true------------------------");
            }
            else{
                //This conains the result you want
            	validateLicenseNo = false;
            	System.out.println("false------------------------");
            }
            
        }catch (Exception e) {
			logger.error("Error on boolean validateLicenseNo(String license_no) method: ",e);
			e.printStackTrace();
		} finally {
			conn.close();
		}

		return validateLicenseNo;
    }
	
	/*
	 * Validate Vehicle plate no for update 
	 * @return boolean
	 * @throws SQLException
	 * 
	 */
		public static String velidateVehiclePlateNoUpdate(String plate_no) throws SQLException {
			
			String plate_no_JSON = "";
			ArrayList<Vehicleholoteq> plate_no_list = new ArrayList<Vehicleholoteq>();
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;
			Vehicleholoteq plateno;
			
			String sql = "SELECT plate_no FROM vehicles WHERE plate_no = "+ plate_no;
			
			System.out.println("SQL --->"+sql);
			
			try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				while (rs.next()) {
					plateno = new Vehicleholoteq();
					
					plateno.setPlate_no(rs.getString("plate_no"));;
					
					plate_no_list.add(plateno);
				}
			
			} catch (SQLException e) {
				System.out.println("Error on String velidateVehiclePlateNoUpdate(String velidateVehiclePlateNoUpdate) method: " + e);
			} finally {
				conn.close();
			}
			
			plate_no_JSON = new Gson().toJson(plate_no_list);
			System.out.println("plate_no_JSON Array JSON-->:"+plate_no_JSON);
		    return plate_no_JSON;
		}
	
	
	/*
	 * Validate Driver License no for update 
	 * @return boolean
	 * @throws SQLException
	 * 
	 */
		public static String validateLicenseNoUpdate(String license_no) throws SQLException {
			
			String plate_no_JSON = "";
			ArrayList<Drivers> plate_no_list = new ArrayList<Drivers>();
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;
			Drivers plate_no;
			
			String sql = "SELECT license_no from drivers WHERE license_no = "+ license_no;
			
			System.out.println("SQL --->"+sql);
			
			try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				while (rs.next()) {
					plate_no = new Drivers();
					
					plate_no.setLicense_no(rs.getString("license_no"));;
					
					plate_no_list.add(plate_no);
				}
			
			} catch (SQLException e) {
				System.out.println("Error on String validateLicenseNoUpdate(String license_no) method: " + e);
			} finally {
				conn.close();
			}
			
			plate_no_JSON = new Gson().toJson(plate_no_list);
			System.out.println("plate_no_JSON Array JSON-->:"+plate_no_JSON);
		    return plate_no_JSON;
		}
		
		
		public static List<Vehicleholoteq> fetchVehicleReportList(String dateString) throws SQLException {
			
			List<Vehicleholoteq> vehicleList = new ArrayList<Vehicleholoteq>();			
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;
			Vehicleholoteq vehicle;
			
			String sql = "SELECT plate_no, make.make, model.model,driver_name,cdate,sdate,rdate,status,remark"
					+ " FROM vehicle_report "
					+ "left join vehicles on vehicles.id=vehicle_report.vehicle_id "
					+" LEFT JOIN make ON make.id = vehicles.make"
					+" LEFT JOIN model ON model.id = vehicles.model"
					+ " LEFT JOIN drivers ON drivers.id = vehicle_report.driver_id";
			if(dateString != null) {
				sql +=" where date(cdate)='"+dateString+"'";
			}
			try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				
				while(rs.next()) {
					vehicle =  new Vehicleholoteq();
					vehicle.setPlate_no(rs.getString("plate_no"));
					vehicle.setMake(rs.getString("make.make"));
					vehicle.setModel(rs.getString("model.model"));
					vehicle.setDriver_name(rs.getString("driver_name"));
					
					vehicle.setcDate(rs.getTimestamp("cdate"));
					vehicle.setsDate(rs.getTimestamp("sdate"));
					vehicle.setrDate(rs.getTimestamp("rdate"));
					vehicle.setStatus(rs.getInt("status"));
					vehicle.setRemark(rs.getString("remark"));
					
					if(vehicle.getPlate_no() != null && vehicle.getDriver_name() != null)
					vehicleList.add(vehicle);
				}
				
			} catch (SQLException e) {
				System.out.println("Error on fetchVehicleList method: " + e);
			} finally {
				conn.close();
			}
			
			
			return vehicleList;
		}
		
		/**
		 * Get All Makes
		 * @return
		 * @throws SQLException
		 */
		public List<Make> getAllMake() throws SQLException {
			List<Make> arrList = new ArrayList<Make>();
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;

			Make mak;

			String sql = "select * from make";
			try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				mak = new Make();
				mak.setId(rs.getInt("id"));
				mak.setMake(rs.getString("make"));
				arrList.add(mak);
			}
			}catch(Exception e) {
				logger.error("Error on ArrayList<Make> getAllMake() method: "+ e);
			} finally {
				conn.close();
				stm.close();
			}

			return arrList;
		}
		
		/**
		 * Get All Model By Make
		 * @param makeId
		 * @return
		 * @throws SQLException
		 */
		public List<Model> getAllModelsByMake(String makeIdStr) throws SQLException {
			List<Model> arrList = new ArrayList<Model>();
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;

			Model model;

			String sql = "select * from model where make_id="+makeIdStr;
			try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			while (rs.next()) {
				model = new Model();
				model.setId(rs.getInt("id"));
				model.setModel(rs.getString("model"));
				arrList.add(model);
			}
			}catch(Exception e) {
				logger.error("Error on ArrayList<Make> getAllModelsByMake() method: "+ e);
			} finally {
				conn.close();
				stm.close();
			}

			return arrList;
		}
		
		/**
		 * Get Vehicle Services Type List
		 * 
		 * @return Array list
		 * @throws SQLException
		 */
		public ArrayList<VehicleServiceType> getVehicleServicesList() throws SQLException {
		
			ArrayList<VehicleServiceType> service_type_list = new ArrayList<VehicleServiceType>();
			Connection conn = null;
			Statement stm = null;
			ResultSet rs = null;
			VehicleServiceType service_type;
			
			String sql = "SELECT id, service_type FROM vehicle_service_type ORDER BY id";
			
			try {
				conn = DB.getConnection();
				stm = conn.createStatement();
				rs = stm.executeQuery(sql);
				
				while (rs.next()) {
					service_type = new VehicleServiceType();
				
					service_type.setId(rs.getInt("id"));
					service_type.setService_type(rs.getString("service_type"));
			
					service_type_list.add(service_type);
				}
			
			} catch (SQLException e) {
				System.out.println("Error on ArrayList<VehicleServiceType> getVehicleServicesList() method: " + e);
			} finally {
				conn.close();
			}
			
			return service_type_list;
		}

		
		/**
		 * Insert Vehicle Service Details
		 
		 * @return boolean[true/false]
		 * @throws SQLException
		 */	
		public static Boolean addVehicleServices(int vehicle, int driver, int service_type, String date, Double cost,String place) throws SQLException {
			
			Connection conn = null;
			boolean isDone = false;
			
			String sql = "INSERT INTO vehicle_services(vehicle_id, service_type_id, driver_id, date, cost, place) VALUES (?,?,?,?,?,?)";
			   System.out.println("SQL Add vehicle services--------------> "+sql);
			try {
				conn = DB.getConnection();
				PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					
				pst.setInt(1, vehicle);
				pst.setInt(2, service_type);
				pst.setInt(3, driver);
				pst.setString(4, date);
				pst.setDouble(5, cost);
				pst.setString(6, place);
				
				int affected = pst.executeUpdate();
				 System.out.println("affected--> "+affected);
				 if(affected == 1 ) {
					 isDone = true;
				 }else {
					 isDone = false;
				 }
			}catch (Exception e) {
			   System.out.println("Exception e "+e);
			   logger. error("Error on boolean addVehicleServices(int vehicle, int driver, int service_type, String date, float cost,String place) method: ",e);
			} finally {
				conn.close();
			}
             System.out.println("SQL Boolean Add vehicle Services--> "+isDone);
			return isDone;
		}

		/**
		 * Fetch Vehicle Service Details
		 
		 * @return boolean[true/false]
		 * @throws SQLException
		 */	
		public static String fetchVehicleServicesList(Map<String, String> filters, Integer draw, Integer length,
				Integer start) throws SQLException{
			
			String VehicleServicesJSON = "";

			Integer totalVehicleServicesCount = fetchVehicleServicesCount(filters);
			
			List<VehicleServices> vehicleSrvicesList = new ArrayList<VehicleServices>();

			JsonObject jsonOBJ = new JsonObject();
			jsonOBJ.addProperty("draw", draw);

			String sql = "SELECT vehicle_services.id, vehicles.plate_no AS vehicle, vehicle_service_type.service_type AS service_type, drivers.driver_name AS driver, date, cost, place FROM vehicle_services"
					+ " LEFT JOIN vehicles ON vehicles.id = vehicle_services.vehicle_id"
					+ " LEFT JOIN vehicle_service_type ON vehicle_service_type.id = vehicle_services.service_type_id"
					+ " LEFT JOIN drivers ON drivers.id = vehicle_services.driver_id WHERE 1 = 1";
			
				  if (filters.size() > 0) {
				  
					  if (filters.get("search_filter") != null) { 
						
					      sql += " AND vehicles.plate_no LIKE '%" + filters.get("search_filter") + "%'";
				      }
					  if (filters.get("date") != null) { 
							
					      sql += " AND date LIKE '%" + filters.get("date") + "%'";
				      }
					  if (filters.get("vehicle") != null) { 
							
					      sql += " AND vehicles.plate_no LIKE '%" + filters.get("vehicle") + "%'";
				      }
					  if (filters.get("service_type") != null) { 
							
					      sql += " AND service_type LIKE '%" + filters.get("service_type") + "%'";
				      }
				  }
				  sql += " ORDER BY date DESC LIMIT " + length + " OFFSET " +start;
			 
            System.out.println("SQL -->"+sql);
			try (Connection connection = DB.getConnection();
		      PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

				final ResultSet rs = preparedStatement.executeQuery();

         //Process the ResultSet object.
				while (rs.next()) {
					
				    int id = rs.getInt("vehicle_services.id");
					String vehicle = rs.getString("vehicle");
					String service_type = rs.getString("service_type");
					String driver = rs.getString("driver");
					String date = rs.getString("date");
					double cost = rs.getDouble("cost");
					String place = rs.getString("place");
					
					vehicleSrvicesList.add(new VehicleServices(id,vehicle,driver,service_type,date,cost,place));
				}
				
				jsonOBJ.addProperty("recordsTotal", totalVehicleServicesCount);
				jsonOBJ.addProperty("recordsFiltered", totalVehicleServicesCount);
				jsonOBJ.addProperty("data", new Gson().toJson(vehicleSrvicesList));

				VehicleServicesJSON = new Gson().toJson(jsonOBJ);

			} catch (SQLException e) {
				logger.error("Error on String fetchTrainersByCardStatus method: " + e);
				System.out.println("SQLException e"+e);
			}
			return VehicleServicesJSON;
		}

		/*
		 *  Fetch Vehicle Services Count
		 *  
		 *  @return count
		 */
		private static Integer fetchVehicleServicesCount(Map<String, String> filters) {
			Integer totalVehicleServices = 0;

			String sql = "SELECT COUNT(vehicles.plate_no) FROM vehicle_services"
					+ " LEFT JOIN vehicles ON vehicles.id = vehicle_services.vehicle_id"
					+ " LEFT JOIN vehicle_service_type ON vehicle_service_type.id = vehicle_services.service_type_id"
					+ " LEFT JOIN drivers ON drivers.id = vehicle_services.driver_id WHERE 1 = 1";
             
					  if (filters.get("search_filter") != null) { 
							
					      sql += " AND vehicles.plate_no LIKE '%" + filters.get("search_filter") + "%'";
				      }
					  if (filters.get("date") != null) { 
							
					      sql += " AND date LIKE '%" + filters.get("date") + "%'";
				      }
					  if (filters.get("vehicle") != null) { 
							
					      sql += " AND vehicles.plate_no LIKE '%" + filters.get("vehicle") + "%'";
				      }
					  if (filters.get("service_type") != null) { 
							
					      sql += " AND service_type LIKE '%" + filters.get("service_type") + "%'";
				      }
		 
			try (Connection connection = DB.getConnection(); Statement statement = connection.createStatement();) {

				final ResultSet rs = statement.executeQuery(sql);
				rs.next();
				totalVehicleServices = rs.getInt(1);

			} catch (SQLException e) {
				logger.error("Error on Integer fetchVehicleServicesCount method: " + e);
			}
           
			return totalVehicleServices;
		}

		/**
		 * Insert Accident Details
		 
		 * @return boolean[true/false]
		 * @throws SQLException
		 */	
		public static Boolean addAccidentDetails(int vehicle_id, int driver_id, String date, String remark, byte[] photoByte, byte[] photoByte2, byte[] photoByte3) throws SQLException {
			Connection conn = null;
			boolean isDone = false;
			
			String sql = "INSERT INTO vehicle_accidents (vehicle_id, driver_id, date, remarks, img1, img2, img3) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

			try {
					conn = DB.getConnection();
					PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						
					pst.setInt(1, vehicle_id);
					pst.setInt(2, driver_id);
					pst.setString(3, date);
					pst.setString(4, remark);
					
					if(photoByte != null) {
						InputStream photoInputStream = new ByteArrayInputStream(photoByte);
						if (photoInputStream != null) {
							pst.setBinaryStream(5, photoInputStream);
						}
					}else {
						pst.setBinaryStream(5, null);
					}
					
					if(photoByte2 != null) {
						InputStream photoInputStream2 = new ByteArrayInputStream(photoByte2);
						if (photoInputStream2 != null) {
							pst.setBinaryStream(6, photoInputStream2);
						}
					}else {
						pst.setBinaryStream(6, null);
					}
					
					if(photoByte3 != null) {
						InputStream vehicleImgInputStream = new ByteArrayInputStream(photoByte3);
						if (vehicleImgInputStream != null) {
							pst.setBinaryStream(7, vehicleImgInputStream);
						}
					}else {
						pst.setBinaryStream(7, null);	
					}
					
					int affected = pst.executeUpdate();
					System.out.println("affected ----->"+affected);
				    System.out.println("sql--------------------->"+sql);
					 if(affected == 1 ) {
						 isDone = true;
					 }else {
						 isDone = false;
					 }
				}catch (Exception e) {
					System.out.println("exception in vehical dao : " + e.getMessage());
					logger.error("Error on boolean addAccidentDetails() method: ",e);
				} finally {
					conn.close();
				}
			
			return isDone;
		}


		/**
		 * Insert Penalty Details
		 
		 * @return boolean[true/false]
		 * @throws SQLException
		 */	
		public static Boolean addPenaltyDetails(int vehicle_id, int driver_id, String penalty_cat, String penalty_date, String remark) throws SQLException {
			Connection conn = null;
			boolean isDone = false;
			
			String sql = "INSERT INTO vehicle_fines (vehicle_id, driver_id, category, date, remarks) "
					+ "VALUES (?, ?, ?, ?, ?)";

			try {
					conn = DB.getConnection();
					PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						
					pst.setInt(1, vehicle_id);
					pst.setInt(2, driver_id);
					pst.setString(3, penalty_cat);
					pst.setString(4, penalty_date);
					pst.setString(5, remark);
					
					int affected = pst.executeUpdate();
					System.out.println("affected ----->"+affected);
				    System.out.println("sql--------------------->"+sql);
					 if(affected == 1 ) {
						 isDone = true;
					 }else {
						 isDone = false;
					 }
				}catch (Exception e) {
					System.out.println("exception in vehical dao : " + e.getMessage());
					logger.error("Error on boolean addPenaltyDetails() method: ",e);
				} finally {
					conn.close();
				}
			
			return isDone;
		}


		/**
		 * Fetch Vehicle Accident Details
		 
		 * @return boolean[true/false]
		 * @throws SQLException
		 */	
		public static String fetchVehicleAccidentsList(Map<String, String> filters, Integer draw, Integer length,Integer start) throws SQLException {
			String VehicleAccidentsJSON = "";

			Integer totalVehicleAccidentsCount = fetchVehicleAccidentsCount(filters);
			
			List<VehicleAccident> vehicleAccidentsList = new ArrayList<VehicleAccident>();

			JsonObject jsonOBJ = new JsonObject();
			jsonOBJ.addProperty("draw", draw);

			String sql = "SELECT vehicle_accidents.id, vehicles.plate_no AS vehicle, drivers.driver_name AS driver, date, remarks FROM vehicle_accidents"
					+ " LEFT JOIN vehicles ON vehicles.id = vehicle_accidents.vehicle_id"
					+ " LEFT JOIN drivers ON drivers.id = vehicle_accidents.driver_id WHERE 1 = 1";
			
				  if (filters.size() > 0) {
				  
					  if (filters.get("search_filter") != null) { 
						
					      sql += " AND vehicles.plate_no LIKE '%" + filters.get("search_filter") + "%'";
				      }
					  if (filters.get("date") != null) { 
							
					      sql += " AND date LIKE '%" + filters.get("date") + "%'";
				      }
					  if (filters.get("accident_vehicle") != null) { 
							
					      sql += " AND vehicles.plate_no LIKE '%" + filters.get("accident_vehicle") + "%'";
				      }
					  if (filters.get("accident_driver") != null) { 
							
					      sql += " AND driver_name LIKE '%" + filters.get("accident_driver") + "%'";
				      }
				  }
				  sql += " ORDER BY date DESC LIMIT " + length + " OFFSET " +start;
			 
            System.out.println("SQL -->"+sql);
			try (Connection connection = DB.getConnection();
		      PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

				final ResultSet rs = preparedStatement.executeQuery();

         //Process the ResultSet object.
				while (rs.next()) {
					
				    int id = rs.getInt("vehicle_accidents.id");
					String vehicle = rs.getString("vehicle");
					String driver = rs.getString("driver");
					String date = rs.getString("date");
					String remark = rs.getString("remarks");
					
					vehicleAccidentsList.add(new VehicleAccident(id,vehicle,driver,date,remark));
				}
				
				jsonOBJ.addProperty("recordsTotal", totalVehicleAccidentsCount);
				jsonOBJ.addProperty("recordsFiltered", totalVehicleAccidentsCount);
				jsonOBJ.addProperty("data", new Gson().toJson(vehicleAccidentsList));

				VehicleAccidentsJSON = new Gson().toJson(jsonOBJ);

			} catch (SQLException e) {
				logger.error("Error on String fetchVehicleAccidentsList method: " + e);
				System.out.println("SQLException e"+e);
			}
			return VehicleAccidentsJSON;
		}

		
		/*
		 *  Fetch Vehicle Accidents Count
		 *  
		 *  @return count
		 */
		private static Integer fetchVehicleAccidentsCount(Map<String, String> filters) {
			Integer totalVehicleAccidents = 0;

			String sql = "SELECT COUNT(*) FROM vehicle_accidents"
					+ " LEFT JOIN vehicles ON vehicles.id = vehicle_accidents.vehicle_id" 
					+ " LEFT JOIN drivers ON drivers.id = vehicle_accidents.driver_id WHERE 1 = 1";
	             
					  if (filters.get("search_filter") != null) { 
							
					      sql += " AND vehicles.plate_no LIKE '%" + filters.get("search_filter") + "%'";
				      }
					  if (filters.get("date") != null) { 
							
					      sql += " AND date LIKE '%" + filters.get("date") + "%'";
				      }
					  if (filters.get("accident_vehicle") != null) { 
							
					      sql += " AND vehicles.plate_no LIKE '%" + filters.get("accident_vehicle") + "%'";
				      }
					  if (filters.get("accident_driver") != null) { 
							
					      sql += " AND driver_name LIKE '%" + filters.get("accident_driver") + "%'";
				      }
		 
			try (Connection connection = DB.getConnection(); Statement statement = connection.createStatement();) {

				final ResultSet rs = statement.executeQuery(sql);
				rs.next();
				totalVehicleAccidents = rs.getInt(1);

			} catch (SQLException e) {
				logger.error("Error on Integer fetchVehicleAccidentsCount method: " + e);
			}
           
			return totalVehicleAccidents;
		}

		/**
		 * Fetch Vehicle Penalty Details
		 
		 * @return boolean[true/false]
		 * @throws SQLException
		 */	
		public static String fetchVehiclePenaltyList(Map<String, String> filters, Integer draw, Integer length, Integer start) throws SQLException {
			String VehiclePenaltyListJSON = "";

			Integer totalVehiclePenaltyCount = fetchVehiclePenaltyCount(filters);
			
			List<VehicleFines> vehiclePenaltyList = new ArrayList<VehicleFines>();

			JsonObject jsonOBJ = new JsonObject();
			jsonOBJ.addProperty("draw", draw);

			String sql = "SELECT vehicle_fines.id, vehicles.plate_no AS vehicle, drivers.driver_name AS driver, category, date, remarks FROM vehicle_fines"
					+ " LEFT JOIN vehicles ON vehicles.id = vehicle_fines.vehicle_id"
					+ " LEFT JOIN drivers ON drivers.id = vehicle_fines.driver_id WHERE 1 = 1";
			
				  if (filters.size() > 0) {
				  
					  if (filters.get("search_filter") != null) { 
						
					      sql += " AND vehicles.plate_no LIKE '%" + filters.get("search_filter") + "%'";
				      }
					  if (filters.get("date") != null) { 
							
					      sql += " AND date LIKE '%" + filters.get("date") + "%'";
				      }
					  if (filters.get("penalty_vehicle") != null) { 
							
					      sql += " AND vehicles.plate_no LIKE '%" + filters.get("penalty_vehicle") + "%'";
				      }
					  if (filters.get("penalty_driver") != null) { 
							
					      sql += " AND drivers.driver_name LIKE '%" + filters.get("penalty_driver") + "%'";
				      }
				  }
				  sql += " ORDER BY date DESC LIMIT " + length + " OFFSET " +start;
			 
            System.out.println("SQL -->"+sql);
			try (Connection connection = DB.getConnection();
		      PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

				final ResultSet rs = preparedStatement.executeQuery();

         //Process the ResultSet object.
				while (rs.next()) {
					
				    int id = rs.getInt("vehicle_fines.id");
					String vehicle = rs.getString("vehicle");
					String driver = rs.getString("driver");
					String category = rs.getString("category");
					String date = rs.getString("date");
					String remark = rs.getString("remarks");
					
					vehiclePenaltyList.add(new VehicleFines(id,vehicle,driver,category,date,remark));
				}
				
				jsonOBJ.addProperty("recordsTotal", totalVehiclePenaltyCount);
				jsonOBJ.addProperty("recordsFiltered", totalVehiclePenaltyCount);
				jsonOBJ.addProperty("data", new Gson().toJson(vehiclePenaltyList));

				VehiclePenaltyListJSON = new Gson().toJson(jsonOBJ);

			} catch (SQLException e) {
				logger.error("Error on String fetchVehiclePenaltyList method: " + e);
				System.out.println("SQLException e"+e);
			}
			return VehiclePenaltyListJSON;
		}

		/*
		 *  Fetch Vehicle Penalty Count
		 *  
		 *  @return count
		 */
		private static Integer fetchVehiclePenaltyCount(Map<String, String> filters) {
			Integer totalVehicleAccidents = 0;

			String sql = "SELECT COUNT(*) FROM vehicle_fines"
					+ " LEFT JOIN vehicles ON vehicles.id = vehicle_fines.vehicle_id" 
					+ " LEFT JOIN drivers ON drivers.id = vehicle_fines.driver_id WHERE 1 = 1";
             
					  if (filters.get("search_filter") != null) { 
							
					      sql += " AND vehicles.plate_no LIKE '%" + filters.get("search_filter") + "%'";
				      }
					  if (filters.get("date") != null) { 
							
					      sql += " AND date LIKE '%" + filters.get("date") + "%'";
				      }
					  if (filters.get("penalty_vehicle") != null) { 
							
					      sql += " AND vehicles.plate_no LIKE '%" + filters.get("penalty_vehicle") + "%'";
				      }
					  if (filters.get("penalty_driver") != null) { 
							
					      sql += " AND drivers.driver_name LIKE '%" + filters.get("penalty_driver") + "%'";
				      }
		 
			try (Connection connection = DB.getConnection(); Statement statement = connection.createStatement();) {

				final ResultSet rs = statement.executeQuery(sql);
				rs.next();
				totalVehicleAccidents = rs.getInt(1);

			} catch (SQLException e) {
				logger.error("Error on Integer fetchVehicleAccidentsCount method: " + e);
			}
           
			return totalVehicleAccidents;
		}
}
