package com.dto;

public class ReviewDto {
private int vehicle_id;
private String vehicle_model;
private String vehicle_make;
private int numberOfReviews;
private double avgRatingOfVehicle;
@Override
public String toString() {
	return "ReviewDto [vehicle_id=" + vehicle_id + ", vehicle_model=" + vehicle_model + ", vehicle_make=" + vehicle_make
			+ ", numberOfReviews=" + numberOfReviews + ", avgRatingOfVehicle=" + avgRatingOfVehicle + "]";
}
public int getVehicle_id() {
	return vehicle_id;
}
public void setVehicle_id(int vehicle_id) {
	this.vehicle_id = vehicle_id;
}
public String getVehicle_model() {
	return vehicle_model;
}
public void setVehicle_model(String vehicle_model) {
	this.vehicle_model = vehicle_model;
}
public String getVehicle_make() {
	return vehicle_make;
}
public void setVehicle_make(String vehicle_make) {
	this.vehicle_make = vehicle_make;
}
public int getNumberOfReviews() {
	return numberOfReviews;
}
public void setNumberOfReviews(int numberOfReviews) {
	this.numberOfReviews = numberOfReviews;
}
public double getAvgRatingOfVehicle() {
	return avgRatingOfVehicle;
}
public void setAvgRatingOfVehicle(double avgRatingOfVehicle) {
	this.avgRatingOfVehicle = avgRatingOfVehicle;
}
public ReviewDto() {
	super();
	// TODO Auto-generated constructor stub
}
public ReviewDto(int vehicle_id, String vehicle_model, String vehicle_make, int numberOfReviews,
		double avgRatingOfVehicle) {
	super();
	this.vehicle_id = vehicle_id;
	this.vehicle_model = vehicle_model;
	this.vehicle_make = vehicle_make;
	this.numberOfReviews = numberOfReviews;
	this.avgRatingOfVehicle = avgRatingOfVehicle;
}
}
