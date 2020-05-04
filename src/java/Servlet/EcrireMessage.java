/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Entity.Agent;
import Entity.User;
import Entity.controller.MessageJpaController;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;



/**
 *
 * @author Gauss
 */
@WebServlet(name = "EcrireMessage", urlPatterns = {"/EcrireMessage"}, initParams = @WebInitParam(
        name = "chemin", value = "/fichiers/"))
@MultipartConfig(location = "C:\fichiers", maxFileSize = 10 * 1024 * 1024, maxRequestSize = 5 * 10 * 1024 * 1024,
        fileSizeThreshold = 1024 * 1024)
public class EcrireMessage extends HttpServlet {

    @PersistenceUnit
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;

    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String CHEMIN = "chemin";
    public static final int TAILLE_TAMPON = 10240;

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

//        Query qrListeAgent = emf.createEntityManager().createNamedQuery("Agent.findAll");
//        List<Agent> listAgent = qrListeAgent.getResultList();
//
//        request.getSession().setAttribute("listAgent", listAgent);
//        request.getRequestDispatcher("Message/EcrireMessage.jsp").forward(request, response);

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

//        processRequest(request, response);
        Query qrListeAgent = emf.createEntityManager().createNamedQuery("Agent.findAll");
        List<Agent> listAgent = qrListeAgent.getResultList();

        request.getSession().setAttribute("listAgent", listAgent);
        request.getRequestDispatcher("Message/EcrireMessage.jsp").forward(request, response);
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
//         processRequest(request, response);
        String chemin = this.getServletConfig().getInitParameter(CHEMIN);
        String btAction = request.getParameter("Envoyer");
        String[] choixAgent = request.getParameterValues("choixAgent");
        String textMessage = request.getParameter("message");
        String expediteur = request.getParameter("expediteur");
        
        // Agent expediteur
        Query qrUserExpediteur = emf.createEntityManager().createNamedQuery("User.findByLogin");
        qrUserExpediteur.setParameter("login", expediteur);
        Agent agentExpediteur = new Agent();
        List listUser = qrUserExpediteur.getResultList();
        User user = (User) listUser.get(0);
        agentExpediteur.setMatriculeAgent(user.getMatriculeAgent().getMatriculeAgent());

        // Pour le fichier 
        Part part = request.getPart("fichierMessage");
        String nomFichier = getNomFichier(part);
        if (nomFichier != null && !nomFichier.isEmpty()) {
            String nomChamp = part.getName();
            nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1).substring(nomFichier.lastIndexOf('\\') + 1);
            /* Écriture du fichier sur le disque */
            ecrireFichier(part, nomFichier, chemin);
        }
        
        
        if (btAction.equals("Envoyer")) {
            Entity.Message message = new Entity.Message();
            String msg = "";
            
            // Enregistrement message
            try {
                    message.setMessage(textMessage); // Message
                    message.setLecture("Non");  // Etat : Non lu en l'envoi
                    message.setMatriculeAgent(agentExpediteur); // Expediteur
                    // Traitement Date
                    Date aujourdhui = new Date();
                    DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
                    Timestamp temps = new Timestamp(Date.parse(shortDateFormat.format(aujourdhui)));

                    message.setDateEnvoi(temps); // Heure d'envoi
                   // message.setDestinateur(expediteur); // expediteur // revoir

                    if (nomFichier != null && !nomFichier.isEmpty()) {
                        message.setFichier(ecrireFichier(part, nomFichier, chemin));
                    }
                   
                    msg = "Envoie du message effectuer";
            } catch (Exception e) {
                request.setAttribute("msg", "Message only : "+ e);
                request.getRequestDispatcher("Message/EcrireMessage.jsp").forward(request, response);
            }
            List<Agent> listAgentDestinateur = new ArrayList<>();
            for (String matriculeAgent : choixAgent) {
                try {
                         Agent agentDestinateur = new Agent();
                         
                         // Pour la table recevoir creer in MLD
                         agentDestinateur.setMatriculeAgent(matriculeAgent);
                         listAgentDestinateur.add(agentDestinateur);
                } catch (Exception ex) {
                    request.setAttribute("msg", ex);
                    request.getRequestDispatcher("Message/EcrireMessage.jsp").forward(request, response);
                }
            }
           
            try {
                // Enregistrement Recevoir
            message.setAgentList(listAgentDestinateur);
            MessageJpaController messageJpaController = new MessageJpaController(utx, emf);
            messageJpaController.create(message);
            
            request.setAttribute("msg", msg);
            request.getRequestDispatcher("Message/EcrireMessage.jsp").forward(request, response);
            } catch (Exception e) {
            request.setAttribute("msg", "Lors du create ensemble : "+e);
            request.getRequestDispatcher("Message/EcrireMessage.jsp").forward(request, response);
            }
                
                
        }

//        request.getRequestDispatcher("Message/EcrireMessage.jsp").forward(request, response);
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

    private static String getNomFichier(Part part) {
        /* Boucle sur chacun des paramètres de l'en-tête "contentdisposition". */
        for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
            /* Recherche de l'éventuelle présence du paramètre "filename".
             */
            if (contentDisposition.trim().startsWith("filename")) {
                /* Si "filename" est présent, alors renvoi de sa valeur,
                 c'est-à-dire du nom de fichier. */
                return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        /* Et pour terminer, si rien n'a été trouvé... */
        return null;
    }

    private byte[] ecrireFichier(Part part, String nomFichier, String chemin) throws IOException {
        /* Prépare les flux. */
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        byte[] tampon = null;
        try {
            /* Ouvre les flux. */
            entree = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
            sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), TAILLE_TAMPON);

            /*
             * Lit le fichier reçu et écrit son contenu dans un fichier sur le
             * disque.
             */
            tampon = new byte[TAILLE_TAMPON];
            int longueur;
            while ((longueur = entree.read(tampon)) > 0) {
                sortie.write(tampon, 0, longueur);
            }
        } finally {
            try {
                sortie.close();
            } catch (IOException ignore) {
            }
            try {
                entree.close();
            } catch (IOException ignore) {
            }
        }
        return tampon;
    }

}
