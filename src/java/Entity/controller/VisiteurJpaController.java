/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Visiter;
import Entity.Visiteur;
import Entity.controller.exceptions.IllegalOrphanException;
import Entity.controller.exceptions.NonexistentEntityException;
import Entity.controller.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Gauss
 */
public class VisiteurJpaController implements Serializable {

    public VisiteurJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Visiteur visiteur) throws RollbackFailureException, Exception {
        if (visiteur.getVisiterList() == null) {
            visiteur.setVisiterList(new ArrayList<Visiter>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Visiter> attachedVisiterList = new ArrayList<Visiter>();
            for (Visiter visiterListVisiterToAttach : visiteur.getVisiterList()) {
                visiterListVisiterToAttach = em.getReference(visiterListVisiterToAttach.getClass(), visiterListVisiterToAttach.getVisiterPK());
                attachedVisiterList.add(visiterListVisiterToAttach);
            }
            visiteur.setVisiterList(attachedVisiterList);
            em.persist(visiteur);
            for (Visiter visiterListVisiter : visiteur.getVisiterList()) {
                Visiteur oldVisiteurOfVisiterListVisiter = visiterListVisiter.getVisiteur();
                visiterListVisiter.setVisiteur(visiteur);
                visiterListVisiter = em.merge(visiterListVisiter);
                if (oldVisiteurOfVisiterListVisiter != null) {
                    oldVisiteurOfVisiterListVisiter.getVisiterList().remove(visiterListVisiter);
                    oldVisiteurOfVisiterListVisiter = em.merge(oldVisiteurOfVisiterListVisiter);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Visiteur visiteur) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Visiteur persistentVisiteur = em.find(Visiteur.class, visiteur.getIdVisiteur());
            List<Visiter> visiterListOld = persistentVisiteur.getVisiterList();
            List<Visiter> visiterListNew = visiteur.getVisiterList();
            List<String> illegalOrphanMessages = null;
            for (Visiter visiterListOldVisiter : visiterListOld) {
                if (!visiterListNew.contains(visiterListOldVisiter)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Visiter " + visiterListOldVisiter + " since its visiteur field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Visiter> attachedVisiterListNew = new ArrayList<Visiter>();
            for (Visiter visiterListNewVisiterToAttach : visiterListNew) {
                visiterListNewVisiterToAttach = em.getReference(visiterListNewVisiterToAttach.getClass(), visiterListNewVisiterToAttach.getVisiterPK());
                attachedVisiterListNew.add(visiterListNewVisiterToAttach);
            }
            visiterListNew = attachedVisiterListNew;
            visiteur.setVisiterList(visiterListNew);
            visiteur = em.merge(visiteur);
            for (Visiter visiterListNewVisiter : visiterListNew) {
                if (!visiterListOld.contains(visiterListNewVisiter)) {
                    Visiteur oldVisiteurOfVisiterListNewVisiter = visiterListNewVisiter.getVisiteur();
                    visiterListNewVisiter.setVisiteur(visiteur);
                    visiterListNewVisiter = em.merge(visiterListNewVisiter);
                    if (oldVisiteurOfVisiterListNewVisiter != null && !oldVisiteurOfVisiterListNewVisiter.equals(visiteur)) {
                        oldVisiteurOfVisiterListNewVisiter.getVisiterList().remove(visiterListNewVisiter);
                        oldVisiteurOfVisiterListNewVisiter = em.merge(oldVisiteurOfVisiterListNewVisiter);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = visiteur.getIdVisiteur();
                if (findVisiteur(id) == null) {
                    throw new NonexistentEntityException("The visiteur with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Visiteur visiteur;
            try {
                visiteur = em.getReference(Visiteur.class, id);
                visiteur.getIdVisiteur();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The visiteur with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Visiter> visiterListOrphanCheck = visiteur.getVisiterList();
            for (Visiter visiterListOrphanCheckVisiter : visiterListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Visiteur (" + visiteur + ") cannot be destroyed since the Visiter " + visiterListOrphanCheckVisiter + " in its visiterList field has a non-nullable visiteur field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(visiteur);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Visiteur> findVisiteurEntities() {
        return findVisiteurEntities(true, -1, -1);
    }

    public List<Visiteur> findVisiteurEntities(int maxResults, int firstResult) {
        return findVisiteurEntities(false, maxResults, firstResult);
    }

    private List<Visiteur> findVisiteurEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Visiteur.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Visiteur findVisiteur(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Visiteur.class, id);
        } finally {
            em.close();
        }
    }

    public int getVisiteurCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Visiteur> rt = cq.from(Visiteur.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
