package com.beans;

public class RequestInk {

	private int id;
	private int order_no;
	private String model;
	private int qty;
	private String cat;
	private String req_date;
	private String order_recieved_person;
	private String order_via;
	private String supplier;
    private String req_by;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public String getOrder_recieved_person() {
		return order_recieved_person;
	}
	public void setOrder_recieved_person(String order_recieved_person) {
		this.order_recieved_person = order_recieved_person;
	}
	public String getOrder_via() {
		return order_via;
	}
	public void setOrder_via(String order_via) {
		this.order_via = order_via;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getReq_by() {
		return req_by;
	}
	public void setReq_by(String req_by) {
		this.req_by = req_by;
	}
	public String getReq_date() {
		return req_date;
	}
	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}

}
