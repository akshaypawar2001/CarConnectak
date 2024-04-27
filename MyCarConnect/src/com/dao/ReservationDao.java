package com.dao;

import java.sql.SQLException;
import java.util.List;
import com.dto.ReservationPerCustomer;
import com.exception.ResourceNotFoundException;
import com.model.Reservation;

public interface ReservationDao {

	//CRUD
	int save(Reservation reservation) throws SQLException; // 1 for successful insertion / 0 for failure
	int deleteById(int id)throws SQLException, ResourceNotFoundException;
	void softDeleteById(int id) throws SQLException,ResourceNotFoundException;
	int update(int id, Reservation updatedReservation) throws SQLException, ResourceNotFoundException;
	List<Reservation> findAll() throws SQLException;
	Boolean findOne(int id) throws SQLException, ResourceNotFoundException;
	
	//Extra
	List<Reservation> customerFindAllReservationsByStatus(int id, String status) throws SQLException, ResourceNotFoundException;
	List<Reservation> vendorFindAllReservationsByStatus(int id, String status) throws SQLException, ResourceNotFoundException;

	List<Reservation> findAllReservationsById(int id) throws SQLException, ResourceNotFoundException;
	//GroupBy
	List<ReservationPerCustomer> getReservationCountPerCustomer() throws SQLException;
}
