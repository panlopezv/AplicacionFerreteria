package entidades;

import entidades.PermisoUsuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-14T00:55:21")
@StaticMetamodel(Permiso.class)
public class Permiso_ { 

    public static volatile SingularAttribute<Permiso, String> permiso;
    public static volatile SingularAttribute<Permiso, Integer> id;
    public static volatile ListAttribute<Permiso, PermisoUsuario> permisoUsuarioList;

}