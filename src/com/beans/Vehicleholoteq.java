package com.beans;

import java.util.Arrays;
import java.util.Date;

public class Vehicleholoteq {
	
	private int id;
	private String plate_no;
	private String make;
	private String model;
	private byte[] permit_img;
	private String permit_date;
	private Date created_date;
	private String driver_name;	
	private Integer driverId;
	
	private Date cDate;
	private Date sDate;
	private Date rDate;
	private Integer status;
	private String remark;
	
	public Date getcDate() {
		return cDate;
	}
	public void setcDate(Date cDate) {
		this.cDate = cDate;
	}
	public Date getsDate() {
		return sDate;
	}
	public void setsDate(Date sDate) {
		this.sDate = sDate;
	}
	public Date getrDate() {
		return rDate;
	}
	public void setrDate(Date rDate) {
		this.rDate = rDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getDriverId() {
		return driverId;
	}
	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPlate_no() {
		return plate_no;
	}
	public void setPlate_no(String plate_no) {
		this.plate_no = plate_no;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public byte[] getPermit_img() {
		return permit_img;
	}
	public void setPermit_img(byte[] data) {
		this.permit_img = data;
	}
	public String getPermit_date() {
		return permit_date;
	}
	public void setPermit_date(String permit_date) {
		this.permit_date = permit_date;
	}
	public Date getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	public String getDriver_name() {
		return driver_name;
	}
	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}
	@Override
	public String toString() {
		return "Vehicleholoteq [id=" + id + ", plate_no=" + plate_no + ", make=" + make + ", model=" + model
				+ ", permit_img=" + Arrays.toString(permit_img) + ", permit_date=" + permit_date + ", created_date="
				+ created_date + ", driver_name=" + driver_name + ", driverId=" + driverId + ", cDate=" + cDate
				+ ", sDate=" + sDate + ", rDate=" + rDate + ", status=" + status + ", remark=" + remark + "]";
	}
	
}
