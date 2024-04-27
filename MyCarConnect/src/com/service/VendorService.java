package com.service;


import java.sql.SQLException;
import java.util.List;

import com.dao.VendorDao;
import com.dao.VendorDaoImpl;
import com.dto.VendorAndCount;
import com.exception.ResourceNotFoundException;
import com.model.Vendor;

public class VendorService {
	
	VendorDao dao = new VendorDaoImpl();
	
	public int save(Vendor v) throws SQLException{
		return dao.save(v);
	}
	
	public int deleteById(int id) throws SQLException, ResourceNotFoundException{
		return dao.deleteById(id);
	}
	
	public void softDeleteById(int id) throws SQLException,ResourceNotFoundException{
		dao.softDeleteById(id);
	}
	
	public int update(int id, Vendor updatedVendor) throws SQLException, ResourceNotFoundException{
		return dao.update(id, updatedVendor);
		
	}
	
	public List<Vendor> findAll() throws SQLException{
		return dao.findAll();
	}
	
	public Boolean findOne(int id) throws SQLException{
		return dao.findOne(id);
	}
	
	
	
	
	
	public List<Vendor> findAllActiveVendor() throws SQLException{
		return dao.findAll();
	}
	
	public List<VendorAndCount> countVendorVehicle() throws SQLException {
		return dao.countVendorVehicle();
	}
	public List<VendorAndCount> vendorWithGoodReviewCount() throws SQLException{
		return dao.vendorWithGoodReviewCount();
	}
	
	public List<VendorAndCount> vendorWithBadReviewCount() throws SQLException {
		return dao.vendorWithBadReviewCount();
	}
	
	public String getVendorIdByUsernamePassword(String username, String password) 
			throws SQLException, ResourceNotFoundException{
		return dao.getVendorIdByUsernamePassword(username, password);
	}
	
}
