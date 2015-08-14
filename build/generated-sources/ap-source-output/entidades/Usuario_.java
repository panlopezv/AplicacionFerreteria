package entidades;

import entidades.Bitacora;
import entidades.Compra;
import entidades.Movimiento;
import entidades.Persona;
import entidades.TipoUsuario;
import entidades.Venta;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-13T23:28:19")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile ListAttribute<Usuario, Bitacora> bitacoraList;
    public static volatile ListAttribute<Usuario, Venta> ventaList;
    public static volatile ListAttribute<Usuario, Movimiento> movimientoList;
    public static volatile SingularAttribute<Usuario, String> usuario;
    public static volatile SingularAttribute<Usuario, Integer> id;
    public static volatile ListAttribute<Usuario, Compra> compraList;
    public static volatile SingularAttribute<Usuario, TipoUsuario> tipoUsuarioid;
    public static volatile SingularAttribute<Usuario, Persona> personaid;

}