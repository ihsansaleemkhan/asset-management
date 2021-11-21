package com.beans;

import java.sql.Date;

public class Drivers {
	
	private int id;
	private int vehicle_id;
	private String driver_name;
	private String license_no;
	private String own_type;
	private String license_img;
	private String given_date;
    private String returned_date;
    private Date created_date;
    private String plate_no;
    private int penalty_count;
    private int accident_count;
    
    private String vehicle;
    
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
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
	public String getDriver_name() {
		return driver_name;
	}
	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}
	public String getLicense_no() {
		return license_no;
	}
	public void setLicense_no(String license_no) {
		this.license_no = license_no;
	}
	public String getOwn_type() {
		return own_type;
	}
	public void setOwn_type(String own_type) {
		this.own_type = own_type;
	}
	public String getLicense_img() {
		return license_img;
	}
	public void setLicense_img(String license_img) {
		this.license_img = license_img;
	}
	public String getGiven_date() {
		return given_date;
	}
	public void setGiven_date(String given_date) {
		this.given_date = given_date;
	}
	public String getReturned_date() {
		return returned_date;
	}
	public void setReturned_date(String returned_date) {
		this.returned_date = returned_date;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public String getPlate_no() {
		return plate_no;
	}
	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}
	public int getPenalty_count() {
		return penalty_count;
	}
	public void setPenalty_count(int penalty_count) {
		this.penalty_count = penalty_count;
	}
	public int getAccident_count() {
		return accident_count;
	}
	public void setAccident_count(int accident_count) {
		this.accident_count = accident_count;
	}
	@Override
	public String toString() {
		return "Drivers [id=" + id + ", vehicle_id=" + vehicle_id + ", driver_name=" + driver_name + ", license_no="
				+ license_no + ", own_type=" + own_type + ", license_img=" + license_img + ", given_date=" + given_date
				+ ", returned_date=" + returned_date + ", created_date=" + created_date + ", plate_no=" + plate_no
				+ ", penalty_count=" + penalty_count + ", accident_count=" + accident_count + ", vehicle=" + vehicle
				+ "]";
	}
}
