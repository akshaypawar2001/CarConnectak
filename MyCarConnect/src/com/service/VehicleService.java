package com.service;
import java.time.Year;


import java.sql.SQLException;
import java.util.List;

import com.dao.VehicleDao;
import com.dao.VehicleDaoImpl;
import com.dto.VehicleDto;
import com.exception.InvalidInputException;

import com.model.Vehicle;


public class VehicleService {
	VehicleDao dao=new VehicleDaoImpl();
	public int add(Vehicle vehicle) throws SQLException {
		return dao.addVehicle(vehicle);
		
	}
	public List<Vehicle> findAll() throws SQLException {
		return dao.findAll();
		
	}
	public void deleteByid(int id) throws InvalidInputException, SQLException {
		boolean isIdValid = dao.findOne(id);
		if(!isIdValid)
			throw new InvalidInputException("Id given is Invalid!!");
		 
		dao.deleteById(id);
		
	}
	/*public void softDeleteByid(int id) throws InvalidInputException, SQLException {
		boolean isIdValid = dao.findOne(id);
		if(!isIdValid)
			throw new InvalidInputException("Id given is Invalid!!");
		 
		dao.softDeleteById(id);
		
	}*/
	public List<VehicleDto> getVehicleStats() throws SQLException {
		return dao.getVehicleStats();
	}
	public List<Vehicle> findAllAvailable() throws SQLException {
	
		return dao.findAllAvailable();
	}
	public List<Vehicle> findMyVehicles(int vendorId) throws SQLException {
		// TODO Auto-generated method stub
		return dao.findMyVehicles(vendorId);
	}
	public double getDailyRate(int vehicleId) throws SQLException, InvalidInputException {
		
		return dao.getDailyRate(vehicleId);
	}
	
	public int getVehicleAge(int vehicle_id) throws SQLException, InvalidInputException{
	
		VehicleDaoImpl vehicleDaoImpl=new VehicleDaoImpl();
		 int currentYear = Year.now().getValue();
		int age=currentYear-vehicleDaoImpl.getVehicleYear(vehicle_id);
		if(age<0) {
			throw new InvalidInputException("Vehicle Id is Invalid, Try Again!");
		}
		if(age>20) {
			throw new InvalidInputException("Vehicle Id is Invalid, Try Again!");
		}
			
		return age;
		
		
	}

}
