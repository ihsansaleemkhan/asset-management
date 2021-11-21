package com.beans;

public class VehicleServiceType {
	
	private int id;
	private String service_type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	@Override
	public String toString() {
		return "VehicleServiceType [id=" + id + ", service_type=" + service_type + "]";
	}
    
}
