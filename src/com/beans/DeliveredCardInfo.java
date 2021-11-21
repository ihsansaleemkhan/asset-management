package com.beans;

import java.util.List;

public class DeliveredCardInfo {
	
	private int id;
	private String name;
	private String qid;
	private String mobile;
	private int delivery_person_id;
	private String card_type;
	private String card_id;
	private List<String> card_no;
	private int card_count;
	
	public int getCard_count() {
		return card_count;
	}
	public void setCard_count(int card_count) {
		this.card_count = card_count;
	}

	private String date;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public List<String> getCard_no() {
		return card_no;
	}
	public void setCard_no(List<String> card_no) {
		this.card_no = card_no;
	}

	private int delivery_id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDelivery_person_id() {
		return delivery_person_id;
	}
	public void setDelivery_person_id(int delivery_person_id) {
		this.delivery_person_id = delivery_person_id;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getDelivery_id() {
		return delivery_id;
	}
	public void setDelivery_id(int delivery_id) {
		this.delivery_id = delivery_id;
	}
	
	@Override
	public String toString() {
		return "DeliveredCardInfo [id=" + id + ", name=" + name + ", qid=" + qid + ", mobile=" + mobile
				+ ", delivery_person_id=" + delivery_person_id + ", card_type=" + card_type + ", card_id=" + card_id
				+ ", card_no=" + card_no + ", date=" + date + ", delivery_id=" + delivery_id + "]";
	}

}
