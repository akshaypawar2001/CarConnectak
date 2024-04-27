package com.service;

import java.sql.SQLException;
import java.util.List;

import com.dao.ReviewDao;
import com.dao.ReviewDaoImpl;
import com.dto.ReviewDto;
import com.exception.InvalidInputException;
import com.model.Review;

public class ReviewService {
   ReviewDao dao=new ReviewDaoImpl();
	public int add(Review review) throws SQLException {
		return dao.add(review);
		
	}
	
	public List<Review> findAll() throws SQLException {
	
		return dao.findAll();
	}

	public void deleteByid(int nextInt) throws SQLException, InvalidInputException {
		boolean isIdValid = dao.findOne(nextInt);
		if(!isIdValid)
			throw new InvalidInputException("Invalid Customer Id!");
		 
		dao.deleteById(nextInt);
		
		
	}

	public List<Review> getReviewsByVendorId(int vendor_id) throws SQLException, InvalidInputException {
		boolean isvendor_idValid = dao.findVendor(vendor_id);
		if(!isvendor_idValid)
			throw new InvalidInputException("Invalid Vendor Id");
	
          return dao.getReviewsByVendorId(vendor_id);
		
	}

	public List<Review> getReviewsByVehicleId(int vehicle_id) throws SQLException, InvalidInputException{
		boolean isvendor_idValid = dao.findVehicle(vehicle_id);
		if(!isvendor_idValid)
			throw new InvalidInputException("Invalid Vehicle Id");
	
          return dao.getReviewsByVehicleId(vehicle_id);
	}

	public List<ReviewDto> getReviewStats() throws SQLException {
		return dao.getReviewStats();
	}

	
	

}
