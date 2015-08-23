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
    ItemProducto item;
    Lote l;

    public Lproducto(ItemProducto ps, int cantidad, double precio) {
        this.item = ps;
        creardetalle(cantidad, precio);
    }

    public void creardetalle(int cantidad, double precio) {
        detalle = new DetalleCompra();
        detalle.setCantidad(cantidad);
        detalle.setPrecio(precio);

    }

    public void crearLote() {
        l = new Lote();
        l.setCantidad(detalle.getCantidad());
        l.setCosto(detalle.getPrecio());
        l.setFechaIngreso(new Date());
        l.setNumero(1);
        l.setProductoSucursalid(item.ps);

    }

    public DetalleCompra getDetalleCompra() {
        crearLote();
        detalle.setLoteid(l);
        return detalle;

    }

    public Lote getL() {
        return l;
    }

    public Lproducto(ItemProducto item) {
        this.item = item;
    }

    public ItemProducto getItem() {
        return item;
    }

    public void setItem(ItemProducto item) {
        this.item = item;
    }

    public DetalleCompra getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleCompra detalle) {
        this.detalle = detalle;
    }

}
