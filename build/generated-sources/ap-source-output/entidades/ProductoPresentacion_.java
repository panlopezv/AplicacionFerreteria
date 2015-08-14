package entidades;

import entidades.Presentacion;
import entidades.Producto;
import entidades.ProductoSucursal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T23:28:19")
@StaticMetamodel(ProductoPresentacion.class)
public class ProductoPresentacion_ { 

    public static volatile SingularAttribute<ProductoPresentacion, String> codigo;
    public static volatile SingularAttribute<ProductoPresentacion, Presentacion> presentacionid;
    public static volatile SingularAttribute<ProductoPresentacion, Producto> productoid;
    public static volatile ListAttribute<ProductoPresentacion, ProductoSucursal> productoSucursalList;
    public static volatile SingularAttribute<ProductoPresentacion, Integer> id;

}