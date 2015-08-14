package entidades;

import entidades.ProductoSucursal;
import entidades.Venta;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T23:28:19")
@StaticMetamodel(DetalleVenta.class)
public class DetalleVenta_ { 

    public static volatile SingularAttribute<DetalleVenta, Double> precio;
    public static volatile SingularAttribute<DetalleVenta, Double> descuento;
    public static volatile SingularAttribute<DetalleVenta, Double> subtotal;
    public static volatile SingularAttribute<DetalleVenta, ProductoSucursal> productoSucursalid;
    public static volatile SingularAttribute<DetalleVenta, Venta> ventaid;
    public static volatile SingularAttribute<DetalleVenta, Integer> id;
    public static volatile SingularAttribute<DetalleVenta, Integer> cantidad;

}