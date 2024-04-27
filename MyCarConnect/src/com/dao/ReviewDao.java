package com.dao;

import java.sql.SQLException;
import java.util.List;

import com.dto.ReviewDto;
import com.exception.InvalidInputException;
import com.model.Review;


public interface ReviewDao {
	 int add(Review review) throws SQLException;
      List<Review> findAll() throws SQLException;
      void deleteById(int id) throws SQLException,InvalidInputException;
	boolean findOne(int customerId) throws SQLException;
	List<Review> getReviewsByVendorId(int vendor_id) throws SQLException;
	List<Review> getReviewsByVehicleId(int vehicle_id) throws SQLException;
	List<ReviewDto> getReviewStats() throws SQLException;
	boolean findVehicle(int vehicleId) throws SQLException;
	boolean findVendor(int vendorId) throws SQLException;
}
