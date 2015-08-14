/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.PersonaProveedor;
import entidades.Proveedor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class ProveedorJpaController implements Serializable {

    public ProveedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proveedor proveedor) {
        if (proveedor.getPersonaProveedorList() == null) {
            proveedor.setPersonaProveedorList(new ArrayList<PersonaProveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PersonaProveedor> attachedPersonaProveedorList = new ArrayList<PersonaProveedor>();
            for (PersonaProveedor personaProveedorListPersonaProveedorToAttach : proveedor.getPersonaProveedorList()) {
                personaProveedorListPersonaProveedorToAttach = em.getReference(personaProveedorListPersonaProveedorToAttach.getClass(), personaProveedorListPersonaProveedorToAttach.getId());
                attachedPersonaProveedorList.add(personaProveedorListPersonaProveedorToAttach);
            }
            proveedor.setPersonaProveedorList(attachedPersonaProveedorList);
            em.persist(proveedor);
            for (PersonaProveedor personaProveedorListPersonaProveedor : proveedor.getPersonaProveedorList()) {
                Proveedor oldProveedoridOfPersonaProveedorListPersonaProveedor = personaProveedorListPersonaProveedor.getProveedorid();
                personaProveedorListPersonaProveedor.setProveedorid(proveedor);
                personaProveedorListPersonaProveedor = em.merge(personaProveedorListPersonaProveedor);
                if (oldProveedoridOfPersonaProveedorListPersonaProveedor != null) {
                    oldProveedoridOfPersonaProveedorListPersonaProveedor.getPersonaProveedorList().remove(personaProveedorListPersonaProveedor);
                    oldProveedoridOfPersonaProveedorListPersonaProveedor = em.merge(oldProveedoridOfPersonaProveedorListPersonaProveedor);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proveedor proveedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor persistentProveedor = em.find(Proveedor.class, proveedor.getId());
            List<PersonaProveedor> personaProveedorListOld = persistentProveedor.getPersonaProveedorList();
            List<PersonaProveedor> personaProveedorListNew = proveedor.getPersonaProveedorList();
            List<String> illegalOrphanMessages = null;
            for (PersonaProveedor personaProveedorListOldPersonaProveedor : personaProveedorListOld) {
                if (!personaProveedorListNew.contains(personaProveedorListOldPersonaProveedor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PersonaProveedor " + personaProveedorListOldPersonaProveedor + " since its proveedorid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PersonaProveedor> attachedPersonaProveedorListNew = new ArrayList<PersonaProveedor>();
            for (PersonaProveedor personaProveedorListNewPersonaProveedorToAttach : personaProveedorListNew) {
                personaProveedorListNewPersonaProveedorToAttach = em.getReference(personaProveedorListNewPersonaProveedorToAttach.getClass(), personaProveedorListNewPersonaProveedorToAttach.getId());
                attachedPersonaProveedorListNew.add(personaProveedorListNewPersonaProveedorToAttach);
            }
            personaProveedorListNew = attachedPersonaProveedorListNew;
            proveedor.setPersonaProveedorList(personaProveedorListNew);
            proveedor = em.merge(proveedor);
            for (PersonaProveedor personaProveedorListNewPersonaProveedor : personaProveedorListNew) {
                if (!personaProveedorListOld.contains(personaProveedorListNewPersonaProveedor)) {
                    Proveedor oldProveedoridOfPersonaProveedorListNewPersonaProveedor = personaProveedorListNewPersonaProveedor.getProveedorid();
                    personaProveedorListNewPersonaProveedor.setProveedorid(proveedor);
                    personaProveedorListNewPersonaProveedor = em.merge(personaProveedorListNewPersonaProveedor);
                    if (oldProveedoridOfPersonaProveedorListNewPersonaProveedor != null && !oldProveedoridOfPersonaProveedorListNewPersonaProveedor.equals(proveedor)) {
                        oldProveedoridOfPersonaProveedorListNewPersonaProveedor.getPersonaProveedorList().remove(personaProveedorListNewPersonaProveedor);
                        oldProveedoridOfPersonaProveedorListNewPersonaProveedor = em.merge(oldProveedoridOfPersonaProveedorListNewPersonaProveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proveedor.getId();
                if (findProveedor(id) == null) {
                    throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proveedor proveedor;
            try {
                proveedor = em.getReference(Proveedor.class, id);
                proveedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proveedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PersonaProveedor> personaProveedorListOrphanCheck = proveedor.getPersonaProveedorList();
            for (PersonaProveedor personaProveedorListOrphanCheckPersonaProveedor : personaProveedorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Proveedor (" + proveedor + ") cannot be destroyed since the PersonaProveedor " + personaProveedorListOrphanCheckPersonaProveedor + " in its personaProveedorList field has a non-nullable proveedorid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(proveedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proveedor> findProveedorEntities() {
        return findProveedorEntities(true, -1, -1);
    }

    public List<Proveedor> findProveedorEntities(int maxResults, int firstResult) {
        return findProveedorEntities(false, maxResults, firstResult);
    }

    private List<Proveedor> findProveedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proveedor.class));
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

    public Proveedor findProveedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proveedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getProveedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proveedor> rt = cq.from(Proveedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
