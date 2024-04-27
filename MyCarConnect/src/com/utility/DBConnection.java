package com.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class DBConnection {
//	step 1 : create connection variables 
	static String userDB="root";
	static String passDB="";
	static String url="jdbc:mysql://localhost:3306/car_connectf";
	static String driver="com.mysql.jdbc.Driver";
	static Connection con;
	
	public static Connection dbConnect() {
//		load the driver
		try {
			Class.forName(driver);
			//System.out.println("Driver loaded...");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Driver not loaded...");
			e.printStackTrace();
		}
		
		try {
			con=DriverManager.getConnection(url,userDB,passDB);
			//System.out.println("connection established");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("connection failed");
			e.printStackTrace();
		}
		return con;
		
	}
	
	public static void dbClose() {
		try {
			con.close();
			//System.out.println("Connection closed ");
			} catch (SQLException e) {
			System.out.println("Connection could not be closed"); 
		}
	}
	
//	establish the connection
	
	
	public static void main(String[] args) {
		DBConnection.dbConnect();
		DBConnection.dbClose();
	}}