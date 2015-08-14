package entidades;

import entidades.Compra;
import entidades.ModoPago;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T23:28:19")
@StaticMetamodel(Abono.class)
public class Abono_ { 

    public static volatile SingularAttribute<Abono, String> descripcion;
    public static volatile SingularAttribute<Abono, Date> fecha;
    public static volatile SingularAttribute<Abono, Double> monto;
    public static volatile SingularAttribute<Abono, ModoPago> modoPagoid;
    public static volatile SingularAttribute<Abono, Double> saldoAnterior;
    public static volatile SingularAttribute<Abono, Integer> id;
    public static volatile SingularAttribute<Abono, Compra> compraid;

}