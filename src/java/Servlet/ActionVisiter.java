/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;


import Entity.Agent;
import Entity.Service;
import Entity.User;
import Entity.Visiter;
import Entity.Visiteur;
import static Servlet.CreationVisiter.ATT_SESSION_USER;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author Gauss
 */
@WebServlet(name = "ActionVisiter", urlPatterns = {"/ActionVisiter"})
public class ActionVisiter extends HttpServlet {

    @PersistenceUnit
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String idVisiter = request.getParameter("idVisiter");
        String btAction = request.getParameter("ActionVisiter");

        HttpSession session = request.getSession();
        User utilisateur = (User) session.getAttribute(ATT_SESSION_USER);
        Agent agent = utilisateur.getMatriculeAgent();
        int idService = agent.getIdService().getIdService();

        Service service = new Service();
        service.setIdService(idService);

        Query qrListAgent = emf.createEntityManager().createNamedQuery("Agent.findByIdService");
        qrListAgent.setParameter("idService", service);

        List<Agent> listAgent = qrListAgent.getResultList();
        request.getSession().setAttribute("listAgent", listAgent);
//
        Query qrListVisiteur = emf.createEntityManager().createNamedQuery("Visiteur.findAll");
        List<Visiteur> listVisiteur = qrListVisiteur.getResultList();
        request.getSession().setAttribute("listVisiteur", listVisiteur);
        
        
        
//        request.setAttribute("date", idVisiter);
//        request.getRequestDispatcher("/Dircab/Accueil.jsp").forward(request, response);
        
         
//                DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
//                Timestamp temps = new Timestamp(Date.parse(shortDateFormat.format(aujourdhui)));
//                visiter.setDateHeure(temps);

        if (btAction.equals("Modifier")) {
            Query qr = emf.createEntityManager().createNamedQuery("Visiter.findByNumero");
            //Timestamp temps = new Timestamp(Date.parse(dateHeure));
            qr.setParameter("numero", Integer.valueOf(idVisiter).intValue());

            List<Visiter> listVisiter = qr.getResultList();
            Visiter visiter = listVisiter.get(0);

            request.getSession().setAttribute("visiter", visiter);
            request.getRequestDispatcher("/Visiter/ModifierVisiter.jsp").forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
