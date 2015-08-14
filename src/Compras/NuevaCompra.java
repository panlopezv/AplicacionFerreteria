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
import entidades.Producto;
import entidades.ProductoPresentacion;
import entidades.ProductoSucursal;
import entidades.Sucursal;
import entidades.TipoPersona;
import entidades.Usuario;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    }
    
    public void crearCompra(){
        compra= new Compra();
        compra.setFecha(null);
        compra.setNumero(null);
        compra.setUsuarioid(Empleado);
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
    public void buscarTelefono(String Numero){
        
    }
   
    
    public void InsertarPersona(Persona p){
                        
        PersonaJpaController cPersona= new PersonaJpaController(conexion.getEmf());
        cPersona.create(p);
        buscarPersona(p.getNit());    
    }
    public List<Producto> buscarProducto(String Nombre) {
        Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("Producto.findByNombre");
        q.setParameter("nombre", Nombre);
        return  q.getResultList();

    }
    
    public ArrayList<Producto> buscarProducto(String Nombre,int Categoria) {
        Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("Producto.findByNombre");
        q.setParameter("nombre", Nombre);
        List<Producto>p1=q.getResultList();
        ArrayList<Producto> p2=new ArrayList<>();
        
        for(Producto p :p1) {
            if(p.getCategoriaid().getId()==Categoria){
                p2.add(p);
            }
            
        }
        
        
        return p2  ;

    }
    
    
    public ArrayList<ItemProducto> obtenerProductos(String Nombre) {
        ArrayList<ItemProducto> items = new ArrayList<>();
        List<Producto> p = buscarProducto(Nombre);
        for (Producto pro : p) {

            for (ProductoPresentacion pp : pro.getProductoPresentacionList()) {

                for (ProductoSucursal ps : pp.getProductoSucursalList()) {
                    ItemProducto item = new ItemProducto();
                    item.setNombre(pro.getNombre());
                    item.setMarca(pro.getMarca());
                    item.setCategoria(pro.getCategoriaid().getCategoria());
                    item.setCodigo(pp.getCodigo());
                    item.setPresentacion(pp.getPresentacionid().getPresentacion());
                    item.setSucursal("Sucursal "+ps.getSucursalid().getId());
                    item.setPrecio(ps.getPrecio());
                    item.setCantidad(ps.getExistencias());
                    items.add(item);

                }

            }

        }
        return items;

    }
    
    public TipoPersona buscarPersona(String tipo,int n){
       Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("TipoPersona.findByTipo");
        q.setParameter("tipo", tipo);
        return ((TipoPersona) q.getResultList().get(0));
        
    }

}
