/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pablo
 */
@Entity
@Table(name = "numero", catalog = "ferreteriadeleon", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Numero.findAll", query = "SELECT n FROM Numero n"),
    @NamedQuery(name = "Numero.findById", query = "SELECT n FROM Numero n WHERE n.id = :id"),
    @NamedQuery(name = "Numero.findByValido", query = "SELECT n FROM Numero n WHERE n.valido = :valido"),
    @NamedQuery(name = "Numero.findBySerie", query = "SELECT n FROM Numero n WHERE n.serie = :serie"),
    @NamedQuery(name = "Numero.findByRangoI", query = "SELECT n FROM Numero n WHERE n.rangoI = :rangoI"),
    @NamedQuery(name = "Numero.findByRangoF", query = "SELECT n FROM Numero n WHERE n.rangoF = :rangoF"),
    @NamedQuery(name = "Numero.findBySiguiente", query = "SELECT n FROM Numero n WHERE n.siguiente = :siguiente")})
public class Numero implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Valido")
    private boolean valido;
    @Basic(optional = false)
    @Column(name = "Serie")
    private String serie;
    @Basic(optional = false)
    @Column(name = "RangoI")
    private int rangoI;
    @Basic(optional = false)
    @Column(name = "RangoF")
    private int rangoF;
    @Basic(optional = false)
    @Column(name = "Siguiente")
    private int siguiente;

    public Numero() {
    }

    public Numero(Integer id) {
        this.id = id;
    }

    public Numero(Integer id, boolean valido, String serie, int rangoI, int rangoF, int siguiente) {
        this.id = id;
        this.valido = valido;
        this.serie = serie;
        this.rangoI = rangoI;
        this.rangoF = rangoF;
        this.siguiente = siguiente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getRangoI() {
        return rangoI;
    }

    public void setRangoI(int rangoI) {
        this.rangoI = rangoI;
    }

    public int getRangoF() {
        return rangoF;
    }

    public void setRangoF(int rangoF) {
        this.rangoF = rangoF;
    }

    public int getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(int siguiente) {
        this.siguiente = siguiente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Numero)) {
            return false;
        }
        Numero other = (Numero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Numero[ id=" + id + " ]";
    }
    
}
