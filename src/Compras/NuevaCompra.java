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
import entidades.ProductoSucursal;
import entidades.Sucursal;
import entidades.Telefono;
import entidades.TelefonoPersona;
import entidades.Usuario;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Diaz
 */
public class NuevaCompra extends ACompra {
    
    
    static private final int BusquedaCategoria=0;
    static private final int BusquedaPresentacion=1;
    static private final int BusquedaCodigo=2;
    static private final int Busquedanombre=3;
    ArrayList<Lproducto> CarroC;
    NuevaCompra(Usuario Empleado, Sucursal Sucursal,ConexionJPA conexion) {
        CarroC = new ArrayList<>();     
        
        this.Sucursal= Sucursal;
        this.Empleado=Empleado;
        this.conexion=conexion;
        persona=new Persona();
        persona.setTelefonoPersonaList(new ArrayList<>());

    }
    
    public void crearCompra(){
        compra= new Compra();
        compra.setFecha(null);
        compra.setNumero(null);
        compra.setUsuarioid(Empleado);
        compra.setPersonaid(persona);
    }
    
    public void agregarDetalle(ProductoSucursal ps, int cantidad,int Precio){
        
        CarroC.add(new Lproducto(ps,cantidad, Precio));
        
    }
    public void BuscarProducto(){
        
    }
    
    public boolean  buscarPersona(String nit){
        Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("Persona.findByNit");
        q.setParameter("nit", nit);
        
        
        if(q.getResultList().size()>0){
            persona=(Persona)q.getSingleResult() ;
            return true;
            
        }else{
            return false;
        }   
        
        
    }
    
    public void ingresarTelefono(Telefono tel){
        TelefonoPersona TelP= new TelefonoPersona();
        TelP.setTelefonoid(tel);
        persona.getTelefonoPersonaList().add(TelP);
        
    }
    
    public void InsertarPersona(Persona p){
                        
        PersonaJpaController cPersona= new PersonaJpaController(conexion.getEmf());
        cPersona.create(p);
        buscarPersona(p.getNit());
        
        
    }

}
