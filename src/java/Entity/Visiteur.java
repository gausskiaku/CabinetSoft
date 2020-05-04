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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "visiteur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visiteur.findAll", query = "SELECT v FROM Visiteur v"),
    @NamedQuery(name = "Visiteur.findByIdVisiteur", query = "SELECT v FROM Visiteur v WHERE v.idVisiteur = :idVisiteur"),
    @NamedQuery(name = "Visiteur.findByNomVisiteur", query = "SELECT v FROM Visiteur v WHERE v.nomVisiteur = :nomVisiteur"),
    @NamedQuery(name = "Visiteur.findByPrenomVisiteur", query = "SELECT v FROM Visiteur v WHERE v.prenomVisiteur = :prenomVisiteur")})
public class Visiteur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_visiteur")
    private Integer idVisiteur;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "nom_visiteur")
    private String nomVisiteur;
    @Size(max = 25)
    @Column(name = "prenom_visiteur")
    private String prenomVisiteur;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "visiteur")
    private List<Visiter> visiterList;

    public Visiteur() {
    }

    public Visiteur(Integer idVisiteur) {
        this.idVisiteur = idVisiteur;
    }

    public Visiteur(Integer idVisiteur, String nomVisiteur) {
        this.idVisiteur = idVisiteur;
        this.nomVisiteur = nomVisiteur;
    }

    public Integer getIdVisiteur() {
        return idVisiteur;
    }

    public void setIdVisiteur(Integer idVisiteur) {
        this.idVisiteur = idVisiteur;
    }

    public String getNomVisiteur() {
        return nomVisiteur;
    }

    public void setNomVisiteur(String nomVisiteur) {
        this.nomVisiteur = nomVisiteur;
    }

    public String getPrenomVisiteur() {
        return prenomVisiteur;
    }

    public void setPrenomVisiteur(String prenomVisiteur) {
        this.prenomVisiteur = prenomVisiteur;
    }

    @XmlTransient
    public List<Visiter> getVisiterList() {
        return visiterList;
    }

    public void setVisiterList(List<Visiter> visiterList) {
        this.visiterList = visiterList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVisiteur != null ? idVisiteur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visiteur)) {
            return false;
        }
        Visiteur other = (Visiteur) object;
        if ((this.idVisiteur == null && other.idVisiteur != null) || (this.idVisiteur != null && !this.idVisiteur.equals(other.idVisiteur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Visiteur[ idVisiteur=" + idVisiteur + " ]";
    }
    
}
