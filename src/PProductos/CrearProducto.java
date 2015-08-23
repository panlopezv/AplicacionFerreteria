/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PProductos;

import conexion.ConexionJPA;
import entidades.Categoria;
import entidades.Presentacion;
import entidades.Producto;
import entidades.ProductoPresentacion;
import entidades.ProductoSucursal;
import entidades.Sucursal;
import java.awt.Component;
import java.awt.GridLayout;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class CrearProducto {

    Productos productos;

    ConexionJPA conexion;
    private ArrayList<Sucursal> sucursal;

    public CrearProducto(ConexionJPA conexion) {
        this.conexion = conexion;
        productos = new Productos(conexion);
        sucursal = new ArrayList<>();
    }

    public void crearCategoria(Categoria categoria) {
        productos.crearCategoria(categoria);
    }

    public void crearPresentacion(Presentacion presentacion) {
        productos.crearPresentacion(presentacion);
    }

    public void crearProducto() {
        productos.crearProducto(null);

    }

    public List<Producto> obtenerProductos(String n) {
        return productos.buscarProducto(n);

    }

    public void setSucursal(ArrayList<Sucursal> sucursal) {
        this.sucursal = sucursal;
    }

    public void setSucursal(List<ProductoSucursal> ps) {
        for (ProductoSucursal psa : ps) {
            sucursal.add(psa.getSucursalid());

        }

    }

    public void setCategoria(String c) {
        productos.buscarCategoria(c);
    }

    public void setPresentacion(String presentacion) {
        productos.buscarPresentacion(presentacion);
    }

    public void setProducto(Producto p) {
        productos.setProducto(p);

    }

    public void setProductop(ProductoPresentacion PP) {
        productos.setPprestenacion(PP);
    }

    public String[] getCategorias() {
        List<Categoria> categorias;
        categorias = productos.getListaCategoria();
        String[] cat = new String[categorias.size()];
        for (int i = 0; i < categorias.size(); i++) {
            cat[i] = categorias.get(i).getCategoria();

        }

        return cat;
    }

    public String[] getPresentacion() {
        List<Presentacion> presentaciones;
        presentaciones = productos.getListaPresentacion();
        String[] pre = new String[presentaciones.size()];
        for (int i = 0; i < presentaciones.size(); i++) {
            pre[i] = presentaciones.get(i).getPresentacion();

        }

        return pre;
    }

    /**
     *
     * @return
     *
     * obtiene las presentaciones que no tiene un producto
     */
    public String[] getSinPresentacion(Producto pro) {

        String[] ps = getPresentacion();
        List<ProductoPresentacion> PP = pro.getProductoPresentacionList();
        ArrayList<String> l = new ArrayList<>();
        String[] p = new String[ps.length];
        boolean var = true;
        for (int i = 0; i < ps.length; i++) {
            for (int j = 0; j < PP.size(); j++) {
                if (ps[i].compareTo(PP.get(j).getPresentacionid().getPresentacion()) == 0) {
                    var = false;
                }
            }
            if (var) {
                l.add(ps[i]);

            }
            var = true;

        }

        p = new String[l.size()];
        for (int i = 0; i < l.size(); i++) {
            p[i] = l.get(i);

        }

        return p;
    }

    public String[] getSucursal() {
        List<Sucursal> Sucursal;
        Sucursal = productos.getSucursal();
        String[] Suc = new String[Sucursal.size()];
        for (int i = 0; i < Sucursal.size(); i++) {
            Suc[i] = "Sucursal " + Sucursal.get(i).getId();

        }

        return Suc;
    }

    public String[] getsinSucursal() {
        List<Sucursal> Sucursal;
        Sucursal = productos.getSucursal();
        String[] Suc = new String[Sucursal.size()];

        if (sucursal.size() > 0) {
            Suc = new String[Sucursal.size() - sucursal.size()];
            int j = 0;
            boolean var = true;
            for (Sucursal su : Sucursal) {
                for (Sucursal s : sucursal) {
                    if (su.getId() == s.getId()) {
                        var = false;

                    }
                }

                if (var) {
                    Suc[j] = "Sucursal " + su.getId();

                    j++;
                }
                var = true;

            }

            return Suc;

        } else {

            Suc = new String[Sucursal.size()];
            for (int i = 0; i < Sucursal.size(); i++) {
                Suc[i] = "Sucursal " + Sucursal.get(i).getId();

            }
            return Suc;
        }
    }

    public ProductoPresentacion getPPresentacion() {
        return productos.getPprestenacion();
    }

    public void nuevoProducto(Producto p, Component c) {

        boolean var = true;
        boolean var2 = true;
        JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        do {
            String[] items = getsinSucursal();
            JComboBox combo = new JComboBox(items);

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Seleccione una sucursal"));
            panel.add(combo);
            panel.add(new JLabel("Cantidad"));
            panel.add(field1);
            panel.add(new JLabel("Precio"));
            panel.add(field2);
            Pattern n = Pattern.compile("[0-9]+[0-9]*?[0-9]*$");
            Pattern n2 = Pattern.compile("[0-9]+[0-9]*?\\.?[0-9]*$");

            int result = JOptionPane.showConfirmDialog(c, panel, "Crear Producto",
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

                        //para que lo guarde la primera vez
                        if (var2) {

                            productos.crearProducto(p);
                            productos.crearProductoP();
                            var2 = false;
                        }
                        Sucursal s = productos.getSucursal((String) combo.getSelectedItem());

                        ProductoSucursal ps = new ProductoSucursal();
                        ps.setExistencias(Integer.parseInt(field1.getText()));
                        ps.setPrecio(Double.parseDouble(field2.getText()));
                        ps.setSucursalid(s);
                        sucursal.add(s);

                        productos.CrearProductoSucursal(ps);
                        if (getsinSucursal().length > 0) {

                            result = JOptionPane.showConfirmDialog(c, "Existe otra sucursal con este producto?, pulse aceptar, si no solo cancelar", "Mensaje",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                            if (result == JOptionPane.OK_OPTION) {
                                var = true;

                            } else {
                                var = false;
                                ((JInternalFrame) c).dispose();

                            }
                        } else {
                            JOptionPane.showMessageDialog(c, "No existe Otra sucursal");
                            var = false;
                            ((JInternalFrame) c).dispose();

                        }

                    }
                }

            } else {
                result = JOptionPane.showConfirmDialog(c, "Desea Cancelar", "Mensaje",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.CLOSED_OPTION);
                if (JOptionPane.OK_OPTION == result) {
                    ((JInternalFrame) c).dispose();
                    var = false;
                } else {
                    var = true;
                }
            }
        } while (var);

    }

    public void nuevaPresentacion(Component c) {
        boolean var = true;
        boolean var2 = true;

        JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        do {
            String[] items = getsinSucursal();
            JComboBox combo = new JComboBox(items);
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Seleccione una sucursal"));
            panel.add(combo);
            panel.add(new JLabel("Cantidad"));
            panel.add(field1);
            panel.add(new JLabel("Precio"));
            panel.add(field2);
            Pattern n = Pattern.compile("[0-9]+[0-9]*?[0-9]*$");
            Pattern n2 = Pattern.compile("[0-9]+[0-9]*?\\.?[0-9]*$");
            int result = JOptionPane.showConfirmDialog(c, panel, "Crear Presentacion",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                Matcher m = n.matcher(field1.getText());
                Matcher m2 = n2.matcher(field2.getText());
                if (!m.matches()) {
                    //valida cantidad
                    JOptionPane.showMessageDialog(c, "Entreada invalida para la cantidad, compruebe que no escribio letras o valores no permitidos");
                    field1.setText("");
                    field1.requestFocus();

                } else {
                    if (!m2.matches()) {
                        JOptionPane.showMessageDialog(c, "Entreada invalida para el precio, compruebe que no escribio letras o valores no permitidos");
                        field2.setText("");
                        field2.requestFocus();

                    } else {
                        if (var2) {

                            productos.crearProductoP();
                            var2 = false;
                        }
                        System.out.println(m.matches()+"  "+m2.matches());
                                
                        Sucursal s = productos.getSucursal((String) combo.getSelectedItem());

                        ProductoSucursal ps = new ProductoSucursal();
                        ps.setExistencias(Integer.parseInt(field1.getText()));
                        ps.setPrecio(Double.parseDouble(field2.getText()));
                        ps.setSucursalid(s);
                        sucursal.add(s);

                        productos.CrearProductoSucursal(ps);
                        if (getsinSucursal().length > 0) {

                            result = JOptionPane.showConfirmDialog(c, "Existe otra sucursal con este producto?, pulse aceptar, si no solo cancelar", "Mensaje",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                            if (result == JOptionPane.OK_OPTION) {
                                var = true;

                            } else {
                                var = false;

                            }
                        } else {
                            JOptionPane.showMessageDialog(c, "No existe Otra sucursal");
                            var = false;
                            ((JInternalFrame) c).dispose();

                        }

                    }
                }

            } else {
                result = JOptionPane.showConfirmDialog(c, "Desea Cancelar", "Mensaje",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.CLOSED_OPTION);
                if (JOptionPane.OK_OPTION == result) {
                    ((JInternalFrame) c).dispose();
                    var = false;
                } else {
                    var = true;
                }

            }
        } while (var);
    }

    public void nuevaSucursal(Component c) {
        boolean var = true;

        JTextField field1 = new JTextField("");
        JTextField field2 = new JTextField("");
        do {
            String[] items = getsinSucursal();
            JComboBox combo = new JComboBox(items);
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Seleccione una sucursal"));
            panel.add(combo);
            panel.add(new JLabel("Cantidad"));
            panel.add(field1);
            panel.add(new JLabel("Precio"));
            panel.add(field2);

            Pattern n = Pattern.compile("[0-9]+[0-9]*?[0-9]*$");
            Pattern n2 = Pattern.compile("[0-9]+[0-9]*?\\.?[0-9]*$");
            if (getsinSucursal().length > 0) {
                int result = JOptionPane.showConfirmDialog(c, panel, "Crear Sucursal",
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

                            Sucursal s = productos.getSucursal((String) combo.getSelectedItem());

                            ProductoSucursal ps = new ProductoSucursal();
                            ps.setExistencias(Integer.parseInt(field1.getText()));
                            ps.setPrecio(Double.parseDouble(field2.getText()));
                            ps.setSucursalid(s);
                            sucursal.add(s);

                            productos.CrearProductoSucursal(ps);

                            result = JOptionPane.showConfirmDialog(c, "Existe otra sucursal con este producto?, pulse aceptar, si no solo cancelar", "Mensaje",
                                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                            if (result == JOptionPane.OK_OPTION) {
                                var = true;

                            } else {
                                var = false;

                            }

                        }
                    }

                } else {
                    result = JOptionPane.showConfirmDialog(c, "Desea Cancelar", "Mensaje",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.CLOSED_OPTION);
                    if (JOptionPane.OK_OPTION == result) {
                        ((JInternalFrame) c).dispose();
                        var = false;
                    } else {
                        var = true;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(c, "No existe Otra sucursal");
                var = false;
                ((JInternalFrame) c).dispose();

            }
        } while (var);

    }

    public ArrayList<ClaseProducto> BusquedaP(String n) {
        ArrayList<ClaseProducto> clasep = new ArrayList<>();
        List<Producto> p = productos.buscarProducto(n);
        for (Producto pro : p) {
            ClaseProducto cpro = new ClaseProducto();
            cpro.setNombre(pro.getNombre());
            cpro.setMarca(pro.getMarca());
            cpro.setCategoria(pro.getCategoriaid().getCategoria());
            for (ProductoPresentacion pp : pro.getProductoPresentacionList()) {
                ClaseProducto clp = new ClaseProducto();
                clp.setNombre(cpro.getNombre());
                clp.setMarca(cpro.getMarca());
                clp.setCategoria(cpro.getCategoria());
                clp.setCodigo(pp.getCodigo());
                clp.setPresentacion(pp.getPresentacionid().getPresentacion());
                clp.setPp(pp);
                clasep.add(clp);

            }

        }
        return clasep;

    }

    public ArrayList<ClaseProducto> BusquedaP(String n, String Categoria) {
        ArrayList<ClaseProducto> clasep = new ArrayList<>();
        List<Producto> p = productos.buscarProducto(n, productos.getCategoria(Categoria).getId());
        for (Producto pro : p) {
            ClaseProducto cpro = new ClaseProducto();
            cpro.setNombre(pro.getNombre());
            cpro.setMarca(pro.getMarca());
            cpro.setCategoria(pro.getCategoriaid().getCategoria());
            for (ProductoPresentacion pp : pro.getProductoPresentacionList()) {
                ClaseProducto clp = new ClaseProducto();
                clp.setNombre(cpro.getNombre());
                clp.setMarca(cpro.getMarca());
                clp.setCategoria(cpro.getCategoria());
                clp.setCodigo(pp.getCodigo());
                clp.setPresentacion(pp.getPresentacionid().getPresentacion());
                clp.setPp(pp);
                clasep.add(clp);

            }

        }
        return clasep;

    }

    public ArrayList<ClaseProducto> busquedaporCodigo(String n) {
        ArrayList<ClaseProducto> clasep = new ArrayList<>();
        List<ProductoPresentacion> p = productos.buscarProductoP(n);

        for (ProductoPresentacion pp : p) {
            ClaseProducto cp = new ClaseProducto();
            cp.setCategoria(pp.getProductoid().getCategoriaid().getCategoria());
            cp.setDescripcion(pp.getProductoid().getDescripcion());
            cp.setCodigo(pp.getCodigo());
            cp.setMarca(pp.getProductoid().getMarca());
            cp.setNombre(pp.getProductoid().getNombre());
            cp.setPp(pp);
            cp.setPresentacion(pp.getPresentacionid().getPresentacion());
            clasep.add(cp);

        }
        return clasep;

    }

}
