/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compras;

import conexion.ConexionJPA;
import controladores.AbonoJpaController;
import controladores.CompraJpaController;
import controladores.DetalleCompraJpaController;
import controladores.PersonaJpaController;
import entidades.Abono;
import entidades.Compra;
import entidades.DetalleCompra;
import entidades.ModoPago;
import entidades.Persona;
import entidades.Sucursal;
import entidades.Usuario;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.soap.Detail;

/**
 *
 * @author Diaz
 */
public abstract class ACompra {
    protected CompraJpaController cCompra;
    protected DetalleCompraJpaController cDetalle;
    protected PersonaJpaController cPersona;
    protected AbonoJpaController cAbono;
    
    
    protected ArrayList<Abono> abonos;


    protected ConexionJPA conexion;
    protected Persona persona;
    protected Compra compra;
    protected Usuario Empleado;
    protected Sucursal Sucursal;
        
    ACompra(){
        compra= new Compra();
    }
    public boolean abonar(double  cantidad, ModoPago mpago,String Descripcion){
        
        if (cantidad <= compra.getSaldo()) {
            Abono abono = new Abono();
            abono.setCompraid(compra);
            abono.setDescripcion(Descripcion);
            abono.setFecha(new Date());
            abono.setModoPagoid(mpago);
            abono.setMonto(cantidad);
            //abono.setSaldoAnterior(compra.getSaldo());
            cAbono.create(abono);
            return true;
        } else {
            return false;

        }

        
        
    }

    public Persona getPersona() {
        return persona;
    }
    
    
}
