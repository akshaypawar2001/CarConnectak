package com.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.dto.VehicleDto;
import com.exception.InvalidInputException;

import com.model.Vehicle;
import com.service.VehicleService;

public class VehicleController {

	public static void main(String[] args) {
		VehicleService vehicleService=new VehicleService();
		Scanner sc=new Scanner(System.in);
		while(true) {
			System.out.println("--------Vehicle Operations-------");
			System.out.println("Press 1. Insert Vehicle");
			System.out.println("Press 2. Display All Vehicle");
			System.out.println("Press 3. Delete Vehicle");
			System.out.println("Press 4. Deactivate Delete Vehicle[Soft delete]");
			System.out.println("Press 5. Display Vehicle Stats");
			System.out.println("Press 6. Display All Available Vehicle");
			System.out.println("Press 7. Display Vendor's Vehicle");
			System.out.println("Press 8. Get Vehicle's Daily Rate");
			System.out.println("Press 9. Get Vehicle's Age");
			System.out.println("Press 0. To Exit");
			int input=sc.nextInt();
			if(input==0)
			{System.out.println("Exiting Vehicle Module...");
			break;	
			}
			switch(input) {
			case 1:
				Random random=new Random();
				int randomNumber = random.nextInt(); 
				int vehicle_id =randomNumber<0?randomNumber*-1:randomNumber; 
				System.out.println("Name of Model");
				sc.nextLine();
				String vehicle_model = sc.nextLine();   
				System.out.println("Enter Model's Manufacturer");
				String vehicle_make =sc.nextLine(); 
				System.out.println("Year of Manufacture");
				int vehicle_year=sc.nextInt();
				System.out.println("color of vehicle");
				sc.nextLine();
				String vehicle_color=sc.nextLine(); 
				
				System.out.println("Registration number of vehicle");
				String vehicle_registration_no=sc.nextLine();
				System.out.println("Availability of vehicle");
				Boolean vehicle_availability=sc.nextBoolean(); 
				System.out.println("Daily Rate of vehicle");
				Double vehicle_daily_rate=sc.nextDouble();
				List<Vehicle> list;
				try {
					list = vehicleService.findAll();
					for(Vehicle v : list) {
						System.out.println(v);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println("Enter Vendor ID");
				int vendor_id=sc.nextInt(); 
				Vehicle vehicle =new Vehicle(vehicle_id, vehicle_model, vehicle_make, vehicle_year, vehicle_color,
						 vehicle_registration_no, vehicle_availability, vehicle_daily_rate, vendor_id);
				
				
				int status;
				try {
					status = vehicleService.add(vehicle);
					if(status == 1)
						System.out.println("Vehicle record added to DB..");
					else
						System.out.println("Insert operation failed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				List<Vehicle> list1;
				try {
					list1 = vehicleService.findAll();
					for(Vehicle v:list1) {
						System.out.println(v);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				System.out.println("Enter vehicle ID");
				try {
					vehicleService.deleteByid(sc.nextInt());
					System.out.println("Artist record deleted..");
				} catch (SQLException e) {
					 System.out.println(e.getMessage());
				} catch (InvalidInputException e) {
					 System.out.println(e.getMessage());
				}				
				break; 
			/*case 4: 
				System.out.println("Enter vehicle ID");
				try {
					vehicleService.softDeleteByid(sc.nextInt());
					System.out.println("Artist record de-activated..");
				} catch (InvalidInputException e) {
					System.out.println(e.getMessage());
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}				
				break;*/ 
			case 5:
				try {
					List<VehicleDto> list11 = vehicleService.getVehicleStats();
					System.out.println("------------------------------------------------------------\n");
					System.out.format("%15s%18s%20s","Vendor Id", "Name of Vendor", "Number of Vehicle");
					System.out.println("\n-----------------------------------------------------------");
					for(VehicleDto a : list11) {
						System.out.format("%15d%18s%10d", a.getVendor_id(), a.getVendorName(),a.getNumberOfVehicles());
						System.out.print("\n");
				}
					System.out.println("-------------------------------------------------------------");
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}	
				break; 
			case 6:
				List<Vehicle> list11;
				try {
					list11 = vehicleService.findAllAvailable();
					for(Vehicle v1:list11) {
						System.out.println(v1);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
			break;
			case 7:
				try {
					//display all artists 
					List<Vehicle> list111 = vehicleService.findAll();
					for(Vehicle v : list111) {
						System.out.println(v);
					}
					//read artist id
					System.out.println("Enter Vendor ID");
					int vendorId = sc.nextInt();
					
					//fetch artworks of this artist
					List<Vehicle> listVehicle = vehicleService.findMyVehicles(vendorId);
					for(Vehicle a : listVehicle) {
						System.out.println(a);
					}
				}  catch (SQLException e) {
					System.out.println(e.getMessage());
				}				
				break; 
			case 8:
				try {
				List<Vehicle> list111 = vehicleService.findAll();
				for(Vehicle v : list111) {
					System.out.println(v);
				}
				System.out.println("Enter Vehicle ID");
				int vehicleId = sc.nextInt();
				double vehicleDailyRate=vehicleService.getDailyRate(vehicleId);
				System.out.println(vehicleDailyRate);}
				catch (SQLException e) {
					System.out.println(e.getMessage());
				}	
				catch (InvalidInputException e) {
					System.out.println(e.getMessage());
				}	
				break;
			case 9:
				System.out.println("Enter vehicle ID");
				int vehicle_id1=sc.nextInt();
				try {
					int age;
					
						age = vehicleService.getVehicleAge(vehicle_id1);
						System.out.println(age);
					} catch (SQLException|InvalidInputException e) {
						System.out.println(e.getMessage());
					}
					
				 
			}
		}
sc.close();
	}

}
