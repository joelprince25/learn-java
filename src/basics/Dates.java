package basics;

import java.time.LocalDate;

public class Dates {
	
	public static void main(String[] args){
		LocalDate today = LocalDate.now();
		System.out.println("Today is " + today);
		
		LocalDate past = LocalDate.of(1800,01,01);
		LocalDate future = LocalDate.of(2100,01,01);
		
		System.out.println(today.isEqual(today));
		System.out.println(today.isAfter(past));
		System.out.println(today.isBefore(future));
	}

}
