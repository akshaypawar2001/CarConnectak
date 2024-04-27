package com.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.exception.InvalidInputException;
import com.exception.ResourceNotFoundException;
import com.model.Reservation;
import com.model.Review;
import com.model.Vehicle;
import com.service.ReservationService;
import com.service.VehicleService;
import com.service.VendorService;
import com.service.ReviewService;
import com.utility.RandomId;

public class VendorController {

	public static void main(String[] args) {
		
		//extract vendorId
		//int vendorId = Integer.parseInt(args[0]);
		int vendorId = 6;
		
		Scanner sc = new Scanner(System.in);
		VehicleService vehicleService = new VehicleService();
		ReservationService reservationService = new ReservationService();
		ReviewService reviewService = new ReviewService();
		
		while(true) {
			System.out.println("          Vendor");
			System.out.println("--------------------------");
			System.out.println();
			System.out.println("1. Add new vehicle");
			System.out.println("2. Show my Vehicle");
			System.out.println("3. Show Booking Requests");
			System.out.println("4. Returned Vehicles List");
			System.out.println("5. Remove a Vehicle");
			System.out.println("6. My vehicle reviews");			
			System.out.println("0. Back");
			
			System.out.println("Enter your choice: ");
			int choice = sc.nextInt();
			
			if(choice == 0) {
				break;
			}
			switch(choice) {
			case 1: 
				
				//Instead we can make a static class for this in utility
				int vId = RandomId.getRandomId();
				
				System.out.println("       Add Vehicle");
				System.out.println("--------------------------");
				sc.nextLine();
				System.out.println("Enter Vehicle Name");
				String vModel = sc.nextLine();
				System.out.println("Enter Vehicle Company");
				String vMake = sc.nextLine();
				System.out.println("Enter Vehicle Year");
				int vYear = sc.nextInt();
				sc.nextLine();
				System.out.println("Enter Vehicle colour");
				String vColor = sc.nextLine();
				System.out.println("Enter Vehicle Registration Number");
				String vNumber = sc.nextLine();
				System.out.println("Enter Vehicle Daily Rate");
				double vPrice = sc.nextDouble();
				
				
				System.out.println("1. Add Vehicle");
				System.out.println("0. Back");
				int c = sc.nextInt();
				if(c == 0)
					break;
				
				try {
					//Attach Values to object
					Vehicle vehicle  = new Vehicle(vId, vModel, vMake, vYear, vColor, 
							vNumber, true, vPrice, vendorId);
					
					//pass vehicle to be added in vehicle table
					int status = vehicleService.add(vehicle);
					if (status==1)
						System.out.println("Sucessfully added a new Vehicle...Thank you") ;
					else
						System.out.println("Insertion failed");
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				
				System.out.println("");
				System.out.println("");
				break;
				
			case 2:
				try {
					System.out.println("      My vehicles");
					System.out.println("--------------------------");
					List<Vehicle> list = vehicleService.findMyVehicles(vendorId);
					System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%n","ID","Make","Model","RegistrationNo", "Availability", "Daily Rate");
					System.out.println("-------------------------------------------------------------------------------");
					for(Vehicle v : list) {
						System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%n",v.getVehicle_id(), v.getVehicle_make(), v.getVehicle_model(),
								v.getVehicle_registration_no(), v.isVehicle_availability(), v.getVehicle_daily_rate());
					}
					System.out.println();
					System.out.println("0. Back");
					int c1 = sc.nextInt();
					if(c1 == 0)
						break;
					
				}
				catch(SQLException e){
					e.getMessage();
				}
				System.out.println("");
				System.out.println("");
				break;
				
			case 3:
					System.out.println("        Booking Requests");
					System.out.println("---------------------------------");
					System.out.println();
					
					//Get list
					List<Reservation> list = null;
					try {
						list = reservationService.vendorFindAllReservationsByStatus(vendorId, "Pending");
					} catch (ResourceNotFoundException e) {
						System.out.println("findAllReservationsByStatus: "+e.getMessage());
					} catch (SQLException e) {
						System.out.println("findAllReservationsByStatus: "+e.getMessage());
					}
					if(list == null || list.size() == 0)
						System.out.println("Sorry, you do not have any reservation.");
					else {
						System.out.format("%-15s%-15s%-15s%-15s%-15s%n","Customer","Vehicle",
								"StartDate","EndDate", "Status");
						System.out.println("--------------------------------------------------------------");
						for(Reservation r : list) {
							System.out.format("%-15s%-15s%-15s%-15s%-15s%n",r.getCustomer_id(), r.getVehicle_id(),
									r.getReservation_start_date(), r.getReservation_end_date(), r.getReservation_status());
						}
						System.out.println();
						System.out.println("1. Accept a reservation");
					}
					System.out.println("0. Back");
					
					//choice
					System.out.println("Enter you choice");
					int choice1 = sc.nextInt();
					
					//0 back
					if(choice1 == 0) break;
					
					//1 : accept
					System.out.println("Enter a Reservation Id to Accept:");
					int reservationId = sc.nextInt();
					
					for(Reservation r : list) {
						if(r.getReservation_id() == reservationId) {
							
							//Create a new object
							Reservation updatedReservation = new Reservation(r.getCustomer_id(), r.getVehicle_id(), 
									r.getReservation_id(), r.getReservation_start_date(), r.getReservation_end_date(),
									r.getReservation_total_cost(), "Confirm", r.getAdmin_id());
							
							//send that object to method
							try {
								reservationService.update(reservationId, updatedReservation);
							} catch (SQLException e) {
								System.out.println(e.getMessage());
							} catch (ResourceNotFoundException e) {
								System.out.println(e.getMessage());
							}
							break;
						}
					}
					System.out.println("Thank you for Accepting");
					System.out.println("");
					System.out.println("");
			break;
			
			case 4:
				try {
					System.out.println("     Returned Vehicles");
					System.out.println("----------------------------");
					
					List<Reservation> list1 = null;
					try {
						list1 = reservationService.vendorFindAllReservationsByStatus(vendorId, "Due");
					} catch (ResourceNotFoundException e) {
						System.out.println("findAllReservationsByStatus: "+e.getMessage());
					} catch (SQLException e) {
						System.out.println("findAllReservationsByStatus: "+e.getMessage());
					}
					
					
					if(list1 == null || list1.size() == 0)
						System.out.println("Sorry, you do not have any return request.");
					else {
						
						//Formatted list output
						System.out.format("%-15s%-15s%-15s%-15s%-15s%n","Customer","Vehicle",
								"StartDate","EndDate", "Status");
						System.out.println("--------------------------------------------------------------");
						for(Reservation r : list1) {
							System.out.format("%-15s%-15s%-15s%-15s%-15s%n",r.getCustomer_id(), r.getVehicle_id(),
									r.getReservation_start_date(), r.getReservation_end_date(), r.getReservation_status());
						}
						System.out.println();
						System.out.println("1. Accept a return");
					}
					System.out.println("0. Back");
					
					//Choice
					System.out.println("Enter you choice");
					int choice11 = sc.nextInt();
					
					//0 - back
					if(choice11 == 0) break;
					
					//1 - accept a return 
					System.out.println("Enter a Reservation Id to close that reservation:");
					int reservationId1 = sc.nextInt();
					
					for(Reservation r : list1) {
						if(r.getReservation_id() == reservationId1) {
							
							//Create a new object
							Reservation updatedReservation = new Reservation(r.getCustomer_id(), r.getVehicle_id(), 
									r.getReservation_id(), r.getReservation_start_date(), r.getReservation_end_date(),
									r.getReservation_total_cost(), "completed", r.getAdmin_id());
							
							//send updated object to update method
							try {
								reservationService.update(reservationId1, updatedReservation);
							} catch (ResourceNotFoundException e) {
								System.out.println(e.getMessage());
							}
							
							break;
						}
					}
					System.out.println("Thank you for Accepting");
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
				
				System.out.println("");
				System.out.println("");
				break;
				
			case 5:
				
					System.out.println("      Remove Vehicle");
					System.out.println("--------------------------");
					
					//List all vehicles vendor has
					try {
						System.out.println("      My vehicles");
						System.out.println("--------------------------");
						List<Vehicle> list1 = vehicleService.findMyVehicles(vendorId);
						System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%n","ID","Make","Model","RegistrationNo", "Availability", "Daily Rate");
						System.out.println("-------------------------------------------------------------------------------");
						for(Vehicle v : list1) {
							System.out.format("%-15s%-15s%-15s%-15s%-15s%-15s%n",v.getVehicle_id(), v.getVehicle_make(), v.getVehicle_model(),
									v.getVehicle_registration_no(), v.isVehicle_availability(), v.getVehicle_daily_rate());
						}
						System.out.println();
						
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
					
					//choice & //Pass vehicleId to vehicleService
					System.out.println("Enter Vehicle Id to remove: ");
					try {
						int id1 = sc.nextInt();
						vehicleService.deleteByid(id1);
						System.out.println("Vehicle deleted sucessfully!!");
					} catch (InvalidInputException e) {
						System.out.println(e.getMessage());
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
					
				System.out.println("");
				System.out.println("");
				break;
				
			case 6:
					System.out.println("   My Vehicle Reviews");
					System.out.println("--------------------------");
		
					
					//prepare a list
					List<Review> list1 = null;
					try {
						list1 = reviewService.getReviewsByVendorId(vendorId);
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					} catch (InvalidInputException e) {
						System.out.println(e.getMessage());
					}
					
					
					if(list1 == null || list1.size() == 0)
						System.out.println("Sorry, your vehicles do not have any reviews");
					else {
						
						//Formatted list output
						System.out.format("%-15s%-15s%-15s%-15s%n","Customer","Vehicle",
								"Comment","Rating(Out of 5)");
						System.out.println("--------------------------------------------------------------");
						for(Review r : list1) {
							System.out.format("%-15s%-15s%-15s%-15s%n",r.getCustomer_id(), r.getVehicle_id(),
									r.getReview_comment(), r.getReview_rating());
						}
						System.out.println();
					}
					
				//choice
				System.out.println("0. Back");
				int c1 = sc.nextInt();
				if(c1 == 0)
					break;
				
				System.out.println("");
				System.out.println("");
				break;
			}
		}
		sc.close();
	}
	
	//Login will call this method to access this controller
	public static void vendorMenu(String[] args) {
		
		VendorService vendorService = new VendorService();
		try {
			String vendorId = vendorService.getVendorIdByUsernamePassword(args[0], args[1]);
			String[] sarr = { vendorId };
			main(sarr);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch (ResourceNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}
