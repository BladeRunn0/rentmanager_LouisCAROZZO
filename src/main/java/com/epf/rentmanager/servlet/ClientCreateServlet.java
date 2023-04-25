package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/users/create")
public class ClientCreateServlet extends HttpServlet {

    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/create.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("last_name");
        String prenom = request.getParameter("first_name");
        String mail = request.getParameter("email");
        String date_naissance = request.getParameter("naissance");
        LocalDate naissance = LocalDate.parse(date_naissance);

        boolean toCreate = true;
        boolean isCreated = false;
        try {
                if(!isLongEnough(nom, prenom)){
                    alertMessage("Le nom et le prénom doivent faire plus de 3 caractères !", response);
                    toCreate = false;
                }

                if(!isLegalAge(naissance)){
                    alertMessage("Le client est mineur !", response);
                    toCreate = false;
                }

                if(!isMailAvailable(mail)){
                    alertMessage("Ce mail est déjà pris !", response);
                    toCreate = false;
                }

                if(toCreate){
                    clientService.create(new Client(nom, prenom, mail, naissance));
                    isCreated = true;
                }

        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
        if(isCreated){
            response.sendRedirect("/rentmanager/users");
        }

    }

    private void alertMessage(String alert, HttpServletResponse response) throws IOException {
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('" + alert + "!');");
            out.println("window.location.href = \"/rentmanager/users/create\";");
        out.println("</script>");
    }
    private boolean isLongEnough(String nom, String prenom) {
        if((nom.length() < 3) || (prenom.length() < 3)){
            return false;
        }
        return true;
    }
    private boolean isLegalAge(LocalDate naissance){
        int yearDiff = LocalDate.now().getYear() - naissance.getYear();
        if(yearDiff < 18){
            return false;
        }
        return true;
    }
    private boolean isMailAvailable(String mail) throws ServiceException {
        List<Client> listClients = clientService.findAll();
        for(int i = 0; i< listClients.size(); i++){
            if(listClients.get(i).getMail().equals(mail)){
               return false;
            }
        }
        return true;
    }

}
