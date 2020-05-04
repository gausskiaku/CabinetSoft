/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.controller;

import Entity.Agent;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entity.Service;
import Entity.Message;
import java.util.ArrayList;
import java.util.List;
import Entity.Online;
import Entity.Visiter;
import Entity.User;
import Entity.controller.exceptions.IllegalOrphanException;
import Entity.controller.exceptions.NonexistentEntityException;
import Entity.controller.exceptions.PreexistingEntityException;
import Entity.controller.exceptions.RollbackFailureException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Gauss
 */
public class AgentJpaController implements Serializable {

    public AgentJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Agent agent) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (agent.getMessageList() == null) {
            agent.setMessageList(new ArrayList<Message>());
        }
        if (agent.getOnlineList() == null) {
            agent.setOnlineList(new ArrayList<Online>());
        }
        if (agent.getVisiterList() == null) {
            agent.setVisiterList(new ArrayList<Visiter>());
        }
        if (agent.getMessageList1() == null) {
            agent.setMessageList1(new ArrayList<Message>());
        }
        if (agent.getUserList() == null) {
            agent.setUserList(new ArrayList<User>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Service idService = agent.getIdService();
            if (idService != null) {
                idService = em.getReference(idService.getClass(), idService.getIdService());
                agent.setIdService(idService);
            }
            List<Message> attachedMessageList = new ArrayList<Message>();
            for (Message messageListMessageToAttach : agent.getMessageList()) {
                messageListMessageToAttach = em.getReference(messageListMessageToAttach.getClass(), messageListMessageToAttach.getIdMessage());
                attachedMessageList.add(messageListMessageToAttach);
            }
            agent.setMessageList(attachedMessageList);
            List<Online> attachedOnlineList = new ArrayList<Online>();
            for (Online onlineListOnlineToAttach : agent.getOnlineList()) {
                onlineListOnlineToAttach = em.getReference(onlineListOnlineToAttach.getClass(), onlineListOnlineToAttach.getIdOnline());
                attachedOnlineList.add(onlineListOnlineToAttach);
            }
            agent.setOnlineList(attachedOnlineList);
            List<Visiter> attachedVisiterList = new ArrayList<Visiter>();
            for (Visiter visiterListVisiterToAttach : agent.getVisiterList()) {
                visiterListVisiterToAttach = em.getReference(visiterListVisiterToAttach.getClass(), visiterListVisiterToAttach.getVisiterPK());
                attachedVisiterList.add(visiterListVisiterToAttach);
            }
            agent.setVisiterList(attachedVisiterList);
            List<Message> attachedMessageList1 = new ArrayList<Message>();
            for (Message messageList1MessageToAttach : agent.getMessageList1()) {
                messageList1MessageToAttach = em.getReference(messageList1MessageToAttach.getClass(), messageList1MessageToAttach.getIdMessage());
                attachedMessageList1.add(messageList1MessageToAttach);
            }
            agent.setMessageList1(attachedMessageList1);
            List<User> attachedUserList = new ArrayList<User>();
            for (User userListUserToAttach : agent.getUserList()) {
                userListUserToAttach = em.getReference(userListUserToAttach.getClass(), userListUserToAttach.getIdUser());
                attachedUserList.add(userListUserToAttach);
            }
            agent.setUserList(attachedUserList);
            em.persist(agent);
            if (idService != null) {
                idService.getAgentList().add(agent);
                idService = em.merge(idService);
            }
            for (Message messageListMessage : agent.getMessageList()) {
                messageListMessage.getAgentList().add(agent);
                messageListMessage = em.merge(messageListMessage);
            }
            for (Online onlineListOnline : agent.getOnlineList()) {
                Agent oldMatriculeAgentOfOnlineListOnline = onlineListOnline.getMatriculeAgent();
                onlineListOnline.setMatriculeAgent(agent);
                onlineListOnline = em.merge(onlineListOnline);
                if (oldMatriculeAgentOfOnlineListOnline != null) {
                    oldMatriculeAgentOfOnlineListOnline.getOnlineList().remove(onlineListOnline);
                    oldMatriculeAgentOfOnlineListOnline = em.merge(oldMatriculeAgentOfOnlineListOnline);
                }
            }
            for (Visiter visiterListVisiter : agent.getVisiterList()) {
                Agent oldAgentOfVisiterListVisiter = visiterListVisiter.getAgent();
                visiterListVisiter.setAgent(agent);
                visiterListVisiter = em.merge(visiterListVisiter);
                if (oldAgentOfVisiterListVisiter != null) {
                    oldAgentOfVisiterListVisiter.getVisiterList().remove(visiterListVisiter);
                    oldAgentOfVisiterListVisiter = em.merge(oldAgentOfVisiterListVisiter);
                }
            }
            for (Message messageList1Message : agent.getMessageList1()) {
                Agent oldMatriculeAgentOfMessageList1Message = messageList1Message.getMatriculeAgent();
                messageList1Message.setMatriculeAgent(agent);
                messageList1Message = em.merge(messageList1Message);
                if (oldMatriculeAgentOfMessageList1Message != null) {
                    oldMatriculeAgentOfMessageList1Message.getMessageList1().remove(messageList1Message);
                    oldMatriculeAgentOfMessageList1Message = em.merge(oldMatriculeAgentOfMessageList1Message);
                }
            }
            for (User userListUser : agent.getUserList()) {
                Agent oldMatriculeAgentOfUserListUser = userListUser.getMatriculeAgent();
                userListUser.setMatriculeAgent(agent);
                userListUser = em.merge(userListUser);
                if (oldMatriculeAgentOfUserListUser != null) {
                    oldMatriculeAgentOfUserListUser.getUserList().remove(userListUser);
                    oldMatriculeAgentOfUserListUser = em.merge(oldMatriculeAgentOfUserListUser);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAgent(agent.getMatriculeAgent()) != null) {
                throw new PreexistingEntityException("Agent " + agent + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Agent agent) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Agent persistentAgent = em.find(Agent.class, agent.getMatriculeAgent());
            Service idServiceOld = persistentAgent.getIdService();
            Service idServiceNew = agent.getIdService();
            List<Message> messageListOld = persistentAgent.getMessageList();
            List<Message> messageListNew = agent.getMessageList();
            List<Online> onlineListOld = persistentAgent.getOnlineList();
            List<Online> onlineListNew = agent.getOnlineList();
            List<Visiter> visiterListOld = persistentAgent.getVisiterList();
            List<Visiter> visiterListNew = agent.getVisiterList();
            List<Message> messageList1Old = persistentAgent.getMessageList1();
            List<Message> messageList1New = agent.getMessageList1();
            List<User> userListOld = persistentAgent.getUserList();
            List<User> userListNew = agent.getUserList();
            List<String> illegalOrphanMessages = null;
            for (Online onlineListOldOnline : onlineListOld) {
                if (!onlineListNew.contains(onlineListOldOnline)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Online " + onlineListOldOnline + " since its matriculeAgent field is not nullable.");
                }
            }
            for (Visiter visiterListOldVisiter : visiterListOld) {
                if (!visiterListNew.contains(visiterListOldVisiter)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Visiter " + visiterListOldVisiter + " since its agent field is not nullable.");
                }
            }
            for (Message messageList1OldMessage : messageList1Old) {
                if (!messageList1New.contains(messageList1OldMessage)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Message " + messageList1OldMessage + " since its matriculeAgent field is not nullable.");
                }
            }
            for (User userListOldUser : userListOld) {
                if (!userListNew.contains(userListOldUser)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain User " + userListOldUser + " since its matriculeAgent field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idServiceNew != null) {
                idServiceNew = em.getReference(idServiceNew.getClass(), idServiceNew.getIdService());
                agent.setIdService(idServiceNew);
            }
            List<Message> attachedMessageListNew = new ArrayList<Message>();
            for (Message messageListNewMessageToAttach : messageListNew) {
                messageListNewMessageToAttach = em.getReference(messageListNewMessageToAttach.getClass(), messageListNewMessageToAttach.getIdMessage());
                attachedMessageListNew.add(messageListNewMessageToAttach);
            }
            messageListNew = attachedMessageListNew;
            agent.setMessageList(messageListNew);
            List<Online> attachedOnlineListNew = new ArrayList<Online>();
            for (Online onlineListNewOnlineToAttach : onlineListNew) {
                onlineListNewOnlineToAttach = em.getReference(onlineListNewOnlineToAttach.getClass(), onlineListNewOnlineToAttach.getIdOnline());
                attachedOnlineListNew.add(onlineListNewOnlineToAttach);
            }
            onlineListNew = attachedOnlineListNew;
            agent.setOnlineList(onlineListNew);
            List<Visiter> attachedVisiterListNew = new ArrayList<Visiter>();
            for (Visiter visiterListNewVisiterToAttach : visiterListNew) {
                visiterListNewVisiterToAttach = em.getReference(visiterListNewVisiterToAttach.getClass(), visiterListNewVisiterToAttach.getVisiterPK());
                attachedVisiterListNew.add(visiterListNewVisiterToAttach);
            }
            visiterListNew = attachedVisiterListNew;
            agent.setVisiterList(visiterListNew);
            List<Message> attachedMessageList1New = new ArrayList<Message>();
            for (Message messageList1NewMessageToAttach : messageList1New) {
                messageList1NewMessageToAttach = em.getReference(messageList1NewMessageToAttach.getClass(), messageList1NewMessageToAttach.getIdMessage());
                attachedMessageList1New.add(messageList1NewMessageToAttach);
            }
            messageList1New = attachedMessageList1New;
            agent.setMessageList1(messageList1New);
            List<User> attachedUserListNew = new ArrayList<User>();
            for (User userListNewUserToAttach : userListNew) {
                userListNewUserToAttach = em.getReference(userListNewUserToAttach.getClass(), userListNewUserToAttach.getIdUser());
                attachedUserListNew.add(userListNewUserToAttach);
            }
            userListNew = attachedUserListNew;
            agent.setUserList(userListNew);
            agent = em.merge(agent);
            if (idServiceOld != null && !idServiceOld.equals(idServiceNew)) {
                idServiceOld.getAgentList().remove(agent);
                idServiceOld = em.merge(idServiceOld);
            }
            if (idServiceNew != null && !idServiceNew.equals(idServiceOld)) {
                idServiceNew.getAgentList().add(agent);
                idServiceNew = em.merge(idServiceNew);
            }
            for (Message messageListOldMessage : messageListOld) {
                if (!messageListNew.contains(messageListOldMessage)) {
                    messageListOldMessage.getAgentList().remove(agent);
                    messageListOldMessage = em.merge(messageListOldMessage);
                }
            }
            for (Message messageListNewMessage : messageListNew) {
                if (!messageListOld.contains(messageListNewMessage)) {
                    messageListNewMessage.getAgentList().add(agent);
                    messageListNewMessage = em.merge(messageListNewMessage);
                }
            }
            for (Online onlineListNewOnline : onlineListNew) {
                if (!onlineListOld.contains(onlineListNewOnline)) {
                    Agent oldMatriculeAgentOfOnlineListNewOnline = onlineListNewOnline.getMatriculeAgent();
                    onlineListNewOnline.setMatriculeAgent(agent);
                    onlineListNewOnline = em.merge(onlineListNewOnline);
                    if (oldMatriculeAgentOfOnlineListNewOnline != null && !oldMatriculeAgentOfOnlineListNewOnline.equals(agent)) {
                        oldMatriculeAgentOfOnlineListNewOnline.getOnlineList().remove(onlineListNewOnline);
                        oldMatriculeAgentOfOnlineListNewOnline = em.merge(oldMatriculeAgentOfOnlineListNewOnline);
                    }
                }
            }
            for (Visiter visiterListNewVisiter : visiterListNew) {
                if (!visiterListOld.contains(visiterListNewVisiter)) {
                    Agent oldAgentOfVisiterListNewVisiter = visiterListNewVisiter.getAgent();
                    visiterListNewVisiter.setAgent(agent);
                    visiterListNewVisiter = em.merge(visiterListNewVisiter);
                    if (oldAgentOfVisiterListNewVisiter != null && !oldAgentOfVisiterListNewVisiter.equals(agent)) {
                        oldAgentOfVisiterListNewVisiter.getVisiterList().remove(visiterListNewVisiter);
                        oldAgentOfVisiterListNewVisiter = em.merge(oldAgentOfVisiterListNewVisiter);
                    }
                }
            }
            for (Message messageList1NewMessage : messageList1New) {
                if (!messageList1Old.contains(messageList1NewMessage)) {
                    Agent oldMatriculeAgentOfMessageList1NewMessage = messageList1NewMessage.getMatriculeAgent();
                    messageList1NewMessage.setMatriculeAgent(agent);
                    messageList1NewMessage = em.merge(messageList1NewMessage);
                    if (oldMatriculeAgentOfMessageList1NewMessage != null && !oldMatriculeAgentOfMessageList1NewMessage.equals(agent)) {
                        oldMatriculeAgentOfMessageList1NewMessage.getMessageList1().remove(messageList1NewMessage);
                        oldMatriculeAgentOfMessageList1NewMessage = em.merge(oldMatriculeAgentOfMessageList1NewMessage);
                    }
                }
            }
            for (User userListNewUser : userListNew) {
                if (!userListOld.contains(userListNewUser)) {
                    Agent oldMatriculeAgentOfUserListNewUser = userListNewUser.getMatriculeAgent();
                    userListNewUser.setMatriculeAgent(agent);
                    userListNewUser = em.merge(userListNewUser);
                    if (oldMatriculeAgentOfUserListNewUser != null && !oldMatriculeAgentOfUserListNewUser.equals(agent)) {
                        oldMatriculeAgentOfUserListNewUser.getUserList().remove(userListNewUser);
                        oldMatriculeAgentOfUserListNewUser = em.merge(oldMatriculeAgentOfUserListNewUser);
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
                String id = agent.getMatriculeAgent();
                if (findAgent(id) == null) {
                    throw new NonexistentEntityException("The agent with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Agent agent;
            try {
                agent = em.getReference(Agent.class, id);
                agent.getMatriculeAgent();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agent with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Online> onlineListOrphanCheck = agent.getOnlineList();
            for (Online onlineListOrphanCheckOnline : onlineListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Agent (" + agent + ") cannot be destroyed since the Online " + onlineListOrphanCheckOnline + " in its onlineList field has a non-nullable matriculeAgent field.");
            }
            List<Visiter> visiterListOrphanCheck = agent.getVisiterList();
            for (Visiter visiterListOrphanCheckVisiter : visiterListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Agent (" + agent + ") cannot be destroyed since the Visiter " + visiterListOrphanCheckVisiter + " in its visiterList field has a non-nullable agent field.");
            }
            List<Message> messageList1OrphanCheck = agent.getMessageList1();
            for (Message messageList1OrphanCheckMessage : messageList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Agent (" + agent + ") cannot be destroyed since the Message " + messageList1OrphanCheckMessage + " in its messageList1 field has a non-nullable matriculeAgent field.");
            }
            List<User> userListOrphanCheck = agent.getUserList();
            for (User userListOrphanCheckUser : userListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Agent (" + agent + ") cannot be destroyed since the User " + userListOrphanCheckUser + " in its userList field has a non-nullable matriculeAgent field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Service idService = agent.getIdService();
            if (idService != null) {
                idService.getAgentList().remove(agent);
                idService = em.merge(idService);
            }
            List<Message> messageList = agent.getMessageList();
            for (Message messageListMessage : messageList) {
                messageListMessage.getAgentList().remove(agent);
                messageListMessage = em.merge(messageListMessage);
            }
            em.remove(agent);
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

    public List<Agent> findAgentEntities() {
        return findAgentEntities(true, -1, -1);
    }

    public List<Agent> findAgentEntities(int maxResults, int firstResult) {
        return findAgentEntities(false, maxResults, firstResult);
    }

    private List<Agent> findAgentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Agent.class));
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

    public Agent findAgent(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Agent.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Agent> rt = cq.from(Agent.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
