package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;
	
	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	
	public long create(Client client) throws ServiceException {
		try{
			return clientDao.create(client);
		}catch(DaoException error_dao){
			throw new ServiceException("Le client n'a pas pu être créé !");
		}
	}

	public void delete(Client client) throws DaoException {
		clientDao.delete(client);
	}

	public Client findById(long id) throws ServiceException {
		if(id <= 0){
			throw new ServiceException("ID inexistant !");
		}
		try{
			return clientDao.findById(id);
		}catch(DaoException error_findId){
			throw new ServiceException("Le client est introuvable !");
		}
	}

	public List<Client> findAll() throws ServiceException {
		try{
			return clientDao.findAll();
		}catch(DaoException error_findAll){
			throw new ServiceException("La liste des clients est introuvable !");
		}
	}

	public int count() throws ServiceException {
		try{
			return clientDao.count();
		} catch (DaoException error_count) {
			throw new ServiceException("Impossible de trouver le nombre de clients !");
		}
	}

}
