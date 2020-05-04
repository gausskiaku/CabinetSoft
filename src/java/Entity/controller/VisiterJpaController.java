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
import Entity.Visiteur;
import Entity.Agent;
import Entity.Visiter;
import Entity.VisiterPK;
import Entity.controller.exceptions.NonexistentEntityException;
import Entity.controller.exceptions.PreexistingEntityException;
import Entity.controller.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Gauss
 */
public class VisiterJpaController implements Serializable {

    public VisiterJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Visiter visiter) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (visiter.getVisiterPK() == null) {
            visiter.setVisiterPK(new VisiterPK());
        }
        visiter.getVisiterPK().setIdVisiteur(visiter.getVisiteur().getIdVisiteur());
        visiter.getVisiterPK().setMatriculeAgent(visiter.getAgent().getMatriculeAgent());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Visiteur visiteur = visiter.getVisiteur();
            if (visiteur != null) {
                visiteur = em.getReference(visiteur.getClass(), visiteur.getIdVisiteur());
                visiter.setVisiteur(visiteur);
            }
            Agent agent = visiter.getAgent();
            if (agent != null) {
                agent = em.getReference(agent.getClass(), agent.getMatriculeAgent());
                visiter.setAgent(agent);
            }
            em.persist(visiter);
            if (visiteur != null) {
                visiteur.getVisiterList().add(visiter);
                visiteur = em.merge(visiteur);
            }
            if (agent != null) {
                agent.getVisiterList().add(visiter);
                agent = em.merge(agent);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findVisiter(visiter.getVisiterPK()) != null) {
                throw new PreexistingEntityException("Visiter " + visiter + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Visiter visiter) throws NonexistentEntityException, RollbackFailureException, Exception {
        visiter.getVisiterPK().setIdVisiteur(visiter.getVisiteur().getIdVisiteur());
        visiter.getVisiterPK().setMatriculeAgent(visiter.getAgent().getMatriculeAgent());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Visiter persistentVisiter = em.find(Visiter.class, visiter.getVisiterPK());
            Visiteur visiteurOld = persistentVisiter.getVisiteur();
            Visiteur visiteurNew = visiter.getVisiteur();
            Agent agentOld = persistentVisiter.getAgent();
            Agent agentNew = visiter.getAgent();
            if (visiteurNew != null) {
                visiteurNew = em.getReference(visiteurNew.getClass(), visiteurNew.getIdVisiteur());
                visiter.setVisiteur(visiteurNew);
            }
            if (agentNew != null) {
                agentNew = em.getReference(agentNew.getClass(), agentNew.getMatriculeAgent());
                visiter.setAgent(agentNew);
            }
            visiter = em.merge(visiter);
            if (visiteurOld != null && !visiteurOld.equals(visiteurNew)) {
                visiteurOld.getVisiterList().remove(visiter);
                visiteurOld = em.merge(visiteurOld);
            }
            if (visiteurNew != null && !visiteurNew.equals(visiteurOld)) {
                visiteurNew.getVisiterList().add(visiter);
                visiteurNew = em.merge(visiteurNew);
            }
            if (agentOld != null && !agentOld.equals(agentNew)) {
                agentOld.getVisiterList().remove(visiter);
                agentOld = em.merge(agentOld);
            }
            if (agentNew != null && !agentNew.equals(agentOld)) {
                agentNew.getVisiterList().add(visiter);
                agentNew = em.merge(agentNew);
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
                VisiterPK id = visiter.getVisiterPK();
                if (findVisiter(id) == null) {
                    throw new NonexistentEntityException("The visiter with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(VisiterPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Visiter visiter;
            try {
                visiter = em.getReference(Visiter.class, id);
                visiter.getVisiterPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The visiter with id " + id + " no longer exists.", enfe);
            }
            Visiteur visiteur = visiter.getVisiteur();
            if (visiteur != null) {
                visiteur.getVisiterList().remove(visiter);
                visiteur = em.merge(visiteur);
            }
            Agent agent = visiter.getAgent();
            if (agent != null) {
                agent.getVisiterList().remove(visiter);
                agent = em.merge(agent);
            }
            em.remove(visiter);
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

    public List<Visiter> findVisiterEntities() {
        return findVisiterEntities(true, -1, -1);
    }

    public List<Visiter> findVisiterEntities(int maxResults, int firstResult) {
        return findVisiterEntities(false, maxResults, firstResult);
    }

    private List<Visiter> findVisiterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Visiter.class));
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

    public Visiter findVisiter(VisiterPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Visiter.class, id);
        } finally {
            em.close();
        }
    }

    public int getVisiterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Visiter> rt = cq.from(Visiter.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
