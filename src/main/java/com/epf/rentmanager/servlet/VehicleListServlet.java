package com.epf.rentmanager.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@WebServlet("/vehicles")
public class VehicleListServlet extends HttpServlet {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ReservationDao reservationDao;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("vehicles", vehicleService.findAll());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("delete_vehicle") != null){
            String vehicleString = req.getParameter("delete_vehicle");

            vehicleString = vehicleString.substring(8, vehicleString.length() - 1);

            String[] clientValues = vehicleString.split(", ");

            int id = Integer.parseInt(clientValues[0].split("=")[1]);
            String constructeur = clientValues[1].split("=")[1].replace("'", "");
            byte nb_places = Byte.parseByte(clientValues[2].split("=")[1].replace("'", ""));

            Vehicle vehicle = new Vehicle(id, constructeur, nb_places);
            try {
                List<Reservation> listResa = reservationDao.findAll();
                for(int i = 0; i < listResa.size(); i++){
                    if(listResa.get(i).getVehicle_id() == id){
                        reservationDao.delete(listResa.get(i));
                    }
                }
                vehicleService.delete(vehicle);
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }

        }

        resp.sendRedirect("/rentmanager/vehicles");
    }
}
