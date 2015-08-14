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
@Table(name = "producto_sucursal", catalog = "ferreteriadeleon", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoSucursal.findAll", query = "SELECT p FROM ProductoSucursal p"),
    @NamedQuery(name = "ProductoSucursal.findById", query = "SELECT p FROM ProductoSucursal p WHERE p.id = :id"),
    @NamedQuery(name = "ProductoSucursal.findByExistencias", query = "SELECT p FROM ProductoSucursal p WHERE p.existencias = :existencias"),
    @NamedQuery(name = "ProductoSucursal.findByPrecio", query = "SELECT p FROM ProductoSucursal p WHERE p.precio = :precio")})
public class ProductoSucursal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Existencias")
    private int existencias;
    @Basic(optional = false)
    @Column(name = "Precio")
    private double precio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoSucursalid")
    private List<Lote> loteList;
    @JoinColumn(name = "Sucursal_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sucursal sucursalid;
    @JoinColumn(name = "ProductoPresentacion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProductoPresentacion productoPresentacionid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoSucursalid")
    private List<DetalleVenta> detalleVentaList;

    public ProductoSucursal() {
    }

    public ProductoSucursal(Integer id) {
        this.id = id;
    }

    public ProductoSucursal(Integer id, int existencias, double precio) {
        this.id = id;
        this.existencias = existencias;
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @XmlTransient
    public List<Lote> getLoteList() {
        return loteList;
    }

    public void setLoteList(List<Lote> loteList) {
        this.loteList = loteList;
    }

    public Sucursal getSucursalid() {
        return sucursalid;
    }

    public void setSucursalid(Sucursal sucursalid) {
        this.sucursalid = sucursalid;
    }

    public ProductoPresentacion getProductoPresentacionid() {
        return productoPresentacionid;
    }

    public void setProductoPresentacionid(ProductoPresentacion productoPresentacionid) {
        this.productoPresentacionid = productoPresentacionid;
    }

    @XmlTransient
    public List<DetalleVenta> getDetalleVentaList() {
        return detalleVentaList;
    }

    public void setDetalleVentaList(List<DetalleVenta> detalleVentaList) {
        this.detalleVentaList = detalleVentaList;
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
        if (!(object instanceof ProductoSucursal)) {
            return false;
        }
        ProductoSucursal other = (ProductoSucursal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ProductoSucursal[ id=" + id + " ]";
    }
    
}
