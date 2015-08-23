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
import controladores.LoteJpaController;
import controladores.PersonaJpaController;
import entidades.Compra;
import entidades.DetalleCompra;
import entidades.Lote;
import entidades.Persona;
import entidades.Producto;
import entidades.ProductoPresentacion;
import entidades.ProductoSucursal;
import entidades.Sucursal;
import entidades.TipoPersona;
import entidades.Usuario;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
    double t;
    NuevaCompra(Usuario Empleado, Sucursal Sucursal,ConexionJPA conexion) {
        CarroC = new ArrayList<>();     
        
        this.Sucursal= Sucursal;
        this.Empleado=Empleado;
        this.conexion=conexion;
        persona=new Persona();
        this.cAbono= new AbonoJpaController(conexion.getEmf());
        this.cCompra= new CompraJpaController(conexion.getEmf());
        this.cDetalle= new DetalleCompraJpaController(conexion.getEmf());
        this.cLote= new LoteJpaController(conexion.getEmf());
        this.cPersona= new PersonaJpaController(conexion.getEmf());
        

    }
    public void BuscarProducto(){
        
    }
    public double total(){
        double n=0;
        for(Lproducto lp: CarroC){
            n=+ lp.getDetalle().getPrecio()*lp.getDetalle().getCantidad();
            
        }
        t=n;
        return n;
                
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
                    item.setPs(ps);
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
    
    public void agregarProductoSucursal(ItemProducto ps, Component c){
       
        boolean var = true;

        JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        do {
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Cantidad"));
            panel.add(field1);
            panel.add(new JLabel("Precio de la Compra"));
            panel.add(field2);

            Pattern n = Pattern.compile("[0-9]+[0-9]*?[0-9]*$");
            Pattern n2 = Pattern.compile("[0-9]+[0-9]*?\\.?[0-9]*$");
            
                int result = JOptionPane.showConfirmDialog(c, panel, "Ingreso de cantidad de Compra",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {

                    Matcher m = n.matcher(field1.getText());
                    Matcher m2 = n2.matcher(field2.getText());
                    if (!m.matches()) {
                        //valida cantidad
                        JOptionPane.showMessageDialog(c, "Entrada invalida para la cantidad, compruebe que no escribio letras o valores no permitidos");
                        field1.setText("");
                        field1.requestFocus();

                    } else {
                        if (!m2.matches()) {
                            JOptionPane.showMessageDialog(c, "Entrada invalida para el precio, compruebe que no escribio letras o valores no permitidos");
                            field2.setText("");
                            field2.requestFocus();

                        } else {

                            CarroC.add(new Lproducto(ps,Integer.parseInt( field1.getText()), Double.parseDouble(field2.getText())));
                            result = JOptionPane.showConfirmDialog(c, "Se agrego el producto a la compra", "Mensaje",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                            var=false;

                            

                        }
                    }

                } else {
                    result = JOptionPane.showConfirmDialog(c, "Desea Cancelar", "Mensaje",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.CLOSED_OPTION);
                    if (JOptionPane.OK_OPTION == result) {
                        
                        var = false;
                    } else {
                        var = true;
                    }
                }
            
        } while (var);

    
        
       
        
    }

    public ArrayList<Lproducto> getCarroC() {
        return CarroC;
    }
    public void eliminarProducto(int n){
        
        CarroC.remove(n);
        
    }
    
    public void buscarCompra(int n){
        compra=cCompra.findCompra(n);
        
    }
    public void crearCompra(Compra c){
        cCompra.create(c);
        Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("Compra.findMaxId");
        buscarCompra((int) q.getSingleResult());
    }
    public Lote crearLote(Lote l){
        cLote.create(l);
        Query q;
        EntityManager em = conexion.getEm();
        q = em.createNamedQuery("Lote.findMaxId");
        return cLote.findLote((int) q.getSingleResult());
        
    }
    public void detalles(){
        for(Lproducto lp: CarroC){
            DetalleCompra dt= lp.getDetalle();
            dt.setLoteid(crearLote(lp.getL()));
            dt.setCompraid(compra);
            cDetalle.create(dt);
            
            
        }
    }
    public void finalizarCompra(Component c, Compra compra){
        if(CarroC.size()>0){
            crearCompra(compra);
            detalles();
           
        }else{
            
        }
    }
    

}
