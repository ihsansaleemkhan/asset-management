package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.beans.AssetInfo;
import com.beans.EmpInfo;
import com.beans.TechInfo;
import com.beans.UserDevice;

public class DeviceDAO {
	
	public List<UserDevice> getUserDevice(Map<String, String> params) {
		ResultSet rs2 = null;
		String sql2 =  "select employee_asset.id as row_id,employee_id,first_name,barcode,check_in_date,check_out_date from employee_asset\r\n" + 
				"			join emp_info on employee_asset.employee_id = emp_info.emp_id \r\n" + 
				"            join asset_info on employee_asset.asset_id = asset_info.asset_id where 1 = 1";
		
		
		if(params.get("check_in_date") != null && params.get("check_in_date") != "" ) {
			sql2+="  AND check_in_date like \""+params.get("check_in_date")+"%\"";
		}
		if(params.get("check_out_date") != null & params.get("check_out_date") != "") {
			sql2+="  AND check_out_date like \""+params.get("check_out_date")+"%\"";
		}
		if(params.get("employee_name_id") != null && params.get("employee_name_id") != "") {
			sql2+="  AND (emp_info.emp_id = \""+params.get("employee_name_id")+"\" OR first_name = \""+params.get("employee_name_id")+"\")";
		}
		if(params.get("isInUse") != null && params.get("isInUse") != "") {
			if(Boolean.valueOf(params.get("isInUse")))
			sql2+="  AND check_out_date is null";
		}
		System.out.println("sql2 ----> " + sql2);
		List<UserDevice> userDeviceList = new ArrayList<UserDevice>();
		UserDevice userDevice ;
		try(Connection conn = DB.getConnection();PreparedStatement pStat = conn.prepareStatement(sql2)){
		
			rs2 = pStat.executeQuery();
			while (rs2.next()) {
				userDevice = new UserDevice();
				userDevice.setUserAssetId(rs2.getInt("row_id"));
				userDevice.setEmployeeId(rs2.getInt("employee_id"));
				userDevice.setEmployeeName(rs2.getString("first_name"));
				userDevice.setDeviceSerialNumber(rs2.getString("barcode"));
				userDevice.setCheckInDate(rs2.getString("check_in_date"));
				if(rs2.getString("check_out_date") != null) {
				userDevice.setCheckOutDate(rs2.getString("check_out_date"));
				}else {
				userDevice.setCheckOutDate("Currently In Use");
				}
				userDeviceList.add(userDevice);
			}
		}catch(Exception ex) {
			System.out.println("Exception in device dao  :  " +ex);
			ex.printStackTrace();
		}
		 
		return userDeviceList;
	}

