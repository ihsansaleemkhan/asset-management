package com.beans;

public class Model {
	private int id;
    private String model;
    private String mk_model;
    private String mk_model_id;
    
	public String getMk_model_id() {
		return mk_model_id;
	}
	public void setMk_model_id(String mk_model_id) {
		this.mk_model_id = mk_model_id;
	}
	public String getMk_model() {
		return mk_model;
	}
	public void setMk_model(String mk_model) {
		this.mk_model = mk_model;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
}
