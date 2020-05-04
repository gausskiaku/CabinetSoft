/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;


import Entity.Agent;
import Entity.Service;
import Entity.User;
import Entity.Visiter;
import Entity.Visiteur;
import Entity.controller.VisiterJpaController;
import Entity.controller.VisiteurJpaController;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;

/**
 *
 * @author Gauss
 */
@WebServlet(name = "CreationVisiter", urlPatterns = {"/CreationVisiter"}, initParams = @WebInitParam(
        name = "chemin", value = "/fichiers/"))
@MultipartConfig(location = "C:\fichiers", maxFileSize = 10 * 1024 * 1024, maxRequestSize = 5 * 10 * 1024 * 1024,
        fileSizeThreshold = 1024 * 1024)

public class CreationVisiter extends HttpServlet {

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

//        if (btAction.equals("Enregistrer")) {
////            try {
//            Visiter visiter = new Visiter();
//            Visiteur enregistrerVisiteur = new Visiteur();
//            Agent agent = new Agent();
//            agent.setMatriculeAgent(matriculeAgent);
//
//            request.getSession().setAttribute("msgVisite", "Ici deja ==> Pour Choix : " + choixNouveauVisiteur);
//            request.getRequestDispatcher("/Visiter/CreationVisiter.jsp").forward(request, response);
//
//            // Pour la date
//            Date dateNow = new Date();
//            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
//
////                if (choixNouveauVisiteur.equals("Non")) {
////                    enregistrerVisiteur.setIdVisiteur(Integer.valueOf(idVisiteur));
////                } else if (choixNouveauVisiteur.equals("Oui")) {
////                    try {
////                        enregistrerVisiteur.setNomVisiteur(nomVisiteur);
////                        enregistrerVisiteur.setPrenomVisiteur(prenomVisiteur);
////                        
////                        VisiteurJpaController visiteurJpaController = new VisiteurJpaController(utx, emf);
////                        visiteurJpaController.create(enregistrerVisiteur);
////                        
////                        Query qrLastVisiteur = emf.createEntityManager().createNamedQuery("Visiteur.findAll");
////                        List<Visiteur> listLastVisiteur = qrLastVisiteur.getResultList();
////                        // dernier engeregistrement
////                        int taille = listLastVisiteur.size();
////                        Visiteur lastVisiteur = listLastVisiteur.get(taille);
////                        
////                        enregistrerVisiteur.setIdVisiteur(lastVisiteur.getIdVisiteur());
////                        enregistrerVisiteur.setNomVisiteur(lastVisiteur.getNomVisiteur());
////                        enregistrerVisiteur.setPrenomVisiteur(lastVisiteur.getPrenomVisiteur());
////                    } catch (RollbackFailureException ex) {
////                        Logger.getLogger(CreationVisiter.class.getName()).log(Level.SEVERE, null, ex);
////                    } catch (Exception ex) {
////                        Logger.getLogger(CreationVisiter.class.getName()).log(Level.SEVERE, null, ex);
////                    }
////                }
////                
////                visiter.setMessage(messageVisiter);
////                //  visiter.setFichier(fichier);
////                visiter.setDisponibilite(disponibilite);
////                visiter.setVisiteur(enregistrerVisiteur);
////                visiter.setAgent(agent);
////                visiter.setDateHeure(new Date(2016, 07, 27, 20, 05, 00));
////                
////                VisiterJpaController visiterJpaController = new VisiterJpaController(utx, emf);
////                visiterJpaController.create(visiter);
////                request.getSession().setAttribute("msgVisite", "Visite enregistrer avec succès !");
////                request.getRequestDispatcher("/Visiter/CreationVisiter.jsp").forward(request, response);
////            } catch (RollbackFailureException ex) {
////                Logger.getLogger(CreationVisiter.class.getName()).log(Level.SEVERE, null, ex);
////            } catch (Exception ex) {
////                Logger.getLogger(CreationVisiter.class.getName()).log(Level.SEVERE, null, ex);
////            }
////            
//      }
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

        Query qrListeVisiteur = emf.createEntityManager().createNamedQuery("Visiteur.findAll");
        List<Visiteur> listVisiteur = qrListeVisiteur.getResultList();

        HttpSession session = request.getSession();
        User utilisateur = (User) session.getAttribute(ATT_SESSION_USER);
        Agent agent = utilisateur.getMatriculeAgent();
        int idService = agent.getIdService().getIdService();

        Service service = new Service();
        service.setIdService(idService);

        Query qrListAgent = emf.createEntityManager().createNamedQuery("Agent.findByIdService");
        qrListAgent.setParameter("idService", service);

        List<Agent> listAgent = qrListAgent.getResultList();
        request.getSession().setAttribute("listAgent", listAgent);

