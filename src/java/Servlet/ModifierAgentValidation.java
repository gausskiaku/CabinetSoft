/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;


import Entity.Agent;
import Entity.Service;
import Entity.controller.AgentJpaController;
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
@WebServlet(name = "ModifierAgentValidation", urlPatterns = {"/ModifierAgentValidation"})
public class ModifierAgentValidation extends HttpServlet {

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

        String matriculeAgent = request.getParameter("matriculeAgent");
        String nomAgent = request.getParameter("nomAgent");
        String prenomAgent = request.getParameter("prenomAgent");
        String numAgent = request.getParameter("numAgent");
        String idService = request.getParameter("choixService");

        Service service = new Service();
        service.setIdService(Integer.valueOf(idService).intValue());

        String btAction = request.getParameter("UpdateAgent");
        try {
            if (btAction.equals("Valider")) {

                AgentJpaController ajc = new AgentJpaController(utx, emf);
                Agent agent = ajc.findAgent(matriculeAgent);

                agent.setMatriculeAgent(matriculeAgent);
                agent.setNomAgent(nomAgent);
                agent.setPrenomAgent(prenomAgent);
                agent.setNumAgent(numAgent);
                agent.setIdService(service);

                ajc.edit(agent);

                request.getSession().setAttribute("agent", agent);
                String msg = "L'agent " + agent.getNomAgent() + " " + agent.getPrenomAgent() + " a été modifier avec succès ! ";
                request.getSession().setAttribute("msg", msg);
                request.getRequestDispatcher("/Agent/ModifierAgent.jsp").forward(request, response);
            }
        } catch (Exception ex) {
           String msg = "Erreur : " + ex.getMessage();
           request.getSession().setAttribute("msg", msg);
        }

      //  request.getRequestDispatcher("/Agent/ModifierAgent.jsp").forward(request, response);
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