	public AssetInfo getAssetInformation(String serialNumber) {
		String sql = "  select asset_info.asset_id,asset_info.s_no,asset_info.barcode,asset_info.country,asset_info.city,asset_info.site,asset_info.tag_name,asset_info.fa_code ,asset_type.asset_type,emp_info.first_name , emp_info.emp_id    \r\n" + 
				"					,tech_info.make,tech_info.model,tech_info.serial_no,tech_info.mac_address,tech_info.processor,tech_info.os,tech_info.hard_disk,tech_info._memory ,tech_info.procurred_date,tech_info.warranty_exp_date,tech_info.amc,tech_info.remarks from asset_info \r\n" + 
				"					left join tech_info on asset_info.tech_info_id = tech_info.tech_info_id \r\n" + 
				"					left join asset_type on asset_info.asset_type = asset_type.id\r\n" + 
				"                    left join emp_info on asset_info.emp_id = emp_info.emp_id\r\n" + 
				"                    where barcode = ? ";
//		List<AssetInfo> assetInfoList = new ArrayList<AssetInfo>();
		AssetInfo assetInfo = null ;
		TechInfo techInfo ;
		EmpInfo empInfo;
		ResultSet rs = null;
		try(Connection conn = DB.getConnection();PreparedStatement pStat = conn.prepareStatement(sql)){
			pStat.setString(1, serialNumber);
			rs = pStat.executeQuery();
			if(rs.next()) {
				empInfo = new EmpInfo();
				empInfo.setFirst_name(rs.getString("first_name")  != null ? rs.getString("first_name"): "");
				empInfo.setEmp_id(rs.getInt("emp_id") != 0 ? rs.getInt("emp_id") : 0);
				
				techInfo = new TechInfo();
				techInfo.setMake(rs.getString("make") != null ? rs.getString("make") : "");
				techInfo.setModel(rs.getString("model") != null ? rs.getString("model") :"");
				techInfo.setSerial_no(rs.getString("serial_no") != null ?rs.getString("serial_no") : "");
				techInfo.setMac_address(rs.getString("mac_address") != null ? rs.getString("mac_address") : "");
				techInfo.setProcessor(rs.getString("processor") != null ? rs.getString("processor") : "");
				techInfo.setOs(rs.getString("os") != null ? rs.getString("os") : "");
				techInfo.setHard_disk(rs.getString("hard_disk") != null ? rs.getString("hard_disk")  : "");
				techInfo.setMemory(rs.getString("_memory") != null ? rs.getString("_memory") : "");
				techInfo.setProcurred_date(rs.getString("procurred_date") != null ? rs.getString("procurred_date") : "");
				techInfo.setWarranty_exp_date(rs.getString("warranty_exp_date") != null ? rs.getString("warranty_exp_date") : "");
				techInfo.setAmc(rs.getString("amc") != null ? rs.getString("amc") : "");
				techInfo.setRemarks(rs.getString("remarks") != null ? rs.getString("remarks") : "");
				
				assetInfo = new AssetInfo();
				assetInfo.setAsset_id(rs.getInt("asset_id"));
				assetInfo.setS_no(rs.getString("s_no")  != null ? rs.getString("s_no"): "");
				assetInfo.setCountry(rs.getString("country")  != null ? rs.getString("country"): "");
				assetInfo.setCity(rs.getString("city")  != null ? rs.getString("city") : "");
				assetInfo.setSite(rs.getString("site")  != null ? rs.getString("site") : "");
				assetInfo.setTag_name(rs.getString("barcode")  != null ? rs.getString("barcode") : "");
				assetInfo.setFa_code(rs.getString("fa_code")  != null ? rs.getString("fa_code")  : "");
				assetInfo.setAsset_type(rs.getString("asset_type")  != null ? rs.getString("asset_type") : "");
				assetInfo.setTechInfoObj(techInfo);
				assetInfo.setOwnerInfoObj(empInfo);
			}
		}catch(Exception ex) {
			System.out.println("Exception in device dao  :  " +ex);
			ex.printStackTrace();
		}
		return assetInfo;
	}
	
	public String getAssignedUserName(int userAssetId) {
		String result= null;
		String sql = "  select first_name from employee_asset \r\n" + 
					 "						join emp_info on employee_asset.employee_id = emp_info.emp_id\r\n" + 
					 "						where employee_asset.id =?";
		ResultSet rs = null;
		try(Connection conn = DB.getConnection();PreparedStatement pStat = conn.prepareStatement(sql)){
			pStat.setInt(1, userAssetId);
			rs = pStat.executeQuery();
			if(rs.next())
				result = rs.getString("first_name");
		}catch(Exception ex) {
			System.out.println("Exception in device dao  :  " +ex);
			ex.printStackTrace();
		}
		return result;
	}
	
	public boolean setCheckOut(int userAssetId) {
		boolean result = false;
		String sql = "update employee_asset set check_out_date = CURRENT_TIME() where id = ?";
		try(Connection conn = DB.getConnection();PreparedStatement pStat = conn.prepareStatement(sql)){
			pStat.setInt(1, userAssetId);
			int affectedRow = pStat.executeUpdate();
			if(affectedRow >= 1) {
				result = true;
			}
		}catch(Exception ex) {
			System.out.println("Exception in device dao  :  " +ex);
			ex.printStackTrace();
		}
		return result;
	}
}
