/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoferreteria;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controladores.PermisoUsuarioJpaController;
import controladores.TipoUsuarioJpaController;
import entidades.PermisoUsuario;
import entidades.TipoUsuario;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.swing.JOptionPane;

/**
 *
 * @author Pablo
 */
public class TiposUsuario extends javax.swing.JInternalFrame {
    private TipoUsuarioJpaController controladorTU;
    /**
     * Creates new form VertTipoUsuario
     */
    public TiposUsuario() {
        initComponents();
        initComponents();
        setIconifiable(Boolean.TRUE);
        setVisible(Boolean.TRUE);
        setClosable(Boolean.TRUE);
        //setResizable(Boolean.TRUE);
        controladorTU = new TipoUsuarioJpaController(Principal.conexion.getEmf());
        mostrarTipos();
    }
    
    public void mostrarTipos(){
        tablaTipos.setModel(new ModeloTablaTiposUsuario(controladorTU.findTipoUsuarioEntities()));
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JPopupMenu();
        ver = new javax.swing.JMenuItem();
        modificar = new javax.swing.JMenuItem();
        eliminar = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTipos = new javax.swing.JTable();
        botonSalir = new javax.swing.JButton();
        botonCrear = new javax.swing.JButton();

        ver.setText("Ver");
        ver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verActionPerformed(evt);
            }
        });
        menu.add(ver);

        modificar.setText("Modificar");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });
        menu.add(modificar);

        eliminar.setText("Eliminar");
        eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarActionPerformed(evt);
            }
        });
        menu.add(eliminar);

        setTitle("Tipos de usuario");

        tablaTipos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaTipos.setComponentPopupMenu(menu);
        jScrollPane1.setViewportView(tablaTipos);

        botonSalir.setText("Salir");
        botonSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        botonCrear.setText("Crear");
        botonCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCrearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(botonCrear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonCrear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonSalir))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void verActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verActionPerformed
        // TODO add your handling code here:
        int fila = tablaTipos.getSelectedRow();
        if (fila >= 0) {
            VerTipoUsuario vr = new VerTipoUsuario(((ModeloTablaTiposUsuario) tablaTipos.getModel()).getTiposUsuario().get(fila).getId());
            getParent().add(vr);
            vr.toFront();
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de usuario.", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_verActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        // TODO add your handling code here:
        int fila = tablaTipos.getSelectedRow();
        if (fila >= 0) {
            ModificarTipoUsuario mr = new ModificarTipoUsuario(((ModeloTablaTiposUsuario) tablaTipos.getModel()).getTiposUsuario().get(fila).getId());
            getParent().add(mr);
            mr.toFront();
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de usuario.", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_modificarActionPerformed

    private void eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarActionPerformed
        // TODO add your handling code here:
        int fila = tablaTipos.getSelectedRow();
        if (fila >= 0) {
            if (JOptionPane.showConfirmDialog(this, "¿Desea eliminar el tipo de usuario?", "Advertencia.",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            PermisoUsuarioJpaController controladorPU = new PermisoUsuarioJpaController(Principal.conexion.getEmf());
            List<PermisoUsuario> permisosTUsuario;
            Query q;
            q = Principal.conexion.getEmf().createEntityManager().createNamedQuery("PermisoUsuario.findByTipoUsuarioId");
            q.setParameter("tipoUsuarioid", ((ModeloTablaTiposUsuario) tablaTipos.getModel()).getTiposUsuario().get(fila));
            permisosTUsuario = q.getResultList();
            try {
                if (permisosTUsuario != null || permisosTUsuario.size() > 0) {
                    for (int i = 0; i < permisosTUsuario.size(); i++) {
                        controladorPU.destroy(permisosTUsuario.get(i).getId());
                    }
                }

                controladorTU.destroy(((ModeloTablaTiposUsuario) tablaTipos.getModel()).getTiposUsuario().get(fila).getId());
                JOptionPane.showMessageDialog(this, "Eliminacion de tipo de usuario realizada con exito.");
            } catch (NonexistentEntityException | IllegalOrphanException ex) {
                Logger.getLogger(TiposUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un tipo de usuario.", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_eliminarActionPerformed

    private void botonCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCrearActionPerformed
        // TODO add your handling code here:
        CrearTipoUsuario cr = new CrearTipoUsuario();
        getParent().add(cr);
        cr.toFront();
    }//GEN-LAST:event_botonCrearActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_botonSalirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCrear;
    private javax.swing.JButton botonSalir;
    private javax.swing.JMenuItem eliminar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu menu;
    private javax.swing.JMenuItem modificar;
    private javax.swing.JTable tablaTipos;
    private javax.swing.JMenuItem ver;
    // End of variables declaration//GEN-END:variables
}