        request.getSession().setAttribute("listVisiteur", listVisiteur);
        request.getRequestDispatcher("/Visiter/CreationVisiter.jsp").forward(request, response);
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
        String chemin = this.getServletConfig().getInitParameter(CHEMIN);
        String btAction = request.getParameter("Enregistrer");
        String choixNouveauVisiteur = request.getParameter("choixNouveauVisiteur");
        String nomVisiteur = request.getParameter("nomVisiteur");
        String prenomVisiteur = request.getParameter("prenomVisiteur");
        String idVisiteur = request.getParameter("listeVisiteur");  // listeVisiteur
        String messageVisiter = request.getParameter("messageVisiter");

        String disponibilite = request.getParameter("disponibilite");
        String matriculeAgent = request.getParameter("listeAgent"); // listeAgent

        // Pour le fichier 
        Part part = request.getPart("fichierVisiter");
        String nomFichier = getNomFichier(part);
        if (nomFichier != null && !nomFichier.isEmpty()) {
//            String nomChamp = part.getName(); // POUR AVOIR LE NOM DU FICHIER
            nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1).substring(nomFichier.lastIndexOf('\\') + 1);
            /* Écriture du fichier sur le disque */
            ecrireFichier(part, nomFichier, chemin);
           // request.setAttribute(nomChamp, nomFichier);
        }

        if (btAction.equals("Enregistrer")) {
            try {
                Visiter visiter = new Visiter();
                Visiteur enregistrerVisiteur = new Visiteur();
                Agent agent = new Agent();
                agent.setMatriculeAgent(matriculeAgent);

//            request.getSession().setAttribute("msgVisite", "Ici deja ==> Pour Choix : " + choixNouveauVisiteur);
//            request.getRequestDispatcher("/Visiter/CreationVisiter.jsp").forward(request, response);
//
////             Pour la date
                Date dateNow = new Date();
                DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
//
                if (choixNouveauVisiteur.equals("ancienVisiteur")) {
                    enregistrerVisiteur.setIdVisiteur(Integer.valueOf(idVisiteur));
                } else if (choixNouveauVisiteur.equals("nouveauVisiteur")) {
                    try {
                        Query qrLastVisiteur = emf.createEntityManager().createNamedQuery("Visiteur.findAll");
                        List<Visiteur> listLastVisiteur = qrLastVisiteur.getResultList();
//                         dernier engeregistrement
                        int taille = listLastVisiteur.size();
//                        
                        VisiteurJpaController visiteurJpaController = new VisiteurJpaController(utx, emf);

//                        
                        // Visiteur lastVisiteur = listLastVisiteur.get(taille);
                        enregistrerVisiteur.setNomVisiteur(nomVisiteur);
                        enregistrerVisiteur.setPrenomVisiteur(prenomVisiteur);
                        enregistrerVisiteur.setIdVisiteur(taille + 1);
//                        enregistrerVisiteur.setNomVisiteur(lastVisiteur.getNomVisiteur());
//                        enregistrerVisiteur.setPrenomVisiteur(lastVisiteur.getPrenomVisiteur());
                        visiteurJpaController.create(enregistrerVisiteur);
                    } catch (Exception ex) {
                        Logger.getLogger(CreationVisiter.class.getName()).log(Level.SEVERE, null, ex);
                        request.getSession().setAttribute("msgVisiteur", "Erreur Create Visiteur : " + ex.getMessage() + " Id visiteur : "+ idVisiteur);
                        request.getRequestDispatcher("/Visiter/CreationVisiter.jsp").forward(request, response);

                    }
                }
//                
                visiter.setMessage(messageVisiter);
                visiter.setFichier( ecrireFichier(part, nomFichier, chemin) );
                visiter.setDisponibilite(disponibilite);
                visiter.setVisiteur(enregistrerVisiteur); // revoir
                visiter.setAgent(agent);   // revoir

                // Traitement Date
                Date aujourdhui = new Date();
                DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
                Timestamp temps = new Timestamp(Date.parse(shortDateFormat.format(aujourdhui)));
                visiter.setDateHeure(temps);
//                
                VisiterJpaController visiterJpaController = new VisiterJpaController(utx, emf);
                visiterJpaController.create(visiter);
                request.getSession().setAttribute("msgVisite", "Visite enregistrer avec succès !");
                request.getRequestDispatcher("/Visiter/CreationVisiter.jsp").forward(request, response);
            } catch (Exception ex) {
               

        
                request.getSession().setAttribute("msgVisite", "Erreur because : " + ex.getMessage() + " Id visiteur : "+ idVisiteur);
                request.getRequestDispatcher("/Visiter/CreationVisiter.jsp").forward(request, response);
            }

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
