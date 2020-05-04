/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Entity.Agent;
import Entity.User;
import Entity.controller.UserJpaController;
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
import javax.transaction.UserTransaction;

/**
 *
 * @author Gauss
 */
@WebServlet(name = "ActionUtilisateur", urlPatterns = {"/ActionUtilisateur"})
public class ActionUtilisateur extends HttpServlet {

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
        
       String idUtilisateur = request.getParameter("idModifierUtilisateur");
        String btAction = request.getParameter("ActionUtilisateur");
        
        Query qrAgent = emf.createEntityManager().createNamedQuery("Agent.findAll");
        List<Agent> listAgent = qrAgent.getResultList();
        
         if(btAction.equals("Modifier")){
            UserJpaController ujc = new UserJpaController(utx, emf);
            User utilisateur = ujc.findUser(Integer.parseInt(idUtilisateur));
            
            request.getSession().setAttribute("listAgent", listAgent);
            request.getSession().setAttribute("utilisateur", utilisateur);
            request.getRequestDispatcher("/Utilisateur/ModifierUtilisateur.jsp").forward(request, response);
        }
         
         if(btAction.equals("Supprimer")){
            try {
                UserJpaController ajc = new UserJpaController(utx, emf);
                User utilisateur = ajc.findUser(Integer.parseInt(idUtilisateur));
                ajc.destroy(Integer.parseInt(idUtilisateur));
                String msg = "L'utilisateur " + utilisateur.getLogin() + " a été supprimer ! ";
                request.getSession().setAttribute("msg", msg);
                
               // request.getSession().setAttribute("agent", agent);
               // request.getRequestDispatcher("/RechercheAgent").forward(request, response);
                request.getRequestDispatcher("/Utilisateur/ListeUtilisateur.jsp").forward(request, response);
            
            } catch (Exception ex) {
            String msg = "Suppression d'utilisateur : " + ex;
            request.getSession().setAttribute("msg", msg);
            request.getRequestDispatcher("/Utilisateur/ListeUtilisateur.jsp").forward(request, response);
   
            }
            
        }
         
         if(btAction.equals("Voir")){
            UserJpaController ajc = new UserJpaController(utx, emf);
            User utilisateur = ajc.findUser(Integer.parseInt(idUtilisateur));
            
            request.getSession().setAttribute("utilisateur", utilisateur);
            request.getRequestDispatcher("/Utilisateur/VoirUtilisateur.jsp").forward(request, response);
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
