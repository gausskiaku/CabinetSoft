/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Entity.Agent;
import Entity.controller.AgentJpaController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "ActionAgent", urlPatterns = {"/ActionAgent"})
public class ActionAgent extends HttpServlet {

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
    
        String idAgent = request.getParameter("idModifierAgent");
        String btAction = request.getParameter("ActionAgent");
        
        
        if(btAction.equals("Modifier")){
            AgentJpaController ajc = new AgentJpaController(utx, emf);
            Agent agent = ajc.findAgent(idAgent);
            
            request.getSession().setAttribute("agent", agent);
            request.getRequestDispatcher("/Agent/ModifierAgent.jsp").forward(request, response);
        }
        
        if(btAction.equals("Supprimer")){
            try {
                AgentJpaController ajc = new AgentJpaController(utx, emf);
                Agent agent = ajc.findAgent(idAgent);
                ajc.destroy(idAgent);
                String msg = "L'agent " + agent.getNomAgent() + " " + agent.getPrenomAgent() + " a été supprimer ! ";
                request.getSession().setAttribute("msg", msg);
                
               // request.getSession().setAttribute("agent", agent);
               // request.getRequestDispatcher("/RechercheAgent").forward(request, response);
                request.getRequestDispatcher("/Agent/ListeAgent.jsp").forward(request, response);
            } catch (Entity.controller.exceptions.NonexistentEntityException ex) {
                Logger.getLogger(ActionAgent.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Entity.controller.exceptions.RollbackFailureException ex) {
                Logger.getLogger(ActionAgent.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ActionAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
        if(btAction.equals("Voir")){
            AgentJpaController ajc = new AgentJpaController(utx, emf);
            Agent agent = ajc.findAgent(idAgent);
            
            request.getSession().setAttribute("agent", agent);
            request.getRequestDispatcher("/Agent/VoirAgent.jsp").forward(request, response);
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
