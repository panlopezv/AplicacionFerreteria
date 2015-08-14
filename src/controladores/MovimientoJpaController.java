/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Cuenta;
import entidades.Movimiento;
import entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class MovimientoJpaController implements Serializable {

    public MovimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Movimiento movimiento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta bancoid = movimiento.getBancoid();
            if (bancoid != null) {
                bancoid = em.getReference(bancoid.getClass(), bancoid.getId());
                movimiento.setBancoid(bancoid);
            }
            Usuario usuarioid = movimiento.getUsuarioid();
            if (usuarioid != null) {
                usuarioid = em.getReference(usuarioid.getClass(), usuarioid.getId());
                movimiento.setUsuarioid(usuarioid);
            }
            em.persist(movimiento);
            if (bancoid != null) {
                bancoid.getMovimientoList().add(movimiento);
                bancoid = em.merge(bancoid);
            }
            if (usuarioid != null) {
                usuarioid.getMovimientoList().add(movimiento);
                usuarioid = em.merge(usuarioid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Movimiento movimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Movimiento persistentMovimiento = em.find(Movimiento.class, movimiento.getId());
            Cuenta bancoidOld = persistentMovimiento.getBancoid();
            Cuenta bancoidNew = movimiento.getBancoid();
            Usuario usuarioidOld = persistentMovimiento.getUsuarioid();
            Usuario usuarioidNew = movimiento.getUsuarioid();
            if (bancoidNew != null) {
                bancoidNew = em.getReference(bancoidNew.getClass(), bancoidNew.getId());
                movimiento.setBancoid(bancoidNew);
            }
            if (usuarioidNew != null) {
                usuarioidNew = em.getReference(usuarioidNew.getClass(), usuarioidNew.getId());
                movimiento.setUsuarioid(usuarioidNew);
            }
            movimiento = em.merge(movimiento);
            if (bancoidOld != null && !bancoidOld.equals(bancoidNew)) {
                bancoidOld.getMovimientoList().remove(movimiento);
                bancoidOld = em.merge(bancoidOld);
            }
            if (bancoidNew != null && !bancoidNew.equals(bancoidOld)) {
                bancoidNew.getMovimientoList().add(movimiento);
                bancoidNew = em.merge(bancoidNew);
            }
            if (usuarioidOld != null && !usuarioidOld.equals(usuarioidNew)) {
                usuarioidOld.getMovimientoList().remove(movimiento);
                usuarioidOld = em.merge(usuarioidOld);
            }
            if (usuarioidNew != null && !usuarioidNew.equals(usuarioidOld)) {
                usuarioidNew.getMovimientoList().add(movimiento);
                usuarioidNew = em.merge(usuarioidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = movimiento.getId();
                if (findMovimiento(id) == null) {
                    throw new NonexistentEntityException("The movimiento with id " + id + " no longer exists.");
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
            Movimiento movimiento;
            try {
                movimiento = em.getReference(Movimiento.class, id);
                movimiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The movimiento with id " + id + " no longer exists.", enfe);
            }
            Cuenta bancoid = movimiento.getBancoid();
            if (bancoid != null) {
                bancoid.getMovimientoList().remove(movimiento);
                bancoid = em.merge(bancoid);
            }
            Usuario usuarioid = movimiento.getUsuarioid();
            if (usuarioid != null) {
                usuarioid.getMovimientoList().remove(movimiento);
                usuarioid = em.merge(usuarioid);
            }
            em.remove(movimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Movimiento> findMovimientoEntities() {
        return findMovimientoEntities(true, -1, -1);
    }

    public List<Movimiento> findMovimientoEntities(int maxResults, int firstResult) {
        return findMovimientoEntities(false, maxResults, firstResult);
    }

    private List<Movimiento> findMovimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Movimiento.class));
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

    public Movimiento findMovimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Movimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getMovimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Movimiento> rt = cq.from(Movimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
