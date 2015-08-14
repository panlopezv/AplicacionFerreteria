package entidades;

import entidades.Banco;
import entidades.Movimiento;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T23:28:19")
@StaticMetamodel(Cuenta.class)
public class Cuenta_ { 

    public static volatile SingularAttribute<Cuenta, Banco> bancoid;
    public static volatile SingularAttribute<Cuenta, String> nCuenta;
    public static volatile ListAttribute<Cuenta, Movimiento> movimientoList;
    public static volatile SingularAttribute<Cuenta, Integer> id;
    public static volatile SingularAttribute<Cuenta, Double> saldo;
    public static volatile SingularAttribute<Cuenta, String> nombre;

}