package com.beans;

public class DeliveredInk {
	
	String shipment;
	String model;
	String cat;
	String barcode;
	String sent_by;
	String delivered_person;
	String delivery_date;
	String location;
	String device_no;
	
	public String getShipment() {
		return shipment;
	}
	public void setShipment(String shipment) {
		this.shipment = shipment;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getSent_by() {
		return sent_by;
	}
	public void setSent_by(String sent_by) {
		this.sent_by = sent_by;
	}
	public String getDelivered_person() {
		return delivered_person;
	}
	public void setDelivered_person(String delivered_person) {
		this.delivered_person = delivered_person;
	}
	public String getDelivery_date() {
		return delivery_date;
	}
	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDevice_no() {
		return device_no;
	}
	public void setDevice_no(String device_no) {
		this.device_no = device_no;
	}
	@Override
	public String toString() {
		return "DeliveredInk [shipment=" + shipment + ", model=" + model + ", cat=" + cat + ", barcode=" + barcode
				+ ", sent_by=" + sent_by + ", delivered_person=" + delivered_person + ", delivery_date=" + delivery_date
				+ ", location=" + location + ", device_no=" + device_no + "]";
	}
}
