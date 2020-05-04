/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gauss
 */
@Entity
@Table(name = "agent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agent.findAll", query = "SELECT a FROM Agent a"),
    @NamedQuery(name = "Agent.findByMatriculeAgent", query = "SELECT a FROM Agent a WHERE a.matriculeAgent = :matriculeAgent"),
    @NamedQuery(name = "Agent.findByNomAgent", query = "SELECT a FROM Agent a WHERE a.nomAgent = :nomAgent"),
    @NamedQuery(name = "Agent.findByPrenomAgent", query = "SELECT a FROM Agent a WHERE a.prenomAgent = :prenomAgent"),
    @NamedQuery(name = "Agent.findByNumAgent", query = "SELECT a FROM Agent a WHERE a.numAgent = :numAgent"),
@NamedQuery(name = "Agent.findByIdService", query = "SELECT a FROM Agent a WHERE a.idService = :idService")})
public class Agent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "matricule_agent")
    private String matriculeAgent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "nom_agent")
    private String nomAgent;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "prenom_agent")
    private String prenomAgent;
    @Size(max = 15)
    @Column(name = "num_agent")
    private String numAgent;
    @ManyToMany(mappedBy = "agentList")
    private List<Message> messageList;
    @JoinColumn(name = "id_service", referencedColumnName = "id_service")
    @ManyToOne(optional = false)
    private Service idService;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matriculeAgent")
    private List<Online> onlineList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private List<Visiter> visiterList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matriculeAgent")
    private List<Message> messageList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "matriculeAgent")
    private List<User> userList;

    public Agent() {
    }

    public Agent(String matriculeAgent) {
        this.matriculeAgent = matriculeAgent;
    }

    public Agent(String matriculeAgent, String nomAgent, String prenomAgent) {
        this.matriculeAgent = matriculeAgent;
        this.nomAgent = nomAgent;
        this.prenomAgent = prenomAgent;
    }

    public String getMatriculeAgent() {
        return matriculeAgent;
    }

    public void setMatriculeAgent(String matriculeAgent) {
        this.matriculeAgent = matriculeAgent;
    }

    public String getNomAgent() {
        return nomAgent;
    }

    public void setNomAgent(String nomAgent) {
        this.nomAgent = nomAgent;
    }

    public String getPrenomAgent() {
        return prenomAgent;
    }

    public void setPrenomAgent(String prenomAgent) {
        this.prenomAgent = prenomAgent;
    }

    public String getNumAgent() {
        return numAgent;
    }

    public void setNumAgent(String numAgent) {
        this.numAgent = numAgent;
    }

    @XmlTransient
    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public Service getIdService() {
        return idService;
    }

    public void setIdService(Service idService) {
        this.idService = idService;
    }

    @XmlTransient
    public List<Online> getOnlineList() {
        return onlineList;
    }

    public void setOnlineList(List<Online> onlineList) {
        this.onlineList = onlineList;
    }

    @XmlTransient
    public List<Visiter> getVisiterList() {
        return visiterList;
    }

    public void setVisiterList(List<Visiter> visiterList) {
        this.visiterList = visiterList;
    }

    @XmlTransient
    public List<Message> getMessageList1() {
        return messageList1;
    }

    public void setMessageList1(List<Message> messageList1) {
        this.messageList1 = messageList1;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matriculeAgent != null ? matriculeAgent.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agent)) {
            return false;
        }
        Agent other = (Agent) object;
        if ((this.matriculeAgent == null && other.matriculeAgent != null) || (this.matriculeAgent != null && !this.matriculeAgent.equals(other.matriculeAgent))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Agent[ matriculeAgent=" + matriculeAgent + " ]";
    }
    
}
