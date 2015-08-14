/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import entidades.Permiso;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.PermisoUsuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class PermisoJpaController implements Serializable {

    public PermisoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Permiso permiso) {
        if (permiso.getPermisoUsuarioList() == null) {
            permiso.setPermisoUsuarioList(new ArrayList<PermisoUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PermisoUsuario> attachedPermisoUsuarioList = new ArrayList<PermisoUsuario>();
            for (PermisoUsuario permisoUsuarioListPermisoUsuarioToAttach : permiso.getPermisoUsuarioList()) {
                permisoUsuarioListPermisoUsuarioToAttach = em.getReference(permisoUsuarioListPermisoUsuarioToAttach.getClass(), permisoUsuarioListPermisoUsuarioToAttach.getId());
                attachedPermisoUsuarioList.add(permisoUsuarioListPermisoUsuarioToAttach);
            }
            permiso.setPermisoUsuarioList(attachedPermisoUsuarioList);
            em.persist(permiso);
            for (PermisoUsuario permisoUsuarioListPermisoUsuario : permiso.getPermisoUsuarioList()) {
                Permiso oldPermisoidOfPermisoUsuarioListPermisoUsuario = permisoUsuarioListPermisoUsuario.getPermisoid();
                permisoUsuarioListPermisoUsuario.setPermisoid(permiso);
                permisoUsuarioListPermisoUsuario = em.merge(permisoUsuarioListPermisoUsuario);
                if (oldPermisoidOfPermisoUsuarioListPermisoUsuario != null) {
                    oldPermisoidOfPermisoUsuarioListPermisoUsuario.getPermisoUsuarioList().remove(permisoUsuarioListPermisoUsuario);
                    oldPermisoidOfPermisoUsuarioListPermisoUsuario = em.merge(oldPermisoidOfPermisoUsuarioListPermisoUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Permiso permiso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Permiso persistentPermiso = em.find(Permiso.class, permiso.getId());
            List<PermisoUsuario> permisoUsuarioListOld = persistentPermiso.getPermisoUsuarioList();
            List<PermisoUsuario> permisoUsuarioListNew = permiso.getPermisoUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (PermisoUsuario permisoUsuarioListOldPermisoUsuario : permisoUsuarioListOld) {
                if (!permisoUsuarioListNew.contains(permisoUsuarioListOldPermisoUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PermisoUsuario " + permisoUsuarioListOldPermisoUsuario + " since its permisoid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PermisoUsuario> attachedPermisoUsuarioListNew = new ArrayList<PermisoUsuario>();
            for (PermisoUsuario permisoUsuarioListNewPermisoUsuarioToAttach : permisoUsuarioListNew) {
                permisoUsuarioListNewPermisoUsuarioToAttach = em.getReference(permisoUsuarioListNewPermisoUsuarioToAttach.getClass(), permisoUsuarioListNewPermisoUsuarioToAttach.getId());
                attachedPermisoUsuarioListNew.add(permisoUsuarioListNewPermisoUsuarioToAttach);
            }
            permisoUsuarioListNew = attachedPermisoUsuarioListNew;
            permiso.setPermisoUsuarioList(permisoUsuarioListNew);
            permiso = em.merge(permiso);
            for (PermisoUsuario permisoUsuarioListNewPermisoUsuario : permisoUsuarioListNew) {
                if (!permisoUsuarioListOld.contains(permisoUsuarioListNewPermisoUsuario)) {
                    Permiso oldPermisoidOfPermisoUsuarioListNewPermisoUsuario = permisoUsuarioListNewPermisoUsuario.getPermisoid();
                    permisoUsuarioListNewPermisoUsuario.setPermisoid(permiso);
                    permisoUsuarioListNewPermisoUsuario = em.merge(permisoUsuarioListNewPermisoUsuario);
                    if (oldPermisoidOfPermisoUsuarioListNewPermisoUsuario != null && !oldPermisoidOfPermisoUsuarioListNewPermisoUsuario.equals(permiso)) {
                        oldPermisoidOfPermisoUsuarioListNewPermisoUsuario.getPermisoUsuarioList().remove(permisoUsuarioListNewPermisoUsuario);
                        oldPermisoidOfPermisoUsuarioListNewPermisoUsuario = em.merge(oldPermisoidOfPermisoUsuarioListNewPermisoUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = permiso.getId();
                if (findPermiso(id) == null) {
                    throw new NonexistentEntityException("The permiso with id " + id + " no longer exists.");
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
            Permiso permiso;
            try {
                permiso = em.getReference(Permiso.class, id);
                permiso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permiso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PermisoUsuario> permisoUsuarioListOrphanCheck = permiso.getPermisoUsuarioList();
            for (PermisoUsuario permisoUsuarioListOrphanCheckPermisoUsuario : permisoUsuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Permiso (" + permiso + ") cannot be destroyed since the PermisoUsuario " + permisoUsuarioListOrphanCheckPermisoUsuario + " in its permisoUsuarioList field has a non-nullable permisoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(permiso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Permiso> findPermisoEntities() {
        return findPermisoEntities(true, -1, -1);
    }

    public List<Permiso> findPermisoEntities(int maxResults, int firstResult) {
        return findPermisoEntities(false, maxResults, firstResult);
    }

    private List<Permiso> findPermisoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permiso.class));
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

    public Permiso findPermiso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permiso.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermisoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permiso> rt = cq.from(Permiso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
