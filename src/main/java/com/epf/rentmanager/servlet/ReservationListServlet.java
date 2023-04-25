package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/rents")
public class ReservationListServlet extends HttpServlet {

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Reservation> reservationList = reservationDao.findAll();
            List<Reservation> formattedList = new ArrayList<>();
            for (Reservation reservation : reservationList){
                Client client = clientService.findById(reservation.getClient_id());
                Vehicle vehicle = vehicleService.findById(reservation.getVehicle_id());
                formattedList.add(new Reservation(reservation.getId(), client, vehicle, reservation.getDebut(), reservation.getFin()));
            }
            request.setAttribute("reservations", formattedList);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/list.jsp").forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("delete_rent") != null){
            String rentString = req.getParameter("delete_rent");

            rentString = rentString.substring(7, rentString.length() - 1);

            String[] rentValues = rentString.split(", ");

            int id = Integer.parseInt(rentValues[0].split("=")[1]);

            long clientId = Long.parseLong(rentValues[1].split("=")[1].replace("'", ""));
            long vehicleId = Long.parseLong(rentValues[2].split("=")[1].replace("'", ""));
            Client client = null;
            Vehicle vehicle = null;
            try {
                client = clientService.findById(clientId);
                vehicle = vehicleService.findById(vehicleId);
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }

            LocalDate debut = LocalDate.parse(rentValues[3].split("=")[1]);
            LocalDate fin = LocalDate.parse(rentValues[4].split("=")[1]);

            Reservation reservation = new Reservation(id, client, vehicle, debut, fin);
            try {
                reservationDao.delete(reservation);
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        }

        resp.sendRedirect("/rentmanager/rents");
    }
}
