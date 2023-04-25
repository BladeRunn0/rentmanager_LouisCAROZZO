package com.epf.rentmanager.main;

import com.epf.rentmanager.AppConfiguration;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.FillDatabase;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

public class main {

    public static void main(String[] args){
//        System.out.println(new Client("Carozzo",
//                "Louis",
//                "louis.carozzo@gmail.com",
//                LocalDate.now()));


        //Getting list of all vehicles
//        VehicleService vehicleService = VehicleService.getInstance();
//        List<Vehicle> listVehicle = null;
//
//        try{
//            listVehicle = vehicleService.findAll();
//            System.out.println(listVehicle);
//        }catch(ServiceException e){
//            throw new RuntimeException(e);
//        }
//
//        //Getting list of all clients
//        ClientService clientService = ClientService.getInstance();
//        List<Client> listClient = null;
//
//        try{
//            listClient = clientService.findAll();
//            System.out.println(listClient);
//        }catch(ServiceException e){
//            throw new RuntimeException(e);
//        }
//
//        //Getting reservations list
//        ReservationDao reservationDao = ReservationDao.getInstance();
//        List<Reservation> listResa = null;
//
//        try{
//            listResa = reservationDao.findAll();
//            System.out.println(listResa);
//        } catch (DaoException e) {
//            throw new RuntimeException(e);
//        }
//
//        //Finding client by ID
//        try{
//            System.out.println(ClientService.getInstance().findById(2));
//        } catch (ServiceException e) {
//            throw new RuntimeException(e);
//        }
//
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
//        ClientService clientService = context.getBean(ClientService.class);
//        VehicleService vehicleService = context.getBean(VehicleService.class);
//
//        try {
//            System.out.println(clientService.findAll());
//        } catch (ServiceException e) {
//            throw new RuntimeException(e);
//        }

    }

}
