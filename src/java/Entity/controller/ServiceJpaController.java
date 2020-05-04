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
import Entity.Service;
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
public class ServiceJpaController implements Serializable {

    public ServiceJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Service service) throws RollbackFailureException, Exception {
        if (service.getAgentList() == null) {
            service.setAgentList(new ArrayList<Agent>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Agent> attachedAgentList = new ArrayList<Agent>();
            for (Agent agentListAgentToAttach : service.getAgentList()) {
                agentListAgentToAttach = em.getReference(agentListAgentToAttach.getClass(), agentListAgentToAttach.getMatriculeAgent());
                attachedAgentList.add(agentListAgentToAttach);
            }
            service.setAgentList(attachedAgentList);
            em.persist(service);
            for (Agent agentListAgent : service.getAgentList()) {
                Service oldIdServiceOfAgentListAgent = agentListAgent.getIdService();
                agentListAgent.setIdService(service);
                agentListAgent = em.merge(agentListAgent);
                if (oldIdServiceOfAgentListAgent != null) {
                    oldIdServiceOfAgentListAgent.getAgentList().remove(agentListAgent);
                    oldIdServiceOfAgentListAgent = em.merge(oldIdServiceOfAgentListAgent);
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

    public void edit(Service service) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Service persistentService = em.find(Service.class, service.getIdService());
            List<Agent> agentListOld = persistentService.getAgentList();
            List<Agent> agentListNew = service.getAgentList();
            List<String> illegalOrphanMessages = null;
            for (Agent agentListOldAgent : agentListOld) {
                if (!agentListNew.contains(agentListOldAgent)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Agent " + agentListOldAgent + " since its idService field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Agent> attachedAgentListNew = new ArrayList<Agent>();
            for (Agent agentListNewAgentToAttach : agentListNew) {
                agentListNewAgentToAttach = em.getReference(agentListNewAgentToAttach.getClass(), agentListNewAgentToAttach.getMatriculeAgent());
                attachedAgentListNew.add(agentListNewAgentToAttach);
            }
            agentListNew = attachedAgentListNew;
            service.setAgentList(agentListNew);
            service = em.merge(service);
            for (Agent agentListNewAgent : agentListNew) {
                if (!agentListOld.contains(agentListNewAgent)) {
                    Service oldIdServiceOfAgentListNewAgent = agentListNewAgent.getIdService();
                    agentListNewAgent.setIdService(service);
                    agentListNewAgent = em.merge(agentListNewAgent);
                    if (oldIdServiceOfAgentListNewAgent != null && !oldIdServiceOfAgentListNewAgent.equals(service)) {
                        oldIdServiceOfAgentListNewAgent.getAgentList().remove(agentListNewAgent);
                        oldIdServiceOfAgentListNewAgent = em.merge(oldIdServiceOfAgentListNewAgent);
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
                Integer id = service.getIdService();
                if (findService(id) == null) {
                    throw new NonexistentEntityException("The service with id " + id + " no longer exists.");
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
            Service service;
            try {
                service = em.getReference(Service.class, id);
                service.getIdService();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The service with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Agent> agentListOrphanCheck = service.getAgentList();
            for (Agent agentListOrphanCheckAgent : agentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Service (" + service + ") cannot be destroyed since the Agent " + agentListOrphanCheckAgent + " in its agentList field has a non-nullable idService field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(service);
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

    public List<Service> findServiceEntities() {
        return findServiceEntities(true, -1, -1);
    }

    public List<Service> findServiceEntities(int maxResults, int firstResult) {
        return findServiceEntities(false, maxResults, firstResult);
    }

    private List<Service> findServiceEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Service.class));
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

    public Service findService(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Service.class, id);
        } finally {
            em.close();
        }
    }

    public int getServiceCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Service> rt = cq.from(Service.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
