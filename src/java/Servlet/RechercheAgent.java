/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;


import Entity.Agent;
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
@WebServlet(name = "RechercheAgent", urlPatterns = {"/RechercheAgent"})
public class RechercheAgent extends HttpServlet {

    @PersistenceUnit
    EntityManagerFactory emf;

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String nomAgent = request.getParameter("nomAgent");
        
        String btAction = request.getParameter("btRecherche");
        if(btAction.equals("Rechercher")){
            
            Query qr = emf.createEntityManager().createNamedQuery("Agent.findByNomAgentLIKE");
            qr.setParameter("nomAgent", "%"+nomAgent+"%");
            
            List<Agent> listAgent = qr.getResultList();
            
            request.getSession().setAttribute("listAgent", listAgent);
            request.getSession().setAttribute("nbreAgent", listAgent.size());
            
            request.getRequestDispatcher("/Agent/ListeAgent.jsp").forward(request, response);
        }
        else if(btAction.equals("Voir tout")){
            Query qr = emf.createEntityManager().createNamedQuery("Agent.findAll");
            
            List<Agent> listAgent = qr.getResultList();
            
            request.getSession().setAttribute("listAgent", listAgent);
            request.getSession().setAttribute("nbreAgent", listAgent.size());
            
            request.getRequestDispatcher("/Agent/ListeAgent.jsp").forward(request, response);
        }
        else if(btAction.equals("Inserer")){
            
            Query qr = emf.createEntityManager().createNamedQuery("Agent.findAll");
            List<Agent> listAgent = qr.getResultList();
            request.setAttribute("tailleEnregistrement", listAgent.size());
            
            
            request.getRequestDispatcher("/Agent/CreationAgent.jsp").forward(request, response);
        }
        
    }

}
