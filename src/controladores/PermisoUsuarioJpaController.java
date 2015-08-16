/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Permiso;
import entidades.PermisoUsuario;
import entidades.TipoUsuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class PermisoUsuarioJpaController implements Serializable {

    public PermisoUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PermisoUsuario permisoUsuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Permiso permisoid = permisoUsuario.getPermisoid();
            if (permisoid != null) {
                permisoid = em.getReference(permisoid.getClass(), permisoid.getId());
                permisoUsuario.setPermisoid(permisoid);
            }
            TipoUsuario tipoUsuarioid = permisoUsuario.getTipoUsuarioid();
            if (tipoUsuarioid != null) {
                tipoUsuarioid = em.getReference(tipoUsuarioid.getClass(), tipoUsuarioid.getId());
                permisoUsuario.setTipoUsuarioid(tipoUsuarioid);
            }
            em.persist(permisoUsuario);
            if (permisoid != null) {
                permisoid.getPermisoUsuarioList().add(permisoUsuario);
                permisoid = em.merge(permisoid);
            }
            if (tipoUsuarioid != null) {
                tipoUsuarioid.getPermisoUsuarioList().add(permisoUsuario);
                tipoUsuarioid = em.merge(tipoUsuarioid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PermisoUsuario permisoUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PermisoUsuario persistentPermisoUsuario = em.find(PermisoUsuario.class, permisoUsuario.getId());
            Permiso permisoidOld = persistentPermisoUsuario.getPermisoid();
            Permiso permisoidNew = permisoUsuario.getPermisoid();
            TipoUsuario tipoUsuarioidOld = persistentPermisoUsuario.getTipoUsuarioid();
            TipoUsuario tipoUsuarioidNew = permisoUsuario.getTipoUsuarioid();
            if (permisoidNew != null) {
                permisoidNew = em.getReference(permisoidNew.getClass(), permisoidNew.getId());
                permisoUsuario.setPermisoid(permisoidNew);
            }
            if (tipoUsuarioidNew != null) {
                tipoUsuarioidNew = em.getReference(tipoUsuarioidNew.getClass(), tipoUsuarioidNew.getId());
                permisoUsuario.setTipoUsuarioid(tipoUsuarioidNew);
            }
            permisoUsuario = em.merge(permisoUsuario);
            if (permisoidOld != null && !permisoidOld.equals(permisoidNew)) {
                permisoidOld.getPermisoUsuarioList().remove(permisoUsuario);
                permisoidOld = em.merge(permisoidOld);
            }
            if (permisoidNew != null && !permisoidNew.equals(permisoidOld)) {
                permisoidNew.getPermisoUsuarioList().add(permisoUsuario);
                permisoidNew = em.merge(permisoidNew);
            }
            if (tipoUsuarioidOld != null && !tipoUsuarioidOld.equals(tipoUsuarioidNew)) {
                tipoUsuarioidOld.getPermisoUsuarioList().remove(permisoUsuario);
                tipoUsuarioidOld = em.merge(tipoUsuarioidOld);
            }
            if (tipoUsuarioidNew != null && !tipoUsuarioidNew.equals(tipoUsuarioidOld)) {
                tipoUsuarioidNew.getPermisoUsuarioList().add(permisoUsuario);
                tipoUsuarioidNew = em.merge(tipoUsuarioidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = permisoUsuario.getId();
                if (findPermisoUsuario(id) == null) {
                    throw new NonexistentEntityException("The permisoUsuario with id " + id + " no longer exists.");
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
            PermisoUsuario permisoUsuario;
            try {
                permisoUsuario = em.getReference(PermisoUsuario.class, id);
                permisoUsuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permisoUsuario with id " + id + " no longer exists.", enfe);
            }
            Permiso permisoid = permisoUsuario.getPermisoid();
            if (permisoid != null) {
                permisoid.getPermisoUsuarioList().remove(permisoUsuario);
                permisoid = em.merge(permisoid);
            }
            TipoUsuario tipoUsuarioid = permisoUsuario.getTipoUsuarioid();
            if (tipoUsuarioid != null) {
                tipoUsuarioid.getPermisoUsuarioList().remove(permisoUsuario);
                tipoUsuarioid = em.merge(tipoUsuarioid);
            }
            em.remove(permisoUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PermisoUsuario> findPermisoUsuarioEntities() {
        return findPermisoUsuarioEntities(true, -1, -1);
    }

    public List<PermisoUsuario> findPermisoUsuarioEntities(int maxResults, int firstResult) {
        return findPermisoUsuarioEntities(false, maxResults, firstResult);
    }

    private List<PermisoUsuario> findPermisoUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PermisoUsuario.class));
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

    public PermisoUsuario findPermisoUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PermisoUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermisoUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PermisoUsuario> rt = cq.from(PermisoUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
