/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compras;

import conexion.ConexionJPA;
import controladores.PersonaJpaController;
import entidades.Compra;
import entidades.Persona;
import entidades.Sucursal;
import entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Diaz
 */
public class Ccompra {
    
    ACompra Icompra;
    
    Usuario usuario;
    
    Sucursal Sucursal;
    
    ConexionJPA conexion;
    
    /**
     * 
     * @param u
     * @param idSucursal
     * @param conexion 
     * Constructor, para crear compra
     */
    Ccompra(Usuario u,Sucursal idSucursal,ConexionJPA conexion){
        this.usuario=u;
        this.Sucursal=idSucursal;
        this.conexion=conexion;
        Icompra = new NuevaCompra(u, idSucursal, conexion);
    }
    /**
     * 
     * @param u
     * @param idSucursal
     * @param conexion
     * @param compra
     * Constructor para ver una compra
     */
    Ccompra(Usuario u,Sucursal idSucursal,ConexionJPA conexion,Compra compra){
        this.usuario=u;
        this.Sucursal=idSucursal;
        this.conexion=conexion;
        Icompra=new VerCompra();
    }

    public ACompra getIcompra() {
        return Icompra;
    }
    
    
    
    
    
    
}
