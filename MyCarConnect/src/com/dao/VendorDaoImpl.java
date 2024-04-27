package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dto.VendorAndCount;
import com.exception.ResourceNotFoundException;
import com.model.Vendor;
import com.utility.DBConnection;

public class VendorDaoImpl implements VendorDao{

	
	@Override
	public int save(Vendor vendor) throws SQLException {
		
		//open connection
		Connection con = DBConnection.dbConnect();
		
		String sql = "INSERT INTO Vendor (vendor_id, vendor_first_name, vendor_last_name,"
				+ "vendor_email, vendor_phone_number,"
				+ "user_id, vendor_registration_date, address_id)"
				+ "VALUES (?,?,?,?,?,?,?,?)";
			
		//prepare statement
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		//attach the data
		pstmt.setInt(1, vendor.getVendor_id());
		pstmt.setString(2, vendor.getVendor_first_name());
		pstmt.setString(3, vendor.getVendor_last_name());
		pstmt.setString(4, vendor.getVendor_email());
		pstmt.setString(5, vendor.getVendor_phone_number());
		pstmt.setInt(6, vendor.getUser_id());
		pstmt.setString(7, vendor.getVendor_registration_date());
		pstmt.setInt(8, vendor.getAddress_id());
		
		//execute the query
		int status = pstmt.executeUpdate();
		
		//close db connection
		DBConnection.dbClose();
		
		return status;
	}

	
	@Override
	public int deleteById(int id) throws SQLException, ResourceNotFoundException {
		
		//open db connection
		Connection con = DBConnection.dbConnect();
		
		String sql = "delete from vendor where vendor_id = ?";
		
		//prepare the statement
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, id);
		
		//execute query
		int status = pstmt.executeUpdate();
		
