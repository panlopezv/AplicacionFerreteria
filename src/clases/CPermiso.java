/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import controladores.PermisoJpaController;
import controladores.PermisoUsuarioJpaController;
import entidades.Permiso;
import java.util.List;
import javax.persistence.Query;
import proyectoferreteria.Principal;

/**
 *
 * @author Pablo
 */
public class CPermiso {
    private int idTipoUsuario;
    private List<Permiso> permisos;
    private PermisoJpaController controladorP;
    private PermisoUsuarioJpaController controladorPU;

    public CPermiso(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;        
        controladorP = new PermisoJpaController(Principal.conexion.getEmf());
        controladorPU = new PermisoUsuarioJpaController(Principal.conexion.getEmf());
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

    public PermisoJpaController getControladorP() {
        return controladorP;
    }

    public void setControladorP(PermisoJpaController controladorP) {
        this.controladorP = controladorP;
    }

    public PermisoUsuarioJpaController getControladorPU() {
        return controladorPU;
    }

    public void setControladorPU(PermisoUsuarioJpaController controladorPU) {
        this.controladorPU = controladorPU;
    }
    
}
