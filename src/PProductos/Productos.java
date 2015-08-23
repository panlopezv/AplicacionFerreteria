/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PProductos;

import conexion.ConexionJPA;
import controladores.CategoriaJpaController;
import controladores.PresentacionJpaController;
import controladores.ProductoJpaController;
import controladores.ProductoPresentacionJpaController;
import controladores.ProductoSucursalJpaController;
import controladores.SucursalJpaController;
import entidades.Categoria;
import entidades.Presentacion;
import entidades.Producto;
import entidades.ProductoPresentacion;
import entidades.ProductoSucursal;
import entidades.Sucursal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Diaz
 */
public class Productos {

    private ConexionJPA conexion;
    int idSucursal;

    private CategoriaJpaController cCategoria;
    private ProductoJpaController cProducto;
    private ProductoPresentacionJpaController cProductoP;
    private PresentacionJpaController cPresentacion;
    private ProductoSucursalJpaController cPSucursal;
    private SucursalJpaController cSucursal;

    private Categoria categoria;
    private Producto producto;
    private ProductoPresentacion pprestenacion;
    private Presentacion presentacion;
    private ProductoSucursal pSucursal;

    Productos(ConexionJPA conexion, int idSucursa) {
        this.conexion = conexion;
        this.idSucursal = idSucursal;
        cPresentacion= new PresentacionJpaController(conexion.getEmf());
        cCategoria = new CategoriaJpaController(conexion.getEmf());
        cProducto = new ProductoJpaController(conexion.getEmf());
        cProductoP = new ProductoPresentacionJpaController(conexion.getEmf());
        cPSucursal= new ProductoSucursalJpaController(conexion.getEmf());
        cSucursal= new SucursalJpaController((conexion.getEmf()));

    }
    Productos(ConexionJPA conexion) {
        this.conexion = conexion;
        cPresentacion= new PresentacionJpaController(conexion.getEmf());
        cCategoria = new CategoriaJpaController(conexion.getEmf());
        cProducto = new ProductoJpaController(conexion.getEmf());
        cProductoP = new ProductoPresentacionJpaController(conexion.getEmf());
        cPSucursal = new ProductoSucursalJpaController(conexion.getEmf());
        cSucursal= new SucursalJpaController((conexion.getEmf()));

    }
    
    /**
     * Limpia todas las referencias de un producto.
     */
    public void Limpiar() {
        categoria = new Categoria();
        producto = new Producto();
        pprestenacion = new ProductoPresentacion();
        presentacion = new Presentacion();

        pSucursal = new ProductoSucursal();

    }
    
    /**
     * 
     * @param Id 
     * 
     * Recibe el id para referenciar  un categoria, no retorna datos ya que en esta estructura la guarda
     */
    public void buscarCategoria(int Id) {
        categoria = cCategoria.findCategoria(Id);
        if (categoria == null) {
            System.out.println("erorrrrr no existe categoria");
        }

    }
    /**
     * 
     * @param Categoria, tipo String
     * 
     * Recibe el id para referenciar  un categoria, no retorna datos ya que  esta estructura la guarda
     */
    public void buscarCategoria(String Categoria) {
        
        Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("Categoria.findByCategoria");
        q.setParameter("categoria", Categoria);
        
        categoria= (Categoria) q.getResultList().get(0);
        if (categoria == null) {
            System.out.println("erorrrrr no existe categoria");
        }

    }
    
    public int buscarCat(String Id) {
        
        Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("Categoria.findByCategoria");
        q.setParameter("categoria", Id);
        return ((Categoria) q.getSingleResult()).getId();
        
    }

    public void crearCategoria(Categoria categoria) {

        cCategoria.create(categoria);
        Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("Categoria.findMaxId");
        buscarCategoria((int) q.getSingleResult());
        System.out.println(categoria.getId());

    }

    public void buscarProducto(int Id) {
        producto = cProducto.findProducto(Id);
        if (producto == null) {
            System.out.println("error no existe producto");
        }

    }
    
