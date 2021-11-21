package com.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Trainer {

	private Long id;
	private String name;
	private String school;
	private String isCardPrinted;
	private String delivery_status;
	private String tagId;
	
	public Trainer(Long id, String name, String school, String isCardPrinted, String delivery_status , String tagId) {
		super();
		this.id = id;
		this.name = name;
		this.school = school;
		this.isCardPrinted = isCardPrinted;
		this.delivery_status = delivery_status;
		this.tagId = tagId;
	}
	
	
	
	
}
