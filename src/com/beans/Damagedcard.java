package com.beans;

public class Damagedcard {
	private Integer id;
	private Integer box_id;
	private String shipment;
	private String carton_group;
	private String box_serial_no;
	private String card_serial_no;
	private String reason;
	private String box_group;
	
	
	public String getCard_serial_no() {
		return card_serial_no;
	}
	public void setCard_serial_no(String card_serial_no) {
		this.card_serial_no = card_serial_no;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getBox_group() {
		return box_group;
	}
	public void setBox_group(String box_group) {
		this.box_group = box_group;
	}
	public String getShipment() {
		return shipment;
	}
	public void setShipment(String shipment) {
		this.shipment = shipment;
	}
	public String getCarton_group() {
		return carton_group;
	}
	public void setCarton_group(String carton_group) {
		this.carton_group = carton_group;
	}
	public String getBox_serial_no() {
		return box_serial_no;
	}
	public void setBox_serial_no(String box_serial_no) {
		this.box_serial_no = box_serial_no;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBox_id() {
		return box_id;
	}
	public void setBox_id(Integer box_id) {
		this.box_id = box_id;
	}

}
