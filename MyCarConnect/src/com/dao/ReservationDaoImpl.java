package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dto.ReservationPerCustomer;
import com.exception.ResourceNotFoundException;
import com.model.Reservation;
import com.utility.DBConnection;

public class ReservationDaoImpl implements ReservationDao {

	@Override
	public int save(Reservation reservation) throws SQLException {

		// open connection
		Connection con = DBConnection.dbConnect();

		String sql = "INSERT INTO Reservation (reservation_id, reservation_start_date,"
				+ "reservation_end_date,reservation_total_cost, reservation_status,"
				+ " customer_id, vehicle_id, admin_id) VALUES(?,?,?,?,?,?,?,?)";

		// prepare statement
		PreparedStatement pstmt = con.prepareStatement(sql);

		// attach the data
		pstmt.setInt(1, reservation.getReservation_id());
		pstmt.setString(2, reservation.getReservation_start_date());
		pstmt.setString(3, reservation.getReservation_end_date());
		pstmt.setDouble(4, reservation.getReservation_total_cost());
		pstmt.setString(5, reservation.getReservation_status());
		pstmt.setInt(6, reservation.getCustomer_id());
		pstmt.setInt(7, reservation.getVehicle_id());
		pstmt.setInt(8, reservation.getAdmin_id());

		// execute the query
		int status = pstmt.executeUpdate();

		// close db connection
		DBConnection.dbClose();

		return status;
	}

	@Override
	public int deleteById(int id) throws SQLException, ResourceNotFoundException {
		// open db connection
		Connection con = DBConnection.dbConnect();

		String sql = "delete from reservation where reservation_id = ?";

		// prepare the statement
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, id);

		// execute query
		int status = pstmt.executeUpdate();

