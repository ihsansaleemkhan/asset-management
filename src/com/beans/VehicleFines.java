package com.beans;

import java.util.List;

public class VehicleFines {
	
	private int id;
	private int accident_id;
	private List<VehicleAccident> accidentObj;
	private int vehicle_id;
	private List<Vehicleholoteq> vehicleObj;
	private int driver_id;
	private List<Drivers> driverObj;
	private String category;
	private String date;
	private String remark;
	
	private String vehicle;
	private String driver;
	
	public VehicleFines(int id, String vehicle, String driver, String category, String date, String remark) {
		super();
		this.id = id;
		this.category = category;
		this.date = date;
		this.remark = remark;
		this.vehicle = vehicle;
		this.driver = driver;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAccident_id() {
		return accident_id;
	}
	public void setAccident_id(int accident_id) {
		this.accident_id = accident_id;
	}
	public List<VehicleAccident> getAccidentObj() {
		return accidentObj;
	}
	public void setAccidentObj(List<VehicleAccident> accidentObj) {
		this.accidentObj = accidentObj;
	}
	public int getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(int vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	public List<Vehicleholoteq> getVehicleObj() {
		return vehicleObj;
	}
	public void setVehicleObj(List<Vehicleholoteq> vehicleObj) {
		this.vehicleObj = vehicleObj;
	}
	public int getDriver_id() {
		return driver_id;
	}
	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}
	public List<Drivers> getDriverObj() {
		return driverObj;
	}
	public void setDriverObj(List<Drivers> driverObj) {
		this.driverObj = driverObj;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		return "VehicleFines [id=" + id + ", accident_id=" + accident_id + ", accidentObj=" + accidentObj
				+ ", vehicle_id=" + vehicle_id + ", vehicleObj=" + vehicleObj + ", driver_id=" + driver_id
				+ ", driverObj=" + driverObj + ", category=" + category + ", date=" + date + ", remark=" + remark + "]";
	}
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
}
