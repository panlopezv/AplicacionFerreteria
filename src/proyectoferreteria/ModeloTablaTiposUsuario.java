/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoferreteria;

import entidades.TipoUsuario;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Pablo
 */
public class ModeloTablaTiposUsuario extends AbstractTableModel {
   
    private String columnas[] = {"Tipo"};
    private List<TipoUsuario> tiposUsuario;

    public ModeloTablaTiposUsuario(List<TipoUsuario> tiposUsuario) {
        this.tiposUsuario = tiposUsuario;
    }

    public List<TipoUsuario> getTiposUsuario() {
        return tiposUsuario;
    }

    public void setTiposUsuario(List<TipoUsuario> tiposUsuario) {
        this.tiposUsuario = tiposUsuario;
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
    public int getRowCount() {
        return tiposUsuario.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        switch(columnIndex){
            case 0: return tiposUsuario.get(rowIndex).getTipo();
            default: return null;
        }
    }
    
}
