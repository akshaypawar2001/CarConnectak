package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dto.ReviewDto;
import com.exception.InvalidInputException;
import com.model.Review;
import com.utility.DBConnection;

public class ReviewDaoImpl implements ReviewDao{

	@Override
	public int add(Review review) throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql="INSERT INTO Review (customer_id, vehicle_id, review_comment, review_id, review_rating)"
				+ "VALUES "
				+ "    (?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		
		pstmt.setInt(1, review.getCustomer_id());
		pstmt.setInt(2, review.getVehicle_id());
		pstmt.setString(3,review.getReview_comment());
		pstmt.setInt(4, review.getReview_id());
		pstmt.setInt(5, review.getReview_rating());
		
		int status=pstmt.executeUpdate();
		DBConnection.dbClose();
		return status;
	}

	@Override
	public List<Review> findAll() throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql="select * from review ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rst = pstmt.executeQuery();
		
		List<Review> list = new ArrayList<>();
		while(rst.next() == true) {
			int review_id  = rst.getInt("review_id");
			int customer_id  = rst.getInt("customer_id");
			int vehicle_id  = rst.getInt("vehicle_id");
			String review_comment = rst.getString("review_comment");
			int review_rating  = rst.getInt("review_rating");
			Review review = new Review(review_id,customer_id,vehicle_id,review_comment,review_rating); //100X 200X 300X
			list.add(review);
		}
		DBConnection.dbClose();		
		return list;
	}

	@Override
	public void deleteById(int id) throws SQLException, InvalidInputException {
		Connection con = DBConnection.dbConnect();
		String sql="delete from review where customer_id =?";
		//prepare the statement 
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
		DBConnection.dbClose();
		
	}

	@Override
	public boolean findOne(int customerId) throws SQLException  {
		Connection con = DBConnection.dbConnect();
		String sql="select customer_id from review where customer_id=?";
		//prepare the statement 
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, customerId);
		ResultSet rst  = pstmt.executeQuery();
		boolean status = rst.next(); //true / false
		DBConnection.dbClose();
		return status;
	}

	@Override
	public List<Review> getReviewsByVendorId(int vendor_id) throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql="select r.* from review r join vehicle v on r.vehicle_id=v.vehicle_id"
				+ " join vendor vd on vd.vendor_id=v.vendor_id where vd.vendor_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, vendor_id);
		ResultSet rst = pstmt.executeQuery();
		List<Review> list = new ArrayList<>();
		while(rst.next() == true) {
			int review_id = rst.getInt("review_id");
			int customer_id = rst.getInt("customer_id");
			int vehicle_id = rst.getInt("vehicle_id");
			
			String review_comment = rst.getString("review_comment");
			int review_rating = rst.getInt("review_rating");
			
			Review review = new Review(review_id,customer_id,vehicle_id,review_comment,review_rating); //100X 200X 300X
			list.add(review);
		}
		DBConnection.dbClose();		
		return list;
	}

	@Override
	public List<Review> getReviewsByVehicleId(int vehicle_id) throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql=" select r.* from review r join vehicle v on r.vehicle_id=v.vehicle_id where v.vehicle_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, vehicle_id);
		ResultSet rst = pstmt.executeQuery();
		List<Review> list = new ArrayList<>();
		while(rst.next() == true) {
			int review_id = rst.getInt("review_id");
			int customer_id = rst.getInt("customer_id");
			String review_comment = rst.getString("review_comment");
			int review_rating = rst.getInt("review_rating");
			
			Review review = new Review(review_id,customer_id,vehicle_id,review_comment,review_rating); //100X 200X 300X
			list.add(review);
		}
		DBConnection.dbClose();		
		return list;
	}

	@Override
	public List<ReviewDto> getReviewStats() throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql="select v.vehicle_id,v.vehicle_model,vehicle_make,"
				+ "count(v.vehicle_id)as numberOfReviews,avg(r.review_rating)as avgRatingOfVehicle"
				+ " from review r join vehicle v on r.vehicle_id=v.vehicle_id "
				+ "group by v.vehicle_id;";
		PreparedStatement pstmt = con.prepareStatement(sql);
		 
		ResultSet rst = pstmt.executeQuery();
		List<ReviewDto> list = new ArrayList<>();
		while(rst.next() == true) {
		 int vehicle_id= rst.getInt("vehicle_id");;
			 String vehicle_model = rst.getString("vehicle_model");;
			 String vehicle_make = rst.getString("vehicle_make");;
			 int numberOfReviews=rst.getInt("numberOfReviews");
			 double avgRatingOfVehicle=rst.getDouble("avgRatingOfVehicle");
			
			 
		ReviewDto review = new ReviewDto(vehicle_id,vehicle_model,vehicle_make,numberOfReviews,avgRatingOfVehicle); //100X 200X 300X
			list.add(review);
		}
		DBConnection.dbClose();		
		return list;
		
	}

	@Override
	public boolean findVehicle(int vehicleId) throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql="select v.vehicle_id from review r join vehicle v on r.vehicle_id=v.vehicle_id where v.vehicle_id=?";
		//prepare the statement 
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, vehicleId);
		ResultSet rst  = pstmt.executeQuery();
		boolean status = rst.next(); //true / false
		DBConnection.dbClose();
		return status;
	}

	@Override
	public boolean findVendor(int vendorId) throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql="select v.vendor_id from review r join vehicle v on r.vehicle_id=v.vehicle_id where v.vendor_id=?";
		//prepare the statement 
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, vendorId);
		ResultSet rst  = pstmt.executeQuery();
		boolean status = rst.next(); //true / false
		DBConnection.dbClose();
		return status;
	}

	

	



}
