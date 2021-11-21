package com.beans;

import java.util.Arrays;
import java.util.List;

public class VehicleAccident {
    private int id;
    private int vehicle_id;
    private List<Vehicleholoteq> vehicleObj;
    private int driver_id;
    private List<Drivers> driverObj;
    private String date;
    private String description;
    private byte[] img1;
    private byte[] img2;
    private byte[] img3;
    
    private String vehicle;
    private String driver;
    
	public VehicleAccident(int id, String vehicle, String driver, String date, String description) {
		super();
		this.id = id;
		this.setVehicle(vehicle);
		this.setDriver(driver);
		this.date = date;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getImg1() {
		return img1;
	}
	public void setImg1(byte[] img1) {
		this.img1 = img1;
	}
	public byte[] getImg2() {
		return img2;
	}
	public void setImg2(byte[] img2) {
		this.img2 = img2;
	}
	public byte[] getImg3() {
		return img3;
	}
	public void setImg3(byte[] img3) {
		this.img3 = img3;
	}
	
	@Override
	public String toString() {
		return "VehicleAccident [id=" + id + ", vehicle_id=" + vehicle_id + ", vehicleObj=" + vehicleObj
				+ ", driver_id=" + driver_id + ", driverObj=" + driverObj + ", date=" + date + ", description="
				+ description + ", img1=" + Arrays.toString(img1) + ", img2=" + Arrays.toString(img2) + ", img3="
				+ Arrays.toString(img3) + "]";
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
