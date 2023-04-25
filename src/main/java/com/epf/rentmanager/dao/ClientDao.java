package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	

	private ClientDao() {}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) AS count FROM Client;";
	
	public long create(Client client) throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(CREATE_CLIENT_QUERY, Statement.RETURN_GENERATED_KEYS);
			prepStat.setString(1, client.getNom());
			prepStat.setString(2, client.getPrenom());
			prepStat.setString(3, client.getMail());
			prepStat.setDate(4, client.toSqlDateNaissance());
			prepStat.execute();
			ResultSet resultSet = prepStat.getGeneratedKeys();

			if(resultSet.next()){
				int id = resultSet.getInt(1);
				return id;
			}

		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du client");
		}
		return 0;
	}
	
	public long delete(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(DELETE_CLIENT_QUERY);
			prepStat.setLong(1, client.getId());
			return prepStat.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression");
		}
	}

	public Client findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(FIND_CLIENT_QUERY);
			prepStat.setLong(1, id);
			ResultSet resultSet = prepStat.executeQuery();
			resultSet.next();
			return new Client((int) id, resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("email"), resultSet.getDate("naissance").toLocalDate());

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la récupération du client");
		}
	}

	public List<Client> findAll() throws DaoException {
		ArrayList<Client> listClient = new ArrayList<Client>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet resultSet = prepStat.executeQuery();

			while(resultSet.next()){
				listClient.add(new Client(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDate(5).toLocalDate()));
			}

			return listClient;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("Erreur lors de la récupération des clients");
		}
	}

	public int count() throws DaoException {
		int nbClients = 0;
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement prepStat = connection.prepareStatement(COUNT_CLIENTS_QUERY);
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
