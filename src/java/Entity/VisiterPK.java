/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Gauss
 */
@Embeddable
public class VisiterPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "matricule_agent")
    private String matriculeAgent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_visiteur")
    private int idVisiteur;

    public VisiterPK() {
    }

    public VisiterPK(String matriculeAgent, int idVisiteur) {
        this.matriculeAgent = matriculeAgent;
        this.idVisiteur = idVisiteur;
    }

    public String getMatriculeAgent() {
        return matriculeAgent;
    }

    public void setMatriculeAgent(String matriculeAgent) {
        this.matriculeAgent = matriculeAgent;
    }

    public int getIdVisiteur() {
        return idVisiteur;
    }

    public void setIdVisiteur(int idVisiteur) {
        this.idVisiteur = idVisiteur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matriculeAgent != null ? matriculeAgent.hashCode() : 0);
        hash += (int) idVisiteur;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisiterPK)) {
            return false;
        }
        VisiterPK other = (VisiterPK) object;
        if ((this.matriculeAgent == null && other.matriculeAgent != null) || (this.matriculeAgent != null && !this.matriculeAgent.equals(other.matriculeAgent))) {
            return false;
        }
        if (this.idVisiteur != other.idVisiteur) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.VisiterPK[ matriculeAgent=" + matriculeAgent + ", idVisiteur=" + idVisiteur + " ]";
    }
    
}
