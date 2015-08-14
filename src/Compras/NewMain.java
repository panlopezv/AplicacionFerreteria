/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compras;

import conexion.ConexionJPA;
import controladores.CategoriaJpaController;
import controladores.ProductoJpaController;
import controladores.ProductoPresentacionJpaController;
import controladores.ProductoSucursalJpaController;
import entidades.Categoria;
import entidades.Persona;
import entidades.Presentacion;
import entidades.Producto;
import entidades.Telefono;
import entidades.TipoPersona;
import java.util.List;
/**
 *
 * @author Diaz
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        ConexionJPA conexion =  ConexionJPA.getInstance("root","root");
       
//        Productos productos= new Productos(conexion, 1);
//        productos.crearCategoria(new Categoria(0, "picsinas"));
//        productos.crearProducto((new Producto(0, "Cruz Roja", "Cemento")));
//        
//        
        
        NuevaCompra NUEVA = new NuevaCompra(null, null, conexion);
        
      
        compras com= new compras(null, null, conexion);
        com.setVisible(true);
            
       
        
        
        
        
    }
    
}
