package com.dao;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dto.VehicleDto;
import com.exception.InvalidInputException;
import com.model.Vehicle;
import com.utility.DBConnection;

public class VehicleDaoImpl implements VehicleDao {

	@Override
	public int addVehicle(Vehicle vehicle) throws SQLException {
		Connection con=DBConnection.dbConnect();
		String sql="INSERT INTO Vehicle (vehicle_id, vehicle_model, vehicle_make, vehicle_year, vehicle_color,"
				+ " vehicle_registration_no, vehicle_availability, vehicle_daily_rate, vendor_id)"
				+ "VALUES "
				+ "(?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, vehicle.getVehicle_id());
		pstmt.setString(2,vehicle.getVehicle_model());
		pstmt.setString(3,vehicle.getVehicle_make());
		pstmt.setInt(4, vehicle.getVehicle_year());
		pstmt.setString(5,vehicle.getVehicle_color());
		pstmt.setString(6,vehicle.getVehicle_registration_no());
		pstmt.setBoolean(7,vehicle.isVehicle_availability());
		pstmt.setDouble(8, vehicle.getVehicle_daily_rate());
		pstmt.setInt(9, vehicle.getVendor_id());
		int status=pstmt.executeUpdate();		
		
		DBConnection.dbClose();
		return status;
	}

