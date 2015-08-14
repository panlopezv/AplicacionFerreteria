package entidades;

import entidades.DetalleVenta;
import entidades.Pago;
import entidades.Persona;
import entidades.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T23:28:19")
@StaticMetamodel(Venta.class)
public class Venta_ { 

    public static volatile SingularAttribute<Venta, Date> fecha;
    public static volatile SingularAttribute<Venta, Double> total;
    public static volatile SingularAttribute<Venta, String> numero;
    public static volatile ListAttribute<Venta, Pago> pagoList;
    public static volatile SingularAttribute<Venta, Double> iva;
    public static volatile ListAttribute<Venta, DetalleVenta> detalleVentaList;
    public static volatile SingularAttribute<Venta, Integer> id;
    public static volatile SingularAttribute<Venta, Double> saldo;
    public static volatile SingularAttribute<Venta, Boolean> pagada;
    public static volatile SingularAttribute<Venta, Usuario> usuarioid;
    public static volatile SingularAttribute<Venta, Persona> personaid;

}