package entidades;

import entidades.DetalleVenta;
import entidades.Lote;
import entidades.ProductoPresentacion;
import entidades.Sucursal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-14T00:55:19")
@StaticMetamodel(ProductoSucursal.class)
public class ProductoSucursal_ { 

    public static volatile SingularAttribute<ProductoSucursal, Integer> existencias;
    public static volatile SingularAttribute<ProductoSucursal, Double> precio;
    public static volatile ListAttribute<ProductoSucursal, DetalleVenta> detalleVentaList;
    public static volatile SingularAttribute<ProductoSucursal, Sucursal> sucursalid;
    public static volatile SingularAttribute<ProductoSucursal, ProductoPresentacion> productoPresentacionid;
    public static volatile ListAttribute<ProductoSucursal, Lote> loteList;
    public static volatile SingularAttribute<ProductoSucursal, Integer> id;

}