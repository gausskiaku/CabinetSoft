/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Gauss
 */
@Entity
@Table(name = "message")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findByIdMessage", query = "SELECT m FROM Message m WHERE m.idMessage = :idMessage"),
    @NamedQuery(name = "Message.findByDateEnvoi", query = "SELECT m FROM Message m WHERE m.dateEnvoi = :dateEnvoi"),
    @NamedQuery(name = "Message.findByLecture", query = "SELECT m FROM Message m WHERE m.lecture = :lecture")})
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMessage")
    private Integer idMessage;
    @Lob
    @Size(max = 65535)
    @Column(name = "message")
    private String message;
    @Lob
    @Column(name = "fichier")
    private byte[] fichier;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dateEnvoi")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnvoi;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "lecture")
    private String lecture;
    @JoinTable(name = "recevoir", joinColumns = {
        @JoinColumn(name = "idMessage", referencedColumnName = "idMessage")}, inverseJoinColumns = {
        @JoinColumn(name = "matricule_agent", referencedColumnName = "matricule_agent")})
    @ManyToMany
    private List<Agent> agentList;
    @JoinColumn(name = "matricule_agent", referencedColumnName = "matricule_agent")
    @ManyToOne(optional = false)
    private Agent matriculeAgent;

    public Message() {
    }

    public Message(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public Message(Integer idMessage, Date dateEnvoi, String lecture) {
        this.idMessage = idMessage;
        this.dateEnvoi = dateEnvoi;
        this.lecture = lecture;
    }

    public Integer getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getFichier() {
        return fichier;
    }

    public void setFichier(byte[] fichier) {
        this.fichier = fichier;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    @XmlTransient
    public List<Agent> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<Agent> agentList) {
        this.agentList = agentList;
    }

    public Agent getMatriculeAgent() {
        return matriculeAgent;
    }

    public void setMatriculeAgent(Agent matriculeAgent) {
        this.matriculeAgent = matriculeAgent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMessage != null ? idMessage.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.idMessage == null && other.idMessage != null) || (this.idMessage != null && !this.idMessage.equals(other.idMessage))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Message[ idMessage=" + idMessage + " ]";
    }
    
}
