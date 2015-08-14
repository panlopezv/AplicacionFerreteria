/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import entidades.DetalleVenta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Venta;
import entidades.ProductoSucursal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class DetalleVentaJpaController implements Serializable {

    public DetalleVentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleVenta detalleVenta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta ventaid = detalleVenta.getVentaid();
            if (ventaid != null) {
                ventaid = em.getReference(ventaid.getClass(), ventaid.getId());
                detalleVenta.setVentaid(ventaid);
            }
            ProductoSucursal productoSucursalid = detalleVenta.getProductoSucursalid();
            if (productoSucursalid != null) {
                productoSucursalid = em.getReference(productoSucursalid.getClass(), productoSucursalid.getId());
                detalleVenta.setProductoSucursalid(productoSucursalid);
            }
            em.persist(detalleVenta);
            if (ventaid != null) {
                ventaid.getDetalleVentaList().add(detalleVenta);
                ventaid = em.merge(ventaid);
            }
            if (productoSucursalid != null) {
                productoSucursalid.getDetalleVentaList().add(detalleVenta);
                productoSucursalid = em.merge(productoSucursalid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleVenta detalleVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleVenta persistentDetalleVenta = em.find(DetalleVenta.class, detalleVenta.getId());
            Venta ventaidOld = persistentDetalleVenta.getVentaid();
            Venta ventaidNew = detalleVenta.getVentaid();
            ProductoSucursal productoSucursalidOld = persistentDetalleVenta.getProductoSucursalid();
            ProductoSucursal productoSucursalidNew = detalleVenta.getProductoSucursalid();
            if (ventaidNew != null) {
                ventaidNew = em.getReference(ventaidNew.getClass(), ventaidNew.getId());
                detalleVenta.setVentaid(ventaidNew);
            }
            if (productoSucursalidNew != null) {
                productoSucursalidNew = em.getReference(productoSucursalidNew.getClass(), productoSucursalidNew.getId());
                detalleVenta.setProductoSucursalid(productoSucursalidNew);
            }
            detalleVenta = em.merge(detalleVenta);
            if (ventaidOld != null && !ventaidOld.equals(ventaidNew)) {
                ventaidOld.getDetalleVentaList().remove(detalleVenta);
                ventaidOld = em.merge(ventaidOld);
            }
            if (ventaidNew != null && !ventaidNew.equals(ventaidOld)) {
                ventaidNew.getDetalleVentaList().add(detalleVenta);
                ventaidNew = em.merge(ventaidNew);
            }
            if (productoSucursalidOld != null && !productoSucursalidOld.equals(productoSucursalidNew)) {
                productoSucursalidOld.getDetalleVentaList().remove(detalleVenta);
                productoSucursalidOld = em.merge(productoSucursalidOld);
            }
            if (productoSucursalidNew != null && !productoSucursalidNew.equals(productoSucursalidOld)) {
                productoSucursalidNew.getDetalleVentaList().add(detalleVenta);
                productoSucursalidNew = em.merge(productoSucursalidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleVenta.getId();
                if (findDetalleVenta(id) == null) {
                    throw new NonexistentEntityException("The detalleVenta with id " + id + " no longer exists.");
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
            DetalleVenta detalleVenta;
            try {
                detalleVenta = em.getReference(DetalleVenta.class, id);
                detalleVenta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleVenta with id " + id + " no longer exists.", enfe);
            }
            Venta ventaid = detalleVenta.getVentaid();
            if (ventaid != null) {
                ventaid.getDetalleVentaList().remove(detalleVenta);
                ventaid = em.merge(ventaid);
            }
            ProductoSucursal productoSucursalid = detalleVenta.getProductoSucursalid();
            if (productoSucursalid != null) {
                productoSucursalid.getDetalleVentaList().remove(detalleVenta);
                productoSucursalid = em.merge(productoSucursalid);
            }
            em.remove(detalleVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleVenta> findDetalleVentaEntities() {
        return findDetalleVentaEntities(true, -1, -1);
    }

    public List<DetalleVenta> findDetalleVentaEntities(int maxResults, int firstResult) {
        return findDetalleVentaEntities(false, maxResults, firstResult);
    }

    private List<DetalleVenta> findDetalleVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleVenta.class));
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

    public DetalleVenta findDetalleVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleVenta> rt = cq.from(DetalleVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
