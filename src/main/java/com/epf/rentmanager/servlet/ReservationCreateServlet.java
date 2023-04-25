package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet {

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            request.setAttribute("cars", vehicleService.findAll());
            request.setAttribute("clients", clientService.findAll());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String vehicle = request.getParameter("car");
        int vehicle_id = Integer.parseInt(vehicle.split(" ")[0]);

        String client = request.getParameter("client");
        int client_id = Integer.parseInt(client.split(" ")[0]);

        LocalDate debut = LocalDate.parse(request.getParameter("begin"));
        LocalDate fin = LocalDate.parse(request.getParameter("end"));

        boolean toCreate = true;
        boolean isCreated = false;
        try {
            if(!isUnderWeek(debut, fin)){
                alertMessage("Un véhicule ne peut pas être réservé par la même personne plus de 7 jours de suite !", response);
                toCreate = false;
            }

            if(!isFree(vehicle_id, debut)){
                alertMessage("Ce véhicule est déjà réservé à cette date ! Merci de vérifier la liste des réservations de ce véhicule ", response);
                toCreate = false;
            }

            if(!isAbleForRent(debut, fin, vehicle_id)){
                alertMessage("Ce véhicule a été réservé plus de 30 jours de suite sans pause. Merci de sélectionner une autre période ", response);
                toCreate = false;
            }

            if(toCreate){
                reservationDao.create(new Reservation(client_id, vehicle_id, debut, fin));
                isCreated = true;
            }

        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

        if(isCreated){
            response.sendRedirect("/rentmanager/rents");
        }

    }

    private void alertMessage(String alert, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<script type=\"text/javascript\">");
        out.println("alert('" + alert + "!');");
        out.println("window.location.href = \"/rentmanager/rents/create\";");
        out.println("</script>");
    }

    private boolean isUnderWeek(LocalDate debut, LocalDate fin){
        if(fin.minusDays(7).isAfter(debut)){
            return false;
        }
        return true;
    }

    private boolean isFree(int vehicle_id, LocalDate debut) throws DaoException {
        List<Reservation> listResa = reservationDao.findResaByVehicleId(vehicle_id);
        if(!listResa.isEmpty()){
            for(int i = 0; i < listResa.size(); i++){
                if(listResa.get(i).getDebut().equals(debut)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isAbleForRent(LocalDate debut, LocalDate fin, int vehicle_id) throws DaoException {
        long numberDays = ChronoUnit.DAYS.between(debut, fin);
        List<Reservation> listResa = reservationDao.findResaByVehicleId(vehicle_id);
        if(!listResa.isEmpty()){
            for(int i = 0; i < listResa.size()-1; i++){
                numberDays += ChronoUnit.DAYS.between(listResa.get(i).getDebut(), listResa.get(i).getFin());
                if(ChronoUnit.DAYS.between(listResa.get(i).getFin(), listResa.get(i+1).getDebut()) >= 1){
                    break;
                }
            }
        }
        if(numberDays >= 30){
            return false;
        }
        return true;
    }

}
