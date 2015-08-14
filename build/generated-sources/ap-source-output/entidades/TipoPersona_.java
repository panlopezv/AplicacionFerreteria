package entidades;

import entidades.PermisoUsuario;
import entidades.Persona;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-14T00:55:21")
@StaticMetamodel(TipoPersona.class)
public class TipoPersona_ { 

    public static volatile SingularAttribute<TipoPersona, String> tipo;
    public static volatile ListAttribute<TipoPersona, Persona> personaList;
    public static volatile SingularAttribute<TipoPersona, Integer> id;
    public static volatile ListAttribute<TipoPersona, PermisoUsuario> permisoUsuarioList;

}