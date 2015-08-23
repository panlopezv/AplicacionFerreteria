/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Pablo
 */
@Entity
@Table(name = "compra", catalog = "ferreteriadeleon", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Compra.findMaxId", query = "SELECT MAX(c.id) FROM Compra c"),
    @NamedQuery(name = "Compra.findAll", query = "SELECT c FROM Compra c"),
    @NamedQuery(name = "Compra.findById", query = "SELECT c FROM Compra c WHERE c.id = :id"),
    @NamedQuery(name = "Compra.findByFecha", query = "SELECT c FROM Compra c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Compra.findByFechaRecibida", query = "SELECT c FROM Compra c WHERE c.fechaRecibida = :fechaRecibida"),
    @NamedQuery(name = "Compra.findByNumero", query = "SELECT c FROM Compra c WHERE c.numero = :numero"),
    @NamedQuery(name = "Compra.findByPagada", query = "SELECT c FROM Compra c WHERE c.pagada = :pagada"),
    @NamedQuery(name = "Compra.findByRecibida", query = "SELECT c FROM Compra c WHERE c.recibida = :recibida"),
    @NamedQuery(name = "Compra.findBySaldo", query = "SELECT c FROM Compra c WHERE c.saldo = :saldo"),
    @NamedQuery(name = "Compra.findByTotal", query = "SELECT c FROM Compra c WHERE c.total = :total")})
public class Compra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "Fecha_Recibida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRecibida;
    @Basic(optional = false)
    @Column(name = "Numero")
    private String numero;
    @Column(name = "Pagada")
    private Boolean pagada;
    @Column(name = "Recibida")
    private Boolean recibida;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Saldo")
    private Double saldo;
    @Basic(optional = false)
    @Column(name = "Total")
    private double total;
    @JoinColumn(name = "Usuario_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usuario usuarioid;
    @JoinColumn(name = "Delegado_Proveedor_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Persona delegadoProveedorid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compraid")
    private List<DetalleCompra> detalleCompraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "compraid")
    private List<Abono> abonoList;

    public Compra() {
    }

    public Compra(Integer id) {
        this.id = id;
    }

    public Compra(Integer id, Date fecha, Date fechaRecibida, String numero, double total) {
        this.id = id;
        this.fecha = fecha;
        this.fechaRecibida = fechaRecibida;
        this.numero = numero;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaRecibida() {
        return fechaRecibida;
    }

    public void setFechaRecibida(Date fechaRecibida) {
        this.fechaRecibida = fechaRecibida;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean getPagada() {
        return pagada;
    }

    public void setPagada(Boolean pagada) {
        this.pagada = pagada;
    }

    public Boolean getRecibida() {
        return recibida;
    }

    public void setRecibida(Boolean recibida) {
        this.recibida = recibida;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Usuario getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
    }

    public Persona getDelegadoProveedorid() {
        return delegadoProveedorid;
    }

    public void setDelegadoProveedorid(Persona delegadoProveedorid) {
        this.delegadoProveedorid = delegadoProveedorid;
    }

    @XmlTransient
    public List<DetalleCompra> getDetalleCompraList() {
        return detalleCompraList;
    }

    public void setDetalleCompraList(List<DetalleCompra> detalleCompraList) {
        this.detalleCompraList = detalleCompraList;
    }

    @XmlTransient
    public List<Abono> getAbonoList() {
        return abonoList;
    }

    public void setAbonoList(List<Abono> abonoList) {
        this.abonoList = abonoList;
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
        if (!(object instanceof Compra)) {
            return false;
        }
        Compra other = (Compra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Compra[ id=" + id + " ]";
    }
    
}
