package com.beans;

import java.util.Date;


public class Cardbox {
	
	private int box_id;
	private int carton_id;
	private int box_no;
	private String box_group;
	private String completed_date;
	private int total_cards;
	private String card_serial_no;
	private Date printed_date;
	private int school_id;
	private String school;
	private String note;
	private int ready_count;
	private int status;
	private String delivery_date;
	private String delivery_person;
	private int deliverdCardCount;
	private String enteredCardSerialNo;

	public int getBox_id() {
		return box_id;
	}

	public void setBox_id(int box_id) {
		this.box_id = box_id;
	}

	public int getCarton_id() {
		return carton_id;
	}

	public void setCarton_id(int carton_id) {
		this.carton_id = carton_id;
	}

	public int getBox_no() {
		return box_no;
	}

	public void setBox_no(int box_no) {
		this.box_no = box_no;
	}

	public String getBox_group() {
		return box_group;
	}

	public void setBox_group(String box_group) {
		this.box_group = box_group;
	}

	public String getBox_type() {
		return completed_date;
	}

	public void setBox_type(String completed_date) {
		this.completed_date = completed_date;
	}

	public int getTotal_cards() {
		return total_cards;
	}

	public void setTotal_cards(int total_cards) {
		this.total_cards = total_cards;
	}

	public String getCard_serial_no() {
		return card_serial_no;
	}

	public void setCard_serial_no(String card_serial_no) {
		this.card_serial_no = card_serial_no;
	}

	public Date getPrinted_date() {
		return printed_date;
	}

	public void setPrinted_date(Date printed_date) {
		this.printed_date = printed_date;
	}

	public int getSchool_id() {
		return school_id;
	}

	public void setSchool_id(int school_id) {
		this.school_id = school_id;
	}

	public int getStatus() {
		return status;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getReady_count() {
		return ready_count;
	}

	public void setReady_count(int ready_count) {
		this.ready_count = ready_count;
	}
	
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public String getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(String delivery_date) {
		this.delivery_date = delivery_date;
	}
	
	public String getDelivery_person() {
		return delivery_person;
	}

	public void setDelivery_person(String delivery_person) {
		this.delivery_person = delivery_person;
	}


	public int getDeliverdCardCount() {
		return deliverdCardCount;
	}

	public void setDeliverdCardCount(int deliverdCardCount) {
		this.deliverdCardCount = deliverdCardCount;
	}

	public String getEnteredCardSerialNo() {
		return enteredCardSerialNo;
	}

	public void setEnteredCardSerialNo(String enteredCardSerialNo) {
		this.enteredCardSerialNo = enteredCardSerialNo;
	}
	
	@Override
	public String toString() {
		return "Cardbox [box_id=" + box_id + ", carton_id=" + carton_id + ", box_no=" + box_no + ", box_group="
				+ box_group + ", completed_date=" + completed_date + ", total_cards=" + total_cards + ", card_serial_no=" + card_serial_no + ", printed_date=" + printed_date
				+ ", school_id=" + school_id + ", status=" + status + "]";
	}
}
