package entidades;

import entidades.Cuenta;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T23:28:19")
@StaticMetamodel(Banco.class)
public class Banco_ { 

    public static volatile ListAttribute<Banco, Cuenta> cuentaList;
    public static volatile SingularAttribute<Banco, Integer> id;
    public static volatile SingularAttribute<Banco, String> nombre;

}