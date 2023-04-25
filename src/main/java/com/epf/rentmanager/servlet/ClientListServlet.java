package com.epf.rentmanager.servlet;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
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
import java.util.Arrays;
import java.util.List;

@WebServlet("/users")
public class ClientListServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ReservationDao reservationDao;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("clients", clientService.findAll());
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("delete_user") != null){
            String clientString = req.getParameter("delete_user");

            clientString = clientString.substring(7, clientString.length() - 1);

            String[] clientValues = clientString.split(", ");

            long id = Long.parseLong(clientValues[0].split("=")[1]);
            String nom = clientValues[1].split("=")[1].replace("'", "");
            String prenom = clientValues[2].split("=")[1].replace("'", "");
            String mail = clientValues[3].split("=")[1].replace("'", "");
            LocalDate naissance = LocalDate.parse(clientValues[4].split("=")[1]);

            Client client = new Client(id, nom, prenom, mail, naissance);

            try {
                List<Reservation> listResa = reservationDao.findAll();
                for(int i = 0; i < listResa.size(); i++){
                    if(listResa.get(i).getClient_id() == id){
                        reservationDao.delete(listResa.get(i));
                    }

                }
                clientService.delete(client);

            } catch (DaoException e) {
                throw new RuntimeException(e);
            }

        }
        resp.sendRedirect("/rentmanager/users");
    }
}
