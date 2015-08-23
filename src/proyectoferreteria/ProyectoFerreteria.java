/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoferreteria;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pablo
 */
public class ProyectoFerreteria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FileReader fr;
        try {
            fr = new FileReader("C:\\Users\\Diaz\\Desktop\\Archivo.txt");
            int numero=fr.read();
            String n="";
            while(numero>=0){
                n+=(char)numero;
                numero=fr.read();
            }
            System.out.println(n);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProyectoFerreteria.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProyectoFerreteria.class.getName()).log(Level.SEVERE, null, ex);
        }
        Principal p = new Principal();
        p.setVisible(Boolean.TRUE);
    }
    
}
