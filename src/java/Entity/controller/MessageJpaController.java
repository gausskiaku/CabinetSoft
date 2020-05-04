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
import Entity.Message;
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
public class MessageJpaController implements Serializable {

    public MessageJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Message message) throws RollbackFailureException, Exception {
        if (message.getAgentList() == null) {
            message.setAgentList(new ArrayList<Agent>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Agent matriculeAgent = message.getMatriculeAgent();
            if (matriculeAgent != null) {
                matriculeAgent = em.getReference(matriculeAgent.getClass(), matriculeAgent.getMatriculeAgent());
                message.setMatriculeAgent(matriculeAgent);
            }
            List<Agent> attachedAgentList = new ArrayList<Agent>();
            for (Agent agentListAgentToAttach : message.getAgentList()) {
                agentListAgentToAttach = em.getReference(agentListAgentToAttach.getClass(), agentListAgentToAttach.getMatriculeAgent());
                attachedAgentList.add(agentListAgentToAttach);
            }
            message.setAgentList(attachedAgentList);
            em.persist(message);
            if (matriculeAgent != null) {
                matriculeAgent.getMessageList().add(message);
                matriculeAgent = em.merge(matriculeAgent);
            }
            for (Agent agentListAgent : message.getAgentList()) {
                agentListAgent.getMessageList().add(message);
                agentListAgent = em.merge(agentListAgent);
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

    public void edit(Message message) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Message persistentMessage = em.find(Message.class, message.getIdMessage());
            Agent matriculeAgentOld = persistentMessage.getMatriculeAgent();
            Agent matriculeAgentNew = message.getMatriculeAgent();
            List<Agent> agentListOld = persistentMessage.getAgentList();
            List<Agent> agentListNew = message.getAgentList();
            if (matriculeAgentNew != null) {
                matriculeAgentNew = em.getReference(matriculeAgentNew.getClass(), matriculeAgentNew.getMatriculeAgent());
                message.setMatriculeAgent(matriculeAgentNew);
            }
            List<Agent> attachedAgentListNew = new ArrayList<Agent>();
            for (Agent agentListNewAgentToAttach : agentListNew) {
                agentListNewAgentToAttach = em.getReference(agentListNewAgentToAttach.getClass(), agentListNewAgentToAttach.getMatriculeAgent());
                attachedAgentListNew.add(agentListNewAgentToAttach);
            }
            agentListNew = attachedAgentListNew;
            message.setAgentList(agentListNew);
            message = em.merge(message);
            if (matriculeAgentOld != null && !matriculeAgentOld.equals(matriculeAgentNew)) {
                matriculeAgentOld.getMessageList().remove(message);
                matriculeAgentOld = em.merge(matriculeAgentOld);
            }
            if (matriculeAgentNew != null && !matriculeAgentNew.equals(matriculeAgentOld)) {
                matriculeAgentNew.getMessageList().add(message);
                matriculeAgentNew = em.merge(matriculeAgentNew);
            }
            for (Agent agentListOldAgent : agentListOld) {
                if (!agentListNew.contains(agentListOldAgent)) {
                    agentListOldAgent.getMessageList().remove(message);
                    agentListOldAgent = em.merge(agentListOldAgent);
                }
            }
            for (Agent agentListNewAgent : agentListNew) {
                if (!agentListOld.contains(agentListNewAgent)) {
                    agentListNewAgent.getMessageList().add(message);
                    agentListNewAgent = em.merge(agentListNewAgent);
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
                Integer id = message.getIdMessage();
                if (findMessage(id) == null) {
                    throw new NonexistentEntityException("The message with id " + id + " no longer exists.");
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
            Message message;
            try {
                message = em.getReference(Message.class, id);
                message.getIdMessage();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The message with id " + id + " no longer exists.", enfe);
            }
            Agent matriculeAgent = message.getMatriculeAgent();
            if (matriculeAgent != null) {
                matriculeAgent.getMessageList().remove(message);
                matriculeAgent = em.merge(matriculeAgent);
            }
            List<Agent> agentList = message.getAgentList();
            for (Agent agentListAgent : agentList) {
                agentListAgent.getMessageList().remove(message);
                agentListAgent = em.merge(agentListAgent);
            }
            em.remove(message);
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

    public List<Message> findMessageEntities() {
        return findMessageEntities(true, -1, -1);
    }

    public List<Message> findMessageEntities(int maxResults, int firstResult) {
        return findMessageEntities(false, maxResults, firstResult);
    }

    private List<Message> findMessageEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Message.class));
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

    public Message findMessage(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Message.class, id);
        } finally {
            em.close();
        }
    }

    public int getMessageCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Message> rt = cq.from(Message.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
