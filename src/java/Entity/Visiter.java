/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gauss
 */
@Entity
@Table(name = "visiter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visiter.findAll", query = "SELECT v FROM Visiter v"),
    @NamedQuery(name = "Visiter.findByDateHeure", query = "SELECT v FROM Visiter v WHERE v.dateHeure = :dateHeure"),
    @NamedQuery(name = "Visiter.findByDisponibilite", query = "SELECT v FROM Visiter v WHERE v.disponibilite = :disponibilite"),
    @NamedQuery(name = "Visiter.findByMatriculeAgent", query = "SELECT v FROM Visiter v WHERE v.visiterPK.matriculeAgent = :matriculeAgent"),
    @NamedQuery(name = "Visiter.findByIdVisiteur", query = "SELECT v FROM Visiter v WHERE v.visiterPK.idVisiteur = :idVisiteur")})
public class Visiter implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VisiterPK visiterPK;
    @Column(name = "date_heure")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateHeure;
    @Lob
    @Size(max = 65535)
    @Column(name = "message")
    private String message;
    @Lob
    @Column(name = "fichier")
    private byte[] fichier;
    @Size(max = 25)
    @Column(name = "disponibilite")
    private String disponibilite;
    @JoinColumn(name = "id_visiteur", referencedColumnName = "id_visiteur", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Visiteur visiteur;
    @JoinColumn(name = "matricule_agent", referencedColumnName = "matricule_agent", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Agent agent;

    public Visiter() {
    }

    public Visiter(VisiterPK visiterPK) {
        this.visiterPK = visiterPK;
    }

    public Visiter(String matriculeAgent, int idVisiteur) {
        this.visiterPK = new VisiterPK(matriculeAgent, idVisiteur);
    }

    public VisiterPK getVisiterPK() {
        return visiterPK;
    }

    public void setVisiterPK(VisiterPK visiterPK) {
        this.visiterPK = visiterPK;
    }

    public Date getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(Date dateHeure) {
        this.dateHeure = dateHeure;
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

    public String getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(String disponibilite) {
        this.disponibilite = disponibilite;
    }

    public Visiteur getVisiteur() {
        return visiteur;
    }

    public void setVisiteur(Visiteur visiteur) {
        this.visiteur = visiteur;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visiterPK != null ? visiterPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visiter)) {
            return false;
        }
        Visiter other = (Visiter) object;
        if ((this.visiterPK == null && other.visiterPK != null) || (this.visiterPK != null && !this.visiterPK.equals(other.visiterPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Visiter[ visiterPK=" + visiterPK + " ]";
    }
    
}
