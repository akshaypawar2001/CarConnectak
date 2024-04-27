package com.dao;

import java.sql.SQLException;
import java.util.*;

import com.dto.VendorAndCount;
import com.exception.ResourceNotFoundException;
import com.model.Vendor;

public interface VendorDao {
	
	//CRUD
	int save(Vendor vendor) throws SQLException;
	int deleteById(int id)throws SQLException, ResourceNotFoundException;
	void softDeleteById(int id) throws SQLException,ResourceNotFoundException;
	int update(int id, Vendor updatedVendor) throws SQLException, ResourceNotFoundException;
	List<Vendor> findAll() throws SQLException;
	Boolean findOne(int id) throws SQLException;
	
	List<Vendor> findAllActiveVendor() throws SQLException;
	List<VendorAndCount> countVendorVehicle() throws SQLException; 
	List<VendorAndCount> vendorWithGoodReviewCount() throws SQLException; // Review rating >= 4
	List<VendorAndCount> vendorWithBadReviewCount() throws SQLException; // Review rating <= 3
	String getVendorIdByUsernamePassword(String username, String password) throws SQLException, ResourceNotFoundException;
}
