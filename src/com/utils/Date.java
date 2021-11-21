package com.utils;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Date {

	public static void main(String[] args) throws ParseException {
		
		LocalDateTime localDate = LocalDateTime.now();
		LocalTime localTime = LocalTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
		String time = localTime.format(dateTimeFormatter);
		System.out.println(localDate.getDayOfMonth()+"-"+localDate.getMonth().getValue()+"-"+localDate.getYear()+" "+time);
	}
}


//10-03-2020 10:11 AM