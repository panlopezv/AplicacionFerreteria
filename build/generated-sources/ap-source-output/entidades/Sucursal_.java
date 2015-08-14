package entidades;

import entidades.ProductoSucursal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-14T00:55:21")
@StaticMetamodel(Sucursal.class)
public class Sucursal_ { 

    public static volatile SingularAttribute<Sucursal, String> direccion;
    public static volatile ListAttribute<Sucursal, ProductoSucursal> productoSucursalList;
    public static volatile SingularAttribute<Sucursal, Integer> id;

}