/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compras;

import conexion.ConexionJPA;
import entidades.Abono;
import entidades.Compra;
import entidades.ModoPago;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Diaz
 */
public class VerCompra extends ACompra {
    
    
    
    public void cargarCompra(Compra compra,ConexionJPA conexion){
        this.compra=compra;
        this.abonos= (ArrayList<Abono>) compra.getAbonoList();
        this.conexion=conexion;
     }
    
    
}
