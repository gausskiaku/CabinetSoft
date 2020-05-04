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
import Entity.Agent;
import Entity.Online;
import Entity.controller.exceptions.NonexistentEntityException;
import Entity.controller.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Gauss
 */
public class OnlineJpaController implements Serializable {

    public OnlineJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Online online) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Agent matriculeAgent = online.getMatriculeAgent();
            if (matriculeAgent != null) {
                matriculeAgent = em.getReference(matriculeAgent.getClass(), matriculeAgent.getMatriculeAgent());
                online.setMatriculeAgent(matriculeAgent);
            }
            em.persist(online);
            if (matriculeAgent != null) {
                matriculeAgent.getOnlineList().add(online);
                matriculeAgent = em.merge(matriculeAgent);
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

    public void edit(Online online) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Online persistentOnline = em.find(Online.class, online.getIdOnline());
            Agent matriculeAgentOld = persistentOnline.getMatriculeAgent();
            Agent matriculeAgentNew = online.getMatriculeAgent();
            if (matriculeAgentNew != null) {
                matriculeAgentNew = em.getReference(matriculeAgentNew.getClass(), matriculeAgentNew.getMatriculeAgent());
                online.setMatriculeAgent(matriculeAgentNew);
            }
            online = em.merge(online);
            if (matriculeAgentOld != null && !matriculeAgentOld.equals(matriculeAgentNew)) {
                matriculeAgentOld.getOnlineList().remove(online);
                matriculeAgentOld = em.merge(matriculeAgentOld);
            }
            if (matriculeAgentNew != null && !matriculeAgentNew.equals(matriculeAgentOld)) {
                matriculeAgentNew.getOnlineList().add(online);
                matriculeAgentNew = em.merge(matriculeAgentNew);
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
                Integer id = online.getIdOnline();
                if (findOnline(id) == null) {
                    throw new NonexistentEntityException("The online with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Online online;
            try {
                online = em.getReference(Online.class, id);
                online.getIdOnline();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The online with id " + id + " no longer exists.", enfe);
            }
            Agent matriculeAgent = online.getMatriculeAgent();
            if (matriculeAgent != null) {
                matriculeAgent.getOnlineList().remove(online);
                matriculeAgent = em.merge(matriculeAgent);
            }
            em.remove(online);
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

    public List<Online> findOnlineEntities() {
        return findOnlineEntities(true, -1, -1);
    }

    public List<Online> findOnlineEntities(int maxResults, int firstResult) {
        return findOnlineEntities(false, maxResults, firstResult);
    }

    private List<Online> findOnlineEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Online.class));
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

    public Online findOnline(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Online.class, id);
        } finally {
            em.close();
        }
    }

    public int getOnlineCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Online> rt = cq.from(Online.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
