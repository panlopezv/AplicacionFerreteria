/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import entidades.Abono;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Compra;
import entidades.ModoPago;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class AbonoJpaController implements Serializable {

    public AbonoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Abono abono) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra compraid = abono.getCompraid();
            if (compraid != null) {
                compraid = em.getReference(compraid.getClass(), compraid.getId());
                abono.setCompraid(compraid);
            }
            ModoPago modoPagoid = abono.getModoPagoid();
            if (modoPagoid != null) {
                modoPagoid = em.getReference(modoPagoid.getClass(), modoPagoid.getId());
                abono.setModoPagoid(modoPagoid);
            }
            em.persist(abono);
            if (compraid != null) {
                compraid.getAbonoList().add(abono);
                compraid = em.merge(compraid);
            }
            if (modoPagoid != null) {
                modoPagoid.getAbonoList().add(abono);
                modoPagoid = em.merge(modoPagoid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Abono abono) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Abono persistentAbono = em.find(Abono.class, abono.getId());
            Compra compraidOld = persistentAbono.getCompraid();
            Compra compraidNew = abono.getCompraid();
            ModoPago modoPagoidOld = persistentAbono.getModoPagoid();
            ModoPago modoPagoidNew = abono.getModoPagoid();
            if (compraidNew != null) {
                compraidNew = em.getReference(compraidNew.getClass(), compraidNew.getId());
                abono.setCompraid(compraidNew);
            }
            if (modoPagoidNew != null) {
                modoPagoidNew = em.getReference(modoPagoidNew.getClass(), modoPagoidNew.getId());
                abono.setModoPagoid(modoPagoidNew);
            }
            abono = em.merge(abono);
            if (compraidOld != null && !compraidOld.equals(compraidNew)) {
                compraidOld.getAbonoList().remove(abono);
                compraidOld = em.merge(compraidOld);
            }
            if (compraidNew != null && !compraidNew.equals(compraidOld)) {
                compraidNew.getAbonoList().add(abono);
                compraidNew = em.merge(compraidNew);
            }
            if (modoPagoidOld != null && !modoPagoidOld.equals(modoPagoidNew)) {
                modoPagoidOld.getAbonoList().remove(abono);
                modoPagoidOld = em.merge(modoPagoidOld);
            }
            if (modoPagoidNew != null && !modoPagoidNew.equals(modoPagoidOld)) {
                modoPagoidNew.getAbonoList().add(abono);
                modoPagoidNew = em.merge(modoPagoidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = abono.getId();
                if (findAbono(id) == null) {
                    throw new NonexistentEntityException("The abono with id " + id + " no longer exists.");
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
            Abono abono;
            try {
                abono = em.getReference(Abono.class, id);
                abono.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The abono with id " + id + " no longer exists.", enfe);
            }
            Compra compraid = abono.getCompraid();
            if (compraid != null) {
                compraid.getAbonoList().remove(abono);
                compraid = em.merge(compraid);
            }
            ModoPago modoPagoid = abono.getModoPagoid();
            if (modoPagoid != null) {
                modoPagoid.getAbonoList().remove(abono);
                modoPagoid = em.merge(modoPagoid);
            }
            em.remove(abono);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Abono> findAbonoEntities() {
        return findAbonoEntities(true, -1, -1);
    }

    public List<Abono> findAbonoEntities(int maxResults, int firstResult) {
        return findAbonoEntities(false, maxResults, firstResult);
    }

    private List<Abono> findAbonoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Abono.class));
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

    public Abono findAbono(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Abono.class, id);
        } finally {
            em.close();
        }
    }

    public int getAbonoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Abono> rt = cq.from(Abono.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
