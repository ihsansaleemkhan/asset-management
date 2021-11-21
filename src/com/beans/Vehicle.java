package com.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Vehicle {

	private Long id;
	private String plate_no;
	private String school;
	private String vehicleType;
	private String isCardPrinted;
	private String delivery_status;
	private String tagId;
	
	public Vehicle(Long id, String plate_no, String school, String vehicleType, String isCardPrinted,
			String delivery_status, String tagId) {
		super();
		this.id = id;
		this.plate_no = plate_no;
		this.school = school;
		this.vehicleType = vehicleType;
		this.isCardPrinted = isCardPrinted;
		this.delivery_status = delivery_status;
		this.tagId = tagId;
	}
	

}
