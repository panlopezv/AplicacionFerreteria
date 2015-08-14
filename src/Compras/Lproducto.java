/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compras;

import entidades.DetalleCompra;
import entidades.Lote;
import entidades.ProductoSucursal;
import java.util.Date;

/**
 *
 * @author Diaz
 */
public class Lproducto {
    
    DetalleCompra detalle;
    ProductoSucursal ps;
    Lote l;

    public Lproducto(ProductoSucursal ps,int cantidad, double precio) {
        this.ps=ps;
        creardetalle(cantidad, precio);
    }
    
    public void creardetalle(int cantidad,double precio){
        detalle= new DetalleCompra();
        detalle.setCantidad(cantidad);
        detalle.setPrecio(precio);
        
    }
    public void crearLote(){
        l= new Lote();
        l.setCantidad(detalle.getCantidad());
        l.setCosto(detalle.getPrecio());
        l.setFechaIngreso(new Date());
        l.setNumero(1);
        l.setProductoSucursalid(ps);
        
    }
    
    public DetalleCompra getDetalleCompra(){
        crearLote();
        detalle.setLoteid(l);
        return detalle;
        
    }
    
}
