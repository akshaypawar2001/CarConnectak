package com.dto;

public class ReservationPerCustomer {
	private String fName;
	private String lName;
	private int rCount;
	
	
	public ReservationPerCustomer() {
		
	}
	@Override
	public String toString() {
		return "ReservationPerCustomer [fName=" + fName + ", lName=" + lName + ", rCount=" + rCount + "]";
	}
	public ReservationPerCustomer(String fName, String lName, int rCount) {
		super();
		this.fName = fName;
		this.lName = lName;
		this.rCount = rCount;
	}
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
	public int getrCount() {
		return rCount;
	}
	public void setrCount(int rCount) {
		this.rCount = rCount;
	}
}
