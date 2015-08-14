package entidades;

import entidades.Cuenta;
import entidades.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T23:28:19")
@StaticMetamodel(Movimiento.class)
public class Movimiento_ { 

    public static volatile SingularAttribute<Movimiento, Date> fecha;
    public static volatile SingularAttribute<Movimiento, Cuenta> bancoid;
    public static volatile SingularAttribute<Movimiento, Double> saldoAnterior;
    public static volatile SingularAttribute<Movimiento, Double> numeroTransaccion;
    public static volatile SingularAttribute<Movimiento, Integer> id;
    public static volatile SingularAttribute<Movimiento, Double> cantidad;
    public static volatile SingularAttribute<Movimiento, Usuario> usuarioid;

}