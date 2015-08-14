/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PProductos;

import entidades.ProductoPresentacion;

/**
 *
 * @author Diaz
 */
public class ClaseProducto {
    
    String Nombre;
    String Marca;
    String Descripcion;
    String Categoria;
    String Codigo;
    String Presentacion;
    
    ProductoPresentacion pp;

    public ClaseProducto(String Nombre, String Marca, String Descripcion, String Categoria, String Codigo) {
        this.Nombre = Nombre;
        this.Marca = Marca;
        this.Descripcion = Descripcion;
        this.Categoria = Categoria;
        this.Codigo = Codigo;
    }
    public ClaseProducto() {
        
    }

    public ProductoPresentacion getPp() {
        return pp;
    }

    public void setPp(ProductoPresentacion pp) {
        this.pp = pp;
    }

    public String getPresentacion() {
        return Presentacion;
    }

    public void setPresentacion(String Presentacion) {
        this.Presentacion = Presentacion;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }
    
    
    
}
