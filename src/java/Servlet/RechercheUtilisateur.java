/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;


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

/**
 *
 * @author Gauss
 */
@WebServlet(name = "RechercheUtilisateur", urlPatterns = {"/RechercheUtilisateur"})
public class RechercheUtilisateur extends HttpServlet {

    @PersistenceUnit
    EntityManagerFactory emf;
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
       
        String login = request.getParameter("loginUtilisateur");
        
        String btAction = request.getParameter("btRecherche");
        if(btAction.equals("Rechercher")){
            
            Query qr = emf.createEntityManager().createNamedQuery("Utilisateur.findByLoginLIKE");
            qr.setParameter("login", "%"+login+"%");
            
            List<User> listUtilisateur = qr.getResultList();
            
            request.getSession().setAttribute("listUtilisateur", listUtilisateur);
            request.getSession().setAttribute("nbreUtilisateur", listUtilisateur.size());
            
            request.getRequestDispatcher("/Utilisateur/ListeUtilisateur.jsp").forward(request, response);
        }
        else if(btAction.equals("Voir tout")){
            Query qr = emf.createEntityManager().createNamedQuery("Utilisateur.findAll");
            
            List<User> listUtilisateur = qr.getResultList();
            
            request.getSession().setAttribute("listUtilisateur", listUtilisateur);
            request.getSession().setAttribute("nbreUtilisateur", listUtilisateur.size());
            
            request.getRequestDispatcher("/Utilisateur/ListeUtilisateur.jsp").forward(request, response);
        }
        else if(btAction.equals("Inserer")){
            
            Query qr = emf.createEntityManager().createNamedQuery("Utilisateur.findAll");
            List<User> listUtilisateur = qr.getResultList();
            request.setAttribute("tailleEnregistrement", listUtilisateur.size());
            
            
            request.getRequestDispatcher("/Utilisateur/CreationUtilisateur.jsp").forward(request, response);
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
