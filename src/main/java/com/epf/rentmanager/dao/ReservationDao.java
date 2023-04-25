package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {


	private ReservationDao() {}

	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) AS count FROM Reservation;";

	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(CREATE_RESERVATION_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepStat.setLong(1, reservation.getClient_id());
			prepStat.setLong(2, reservation.getVehicle_id());
			prepStat.setDate(3, reservation.toSqlDateDebut());
			prepStat.setDate(4, reservation.toSqlDateFin());
			prepStat.execute();
			ResultSet resultSet = prepStat.getGeneratedKeys();

			if(resultSet.next()){
				int id = resultSet.getInt(1);
				return id;
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création de la réservation");
		}
		return 0;
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(DELETE_RESERVATION_QUERY);
			prepStat.setInt(1, reservation.getId());
			return prepStat.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression de la réservation");
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		ArrayList<Reservation> listResa = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			prepStat.setLong(1, clientId);
			ResultSet resultSet = prepStat.executeQuery();

			while(resultSet.next()){
				listResa.add(new Reservation(resultSet.getInt("id"), resultSet.getLong("vehicle_id"), resultSet.getDate("debut").toLocalDate(), resultSet.getDate("fin").toLocalDate()));
			}

			return listResa;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations par Id client");
		}
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		ArrayList<Reservation> listResa = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			prepStat.setLong(1, vehicleId);
			ResultSet resultSet = prepStat.executeQuery();

			while(resultSet.next()){
				listResa.add(new Reservation(resultSet.getInt("id"), resultSet.getLong("client_id"), resultSet.getDate("debut").toLocalDate(), resultSet.getDate("fin").toLocalDate()));
			}

			return listResa;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations par Id véhicule");
		}
	}

	public List<Reservation> findAll() throws DaoException {
		ArrayList<Reservation> listResa = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(FIND_RESERVATIONS_QUERY);
			ResultSet resultSet = prepStat.executeQuery();

			while(resultSet.next()){
				listResa.add(new Reservation(resultSet.getInt("id"),resultSet.getInt("client_id"), resultSet.getInt("vehicle_id"), resultSet.getDate("debut").toLocalDate(), resultSet.getDate("fin").toLocalDate()));
			}

			return listResa;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des réservations");
		}
	}

	public int count() throws DaoException {
		int nbClients = 0;
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(COUNT_RESERVATIONS_QUERY);
			ResultSet resultSet = prepStat.executeQuery();

			while(resultSet.next()){
				nbClients = resultSet.getInt(1);
			}
			return nbClients;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du compte de clients");
		}
	}
}
