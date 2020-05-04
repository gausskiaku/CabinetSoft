/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;


import Entity.Agent;
import Entity.Service;
import Entity.User;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Gauss
 */
@WebServlet(name = "Connexion", urlPatterns = {"/Connexion"})
public class Connexion extends HttpServlet {

    @PersistenceUnit
    EntityManagerFactory emf;

    private static final String NOM_UTILISATEUR = "Utilisateur";

    public static final String ATT_SESSION_USER = "sessionUtilisateur";

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
        //   
        String login = request.getParameter("login");
        String motdepasse = request.getParameter("motdepasse");
        HttpSession session = request.getSession();

        Query qr = emf.createEntityManager().createNamedQuery("User.findByLoginAndMotdepasse");
        qr.setParameter("login", login);
        qr.setParameter("motdepasse", motdepasse);

        List<User> listUtilisateur = qr.getResultList();
        if (listUtilisateur.isEmpty()) {

            request.setAttribute("msg", "Desolé, ce compte n'existe pas !");
            request.getRequestDispatcher("/Connexion.jsp").forward(request, response);
        } else {
            // Recuperation Service par ID
            Agent agent = listUtilisateur.get(0).getMatriculeAgent();
            Service service = agent.getIdService();
            int idService = service.getIdService();

            Query qrService = emf.createEntityManager().createNamedQuery("Service.findByIdService");
            qrService.setParameter("idService", idService);

            List<Service> listService = qrService.getResultList();
            if (listService.isEmpty()) {
                // Si le nom de Service apparait vide.
                request.setAttribute("msg", "Aucun service a été assigné a ce compte! Veuillez contacter l'Administracteur");
            } else {
                request.setAttribute(NOM_UTILISATEUR, listUtilisateur.get(0).getLogin());
                if (listService.get(0).getNomService().equals("Dircab")) {
                    session.setAttribute(ATT_SESSION_USER, listUtilisateur.get(0));
                    request.getRequestDispatcher("/Dircab/Accueil.jsp").forward(request, response);
                } else if (listService.get(0).getNomService().equals("Courriel")) {
                    session.setAttribute(ATT_SESSION_USER, listUtilisateur.get(0));
                    request.getRequestDispatcher("/Courriel/Accueil.jsp").forward(request, response);
                } else if (listService.get(0).getNomService().equals("Directeur")) {
                    session.setAttribute(ATT_SESSION_USER, listUtilisateur.get(0));
                    request.getRequestDispatcher("/Directeur/Accueil.jsp").forward(request, response);
                } else if (listService.get(0).getNomService().equals("Admin")) {
                    session.setAttribute(ATT_SESSION_USER, listUtilisateur.get(0));
                    request.getRequestDispatcher("/Admin/Accueil.jsp").forward(request, response);
                }

            }

            request.getRequestDispatcher("ListeAgent").forward(request, response);
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
