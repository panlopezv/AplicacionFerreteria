package entidades;

import entidades.DetalleCompra;
import entidades.ProductoSucursal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-14T00:55:21")
@StaticMetamodel(Lote.class)
public class Lote_ { 

    public static volatile SingularAttribute<Lote, Date> fechaIngreso;
    public static volatile ListAttribute<Lote, DetalleCompra> detalleCompraList;
    public static volatile SingularAttribute<Lote, Integer> numero;
    public static volatile SingularAttribute<Lote, Double> costo;
    public static volatile SingularAttribute<Lote, ProductoSucursal> productoSucursalid;
    public static volatile SingularAttribute<Lote, Integer> id;
    public static volatile SingularAttribute<Lote, Integer> cantidad;

}