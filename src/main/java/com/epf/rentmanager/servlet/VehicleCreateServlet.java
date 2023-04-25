package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/vehicles/create")
public class VehicleCreateServlet extends HttpServlet {

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String constructeur = request.getParameter("manufacturer");
        String nbPlaces = request.getParameter("seats");

        byte nb_places = Integer.valueOf(nbPlaces).byteValue();

        boolean toCreate = true;
        boolean isCreated = false;
        try {

            if((nb_places < 2) || (nb_places > 9)){
                alertMessage("Un véhicule doit avoir entre 2 et 9 places", response);
                toCreate = false;
            }

            if(toCreate){
                vehicleService.create(new Vehicle(constructeur, nb_places));
                isCreated = true;
            }

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        if(isCreated){
            response.sendRedirect("/rentmanager/vehicles");
        }


    }

    private void alertMessage(String alert, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("<script type=\"text/javascript\">");
        out.println("alert('" + alert + "!');");
        out.println("window.location.href = \"/rentmanager/vehicles/create\";");
        out.println("</script>");
    }
}
