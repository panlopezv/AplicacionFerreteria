/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PProductos;



import entidades.Producto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diaz
 */
public class ModeloTablaProductoProducto extends AbstractTableModel {
   
    private String columnas[] = {"Codigo","Nombre","Marca", "Categoria","Presentacion"};
   ArrayList<ClaseProducto> productos;

    public ModeloTablaProductoProducto(ArrayList<ClaseProducto> p) {
       
      this.productos=p;
    }

    public ArrayList<ClaseProducto> getProductos() {
        return productos;
    }
    
    public ClaseProducto getProducto(int n) {
        System.out.println(productos.size());
        return productos.get(n);
    }
    
    
    @Override
    public int getRowCount() {
        return productos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClaseProducto a= this.productos.get(rowIndex);
        
        switch(columnIndex){
            case 0: return a.getCodigo();
            case 1: return a.getNombre();
            case 2: return a.getMarca();
            case 3: return a.getCategoria();
            case 4: return a.getPresentacion();
                
            
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
    }
    
}
