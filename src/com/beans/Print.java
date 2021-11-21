package com.beans;

import java.util.Date;

public class Print {
	
	private String box_group;
	private String card_serial_no;
	private String printedDate;
	private String deliveredDate;
	private String delivered_cards;
	private String returnedCard;
	private String delivered_person;
	private String damaged_card;
	private String school;
	
	
	
	public String getBox_group() {
		return box_group;
	}
	public void setBox_group(String box_group) {
		this.box_group = box_group;
	}
	public String getCard_serial_no() {
		return card_serial_no;
	}
	public void setCard_serial_no(String card_serial_no) {
		this.card_serial_no = card_serial_no;
	}

	public String getPrintedDate() {
		return printedDate;
	}
	public void setPrintedDate(String printedDate) {
		this.printedDate = printedDate;
	}

	public String getDeliveredDate() {
		return deliveredDate;
	}
	public void setDeliveredDate(String deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

	public String getDelivered_cards() {
		return delivered_cards;
	}
	public void setDelivered_cards(String delivered_cards) {
		this.delivered_cards = delivered_cards;
	}

	public String getReturnedCard() {
		return returnedCard;
	}
	public void setReturnedCard(String returnedCard) {
		this.returnedCard = returnedCard;
	}

	public String getDelivered_person() {
		return delivered_person;
	}
	public void setDelivered_person(String delivered_person) {
		this.delivered_person = delivered_person;
	}
	
	public String getDamaged_card() {
		return damaged_card;
	}
	public void setDamaged_card(String damaged_card) {
		this.damaged_card = damaged_card;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}

	@Override
	public String toString() {
		return "Print [card_serial_no=" + card_serial_no + ", printedDate=" + printedDate + ", deliveredDate="
				+ deliveredDate + ", delivered_cards=" + delivered_cards + ", returnedCard=" + returnedCard
				+ ", delivered_person=" + delivered_person + "]";
	}

	
	
	
}
