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
import entidades.Compra;
import entidades.DetalleCompra;
import entidades.Lote;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class DetalleCompraJpaController implements Serializable {

    public DetalleCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleCompra detalleCompra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra compraid = detalleCompra.getCompraid();
            if (compraid != null) {
                compraid = em.getReference(compraid.getClass(), compraid.getId());
                detalleCompra.setCompraid(compraid);
            }
            Lote loteid = detalleCompra.getLoteid();
            if (loteid != null) {
                loteid = em.getReference(loteid.getClass(), loteid.getId());
                detalleCompra.setLoteid(loteid);
            }
            em.persist(detalleCompra);
            if (compraid != null) {
                compraid.getDetalleCompraList().add(detalleCompra);
                compraid = em.merge(compraid);
            }
            if (loteid != null) {
                loteid.getDetalleCompraList().add(detalleCompra);
                loteid = em.merge(loteid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleCompra detalleCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleCompra persistentDetalleCompra = em.find(DetalleCompra.class, detalleCompra.getId());
            Compra compraidOld = persistentDetalleCompra.getCompraid();
            Compra compraidNew = detalleCompra.getCompraid();
            Lote loteidOld = persistentDetalleCompra.getLoteid();
            Lote loteidNew = detalleCompra.getLoteid();
            if (compraidNew != null) {
                compraidNew = em.getReference(compraidNew.getClass(), compraidNew.getId());
                detalleCompra.setCompraid(compraidNew);
            }
            if (loteidNew != null) {
                loteidNew = em.getReference(loteidNew.getClass(), loteidNew.getId());
                detalleCompra.setLoteid(loteidNew);
            }
            detalleCompra = em.merge(detalleCompra);
            if (compraidOld != null && !compraidOld.equals(compraidNew)) {
                compraidOld.getDetalleCompraList().remove(detalleCompra);
                compraidOld = em.merge(compraidOld);
            }
            if (compraidNew != null && !compraidNew.equals(compraidOld)) {
                compraidNew.getDetalleCompraList().add(detalleCompra);
                compraidNew = em.merge(compraidNew);
            }
            if (loteidOld != null && !loteidOld.equals(loteidNew)) {
                loteidOld.getDetalleCompraList().remove(detalleCompra);
                loteidOld = em.merge(loteidOld);
            }
            if (loteidNew != null && !loteidNew.equals(loteidOld)) {
                loteidNew.getDetalleCompraList().add(detalleCompra);
                loteidNew = em.merge(loteidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleCompra.getId();
                if (findDetalleCompra(id) == null) {
                    throw new NonexistentEntityException("The detalleCompra with id " + id + " no longer exists.");
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
            DetalleCompra detalleCompra;
            try {
                detalleCompra = em.getReference(DetalleCompra.class, id);
                detalleCompra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleCompra with id " + id + " no longer exists.", enfe);
            }
            Compra compraid = detalleCompra.getCompraid();
            if (compraid != null) {
                compraid.getDetalleCompraList().remove(detalleCompra);
                compraid = em.merge(compraid);
            }
            Lote loteid = detalleCompra.getLoteid();
            if (loteid != null) {
                loteid.getDetalleCompraList().remove(detalleCompra);
                loteid = em.merge(loteid);
            }
            em.remove(detalleCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleCompra> findDetalleCompraEntities() {
        return findDetalleCompraEntities(true, -1, -1);
    }

    public List<DetalleCompra> findDetalleCompraEntities(int maxResults, int firstResult) {
        return findDetalleCompraEntities(false, maxResults, firstResult);
    }

    private List<DetalleCompra> findDetalleCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleCompra.class));
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

    public DetalleCompra findDetalleCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleCompra> rt = cq.from(DetalleCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
