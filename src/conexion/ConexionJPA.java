/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Pablo
 */
public class ConexionJPA {
    
    private EntityManager em;
    private EntityManagerFactory emf;
    private static ConexionJPA instancia;
    private Map prop = new HashMap();
    
    public static ConexionJPA getInstance(String user,String pass) throws Exception{
        if(instancia==null){
            instancia = new ConexionJPA(user,pass);
        }
        return instancia;   
    }

    private ConexionJPA(String user,String pass) {
        prop.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/ferreteriadeleon");
        prop.put("javax.persistence.jdbc.password", pass);
        prop.put("javax.persistence.jdbc.user", user);
        emf= Persistence.createEntityManagerFactory("ProyectoFerreteriaPU",prop);
        em = emf.createEntityManager();
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public EntityManager getEm() {
        return em;
    }
    
    public void close() {
        this.em=null;
        this.emf=null;
        this.prop= null;
        ConexionJPA.instancia=null;
    }
}
