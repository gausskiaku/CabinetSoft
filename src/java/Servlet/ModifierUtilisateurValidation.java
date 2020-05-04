/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;


import Entity.Service;
import Entity.User;
import Entity.controller.UserJpaController;
import java.io.IOException;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
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
@WebServlet(name = "ModifierUtilisateurValidation", urlPatterns = {"/ModifierUtilisateurValidation"})
public class ModifierUtilisateurValidation extends HttpServlet {

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
        
        String idUtilisateur = request.getParameter("idUtilisateur");
        String login = request.getParameter("loginUtilisateur");
        String motdepasse = request.getParameter("motdepasseUtilisateur");
        String idService = request.getParameter("choixAgent");

        Service service = new Service();
        service.setIdService(Integer.parseInt(idService));

        String btAction = request.getParameter("UpdateUtilisateur");
        try {
            if (btAction.equals("Valider")) {

                UserJpaController ujc = new UserJpaController(utx, emf);
                User utilisateur = ujc.findUser(Integer.parseInt(idUtilisateur));

                utilisateur.setLogin(login);
                utilisateur.setMotdepasse(motdepasse);
         //       utilisateur.setIdService(service); // A revoir

                ujc.edit(utilisateur);

                request.getSession().setAttribute("utilisateur", utilisateur);
                String msg = "L'utilisateur " + utilisateur.getLogin() + " a été modifier avec succès ! ";
                request.getSession().setAttribute("msg", msg);
                request.getRequestDispatcher("/Utilisateur/ModifierUtilisateur.jsp").forward(request, response);
            }
        } catch (Exception ex) {
           String msg = "Erreur : " + ex.getMessage();
           request.getSession().setAttribute("msg", msg);
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
