package entidades;

import entidades.Abono;
import entidades.Pago;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-14T00:55:21")
@StaticMetamodel(ModoPago.class)
public class ModoPago_ { 

    public static volatile SingularAttribute<ModoPago, String> descripcion;
    public static volatile ListAttribute<ModoPago, Pago> pagoList;
    public static volatile ListAttribute<ModoPago, Abono> abonoList;
    public static volatile SingularAttribute<ModoPago, Integer> id;
    public static volatile SingularAttribute<ModoPago, String> modoPago;

}