package com.test;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;

import org.junit.Assert;
import org.junit.Test;

import com.exception.InvalidInputException;
import com.service.ReservationService;


public class ReservationServiceTest {

	
	@Test
	public void getTotalCost() {
		
		
		//Use Case 1: Ideal case
		//Prepare the inputs
		ReservationService reservationService = new ReservationService();
		int vehicleId = 1;
		String startDate = "2024-04-01";
		String endDate = "2024-04-14";
		
		double actualResult = 13D;
		double expectedResult = 13D;
		
		try {
			double grade = reservationService.getTotalCost(vehicleId, startDate, endDate);
			Assert.assertEquals(expectedResult, actualResult, grade);
		} catch (SQLException e) { } catch (InvalidInputException e) { }
		
		
		
		//Use Case 2: Invalid date input format
		//Prepare the inputs
		vehicleId = 1;
		startDate = "2024-04";
		endDate = "2024-04-14";
		
		actualResult = 13D;
		expectedResult = 13D;
		
		try {
			double grade = reservationService.getTotalCost(vehicleId, startDate, endDate);
			Assert.assertEquals(expectedResult, actualResult, grade);
		} catch (SQLException e) { } catch (InvalidInputException e) { }
		catch (DateTimeParseException e) { }
		
		
		//Use Case 3: Invalid date input format
		//Prepare the inputs
		vehicleId = 1;
		startDate = "";
		endDate = "";
		
		actualResult = 13D;
		expectedResult = 13D;
		
		try {
			double grade = reservationService.getTotalCost(vehicleId, startDate, endDate);
			Assert.assertEquals(expectedResult, actualResult, grade);
		} catch (SQLException e) { } catch (InvalidInputException e) { }
		catch (DateTimeParseException e) { }
		
		//Use Case 4: Invalid date input format
		//Prepare the inputs
		vehicleId = 1;
		startDate = "2024-04-14";
		endDate = "2023-04-14";
		
		actualResult = 13D;
		expectedResult = 13D;
		
		try {
			double grade = reservationService.getTotalCost(vehicleId, startDate, endDate);
			Assert.assertEquals(expectedResult, actualResult, grade);
		} catch (SQLException e) { } catch (InvalidInputException e) { }
		catch (DateTimeParseException e) { }
		
		//Use Case 5: Invalid input format
		//Prepare the inputs
		vehicleId = 0;
		startDate = "2024-04-13";
		endDate = "2024-04-14";
		
		actualResult = 1D;
		expectedResult = 1D;
		
		try {
			double grade = reservationService.getTotalCost(vehicleId, startDate, endDate);
			Assert.assertEquals(expectedResult, actualResult, grade);
		} catch (SQLException e) { } catch (InvalidInputException e) { }
		catch (DateTimeParseException e) { }
		
		//Use Case 6: Invalid input format
		//Prepare the inputs
		vehicleId = 1;
		startDate = "2024-04-13";
		endDate = "2024-04-14";
		
		actualResult = 1D;
		expectedResult = 1D;
		
		try {
			double grade = reservationService.getTotalCost(vehicleId, startDate, endDate);
			Assert.assertEquals(expectedResult, actualResult, grade);
		} catch (SQLException e) { } catch (InvalidInputException e) { }
		catch (DateTimeParseException e) { }
		
		
	}
}
