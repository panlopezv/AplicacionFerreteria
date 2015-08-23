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
@Table(name = "producto_presentacion", catalog = "ferreteriadeleon", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoPresentacion.findMaxId", query = "SELECT MAX(p.id) FROM ProductoPresentacion p"),
    @NamedQuery(name = "ProductoPresentacion.findAll", query = "SELECT p FROM ProductoPresentacion p"),
    @NamedQuery(name = "ProductoPresentacion.findById", query = "SELECT p FROM ProductoPresentacion p WHERE p.id = :id"),
    @NamedQuery(name = "ProductoPresentacion.findByCodigo", query = "SELECT p FROM ProductoPresentacion p WHERE p.codigo like :codigo")})
public class ProductoPresentacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Codigo")
    private String codigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productoPresentacionid")
    private List<ProductoSucursal> productoSucursalList;
    @JoinColumn(name = "Presentacion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Presentacion presentacionid;
    @JoinColumn(name = "Producto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Producto productoid;

    public ProductoPresentacion() {
    }

    public ProductoPresentacion(Integer id) {
        this.id = id;
    }

    public ProductoPresentacion(Integer id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlTransient
    public List<ProductoSucursal> getProductoSucursalList() {
        return productoSucursalList;
    }

    public void setProductoSucursalList(List<ProductoSucursal> productoSucursalList) {
        this.productoSucursalList = productoSucursalList;
    }

    public Presentacion getPresentacionid() {
        return presentacionid;
    }

    public void setPresentacionid(Presentacion presentacionid) {
        this.presentacionid = presentacionid;
    }

    public Producto getProductoid() {
        return productoid;
    }

    public void setProductoid(Producto productoid) {
        this.productoid = productoid;
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
        if (!(object instanceof ProductoPresentacion)) {
            return false;
        }
        ProductoPresentacion other = (ProductoPresentacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ProductoPresentacion[ id=" + id + " ]";
    }
    
}
