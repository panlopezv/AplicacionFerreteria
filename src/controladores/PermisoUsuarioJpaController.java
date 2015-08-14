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
import entidades.TipoPersona;
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
            TipoPersona tipoPersonaid = permisoUsuario.getTipoPersonaid();
            if (tipoPersonaid != null) {
                tipoPersonaid = em.getReference(tipoPersonaid.getClass(), tipoPersonaid.getId());
                permisoUsuario.setTipoPersonaid(tipoPersonaid);
            }
            em.persist(permisoUsuario);
            if (permisoid != null) {
                permisoid.getPermisoUsuarioList().add(permisoUsuario);
                permisoid = em.merge(permisoid);
            }
            if (tipoPersonaid != null) {
                tipoPersonaid.getPermisoUsuarioList().add(permisoUsuario);
                tipoPersonaid = em.merge(tipoPersonaid);
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
            TipoPersona tipoPersonaidOld = persistentPermisoUsuario.getTipoPersonaid();
            TipoPersona tipoPersonaidNew = permisoUsuario.getTipoPersonaid();
            if (permisoidNew != null) {
                permisoidNew = em.getReference(permisoidNew.getClass(), permisoidNew.getId());
                permisoUsuario.setPermisoid(permisoidNew);
            }
            if (tipoPersonaidNew != null) {
                tipoPersonaidNew = em.getReference(tipoPersonaidNew.getClass(), tipoPersonaidNew.getId());
                permisoUsuario.setTipoPersonaid(tipoPersonaidNew);
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
            if (tipoPersonaidOld != null && !tipoPersonaidOld.equals(tipoPersonaidNew)) {
                tipoPersonaidOld.getPermisoUsuarioList().remove(permisoUsuario);
                tipoPersonaidOld = em.merge(tipoPersonaidOld);
            }
            if (tipoPersonaidNew != null && !tipoPersonaidNew.equals(tipoPersonaidOld)) {
                tipoPersonaidNew.getPermisoUsuarioList().add(permisoUsuario);
                tipoPersonaidNew = em.merge(tipoPersonaidNew);
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
            TipoPersona tipoPersonaid = permisoUsuario.getTipoPersonaid();
            if (tipoPersonaid != null) {
                tipoPersonaid.getPermisoUsuarioList().remove(permisoUsuario);
                tipoPersonaid = em.merge(tipoPersonaid);
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
