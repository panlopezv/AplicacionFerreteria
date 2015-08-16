/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoferreteria;

import controladores.PermisoJpaController;
import controladores.PermisoUsuarioJpaController;
import controladores.TipoUsuarioJpaController;
import entidades.Permiso;
import entidades.PermisoUsuario;
import entidades.TipoPersona;
import entidades.TipoUsuario;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pablo
 */
public class CrearTipoUsuario extends javax.swing.JInternalFrame {
    private int id=1;
    private List<Permiso> permisos;
    private JCheckBox[] eleccionPermisos;
    private ArrayList<String> permisosUsuario;
    private PermisoUsuarioJpaController controladorPU;
    private TipoUsuarioJpaController controladorTU;
    /**
     * Creates new form CrearTipoUsuario
     */
    public CrearTipoUsuario() {
        initComponents();
        setIconifiable(Boolean.TRUE);
        setVisible(Boolean.TRUE);
        setClosable(Boolean.TRUE);
        //setResizable(Boolean.TRUE);
        
        controladorPU=new PermisoUsuarioJpaController(Principal.conexion.getEmf());
        controladorTU=new TipoUsuarioJpaController(Principal.conexion.getEmf());    
        cargarPermisos();
        actualizarListaTabla();
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        campoTipo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        botonModificar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        botonAceptar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel1.setText("Tipo usuario:");

        jLabel2.setText("Permisos:");

        botonModificar.setText("Modificar");
        botonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarActionPerformed(evt);
            }
        });

        botonCancelar.setText("Cancelar");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        botonAceptar.setText("Aceptar");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(campoTipo, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(botonModificar)
                .addContainerGap(169, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonAceptar)
                .addGap(18, 18, 18)
                .addComponent(botonCancelar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonModificar)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelar)
                    .addComponent(botonAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarActionPerformed
        // TODO add your handling code here:
        if(permisosUsuario.size()>0){
            for(int j=0;j<permisos.size();j++){
                eleccionPermisos[j].setSelected(Boolean.FALSE);
            }
            
            for(int i=0;i<permisosUsuario.size();i++){
                for(int j=0;j<permisos.size();j++){
                    if(permisosUsuario.get(i).compareTo(permisos.get(j).getPermiso())==0){
                        eleccionPermisos[j].setSelected(Boolean.TRUE);
                    }
                }
            }
        }
        int opcion = JOptionPane.showConfirmDialog(this, eleccionPermisos, "Selección de permisos.", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            permisosUsuario=new ArrayList<>();
            for(int j=0;j<eleccionPermisos.length;j++){
                if(eleccionPermisos[j].isSelected()==Boolean.TRUE){
                    permisosUsuario.add(permisos.get(j).getPermiso());
                }
            } 
            actualizarListaTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se han modificado los permisos.");
        }
    }//GEN-LAST:event_botonModificarActionPerformed

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        // TODO add your handling code here:
        if(campoTipo.getText().compareTo("")!=0){
            controladorTU.create(new TipoUsuario(0, campoTipo.getText()));
            cargarMaxID();
            permisosUsuario.stream().forEach((permisosUsuario1) -> {
                permisos.stream().filter((permiso) -> (permisosUsuario1.compareTo(permiso.getPermiso()) == 0)).forEach((permiso) -> {
                    controladorPU.create(new PermisoUsuario(0, permiso, new TipoUsuario(id)));
                });
            });
            JOptionPane.showMessageDialog(this, "Creacion de Tipo de usuario exitosa.");
            this.dispose();
        }
        else {
            JOptionPane.showMessageDialog(this, "Debe asignarle un valor a Tipo de usuario.");
        }
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botonCancelarActionPerformed
    
    private void cargarPermisos(){
        permisos= (new PermisoJpaController(Principal.conexion.getEmf())).findPermisoEntities();
        eleccionPermisos=new JCheckBox[permisos.size()]; 
        permisosUsuario= new ArrayList<>();
        for(int i=0;i<permisos.size();i++){
            eleccionPermisos[i]=new JCheckBox(permisos.get(i).getPermiso());
        }
    }
    
    public void cargarMaxID(){
        Query q;
        q = Principal.conexion.getEmf().createEntityManager().createNamedQuery("TipoUsuario.MaxId");
        Object maximo = q.getSingleResult();
        if(maximo!=null){
            id=(int)maximo;
        }    
    }
    private void actualizarListaTabla(){
        DefaultTableModel modelo = new DefaultTableModel();
        jTable1.setModel(modelo);
        jTable1.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        modelo.addColumn("Modulo");
        for(int i=0;i<permisosUsuario.size();i++){
            modelo.addRow(new Object[]{permisosUsuario.get(i)});
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonModificar;
    private javax.swing.JTextField campoTipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
