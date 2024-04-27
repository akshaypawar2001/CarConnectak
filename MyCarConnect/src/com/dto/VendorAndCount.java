package com.dto;

public class VendorAndCount {
	private String fName;
	private String lName;
	private int count;
	
	
	public VendorAndCount() {
	}


	//Constructor
	public VendorAndCount(String fName, String lName, int count) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.count = count;
	}


	//Getters and Setters
	public String getfName() {
		return fName;
	}


	public void setfName(String fName) {
		this.fName = fName;
	}


	public String getlName() {
		return lName;
	}


	public void setlName(String lName) {
		this.lName = lName;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	@Override
	public String toString() {
		return "VendorVehicleCount [fName=" + fName + ", lName=" + lName + ", count=" + count + "]";
	}
	
	
}
