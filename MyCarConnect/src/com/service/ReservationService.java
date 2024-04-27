package com.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.dao.ReservationDao;
import com.dao.ReservationDaoImpl;
import com.dto.ReservationPerCustomer;
import com.exception.InvalidInputException;
import com.exception.ResourceNotFoundException;
import com.model.Reservation;

public class ReservationService {

	ReservationDao dao = new ReservationDaoImpl();
	VehicleService vehicleService = new VehicleService(); 
	
	public int save(Reservation reservation) throws SQLException{
		return dao.save(reservation);
	}
	
	public int deleteById(int id) throws SQLException, ResourceNotFoundException{
		return dao.deleteById(id);
	}
	
	public void softDeleteById(int id) throws SQLException,ResourceNotFoundException{
		dao.softDeleteById(id);
	}
	
	public int update(int id, Reservation updatedReservation) throws SQLException, ResourceNotFoundException{
		return dao.update(id, updatedReservation);
		
	}
	
	public List<Reservation> findAll() throws SQLException{
		return dao.findAll();
	}
	
	public Boolean findOne(int id) throws SQLException, ResourceNotFoundException{
		return dao.findOne(id);
	}

	
	public List<Reservation> findAllReservationsById(int id) throws SQLException, ResourceNotFoundException {
		return dao.findAllReservationsById(id);
	}
	
	public List<ReservationPerCustomer> getReservationCountPerCustomer() throws SQLException{
		return dao.getReservationCountPerCustomer();
	}
	
	
	public double getTotalCost(int vehicleId, String startDate, String endDate) throws SQLException,
	InvalidInputException, DateTimeParseException {
		
		//Get Daily rate of a vehicle by vehicleId
		double costPerDay = vehicleService.getDailyRate(vehicleId);
						
		// Parse the date strings to LocalDate objects
		LocalDate date1 = null;
		LocalDate date2 = null;
		try {
			date1 = LocalDate.parse(startDate);
			date2 = LocalDate.parse(endDate);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}

		// Calculate the difference in days between the two dates
		int daysDifference = (int) ChronoUnit.DAYS.between(date1, date2);
				        
		return costPerDay*daysDifference;
	}
	
	public List<Reservation> customerFindAllReservationsByStatus(int id, String status) throws SQLException, ResourceNotFoundException {
		
		return dao.customerFindAllReservationsByStatus(id, status);
	}
	
	public List<Reservation> vendorFindAllReservationsByStatus(int id, String status) throws SQLException, ResourceNotFoundException {
		
		return dao.vendorFindAllReservationsByStatus(id, status);
	}
}
