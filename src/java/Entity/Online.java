/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gauss
 */
@Entity
@Table(name = "online")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Online.findAll", query = "SELECT o FROM Online o"),
    @NamedQuery(name = "Online.findByIdOnline", query = "SELECT o FROM Online o WHERE o.idOnline = :idOnline"),
    @NamedQuery(name = "Online.findBySession", query = "SELECT o FROM Online o WHERE o.session = :session")})
public class Online implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idOnline")
    private Integer idOnline;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "session")
    private String session;
    @JoinColumn(name = "matricule_agent", referencedColumnName = "matricule_agent")
    @ManyToOne(optional = false)
    private Agent matriculeAgent;

    public Online() {
    }

    public Online(Integer idOnline) {
        this.idOnline = idOnline;
    }

    public Online(Integer idOnline, String session) {
        this.idOnline = idOnline;
        this.session = session;
    }

    public Integer getIdOnline() {
        return idOnline;
    }

    public void setIdOnline(Integer idOnline) {
        this.idOnline = idOnline;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
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
        hash += (idOnline != null ? idOnline.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Online)) {
            return false;
        }
        Online other = (Online) object;
        if ((this.idOnline == null && other.idOnline != null) || (this.idOnline != null && !this.idOnline.equals(other.idOnline))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Online[ idOnline=" + idOnline + " ]";
    }
    
}