		//close db connection
		DBConnection.dbClose();
		return status;
	}

	
	@Override
	public int update(int id, Vendor updatedVendor) throws SQLException, ResourceNotFoundException {
		Connection con = DBConnection.dbConnect();
		String sql = "update vendor"
				+ "set vendor_id = ?, "
				+ "vendor_first_name = ?,"
				+ "vendor_last_name = ?,"
				+ "vendor_email = ?,"
				+ "vendor_phone_number = ?,"
				+ "user_id = ?,"
				+ "vendor_registration_date = ?,"
				+ "address_id = ?"
				+ "WHERE vendor_id = ?";
		
		//prepare statement
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, updatedVendor.getVendor_id());
		pstmt.setString(2, updatedVendor.getVendor_first_name());
		pstmt.setString(3, updatedVendor.getVendor_last_name());
		pstmt.setString(4, updatedVendor.getVendor_email());
		pstmt.setString(5, updatedVendor.getVendor_phone_number());
		pstmt.setInt(6, updatedVendor.getUser_id());
		pstmt.setString(7, updatedVendor.getVendor_registration_date());
		pstmt.setInt(8, updatedVendor.getAddress_id());
		
		int status = pstmt.executeUpdate(); // 1 - updates/ 0 - not updated
		
		DBConnection.dbClose();
		return status;
	}

	
	@Override
	public List<Vendor> findAll() throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql = "select * from vendor";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rst = pstmt.executeQuery();
		List<Vendor> list = new ArrayList<>();
		while(rst.next()) {
			int id = rst.getInt("vendor_id");
			String fName = rst.getString("vendor_first_name");
			String lName = rst.getString("vendor_last_name");
			String email = rst.getString("vendor_email");
			String phoneNo = rst.getString("vendor_phone_number");
			int uId = rst.getInt("user_id");
			String rDate = rst.getString("vendor_registration_date");
			int aId = rst.getInt("address_id");
			Vendor v = new Vendor(id, fName, lName, email, phoneNo, rDate, uId, aId);
			list.add(v);
		}
		DBConnection.dbClose();
		return list;
	}

	
	@Override
	public Boolean findOne(int id) throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql = "select vendor_id from vendor where vendor_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rst = pstmt.executeQuery();
		boolean status = rst.next();
		DBConnection.dbClose();
		return status;
	}

	
	@Override
	public void softDeleteById(int id) throws SQLException, ResourceNotFoundException {
		Connection con = DBConnection.dbConnect();
		String sql = "update vendor set vendor_isActive='no' where vendor_id = ?";
		
		//prepare and set statement
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, id);
		
		//execute query
		pstmt.executeUpdate();
		
		//close db connection
		DBConnection.dbClose();
	}

	
	
	

	@Override
	public List<Vendor> findAllActiveVendor() throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql = "select * from vendor where vendor_isActive = 'yes'";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rst = pstmt.executeQuery();
		List<Vendor> list = new ArrayList<>();
		while(rst.next()) {
			int id = rst.getInt("vendor_id");
			String fName = rst.getString("vendor_first_name");
			String lName = rst.getString("vendor_last_name");
			String email = rst.getString("vendor_email");
			String phoneNo = rst.getString("vendor_phone_number");
			int uId = rst.getInt("user_id");
			String rDate = rst.getString("vendor_registration_date");
			int aId = rst.getInt("address_id");
			Vendor v = new Vendor(id, fName, lName, email, phoneNo, rDate, uId, aId);
			list.add(v);
		}
		DBConnection.dbClose();
		return list;
	}


	@Override
	public List<VendorAndCount> countVendorVehicle() throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql = "select vendor_first_name, vendor_last_name, count(*) as VehicleCount"
				+ "from vendor vd left join vehicle v on vd.vendor_id = v.vendor_id"
				+ "group by vendor_first_name, vendor_last_name";
				//+ "order by VehicleCount desc;";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rst = pstmt.executeQuery();
		List<VendorAndCount> list = new ArrayList<>();
		while(rst.next()) {
			String fName = rst.getString("vendor_first_name");
			String lName = rst.getString("vendor_last_name");
			int vCount = rst.getInt("VehicleCount");
			
			VendorAndCount vc = new VendorAndCount(fName, lName, vCount);
			list.add(vc);
		}
		DBConnection.dbClose();
		return list;
	}


	@Override
	public List<VendorAndCount> vendorWithGoodReviewCount() throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql = "select vendor_first_name, vendor_last_name, count(*) as Above3StarReviewCount"
				+ "from vendor vd join vehicle v on vd.vendor_id = v.vendor_id"
				+ "join review r on r.vehicle_id = v.vehicle_id"
				+ "where r.review_rating >= 4"
				+ "group by vendor_first_name, vendor_last_name";
				//+ "order by Above4StarReviewCount desc";
		
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rst = pstmt.executeQuery();
		List<VendorAndCount> list = new ArrayList<>();
		while(rst.next()) {
			String fName = rst.getString("vendor_first_name");
			String lName = rst.getString("vendor_last_name");
			int vCount = rst.getInt("Above3StarReviewCount");
			
			VendorAndCount vc = new VendorAndCount(fName, lName, vCount);
			list.add(vc);
		}
		DBConnection.dbClose();
		return list;
	}


	@Override
	public List<VendorAndCount> vendorWithBadReviewCount() throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql = "select vendor_first_name, vendor_last_name, count(*) as Below3StarReviewCount"
				+ "from vendor vd join vehicle v on vd.vendor_id = v.vendor_id"
				+ "join review r on r.vehicle_id = v.vehicle_id"
				+ "where r.review_rating < 3"
				+ "group by vendor_first_name, vendor_last_name";
				//+ "order by Above4StarReviewCount desc";
		
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rst = pstmt.executeQuery();
		List<VendorAndCount> list = new ArrayList<>();
		while(rst.next()) {
			String fName = rst.getString("vendor_first_name");
			String lName = rst.getString("vendor_last_name");
			int vCount = rst.getInt("Below3StarReviewCounts");
			
			VendorAndCount vc = new VendorAndCount(fName, lName, vCount);
			list.add(vc);
		}
		DBConnection.dbClose();
		return list;
	}


	@Override
	public String getVendorIdByUsernamePassword(String username, String password) throws SQLException, ResourceNotFoundException {
		
		Connection con = DBConnection.dbConnect();
		String sql = "select v.vendor_id"
				+ "from user u join vendor v on u.user_id = v.user_id"
				+ "where u.user_username = ? and u.user_password = ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, username);
		pstmt.setString(2, password);
		
		ResultSet rst = pstmt.executeQuery();
		int vendor_id = rst.getInt("vendor_id");
		
		DBConnection.dbClose();
		return ""+vendor_id;
	}
	
	

}

