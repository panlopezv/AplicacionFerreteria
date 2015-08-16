/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Map prop;
    
    public static ConexionJPA getInstance(String user,String pass) {
 
        if (instancia == null) {
            try {
                instancia = new ConexionJPA(user, pass);
            } catch (Exception ex) {
                //Logger.getLogger(ConexionJPA.class.getName()).log(Level.SEVERE, null, ex);
                instancia = null;
            }
        }   
        return instancia;
    }

    private ConexionJPA(String user,String pass) throws Exception {
        prop = new HashMap();
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
        em.close();
        emf.close();
        prop.clear();
        em=null;
        emf=null;
        prop= null;
        instancia=null;
    }
}
