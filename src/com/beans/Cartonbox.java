package com.beans;

import java.util.Date;

public class Cartonbox {
	
	private int carton_id;
	private int carton_no;
	private String carton_group;
	
	private String stock_in_date;
	private Date stock_out_date;
	
	private int total_boxes;
	private int total_cards;
	
	private int status;
	private String shipment;
	private int new_box_count;
	private int pending_box_count;
	private int delivered_box_count;
	
	public int getCarton_id() {
		return carton_id;
	}

	public void setCarton_id(int carton_id) {
		this.carton_id = carton_id;
	}
	
	public int getCartonNo() {
		return carton_no;
	}
	
	public void setCartonNo(int carton_no){
		this.carton_no = carton_no;		
	}     
	
	public String getCartonGroup() {
		return carton_group;
	}
	
	public void setCartonGroup(String carton_group) {
		this.carton_group = carton_group;
	}
	
	public String getStockinDate() {
		return stock_in_date;
	}
	
	public void setStockinDate(String stock_in_date) {
		this.stock_in_date = stock_in_date;
	}
	
	public Date getStockOutDate() {
		return stock_out_date;
	}
	
	public void setStockOutDate(Date stock_out_date) {
		this.stock_out_date = stock_out_date;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public int getTotal_boxes() {
		return total_boxes;
	}

	public void setTotal_boxes(int total_boxes) {
		this.total_boxes = total_boxes;
	}

	public int getTotal_cards() {
		return total_cards;
	}

	public void setTotal_cards(int total_cards) {
		this.total_cards = total_cards;
	}

	public String getShipment() {
		return shipment;
	}

	public void setShipment(String shipment) {
		this.shipment = shipment;
	}

	public int getDeliveredCount() {
		return delivered_box_count;
	}

	public void setDeliveredCount(int count) {
		this.delivered_box_count = count;
	}

	public int getPending_box_count() {
		return pending_box_count;
	}

	public void setPending_box_count(int pending_box_count) {
		this.pending_box_count = pending_box_count;
	}

	public int getNew_box_count() {
		return new_box_count;
	}

	public void setNew_box_count(int new_box_count) {
		this.new_box_count = new_box_count;
	}	

}