		// close db connection
		DBConnection.dbClose();
		return status;
	}

	@Override
	public void softDeleteById(int id) throws SQLException, ResourceNotFoundException {
		Connection con = DBConnection.dbConnect();
		String sql = "update reservation set reservation_isActive='no' where reservation_id = ?";

		// prepare and set statement
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, id);

		// execute query
		pstmt.executeUpdate();

		// close db connection
		DBConnection.dbClose();
	}

	@Override
	public int update(int id, Reservation updatedReservation) throws SQLException, ResourceNotFoundException {
		Connection con = DBConnection.dbConnect();
		String sql = "update reservation " + "set customer_id = ?, " + "vehicle_id = ?, "
				+ "reservation_start_date = ?, " + "reservation_end_date = ?, " + "reservation_total_cost = ?, "
				+ "reservation_status = ?, " + "admin_id = ? " + "where reservation_id = ?";

		// prepare statement
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, updatedReservation.getCustomer_id());
		pstmt.setInt(2, updatedReservation.getVehicle_id());
		pstmt.setString(3, updatedReservation.getReservation_start_date());
		pstmt.setString(4, updatedReservation.getReservation_end_date());
		pstmt.setDouble(5, updatedReservation.getReservation_total_cost());
		pstmt.setString(6, updatedReservation.getReservation_status());
		pstmt.setInt(7, updatedReservation.getAdmin_id());
		pstmt.setInt(8, updatedReservation.getReservation_id());
		int status = pstmt.executeUpdate(); // 1 - updates/ 0 - not updated

		DBConnection.dbClose();
		return status;
	}

	@Override
	public List<Reservation> findAll() throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql = "select * from reservation";
		PreparedStatement pstmt = con.prepareStatement(sql);

		ResultSet rst = pstmt.executeQuery();
		List<Reservation> list = new ArrayList<>();
		while (rst.next()) {
			int id = rst.getInt("reservation_id");
			String startDate = rst.getString("reservation_start_date");
			String endDate = rst.getString("reservation_end_date");
			double totalCost = rst.getDouble("reservation_total_cost");
			String status = rst.getString("reservation_status");
			int cId = rst.getInt("customer_id");
			int vId = rst.getInt("vehicle_id");
			int aId = rst.getInt("admin_id");
			Reservation reservation = new Reservation(cId, vId, id, startDate, endDate, totalCost, status, aId);
			list.add(reservation);
		}
		DBConnection.dbClose();
		return list;
	}

	@Override
	public Boolean findOne(int id) throws SQLException {
		Connection con = DBConnection.dbConnect();
		String sql = "select reservation_id from reservation where reservation_id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rst = pstmt.executeQuery();
		boolean status = rst.next();
		DBConnection.dbClose();
		return status;
	}

	
	@Override
	public List<Reservation> findAllReservationsById(int id) throws SQLException, ResourceNotFoundException {
		Connection con = DBConnection.dbConnect();
		String sql = "select r.* from customer c JOIN reservation r ON " + "c.customer_id=r.customer_id "
				+ "where c.customer_id=?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rst = pstmt.executeQuery();
		List<Reservation> list = new ArrayList<>();
		while (rst.next()) {
			int id1 = rst.getInt("reservation_id");
			String startDate = rst.getString("reservation_start_date");
			String endDate = rst.getString("reservation_end_date");
			double totalCost = rst.getDouble("reservation_total_cost");
			String status1 = rst.getString("reservation_status");
			int cId = rst.getInt("customer_id");
			int vId = rst.getInt("vehicle_id");
			int aId = rst.getInt("admin_id");
			Reservation reservation = new Reservation(cId, vId, id1, startDate, endDate, totalCost, status1, aId);
			list.add(reservation);
		}
		DBConnection.dbClose();
		return list;
	}


	@Override
	public List<ReservationPerCustomer> getReservationCountPerCustomer() throws SQLException {

		Connection con = DBConnection.dbConnect();
		String sql = "select customer_first_name, customer_last_name, count(*) as ReservationCount"
				+ "from reservation r join customer c" + "on r.customer_id = c.customer_id "
				+ "group by customer_first_name, customer_last_name";
		// + "order by ReservationCount desc;";

		// prepare statement
		PreparedStatement pstmt = con.prepareStatement(sql);

		// executeStatement
		ResultSet rst = pstmt.executeQuery(sql);

		// get result into list
		List<ReservationPerCustomer> list = new ArrayList<>();
		while (!rst.next()) {
			String fName = rst.getString("customer_first_name");
			String lName = rst.getString("customer_last_name");
			int cnt = rst.getInt("ReservationCount");
			ReservationPerCustomer obj = new ReservationPerCustomer(fName, lName, cnt);
			list.add(obj);
		}

		// close db connection
		DBConnection.dbClose();

		return list;
	}

	@Override
	public List<Reservation> customerFindAllReservationsByStatus(int id, String status) //confirmed or pending
			throws SQLException, ResourceNotFoundException {

		Connection con = DBConnection.dbConnect();

		// prepare sql string
		String sql = "select r.* from customer c JOIN reservation r ON"
						+ "c.customer_id=r.customer_id"
						+ "where c.customer_id=? AND r.reservation_status=?";
	
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.setString(2, status);

		ResultSet rst = pstmt.executeQuery();
		List<Reservation> list = new ArrayList<>();
		while (rst.next()) {
			int id1 = rst.getInt("reservation_id");
			String startDate = rst.getString("reservation_start_date");
			String endDate = rst.getString("reservation_end_date");
			double totalCost = rst.getDouble("reservation_total_cost");
			String status1 = rst.getString("reservation_status");
			int cId = rst.getInt("customer_id");
			int vId = rst.getInt("vehicle_id");
			int aId = rst.getInt("admin_id");
			Reservation reservation = new Reservation(cId, vId, id1, startDate, endDate, totalCost, status1, aId);
			list.add(reservation);
		}
		DBConnection.dbClose();
		return list;
	}

	@Override
	public List<Reservation> vendorFindAllReservationsByStatus(int id, String status) //pending or Due
			throws SQLException, ResourceNotFoundException {
		
		Connection con = DBConnection.dbConnect();

		// prepare sql string
		String sql = "select r.*" + "from reservation r join vehicle v on r.vehicle_id = v.vehicle_id"
				+ "join vendor vd on vd.vendor_id = v.vendor_id" 
				+ "where v.vendor_id = ? AND r.reservation_status = ?";
		
	
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.setString(2, status);

		ResultSet rst = pstmt.executeQuery();
		List<Reservation> list = new ArrayList<>();
		while (rst.next()) {
			int id1 = rst.getInt("reservation_id");
			String startDate = rst.getString("reservation_start_date");
			String endDate = rst.getString("reservation_end_date");
			double totalCost = rst.getDouble("reservation_total_cost");
			String status1 = rst.getString("reservation_status");
			int cId = rst.getInt("customer_id");
			int vId = rst.getInt("vehicle_id");
			int aId = rst.getInt("admin_id");
			Reservation reservation = new Reservation(cId, vId, id1, startDate, endDate, totalCost, status1, aId);
			list.add(reservation);
		}
		DBConnection.dbClose();
		return list;
	}

}
