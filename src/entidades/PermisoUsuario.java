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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pablo
 */
@Entity
@Table(name = "permiso_usuario", catalog = "ferreteriadeleon", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PermisoUsuario.findAll", query = "SELECT p FROM PermisoUsuario p"),
    @NamedQuery(name = "PermisoUsuario.findById", query = "SELECT p FROM PermisoUsuario p WHERE p.id = :id")})
public class PermisoUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "Permiso_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Permiso permisoid;
    @JoinColumn(name = "Tipo_Persona_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoPersona tipoPersonaid;

    public PermisoUsuario() {
    }

    public PermisoUsuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Permiso getPermisoid() {
        return permisoid;
    }

    public void setPermisoid(Permiso permisoid) {
        this.permisoid = permisoid;
    }

    public TipoPersona getTipoPersonaid() {
        return tipoPersonaid;
    }

    public void setTipoPersonaid(TipoPersona tipoPersonaid) {
        this.tipoPersonaid = tipoPersonaid;
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
        if (!(object instanceof PermisoUsuario)) {
            return false;
        }
        PermisoUsuario other = (PermisoUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.PermisoUsuario[ id=" + id + " ]";
    }
    
}
