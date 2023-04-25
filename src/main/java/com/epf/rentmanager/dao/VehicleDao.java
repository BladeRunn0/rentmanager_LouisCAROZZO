package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
	

	private VehicleDao() {}

	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS count FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(CREATE_VEHICLE_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepStat.setString(1, vehicle.getConstructeur());
			prepStat.setByte(2, vehicle.getNb_places());
			prepStat.execute();
			ResultSet resultSet = prepStat.getGeneratedKeys();

			if(resultSet.next()){
				int id = resultSet.getInt(1);
				return id;
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du véhicule");
		}
		return 0;
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(DELETE_VEHICLE_QUERY);
			prepStat.setLong(1, vehicle.getId());
			return prepStat.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du véhicule");
		}
	}

	public Vehicle findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(FIND_VEHICLE_QUERY);
			prepStat.setLong(1, id);
			ResultSet resultSet = prepStat.executeQuery();

			resultSet.next();
			return new Vehicle(resultSet.getLong("id"), resultSet.getString("constructeur"), resultSet.getByte("nb_places"));

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new DaoException("Erreur lors de la récupération du véhicule");
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		ArrayList<Vehicle> listVehicule = new ArrayList<Vehicle>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(FIND_VEHICLES_QUERY);
			ResultSet resultSet = prepStat.executeQuery();

			while(resultSet.next()){
				int id = resultSet.getInt(1);
                String construct = resultSet.getString(2);
                byte nbPlaces = resultSet.getByte(3);
				listVehicule.add(new Vehicle(id, construct, nbPlaces));
			}

			return listVehicule;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la récupération des véhicules");
		}
		
	}

	public int count() throws DaoException {
		int nbClients = 0;
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(COUNT_VEHICLES_QUERY);
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
