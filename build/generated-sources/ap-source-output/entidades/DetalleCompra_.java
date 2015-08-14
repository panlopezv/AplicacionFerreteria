package entidades;

import entidades.Compra;
import entidades.Lote;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T23:28:19")
@StaticMetamodel(DetalleCompra.class)
public class DetalleCompra_ { 

    public static volatile SingularAttribute<DetalleCompra, Double> precio;
    public static volatile SingularAttribute<DetalleCompra, Lote> loteid;
    public static volatile SingularAttribute<DetalleCompra, Double> subtotal;
    public static volatile SingularAttribute<DetalleCompra, Integer> id;
    public static volatile SingularAttribute<DetalleCompra, Integer> cantidad;
    public static volatile SingularAttribute<DetalleCompra, Compra> compraid;

}