/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;


import Entity.Agent;
import Entity.Visiter;
import Entity.Visiteur;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
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
@WebServlet(name = "EnregistrerVisiter", urlPatterns = {"/EnregistrerVisiter"})
public class EnregistrerVisiter extends HttpServlet {

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
        String btAction = request.getParameter("Enregistrer");
        String choixNouveauVisiteur = request.getParameter("choixNouveauVisiteur");
        String nomVisiteur = request.getParameter("nomVisiteur");
        String prenomVisiteur = request.getParameter("prenomVisiteur");
        String idVisiteur = request.getParameter("listeVisiteur");  // listeVisiteur
        String messageVisiter = request.getParameter("messageVisiter");
        // Pour le fichier 
        String disponibilite = request.getParameter("disponibilite");
        String matriculeAgent = request.getParameter("listeAgent"); // listeAgent

        if (btAction.equals("Enregistrer")) {
//            try {
                Visiter visiter = new Visiter();
                Visiteur enregistrerVisiteur = new Visiteur();
                Agent agent = new Agent();
                agent.setMatriculeAgent(matriculeAgent);
                
                request.getSession().setAttribute("msgVisite", "On entre dans if avec Message : " + messageVisiter);
                request.getRequestDispatcher("/Visiter/CreationVisiter.jsp").forward(request, response);
                   
                
                // Pour la date
                Date dateNow = new Date();
                DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
                
                
//                if (choixNouveauVisiteur.equals("Non")) {
//                    enregistrerVisiteur.setIdVisiteur(Integer.valueOf(idVisiteur));
//                } else if (choixNouveauVisiteur.equals("Oui")) {
//                    try {
//                        enregistrerVisiteur.setNomVisiteur(nomVisiteur);
//                        enregistrerVisiteur.setPrenomVisiteur(prenomVisiteur);
//                        
//                        VisiteurJpaController visiteurJpaController = new VisiteurJpaController(utx, emf);
//                        visiteurJpaController.create(enregistrerVisiteur);
//                        
//                        Query qrLastVisiteur = emf.createEntityManager().createNamedQuery("Visiteur.findAll");
//                        List<Visiteur> listLastVisiteur = qrLastVisiteur.getResultList();
//                        // dernier engeregistrement
//                        int taille = listLastVisiteur.size();
//                        Visiteur lastVisiteur = listLastVisiteur.get(taille);
//                        
//                        enregistrerVisiteur.setIdVisiteur(lastVisiteur.getIdVisiteur());
//                        enregistrerVisiteur.setNomVisiteur(lastVisiteur.getNomVisiteur());
//                        enregistrerVisiteur.setPrenomVisiteur(lastVisiteur.getPrenomVisiteur());
//                    } catch (RollbackFailureException ex) {
//                        Logger.getLogger(CreationVisiter.class.getName()).log(Level.SEVERE, null, ex);
////                        request.getSession().setAttribute("msgVisite", "Erreur creation visiteur : " + ex.getMessage());
////                        request.getRequestDispatcher("/Visiter/CreationVisiter.jsp").forward(request, response);
//                    } catch (Exception ex) {
//                        Logger.getLogger(CreationVisiter.class.getName()).log(Level.SEVERE, null, ex);
//                        request.getSession().setAttribute("msgVisite", "Erreur creation visiteur : " + ex.getMessage());
//                        request.getRequestDispatcher("/Visiter/CreationVisiter.jsp").forward(request, response);
//                    }
//                }
//                
//                visiter.setMessage(messageVisiter);
//                //  visiter.setFichier(fichier);
//                visiter.setDisponibilite(disponibilite);
//                visiter.setVisiteur(enregistrerVisiteur);
//                visiter.setAgent(agent);
//                visiter.setDateHeure(new Date(2016, 07, 27, 20, 05, 00));
//                
//                VisiterJpaController visiterJpaController = new VisiterJpaController(utx, emf);
//                visiterJpaController.create(visiter);
//                request.getSession().setAttribute("msgVisite", "Visite enregistrer avec succès !");
//                request.getRequestDispatcher("/Visiter/CreationVisiter.jsp").forward(request, response);
//            } catch (RollbackFailureException ex) {
//                Logger.getLogger(CreationVisiter.class.getName()).log(Level.SEVERE, null, ex);
//                request.getSession().setAttribute("msgVisite", "Erreur : " + ex.getMessage());
//                        request.getRequestDispatcher("/Visiter/CreationVisiter.jsp").forward(request, response);
//            } catch (Exception ex) {
//                Logger.getLogger(CreationVisiter.class.getName()).log(Level.SEVERE, null, ex);
//                request.getSession().setAttribute("msgVisite", "Erreur : " + ex.getMessage());
//                        request.getRequestDispatcher("/Visiter/CreationVisiter.jsp").forward(request, response);
//            }
            
            
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
