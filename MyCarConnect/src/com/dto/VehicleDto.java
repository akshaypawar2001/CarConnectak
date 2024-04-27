package com.dto;

public class VehicleDto {
private int vendor_id;
private String vendorName;
private int numberOfVehicles;
@Override
public String toString() {
	return "VehicleDto [vendor_id=" + vendor_id + ", vendorName=" + vendorName + ", numberOfVehicles="
			+ numberOfVehicles + "]";
}
public int getVendor_id() {
	return vendor_id;
}
public void setVendor_id(int vendor_id) {
	this.vendor_id = vendor_id;
}
public String getVendorName() {
	return vendorName;
}
public void setVendorName(String vendorName) {
	this.vendorName = vendorName;
}
public int getNumberOfVehicles() {
	return numberOfVehicles;
}
public void setNumberOfVehicles(int numberOfVehicles) {
	this.numberOfVehicles = numberOfVehicles;
}
public VehicleDto(int vendor_id, String vendorName, int numberOfVehicles) {
	super();
	this.vendor_id = vendor_id;
	this.vendorName = vendorName;
	this.numberOfVehicles = numberOfVehicles;
}
public VehicleDto() {
	super();
	// TODO Auto-generated constructor stub
}

}
