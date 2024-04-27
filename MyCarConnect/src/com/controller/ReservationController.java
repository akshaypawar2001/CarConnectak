package com.controller;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.exception.InvalidInputException;
import com.model.Reservation;
import com.service.ReservationService;
import com.service.VehicleService;
import com.utility.RandomId;
import com.model.Vehicle;

public class ReservationController {

	public static void main(String[] args) {
		
		int customerId = 1;
		//int customerId = Integer.parseInt(args[0]);
		
		Scanner sc = new Scanner(System.in);
		ReservationService reservationService = new ReservationService();
		VehicleService vehicleService = new VehicleService();
		
		while(true) {
			System.out.println("Make Reservation");
		System.out.println("----------------------------");
		
		//List all available vehicles
		System.out.println("Available vehicles: ");
		System.out.println();
		
		try {
			List<Vehicle> list = vehicleService.findAllAvailable();
			System.out.format("%-15s%-15s%-15s%-15s%n","Company","Model","Year","Color","Daily Rate");
			System.out.println("----------------------------------------------------------");
			for(Vehicle v : list) {
				System.out.format("%-15s%-15s%-15s%-15s%n",v.getVehicle_make(), v.getVehicle_model(),
						v.getVehicle_year(), v.getVehicle_color(), v.getVehicle_daily_rate());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println();
		//ID
		System.out.println("Enter VehicleId");
		int vehicleId = sc.nextInt();
		sc.nextLine();
		
		//startDate
		System.out.println("Enter Reservation start date(yyyy-mm-dd):");
		String startDate = sc.nextLine();
        
        //EndDate
		System.out.println("Enter Reservation end date(yyyy-mm-dd):");
		String endDate = sc.nextLine();
        
        //calculate totalCost;
        double totalCost = 0;
		try {
			totalCost = reservationService.getTotalCost(vehicleId, startDate, endDate);
		} catch (SQLException | InvalidInputException e) {
			System.out.println(e.getMessage());
		}
        
        System.out.println("Total Cost = "+totalCost);
        
		 //Choice
		System.out.println("1. Book");
		System.out.println("0. Exit");
        System.out.println("Enter your choice: ");
        int choice = sc.nextInt();
        
        //0. Exit
        if(choice == 0)
        	break;
        
		//1. Book
		try {
			//generate unique reservationId
			int reservationId = RandomId.getRandomId();

			//Attach Values to object
			Reservation newReservation = new Reservation(customerId, vehicleId, reservationId,
					startDate, endDate, totalCost, "Pending", 1);
					
			int status = reservationService.save(newReservation);
			if (status==1)
				System.out.println("Reservation Sucessfully!! Thank you...") ;
			else
				System.out.println("Reservation failed");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		//Make a new reservation again
		System.out.println("Do you want to make another reservation?(y/n)");
		String c1 = sc.next();
		if(c1.equalsIgnoreCase("n"))
			break;
		}
		sc.close();
	}
	
	public static void reservationMenu(String[] args) {
		main(args);
	}

}
