package entidades;

import entidades.Compra;
import entidades.PersonaProveedor;
import entidades.TipoPersona;
import entidades.Usuario;
import entidades.Venta;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-14T00:55:21")
@StaticMetamodel(Persona.class)
public class Persona_ { 

    public static volatile ListAttribute<Persona, Usuario> usuarioList;
    public static volatile SingularAttribute<Persona, String> direccion;
    public static volatile ListAttribute<Persona, Compra> compraList;
    public static volatile SingularAttribute<Persona, String> nombre;
    public static volatile SingularAttribute<Persona, String> cui;
    public static volatile ListAttribute<Persona, Venta> ventaList;
    public static volatile SingularAttribute<Persona, String> apellido;
    public static volatile SingularAttribute<Persona, String> correo;
    public static volatile ListAttribute<Persona, PersonaProveedor> personaProveedorList;
    public static volatile SingularAttribute<Persona, String> nit;
    public static volatile SingularAttribute<Persona, String> telefono1;
    public static volatile SingularAttribute<Persona, String> telefono2;
    public static volatile SingularAttribute<Persona, Integer> id;
    public static volatile SingularAttribute<Persona, TipoPersona> tipoPersonaid;

}