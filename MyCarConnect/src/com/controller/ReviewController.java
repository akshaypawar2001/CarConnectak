package com.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.dto.ReviewDto;
import com.exception.InvalidInputException;
import com.model.Review;

import com.service.ReviewService;

public class ReviewController {

	public static void main(String[] args)   {
	ReviewService reviewService=new ReviewService();
	Scanner sc=new Scanner(System.in);
	while(true) {
		System.out.println("--------Review Operations-------");
		System.out.println("Press 1. Add Review");
		System.out.println("Press 2. Display All Reviews");
		System.out.println("Press 3. Delete Review");
		System.out.println("Press 4. Display reviews for vendor Id");
		System.out.println("Press 5. Display reviews for vehicle Id");
		System.out.println("Press 6. Display Review Stats");
		System.out.println("Press 0. To Exit");
		int input=sc.nextInt();
		if(input==0)
		{System.out.println("Exiting Review Module...");
		break;	
		}
		switch(input) {
		case 1:
			Random random=new Random();
			int randomNumber = random.nextInt(); 
			int review_id =randomNumber<0?randomNumber*-1:randomNumber; 
			System.out.println("customer id");
			//sc.nextLine();
			int customer_id= sc.nextInt();   
			System.out.println("vehicle id");
			//sc.nextLine();
			int vehicle_id= sc.nextInt(); 
			System.out.println("comment");
			sc.nextLine();
			String review_comment =sc.nextLine(); 
			
			System.out.println("review rating");
			int review_rating= sc.nextInt(); 
			 
			Review review =new Review(review_id,customer_id,vehicle_id,review_comment,review_rating);
			
			
			
			try {
				int status = reviewService.add(review);
				
				if(status == 1)
					System.out.println("review record added to DB..");
				else
					System.out.println("Adding operation failed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
			break;
		
		case 2:
			 
			try {
			
			 List<Review> list = reviewService.findAll();
				for(Review r: list) {
					System.out.println(r);}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}break;
			
		case 3:
			System.out.println("Enter customer_id");
			try {
				reviewService.deleteByid(sc.nextInt());
				System.out.println("review record deleted..");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 4:
			try {
			//List<Review> list = reviewService.findAll();
			//for(Review a : list) {
				//System.out.println(a);
			//}
			//read artist id
			System.out.println("Enter Vendor ID");
			int vendor_id = sc.nextInt();
			
			//fetch artworks of this artist
			List<Review> listReview = reviewService.getReviewsByVendorId(vendor_id);
			for(Review r : listReview) {
				System.out.println(r);
			}}
			catch (InvalidInputException e) {
				System.out.println(e.getMessage());
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}	
           break;
		case 5:
			try {
			List<Review> list = reviewService.findAll();
			for(Review a : list) {
				System.out.println(a);
			}
			//read artist id
			System.out.println("Enter Vehicle ID");
			int vehicle_id1 = sc.nextInt();
			
			//fetch artworks of this artist
			List<Review> listReview = reviewService.getReviewsByVehicleId(vehicle_id1);
			for(Review r : listReview) {
				System.out.println(r);
			}}
			catch (InvalidInputException e) {
				System.out.println(e.getMessage());
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}	
			break;
		case 6:
			try {
				List<ReviewDto> list = reviewService.getReviewStats();
				System.out.println("-----------------------------------------------------------------------------------------\n");
				System.out.format("%12s%15s%20s%20s%24s", "Vehicle Id", "Model Name", "Manufacturer Name", "Number of Reviews", "Avg Rating Of Vehicle");
				System.out.println("\n----------------------------------------------------------------------------------");
				for(ReviewDto a : list) {
					System.out.format("%12d%15s%20s%20d%22f", a.getVehicle_id(), a.getVehicle_model(),a.getVehicle_make(),a.getNumberOfReviews(),a.getAvgRatingOfVehicle());
					System.out.print("\n");
			}
				System.out.println("---------------------------------------------------------------------------------------");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}	
			break; 
		
			
		}
		}
	sc.close();
	}}

