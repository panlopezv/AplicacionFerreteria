/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Compras;



import entidades.ProductoPresentacion;
import entidades.ProductoSucursal;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Diaz
 */
public class ModeloTablaProducto2 extends AbstractTableModel {
   
    private String columnas[] = {"Codigo","Nombre","Presentacion","Marca","Categoria","Sucursal","Precio","Cantidad"};
    ArrayList<ItemProducto> Items;

    public ModeloTablaProducto2(ArrayList<ItemProducto> items) {
       
      this.Items=items;
    }

    public ArrayList<ItemProducto> getProductos() {
        return Items;
    }
    
    public ItemProducto getProducto(int n) {
        return Items.get(n);
    }
    
    
    @Override
    public int getRowCount() {
        return Items.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemProducto a= this.Items.get(rowIndex);
        
        switch(columnIndex){
            case 0: return a.getCodigo();
            case 1: return a.getNombre();
            case 2: return a.getPresentacion();
            case 3: return a.getMarca();
            case 4: return a.getCategoria();
            case 5: return a.getSucursal();
            case 6: return a.getPrecio();  
            case 7: return a.getCantidad();
                
            
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
    }
    
}
