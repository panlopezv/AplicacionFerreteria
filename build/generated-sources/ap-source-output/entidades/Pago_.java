package entidades;

import entidades.ModoPago;
import entidades.Venta;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T23:28:19")
@StaticMetamodel(Pago.class)
public class Pago_ { 

    public static volatile SingularAttribute<Pago, String> descripcion;
    public static volatile SingularAttribute<Pago, Date> fecha;
    public static volatile SingularAttribute<Pago, Double> monto;
    public static volatile SingularAttribute<Pago, ModoPago> modoPagoid;
    public static volatile SingularAttribute<Pago, Double> saldoAnterior;
    public static volatile SingularAttribute<Pago, Integer> idPago;
    public static volatile SingularAttribute<Pago, Venta> ventaid;

}