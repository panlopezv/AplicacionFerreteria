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
public class ModeloTablaProductoCompra extends AbstractTableModel {
   
    private String columnas[] = {"Codigo","Nombre","Presentacion","Marca","Categoria","Sucursal","Precio Compra","Cantidad de compra"};
    ArrayList<Lproducto> Items;

    public ModeloTablaProductoCompra(ArrayList<Lproducto> items) {
       
      this.Items=items;
    }

    public ArrayList<Lproducto> getProductos() {
        return Items;
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
        
        ItemProducto a= this.Items.get(rowIndex).getItem();
        
        switch(columnIndex){
            case 0: return a.getCodigo();
            case 1: return a.getNombre();
            case 2: return a.getPresentacion();
            case 3: return a.getMarca();
            case 4: return a.getCategoria();
            case 5: return a.getSucursal();
            case 6: return this.Items.get(rowIndex).getDetalle().getPrecio();  
            case 7: return this.Items.get(rowIndex).getDetalle().getCantidad();
                
            
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch(rowIndex){
            case 6: return true;
            case 7: return true;    
            
            default : return false;
        }
        
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
    }
    
}
