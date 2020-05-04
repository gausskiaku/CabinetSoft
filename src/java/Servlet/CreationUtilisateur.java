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
@WebServlet(name = "CreationUtilisateur", urlPatterns = {"/CreationUtilisateur"})
public class CreationUtilisateur extends HttpServlet {

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
        Query qrAgent = emf.createEntityManager().createNamedQuery("Agent.findAll");
        List<Agent> listAgent = qrAgent.getResultList();
        request.setAttribute("listAgent", listAgent);
        request.getRequestDispatcher("/Utilisateur/CreationUtilisateur.jsp").forward(request, response);
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
      try {
            response.setContentType("text/html;charset=UTF-8");
            
            Query qr = emf.createEntityManager().createNamedQuery("User.findAll");
            List<User> listUtilisateur = qr.getResultList();
            
            
            // Get Parametres          
            
            String login = request.getParameter("loginUtilisateur");
            String motdepasse = request.getParameter("motdepasseUtilisateur");
            String motdepasseUtilisateurConfirmation = request.getParameter("motdepasseUtilisateurConfirmation");
            String idAgent = request.getParameter("listeAgent");
            
            Agent agent = new Agent();
            agent.setMatriculeAgent(idAgent);
            
            User utilisateur = new User();
            utilisateur.setLogin(login);
            utilisateur.setMotdepasse(motdepasse);
            utilisateur.setMatriculeAgent(agent);
            
            if(motdepasse.equals(motdepasseUtilisateurConfirmation)){
                
            UserJpaController ujc = new UserJpaController(utx, emf);
            ujc.create(utilisateur);
            
            String msg = "L'Utilisateur " + utilisateur.getLogin() + " a été ajouté avec succès ! ";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/Utilisateur/CreationUtilisateur.jsp").forward(request, response);
            } else {
            String msg = "Mot de passe incoherent";
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/Utilisateur/CreationUtilisateur.jsp").forward(request, response);
            }
            } catch (Exception ex) {
            String msg = "Erreur de la creation de l'utilisateur : " + ex;
        //    msg += "\n Login : " + request.getParameter("loginUtilisateur") +" et Mot de passe : "+ request.getParameter("motdepasseUtilisateur") + "  \n Service : " + request.getParameter("choixService");
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("/Utilisateur/CreationUtilisateur.jsp").forward(request, response);
        }

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
