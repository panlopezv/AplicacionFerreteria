package entidades;

import entidades.Abono;
import entidades.DetalleCompra;
import entidades.Persona;
import entidades.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-08-14T00:55:21")
@StaticMetamodel(Compra.class)
public class Compra_ { 

    public static volatile SingularAttribute<Compra, Date> fechaRecibida;
    public static volatile ListAttribute<Compra, DetalleCompra> detalleCompraList;
    public static volatile SingularAttribute<Compra, Date> fecha;
    public static volatile SingularAttribute<Compra, Double> total;
    public static volatile SingularAttribute<Compra, String> numero;
    public static volatile ListAttribute<Compra, Abono> abonoList;
    public static volatile SingularAttribute<Compra, Persona> delegadoProveedorid;
    public static volatile SingularAttribute<Compra, Double> saldo;
    public static volatile SingularAttribute<Compra, Integer> id;
    public static volatile SingularAttribute<Compra, Boolean> pagada;
    public static volatile SingularAttribute<Compra, Usuario> usuarioid;
    public static volatile SingularAttribute<Compra, Boolean> recibida;

}