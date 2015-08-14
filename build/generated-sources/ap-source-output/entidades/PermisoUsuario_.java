package entidades;

import entidades.Permiso;
import entidades.TipoPersona;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T23:28:19")
@StaticMetamodel(PermisoUsuario.class)
public class PermisoUsuario_ { 

    public static volatile SingularAttribute<PermisoUsuario, Integer> id;
    public static volatile SingularAttribute<PermisoUsuario, Permiso> permisoid;
    public static volatile SingularAttribute<PermisoUsuario, TipoPersona> tipoPersonaid;

}