package com.beans;

public class DeliveryPerson {
	
	private int id;
	private String qid;
	private String name;
	private String mobile;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "DeliveryPerson [id=" + id + ", qid=" + qid + ", name=" + name + ", mobile=" + mobile + "]";
	}
	

}
