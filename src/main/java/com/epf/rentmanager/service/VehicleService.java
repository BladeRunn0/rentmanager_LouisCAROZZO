package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;

	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	
	
	public long create(Vehicle vehicle) throws ServiceException {
		try{
			return vehicleDao.create(vehicle);
		}catch(DaoException error_dao){
			throw new ServiceException("Le véhicule n'a pas pu être créé !");
		}
		
	}

	public Vehicle findById(long id) throws ServiceException {
		Vehicle vehicle;

		try{
			vehicle = vehicleDao.findById(id);
		}catch(DaoException error_findId){
			throw new ServiceException("Le véhicule est introuvable !");
		}
		return vehicle;
		
	}

	public List<Vehicle> findAll() throws ServiceException {
		try{
			return vehicleDao.findAll();
		}catch(DaoException error_findAll){
			throw new ServiceException("La liste des véhicules est introuvable !");
		}
		
	}

	public void delete(Vehicle vehicle) throws DaoException {
		vehicleDao.delete(vehicle);
	}

	public int count() throws ServiceException {
		try{
			return vehicleDao.count();
		} catch (DaoException error_count) {
			throw new ServiceException("Impossible de trouver le nombre de clients !");
		}
	}
	
}
