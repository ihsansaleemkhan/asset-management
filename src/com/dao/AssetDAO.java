package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.beans.AssetData;
import com.beans.AssetInfo;
import com.beans.AssetType;
import com.beans.Make;
import com.beans.Model;
import com.beans.TechInfo;
import com.beans.VehicleServices;
import com.beans.EmpInfo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class AssetDAO {

	static Logger logger = Logger.getLogger(VehicleDAO.class);
	
	/**
	 * Insert Asset Details
	 * @param remark 
	 * @param warranty_amc 
	 * @param warranty_exp_date 
	 * @param procurred_date 
	 * @param memory 
	 * @param hard_disk 
	 * @param os 
	 * @param processor 
	 * @param mac_address 
	 * @param model_id 
	 * @param make_id 
	 * @param tag_name 
	 * @param barcode 
	 * @param site 
	 * @param city 
	 * @param country 
	 * @param asset_type_id 
	 * @param emp_id 
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static Boolean addAssetInfo(int asset_type_id, String country, String city, String site, String barcode, String tag_name, String make, String model, String mac_address, String processor, String os, String hard_disk, String memory, String procurred_date, String warranty_exp_date, String warranty_amc, String remark, int emp_id) throws SQLException {
		Connection conn = null;
		boolean isDone = false;
		
		String sql = "INSERT INTO tech_info (make, model, serial_no, mac_address, processor, os, hard_disk, _memory, procurred_date, warranty_exp_date, amc, remarks)"
				   + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				  
		String sql2 = "INSERT INTO asset_info (s_no, barcode, country, city, site, asset_type, tag_name, fa_code, emp_id, tech_info_id, create_at)"
				   + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);  
			PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			PreparedStatement pst2 = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			
			pst.setString(1, make);
			pst.setString(2, model);
			pst.setString(3, "");
			pst.setString(4, mac_address);
			pst.setString(5, processor);
			pst.setString(6, os);
			pst.setString(7, hard_disk);
			pst.setString(8, memory);
			pst.setString(9, procurred_date);
			pst.setString(10, warranty_exp_date);
			pst.setString(11, warranty_amc);
			pst.setString(12, remark);
			
			int affected = pst.executeUpdate();
			
			ResultSet rs = null;
			Integer key = 0;
			if(affected > 0) {
				
				isDone = true;
				
				rs = pst.getGeneratedKeys();
				
				rs.next();
				
				key = rs.getInt(1);
				System.out.println("KEYYYYYYYYYY "+key);
				
				pst2.setInt(1, 2);
				pst2.setString(2, barcode);
				pst2.setString(3, country);
				pst2.setString(4, city);
				pst2.setString(5, site);
				pst2.setInt(6, asset_type_id);
				pst2.setString(7, tag_name);
				pst2.setString(8, tag_name);
				pst2.setInt(9, emp_id);
				pst2.setInt(10, key);
				
				int tech_info_affected = pst2.executeUpdate();
				
				if(tech_info_affected > 0) {
					isDone = true;
					conn.commit();
				}else {
					conn.rollback();
				}
				
			}
			
		System.out.println("SQL 1-------------> "+sql);
		System.out.println("SQL 2-------------> "+sql2);
		
		}catch (Exception e) {
			System.out.println("exception in AssetDAO : " + e.getMessage());
			logger.error("Error on boolean addAssetInfo() method: ",e);
			isDone = false;
		} finally {
			conn.close();
		}
		return isDone;
	}
	
	/**
	 * Get Asset Type List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<AssetType> getAssetType() throws SQLException {
	
		ArrayList<AssetType> assetTypeList = new ArrayList<AssetType>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		AssetType assetType;
		
		String sql = "SELECT id, asset_type FROM asset_type GROUP BY id";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			
			while (rs.next()) {
				assetType = new AssetType();
			
				assetType.setId(rs.getInt("id"));
				assetType.setAsset_type(rs.getString("asset_type"));
				
				assetTypeList.add(assetType);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<AssetType> getAssetType() method: " + e);
		} finally {
			conn.close();
		}
		return assetTypeList;
	}
	
	/**
	 * Get Asset Owner List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<EmpInfo> getAssetOwnerList() throws SQLException {
	
		ArrayList<EmpInfo> assetOwnerList = new ArrayList<EmpInfo>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		EmpInfo assetOwner;
		
		String sql = "SELECT emp_id, first_name, last_name, mobile_no FROM emp_info";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			
			while (rs.next()) {
				assetOwner = new EmpInfo();
			
				assetOwner.setEmp_id(rs.getInt("emp_id"));
				assetOwner.setFirst_name(rs.getString("first_name"));
				assetOwner.setLast_name(rs.getString("last_name"));
				assetOwner.setMobile_no(rs.getString("mobile_no"));
				
				assetOwnerList.add(assetOwner);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<OwnerInfo> getAssetOwnerList() method: " + e);
		} finally {
			conn.close();
		}
		return assetOwnerList;
	}
	
	/**
	 * Get Make List
	 * 
	 * @return Array list
	 * @throws SQLException
	 */
	public ArrayList<Make> getAssetMakeList() throws SQLException {
	
		ArrayList<Make> assetMakeList = new ArrayList<Make>();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		Make assetMake;
		
		String sql = "SELECT make_id, make FROM asset_make";
		
		try {
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			
			while (rs.next()) {
				assetMake = new Make();
			
				assetMake.setId(rs.getInt("make_id"));
				assetMake.setMake(rs.getString("make"));
				
				assetMakeList.add(assetMake);
			
			}
		
		} catch (SQLException e) {
			System.out.println("Error on ArrayList<Make> getAssetMakeList() method: " + e);
		} finally {
			conn.close();
		}
		return assetMakeList;
	}

	/**
	 * get Asset model by Asset make
	 * 
	 * @param makeId
	 * @return JSON Asset model list
	 * @throws SQLException
	 */
	public String getModelsByMake(Integer makeId) throws SQLException {
		String modelsJSON = "";
		ArrayList<Model> models = new ArrayList<Model>();
		Model model;

		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;

		String sql = "SELECT model_id, make_id, model FROM asset_model WHERE make_id = " + makeId;

		try {

			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);

			while (rs.next()) {
				model = new Model();

				model.setId(rs.getInt("model_id"));
				model.setModel(rs.getString("model"));

				models.add(model);
			}

			modelsJSON = new Gson().toJson(models);

		} catch (SQLException e) {

			logger.error("Error on ArrayList<Model> getModelsByMake() method: " + e);
		} finally {
			conn.close();
		}

		return modelsJSON;

	}

	/**
	 * Get Requested Employee Info
	 * 
	 * @param emp_id
	 * @return Json data
	 * @throws SQLException
	 */
	public static String getEmpDetails(Integer id) throws SQLException{
		
		ArrayList<EmpInfo> empInfoList = new ArrayList<EmpInfo>();
		String empInfoJSON = "";
		Connection conn = null;
		Statement stm = null;
		ResultSet rs = null;
		EmpInfo empInfo;
		
		String sql ="SELECT first_name, last_name, email, mobile_no, department "
				  + "FROM emp_info WHERE emp_id = "+id;
		
		try {
			
			conn = DB.getConnection();
			stm = conn.createStatement();
			rs = stm.executeQuery(sql);
			
			while (rs.next()) {
				empInfo = new EmpInfo();
				
				empInfo.setFirst_name(rs.getString("first_name"));
				empInfo.setLast_name(rs.getString("last_name"));
				empInfo.setEmail(rs.getString("email"));
				empInfo.setMobile_no(rs.getString("mobile_no"));
				empInfo.setDepartment("department");
				
				empInfoList.add(empInfo);
			}
			
		} catch (SQLException e) {
			System.out.println("Error on String getEmpDetails(Integer id) method: " + e.getLocalizedMessage());
			 e.printStackTrace();
		 } finally {
			 conn.close();
		 }
		
		empInfoJSON = new Gson().toJson(empInfoList);
		return empInfoJSON;
	}

	/**
	 * Fetch Asset List by Asset Type
	 
	 * @return boolean[true/false]
	 * @throws SQLException
	 */	
	public static String fetchAssetListByAssetType(Map<String, String> filters, Integer draw, Integer length, Integer start, Integer asset_type) throws SQLException {
		String ServerListJSON = "";
		Integer totalServerCount = fetchServersCount(filters);
		List<AssetData> serverList = new ArrayList<AssetData>();
		AssetData server;
		
		JsonObject jsonOBJ = new JsonObject();
		jsonOBJ.addProperty("draw", draw);
		
		String sql = "SELECT asset_id, s_no, barcode, country, city, site, asset_type, tag_name, fa_code,"
				+ " make, model, serial_no, mac_address, processor, os, hard_disk, _memory, procurred_date, warranty_exp_date, amc, remarks,"
				+ " first_name, last_name, email, mobile_no, department FROM asset_info "
				+ " LEFT JOIN tech_info ON tech_info.tech_info_id = asset_info.tech_info_id"
				+ " LEFT JOIN emp_info ON emp_info.emp_id = asset_info.emp_id WHERE asset_type = "+ asset_type;
		
			  if (filters.size() > 0) {
			  
				  if (filters.get("search_filter") != null) { 
					
				      sql += " AND barcode LIKE '%" + filters.get("search_filter") + "%'";
			      }
			  }
			  sql += " ORDER BY s_no ASC LIMIT " + length + " OFFSET " +start;

		try (Connection connection = DB.getConnection();
	      PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

			final ResultSet rs = preparedStatement.executeQuery();
			
			 //Process the ResultSet object.
			while (rs.next()) {
				
				server = new AssetData(); 
				server.setAsset_id(rs.getInt("asset_id"));
				server.setS_no(rs.getString("s_no"));
				server.setBarcode(rs.getString("barcode"));
				server.setCountry(rs.getString("country"));
				server.setCity(rs.getString("city"));
				server.setSite(rs.getString("site"));
				server.setAsset_type(rs.getInt("asset_type"));
				server.setTag_name(rs.getString("tag_name"));
				server.setFa_code(rs.getString("fa_code"));
				server.setMake(rs.getString("make"));
				server.setModel(rs.getString("model"));
				server.setSerial_no(rs.getString("serial_no"));
				server.setSerial_no(rs.getString("serial_no"));
				server.setMac_address(rs.getString("mac_address"));
				server.setProcessor(rs.getString("processor"));
				server.setOs(rs.getString("os"));
				server.setHard_disk(rs.getString("hard_disk"));
				server.setMemory(rs.getString("_memory"));
				server.setProcurred_date(rs.getString("procurred_date"));
				server.setWarranty_exp_date(rs.getString("warranty_exp_date"));
				server.setAmc(rs.getString("amc"));
				server.setRemarks(rs.getString("remarks"));
				server.setFirst_name(rs.getString("first_name"));
				server.setLast_name(rs.getString("last_name"));
				server.setEmail(rs.getString("email"));
				server.setMobile(rs.getString("mobile_no"));
				server.setDepartment(rs.getString("department"));
				
				serverList.add(server);
			}
			
			jsonOBJ.addProperty("recordsTotal", totalServerCount);
			jsonOBJ.addProperty("recordsFiltered", totalServerCount);
			jsonOBJ.addProperty("data", new Gson().toJson(serverList));

			ServerListJSON = new Gson().toJson(jsonOBJ);
			
		} catch (SQLException e) {
			logger.error("Error on String fetchServersList method: " + e);
			System.out.println("SQLException e"+e);
		}
		return ServerListJSON;
	}

	private static Integer fetchServersCount(Map<String, String> filters) {
		Integer totalVehicleServices = 0;

		String sql = "SELECT COUNT(*) FROM asset_info a"
				+ " LEFT JOIN tech_info t ON t.tech_info_id = a.tech_info_id"
				+ " LEFT JOIN emp_info e ON e.emp_id = a.emp_id WHERE asset_type = 8";
         
				  if (filters.get("search_filter") != null) { 
						
				      sql += " AND a.barcode LIKE '%" + filters.get("search_filter") + "%'";
			      }
	 
		try (Connection connection = DB.getConnection(); Statement statement = connection.createStatement();) {

			final ResultSet rs = statement.executeQuery(sql);
			rs.next();
			totalVehicleServices = rs.getInt(1);

		} catch (SQLException e) {
			logger.error("Error on Integer fetchServersCount method: " + e);
		}
       
		return totalVehicleServices;
	}

	public static String getAssetDetails(Integer asset_id) throws SQLException {
		String assetDataJSON = "";
		JsonObject jsonOBJ = new JsonObject();
		
		String sql = "SELECT a.asset_id, a.s_no, a.barcode, a.country, a.city, a.site, a.asset_type, a.tag_name, a.fa_code, "
				+ "t.make, t.model, t.serial_no, t.mac_address, t.processor, t.os, t.hard_disk, t._memory, t.procurred_date, t.warranty_exp_date, t.amc, t.remarks, "
				+ "e.first_name, e.last_name, e.email, e.mobile_no, e.department "
				+ "FROM asset_info a "
				+ "LEFT JOIN tech_info t ON t.tech_info_id = a.tech_info_id "
				+ "LEFT JOIN emp_info e ON e.emp_id = a.emp_id WHERE asset_id = "+asset_id;
		
		AssetData assetData = null;
		
		ResultSet rs = null;
		try(Connection conn = DB.getConnection();Statement stm = conn.createStatement()){
			
			rs = stm.executeQuery(sql);
			if(rs.next()) {
				assetData = new AssetData();
				
				assetData.setAsset_id(rs.getInt("a.asset_id"));
				assetData.setS_no(rs.getString("a.s_no"));
				assetData.setBarcode(rs.getString("a.barcode"));
                assetData.setCountry(rs.getString("a.country"));
                assetData.setCity(rs.getString("a.city"));
                assetData.setSite(rs.getString("a.site"));
                assetData.setAsset_type(rs.getInt("a.asset_type"));
                assetData.setTag_name(rs.getString("a.tag_name"));
                assetData.setFa_code(rs.getString("a.fa_code"));
                assetData.setMake(rs.getString("t.make"));
                assetData.setModel(rs.getString("t.model"));
                assetData.setSerial_no(rs.getString("t.serial_no"));
                assetData.setMac_address(rs.getString("t.mac_address"));
                assetData.setProcessor(rs.getString("t.processor"));
                assetData.setOs(rs.getString("t.os"));
                assetData.setHard_disk(rs.getString("t.hard_disk"));
                assetData.setMemory(rs.getString("t._memory"));
                assetData.setProcurred_date(rs.getString("t.procurred_date"));
                assetData.setWarranty_exp_date(rs.getString("t.warranty_exp_date"));
                assetData.setAmc(rs.getString("t.amc"));
                assetData.setRemarks(rs.getString("t.remarks"));
                assetData.setFirst_name(rs.getString("e.first_name"));
                assetData.setLast_name("e.last_name");
                assetData.setEmail(rs.getString("e.email"));
                assetData.setMobile(rs.getString("e.mobile_no"));
                assetData.setDepartment(rs.getString("e.department"));
			}
		}catch(Exception ex) {
			System.out.println("Exception in Asset dao in getAssetDetails:  " + ex);
			ex.printStackTrace();
		}
		
		jsonOBJ.addProperty("data", new Gson().toJson(assetData));

		assetDataJSON = new Gson().toJson(jsonOBJ);
		System.out.println("assetData -----> " + assetDataJSON);
		return assetDataJSON;
	}

}
