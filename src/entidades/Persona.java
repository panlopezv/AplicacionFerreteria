/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Pablo
 */
@Entity
@Table(name = "persona", catalog = "ferreteriadeleon", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findById", query = "SELECT p FROM Persona p WHERE p.id = :id"),
    @NamedQuery(name = "Persona.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Persona.findByApellido", query = "SELECT p FROM Persona p WHERE p.apellido = :apellido"),
    @NamedQuery(name = "Persona.findByDireccion", query = "SELECT p FROM Persona p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "Persona.findByCui", query = "SELECT p FROM Persona p WHERE p.cui = :cui"),
    @NamedQuery(name = "Persona.findByNit", query = "SELECT p FROM Persona p WHERE p.nit = :nit"),
    @NamedQuery(name = "Persona.findByCorreo", query = "SELECT p FROM Persona p WHERE p.correo = :correo"),
    @NamedQuery(name = "Persona.findByTelefono1", query = "SELECT p FROM Persona p WHERE p.telefono1 = :telefono1"),
    @NamedQuery(name = "Persona.findByTelefono2", query = "SELECT p FROM Persona p WHERE p.telefono2 = :telefono2")})
public class Persona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Apellido")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "Direccion")
    private String direccion;
    @Column(name = "CUI")
    private String cui;
    @Column(name = "NIT")
    private String nit;
    @Column(name = "Correo")
    private String correo;
    @Column(name = "Telefono1")
    private String telefono1;
    @Column(name = "Telefono2")
    private String telefono2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "delegadoProveedorid")
    private List<Compra> compraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personaid")
    private List<Venta> ventaList;
    @JoinColumn(name = "Tipo_Persona_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TipoPersona tipoPersonaid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personaid")
    private List<PersonaProveedor> personaProveedorList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personaid")
    private List<Usuario> usuarioList;

    public Persona() {
    }

    public Persona(Integer id) {
        this.id = id;
    }

    public Persona(Integer id, String nombre, String apellido, String direccion, String nit) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.nit = nit;
    }

    public Persona(Integer id, String nombre, String apellido, String direccion, String cui, String nit, String correo, String telefono1, String telefono2, TipoPersona tipoPersonaid) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.cui = cui;
        this.nit = nit;
        this.correo = correo;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.tipoPersonaid = tipoPersonaid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    @XmlTransient
    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
    }

    @XmlTransient
    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    public TipoPersona getTipoPersonaid() {
        return tipoPersonaid;
    }

    public void setTipoPersonaid(TipoPersona tipoPersonaid) {
        this.tipoPersonaid = tipoPersonaid;
    }

    @XmlTransient
    public List<PersonaProveedor> getPersonaProveedorList() {
        return personaProveedorList;
    }

    public void setPersonaProveedorList(List<PersonaProveedor> personaProveedorList) {
        this.personaProveedorList = personaProveedorList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
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
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Persona[ id=" + id + " ]";
    }
    
}
