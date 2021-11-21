package com.beans;

public class VehicleServices {
	
		   private int id;
		   private int vehicle_id;
		   private String vehicle;
		   private int driver_id;
		   private String driver;
		   private int service_type_id;
		   private String service_type;
		   private String date;
		   private double cost;
		   private String place;
       
		
		public VehicleServices(int id, String vehicle, String driver, String service_type, String date, double cost, String place) {
			super();
			this.id = id;
			this.vehicle = vehicle;
			this.driver = driver;
			this.service_type = service_type;
			this.date = date;
			this.cost = cost;
			this.place = place;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getVehicle_id() {
			return vehicle_id;
		}
		public void setVehicle_id(int vehicle_id) {
			this.vehicle_id = vehicle_id;
		}
		public int getDriver_id() {
			return driver_id;
		}
		public void setDriver_id(int driver_id) {
			this.driver_id = driver_id;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public double getCost() {
			return cost;
		}
		public void setCost(double cost) {
			this.cost = cost;
		}
		public String getPlace() {
			return place;
		}
		public void setPlace(String place) {
			this.place = place;
		}
		public int getService_type_id() {
			return service_type_id;
		}
		public void setService_type_id(int service_type_id) {
			this.service_type_id = service_type_id;
		}
		public String getVehicle() {
			return vehicle;
		}
		public void setVehicle(String vehicle) {
			this.vehicle = vehicle;
		}
		public String getDriver() {
			return driver;
		}
		public void setDriver(String driver) {
			this.driver = driver;
		}
		public String getService_type() {
			return service_type;
		}
		public void setService_type(String service_type) {
			this.service_type = service_type;
		}
		@Override
		public String toString() {
			return "VehicleServices [id=" + id + ", vehicle_id=" + vehicle_id + ", vehicle=" + vehicle + ", driver_id="
					+ driver_id + ", driver=" + driver + ", service_type_id=" + service_type_id + ", service_type="
					+ service_type + ", date=" + date + ", cost=" + cost + ", place=" + place + "]";
		} 
			
}