    public List<Producto> buscarProducto(String Nombre) {
        Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("Producto.findByNombre");
        q.setParameter("nombre", Nombre);
        return  q.getResultList();

    }
    public List<Producto> buscarProducto(String Nombre,int Categoria) {
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

    public void crearProducto(Producto p) {
        p.setCategoriaid(categoria);
        cProducto.create(p);
        Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("Producto.findMaxId");
        buscarProducto((int) q.getSingleResult());
        System.out.println(producto.getId());

    }

    public void buscarPresentacion(int Id) {
        presentacion = cPresentacion.findPresentacion(Id);
        if (presentacion == null) {
            System.out.println("error no existe presetnacion");
        }

    }
    public void buscarPresentacion(String Id) {
         Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("Presentacion.findByPresentacion");
        q.setParameter("presentacion", Id);
        presentacion = (Presentacion) q.getResultList().get(0);
        if (presentacion == null) {
            System.out.println("error no existe presetnacion");
        }

    }

    public void crearPresentacion(Presentacion p) {
        cPresentacion.create(p);
        Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("Presentacion.findMaxId");
        buscarPresentacion((int) q.getSingleResult());
        System.out.println(presentacion.getId());

    }

    public void buscarProductoP(int Id) {
        pprestenacion = cProductoP.findProductoPresentacion(Id);
        if (pprestenacion == null) {
            System.out.println("Error no existe productoPresentacion");
        }

    }
    public List<ProductoPresentacion> buscarProductoP(String codigo) {
         Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("ProductoPresentacion.findByCodigo");
        q.setParameter("codigo", codigo);
        return q.getResultList();

    }

    public void crearProductoP() {
        String codigo = getCategoria().getId() + "" + getProducto().getId() + "" + getPresentacion().getId();
        ProductoPresentacion pp = new ProductoPresentacion();
        pp.setCodigo(codigo);
        pp.setId(15);
        pp.setProductoid(producto);
        pp.setPresentacionid(presentacion);
        
        cProductoP.create(pp);
        Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("ProductoPresentacion.findMaxId");
        buscarProductoP((int) q.getSingleResult());
    }
    public void buscarPSucursal(int id){
        pSucursal = cPSucursal.findProductoSucursal(id);
        if (pprestenacion == null) {
            System.out.println("Error no existe productoPresentacion");
        }
        
    }
    public void CrearProductoSucursal(ProductoSucursal ps){
        ps.setProductoPresentacionid(pprestenacion);
        cPSucursal.create(ps);
        
    }
    
    public List<Categoria> getListaCategoria() {
        List<Categoria> CAT= cCategoria.findCategoriaEntities();
        
        return CAT ;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    
    public Categoria getCategoria(String cat) {
         Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("Categoria.findByCategoria");
        q.setParameter("categoria", cat);
        
        
        return (Categoria) q.getResultList().get(0);
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    
    public ProductoPresentacion getPprestenacion() {
        return pprestenacion;
    }

    public void setPprestenacion(ProductoPresentacion pprestenacion) {
        this.pprestenacion = pprestenacion;
    }
    public List<Presentacion> getListaPresentacion() {
        List<Presentacion> p= cPresentacion.findPresentacionEntities();
        return p;
    }
    public Presentacion getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(Presentacion presentacion) {
        this.presentacion = presentacion;
    }
    
    
    public ProductoSucursal getpSucursal() {
        return pSucursal;
    }

    public void setpSucursal(ProductoSucursal pSucursal) {
        this.pSucursal = pSucursal;
    }
   public List<Sucursal> getSucursal(){
       return  cSucursal.findSucursalEntities();
   }
    public Sucursal getSucursal(String n) {
        for (Sucursal s : cSucursal.findSucursalEntities()) {
            if (("Sucursal " + s.getId()).compareTo(n) == 0) {
                return s;
            }
        }

        return null;
    }
}