	@Override
	public List<Vehicle> findAll() throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql="select * from vehicle  ";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rst = pstmt.executeQuery();
		List<Vehicle> list = new ArrayList<>();
		while(rst.next() == true) {
			int vehicle_id=rst.getInt("vehicle_id");
			String vehicle_model =rst.getString("vehicle_model");
			String vehicle_make =rst.getString("vehicle_make");
			int vehicle_year=rst.getInt("vehicle_year");
			String vehicle_color=rst.getString("vehicle_color");
			String vehicle_registration_no=rst.getString("vehicle_registration_no");
			Boolean vehicle_availability=rst.getBoolean("vehicle_availability");
			Double vehicle_daily_rate=rst.getDouble("vehicle_daily_rate");
			int vendor_id=rst.getInt("vendor_id");
			Vehicle vehicle =new Vehicle(vehicle_id, vehicle_model, vehicle_make, vehicle_year, vehicle_color,
					 vehicle_registration_no, vehicle_availability, vehicle_daily_rate, vendor_id);
			list.add(vehicle);			
		}
		DBConnection.dbClose();
		return list;
	}

	@Override
	public void deleteById(int vehicleId) throws SQLException, InvalidInputException {
		Connection con = DBConnection.dbConnect();
		String sql="delete from vehicle where vehicle_id =?";
		//prepare the statement 
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, vehicleId);
		pstmt.executeUpdate();
		DBConnection.dbClose();
		
	}

	@Override
	public Boolean findOne(int vehicleId) throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql="select vehicle_id from vehicle where vehicle_id=?";
		//prepare the statement 
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, vehicleId);
		ResultSet rst  = pstmt.executeQuery();
		boolean status = rst.next(); 
		//true / false
		DBConnection.dbClose();
		return status;
	}

	
	/*
	@Override
	public void softDeleteById(int id) throws SQLException, InvalidInputException {
		Connection con = DBConnection.dbConnect();
		String sql="update vehicle SET isActive='no' where vehicle_id =?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.executeUpdate();
		DBConnection.dbClose();
		
	}*/

	@Override
	public List<VehicleDto> getVehicleStats() throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql="select v.vendor_id,concat(vendor_first_name,\" \",vendor_last_name)as vendorName,count(v.vendor_id)as numberOfVehicles from vendor v join vehicle ve on v.vendor_id=ve.vendor_id group by v.vendor_id;";
		PreparedStatement pstmt = con.prepareStatement(sql);
		 
		ResultSet rst = pstmt.executeQuery();
		List<VehicleDto> list = new ArrayList<>();
		while(rst.next() == true) {
			 int vendor_id=rst.getInt("vendor_id");
			 String vendorName=rst.getString("vendorName");;
			 int numberOfVehicles= rst.getInt("numberOfVehicles");;
			
			 
			VehicleDto vehicle1 = new VehicleDto(vendor_id,vendorName,numberOfVehicles); //100X 200X 300X
			list.add(vehicle1);
		}
		DBConnection.dbClose();		
		return list;
	}

	@Override
	public List<Vehicle> findAllAvailable() throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql="select * from vehicle where vehicle_availability=1";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rst = pstmt.executeQuery();
		List<Vehicle> list = new ArrayList<>();
		while(rst.next() == true) {
			int vehicle_id=rst.getInt("vehicle_id");
			String vehicle_model =rst.getString("vehicle_model");
			String vehicle_make =rst.getString("vehicle_make");
			int vehicle_year=rst.getInt("vehicle_year");
			String vehicle_color=rst.getString("vehicle_color");
			String vehicle_registration_no=rst.getString("vehicle_registration_no");
			Boolean vehicle_availability=rst.getBoolean("vehicle_availability");
			Double vehicle_daily_rate=rst.getDouble("vehicle_daily_rate");
			int vendor_id=rst.getInt("vendor_id");
			Vehicle vehicle =new Vehicle(vehicle_id, vehicle_model, vehicle_make, vehicle_year, vehicle_color,
					 vehicle_registration_no, vehicle_availability, vehicle_daily_rate, vendor_id);
			list.add(vehicle);			
		}
		DBConnection.dbClose();
		return list;
	}

	@Override
	public List<Vehicle> findMyVehicles(int vendorId) throws SQLException {
		Connection con = DBConnection.dbConnect();
		/*Sub-Query*/
		String sql="select * from vehicle  where vendor_id in "
				+ "(select vendor_id from vendor where vendor_id=?);";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, vendorId);
		ResultSet rst = pstmt.executeQuery();
		List<Vehicle> list = new ArrayList<>();
		while(rst.next() == true) {
			int vehicle_id=rst.getInt("vehicle_id");
			String vehicle_model =rst.getString("vehicle_model");
			String vehicle_make =rst.getString("vehicle_make");
			int vehicle_year=rst.getInt("vehicle_year");
			String vehicle_color=rst.getString("vehicle_color");
			String vehicle_registration_no=rst.getString("vehicle_registration_no");
			Boolean vehicle_availability=rst.getBoolean("vehicle_availability");
			Double vehicle_daily_rate=rst.getDouble("vehicle_daily_rate");
			
			Vehicle vehicle =new Vehicle(vehicle_id, vehicle_model, vehicle_make, vehicle_year, vehicle_color,
					 vehicle_registration_no, vehicle_availability, vehicle_daily_rate, vendorId);
			
			list.add(vehicle);
		}
		DBConnection.dbClose();		
		return list;
	}

	@Override
	public double getDailyRate(int vehicleId) throws SQLException, InvalidInputException {
		Connection con = DBConnection.dbConnect();
		String sql="select vehicle_daily_rate from vehicle where vehicle_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, vehicleId);
		ResultSet rst = pstmt.executeQuery();
		
		if(rst.next()==true) {
			double vehicleDailyRate=rst.getDouble("vehicle_daily_rate");
			DBConnection.dbClose();	
		return vehicleDailyRate;}
		else {
			throw new InvalidInputException("vehicle Id is not valid");
		}
		
	}

	@Override
	public int getVehicleYear(int vehicleId) throws SQLException, InvalidInputException {
		Connection con = DBConnection.dbConnect();
		String sql="select vehicle_year from vehicle where vehicle_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, vehicleId);
		ResultSet rst = pstmt.executeQuery();
		
		if(rst.next()==true) {
			int vehicle_year=rst.getInt("vehicle_year");
			DBConnection.dbClose();	
		return vehicle_year;}
		else {
			throw new InvalidInputException("vehicle Id is not valid");
		}
	}

	
	

	

	
	

	

	

}
