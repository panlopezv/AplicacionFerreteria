/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pablo
 */
@Entity
@Table(name = "abono", catalog = "ferreteriadeleon", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Abono.findAll", query = "SELECT a FROM Abono a"),
    @NamedQuery(name = "Abono.findById", query = "SELECT a FROM Abono a WHERE a.id = :id"),
    @NamedQuery(name = "Abono.findByFecha", query = "SELECT a FROM Abono a WHERE a.fecha = :fecha"),
    @NamedQuery(name = "Abono.findByMonto", query = "SELECT a FROM Abono a WHERE a.monto = :monto"),
    @NamedQuery(name = "Abono.findBySaldoAnterior", query = "SELECT a FROM Abono a WHERE a.saldoAnterior = :saldoAnterior")})
public class Abono implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "Monto")
    private double monto;
    @Basic(optional = false)
    @Column(name = "SaldoAnterior")
    private double saldoAnterior;
    @JoinColumn(name = "Compra_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Compra compraid;
    @JoinColumn(name = "Modo_Pago_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ModoPago modoPagoid;

    public Abono() {
    }

    public Abono(Integer id) {
        this.id = id;
    }

    public Abono(Integer id, Date fecha, double monto, double saldoAnterior) {
        this.id = id;
        this.fecha = fecha;
        this.monto = monto;
        this.saldoAnterior = saldoAnterior;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(double saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public Compra getCompraid() {
        return compraid;
    }

    public void setCompraid(Compra compraid) {
        this.compraid = compraid;
    }

    public ModoPago getModoPagoid() {
        return modoPagoid;
    }

    public void setModoPagoid(ModoPago modoPagoid) {
        this.modoPagoid = modoPagoid;
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
        if (!(object instanceof Abono)) {
            return false;
        }
        Abono other = (Abono) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Abono[ id=" + id + " ]";
    }
    
}
