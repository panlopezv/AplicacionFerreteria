/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Proveedor;
import entidades.Persona;
import entidades.PersonaProveedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class PersonaProveedorJpaController implements Serializable {

    public PersonaProveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PersonaProveedor personaProveedor) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor proveedorid = personaProveedor.getProveedorid();
            if (proveedorid != null) {
                proveedorid = em.getReference(proveedorid.getClass(), proveedorid.getId());
                personaProveedor.setProveedorid(proveedorid);
            }
            Persona personaid = personaProveedor.getPersonaid();
            if (personaid != null) {
                personaid = em.getReference(personaid.getClass(), personaid.getId());
                personaProveedor.setPersonaid(personaid);
            }
            em.persist(personaProveedor);
            if (proveedorid != null) {
                proveedorid.getPersonaProveedorList().add(personaProveedor);
                proveedorid = em.merge(proveedorid);
            }
            if (personaid != null) {
                personaid.getPersonaProveedorList().add(personaProveedor);
                personaid = em.merge(personaid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PersonaProveedor personaProveedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PersonaProveedor persistentPersonaProveedor = em.find(PersonaProveedor.class, personaProveedor.getId());
            Proveedor proveedoridOld = persistentPersonaProveedor.getProveedorid();
            Proveedor proveedoridNew = personaProveedor.getProveedorid();
            Persona personaidOld = persistentPersonaProveedor.getPersonaid();
            Persona personaidNew = personaProveedor.getPersonaid();
            if (proveedoridNew != null) {
                proveedoridNew = em.getReference(proveedoridNew.getClass(), proveedoridNew.getId());
                personaProveedor.setProveedorid(proveedoridNew);
            }
            if (personaidNew != null) {
                personaidNew = em.getReference(personaidNew.getClass(), personaidNew.getId());
                personaProveedor.setPersonaid(personaidNew);
            }
            personaProveedor = em.merge(personaProveedor);
            if (proveedoridOld != null && !proveedoridOld.equals(proveedoridNew)) {
                proveedoridOld.getPersonaProveedorList().remove(personaProveedor);
                proveedoridOld = em.merge(proveedoridOld);
            }
            if (proveedoridNew != null && !proveedoridNew.equals(proveedoridOld)) {
                proveedoridNew.getPersonaProveedorList().add(personaProveedor);
                proveedoridNew = em.merge(proveedoridNew);
            }
            if (personaidOld != null && !personaidOld.equals(personaidNew)) {
                personaidOld.getPersonaProveedorList().remove(personaProveedor);
                personaidOld = em.merge(personaidOld);
            }
            if (personaidNew != null && !personaidNew.equals(personaidOld)) {
                personaidNew.getPersonaProveedorList().add(personaProveedor);
                personaidNew = em.merge(personaidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personaProveedor.getId();
                if (findPersonaProveedor(id) == null) {
                    throw new NonexistentEntityException("The personaProveedor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PersonaProveedor personaProveedor;
            try {
                personaProveedor = em.getReference(PersonaProveedor.class, id);
                personaProveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personaProveedor with id " + id + " no longer exists.", enfe);
            }
            Proveedor proveedorid = personaProveedor.getProveedorid();
            if (proveedorid != null) {
                proveedorid.getPersonaProveedorList().remove(personaProveedor);
                proveedorid = em.merge(proveedorid);
            }
            Persona personaid = personaProveedor.getPersonaid();
            if (personaid != null) {
                personaid.getPersonaProveedorList().remove(personaProveedor);
                personaid = em.merge(personaid);
            }
            em.remove(personaProveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PersonaProveedor> findPersonaProveedorEntities() {
        return findPersonaProveedorEntities(true, -1, -1);
    }

    public List<PersonaProveedor> findPersonaProveedorEntities(int maxResults, int firstResult) {
        return findPersonaProveedorEntities(false, maxResults, firstResult);
    }

    private List<PersonaProveedor> findPersonaProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PersonaProveedor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PersonaProveedor findPersonaProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PersonaProveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PersonaProveedor> rt = cq.from(PersonaProveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
