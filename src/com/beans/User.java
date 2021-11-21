package com.beans;

public class User {

	private int id;
	private String user_name;
	private String password;

	private int rold_id;
	private String rold_name;
	private int employee_id;

	private boolean haveDashBoardAccess;
	private boolean haveMaterialAccess;
	private boolean haveDtsCardAccess;
	private boolean haveVehicleAccess;
	private boolean haveHardwareAccess;
	private boolean haveManageUsersAccess;
	private boolean haveAddAssetAccess;
	private boolean haveAddEmployeeAccess;
	private boolean haveManageDeliveryPersonAccess;

	public boolean isHaveManageDeliveryPersonAccess() {
		return haveManageDeliveryPersonAccess;
	}

	public void setHaveManageDeliveryPersonAccess(boolean haveManageDeliveryPersonAccess) {
		this.haveManageDeliveryPersonAccess = haveManageDeliveryPersonAccess;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRold_id() {
		return rold_id;
	}

	public void setRold_id(int rold_id) {
		this.rold_id = rold_id;
	}

	public String getRold_name() {
		return rold_name;
	}

	public void setRold_name(String rold_name) {
		this.rold_name = rold_name;
	}

	public boolean isHaveDashBoardAccess() {
		return haveDashBoardAccess;
	}

	public void setHaveDashBoardAccess(boolean haveDashBoardAccess) {
		this.haveDashBoardAccess = haveDashBoardAccess;
	}

	public boolean isHaveMaterialAccess() {
		return haveMaterialAccess;
	}

	public void setHaveMaterialAccess(boolean haveMaterialAccess) {
		this.haveMaterialAccess = haveMaterialAccess;
	}

	public boolean isHaveDtsCardAccess() {
		return haveDtsCardAccess;
	}

	public void setHaveDtsCardAccess(boolean haveDtsCardAccess) {
		this.haveDtsCardAccess = haveDtsCardAccess;
	}

	public boolean isHaveVehicleAccess() {
		return haveVehicleAccess;
	}

	public void setHaveVehicleAccess(boolean haveVehicleAccess) {
		this.haveVehicleAccess = haveVehicleAccess;
	}

	public boolean isHaveHardwareAccess() {
		return haveHardwareAccess;
	}

	public void setHaveHardwareAccess(boolean haveHardwareAccess) {
		this.haveHardwareAccess = haveHardwareAccess;
	}

	public boolean isHaveManageUsersAccess() {
		return haveManageUsersAccess;
	}

	public void setHaveManageUsersAccess(boolean haveManageUsersAccess) {
		this.haveManageUsersAccess = haveManageUsersAccess;
	}

	public boolean isHaveAddAssetAccess() {
		return haveAddAssetAccess;
	}

	public void setHaveAddAssetAccess(boolean haveAddAssetAccess) {
		this.haveAddAssetAccess = haveAddAssetAccess;
	}

	public boolean isHaveAddEmployeeAccess() {
		return haveAddEmployeeAccess;
	}

	public void setHaveAddEmployeeAccess(boolean haveAddEmployeeAccess) {
		this.haveAddEmployeeAccess = haveAddEmployeeAccess;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

}
