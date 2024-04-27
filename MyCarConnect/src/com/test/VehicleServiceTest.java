package com.test;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.exception.InvalidInputException;

import com.service.VehicleService;


public class VehicleServiceTest {
	VehicleService vehicleService=new VehicleService();	@Test
	public void getVehicleAge() {
		int vehicle_id=5;
		int expectedResult=4;
		int age;
		try {
				age=vehicleService.getVehicleAge(vehicle_id);
			     Assert.assertEquals(expectedResult, age);
		} catch (SQLException|InvalidInputException e) {
			System.out.println(e.getMessage());
		} 
		
		
		vehicle_id=50;
		 String expectedRes="Vehicle Id is Invalid, Try Again!";
		
		try {
				age=vehicleService.getVehicleAge(vehicle_id);
			     Assert.assertEquals(expectedRes, age);
		} catch (SQLException|InvalidInputException e) {
			System.out.println(e.getMessage());
		} 
	}

}
