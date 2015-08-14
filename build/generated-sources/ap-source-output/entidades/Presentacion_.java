package entidades;

import entidades.ProductoPresentacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-14T00:55:21")
@StaticMetamodel(Presentacion.class)
public class Presentacion_ { 

    public static volatile SingularAttribute<Presentacion, String> descripcion;
    public static volatile SingularAttribute<Presentacion, String> presentacion;
    public static volatile ListAttribute<Presentacion, ProductoPresentacion> productoPresentacionList;
    public static volatile SingularAttribute<Presentacion, Integer> id;

}