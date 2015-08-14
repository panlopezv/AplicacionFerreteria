/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Sucursal;
import entidades.ProductoPresentacion;
import entidades.Lote;
import java.util.ArrayList;
import java.util.List;
import entidades.DetalleVenta;
import entidades.ProductoSucursal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class ProductoSucursalJpaController implements Serializable {

    public ProductoSucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductoSucursal productoSucursal) {
        if (productoSucursal.getLoteList() == null) {
            productoSucursal.setLoteList(new ArrayList<Lote>());
        }
        if (productoSucursal.getDetalleVentaList() == null) {
            productoSucursal.setDetalleVentaList(new ArrayList<DetalleVenta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal sucursalid = productoSucursal.getSucursalid();
            if (sucursalid != null) {
                sucursalid = em.getReference(sucursalid.getClass(), sucursalid.getId());
                productoSucursal.setSucursalid(sucursalid);
            }
            ProductoPresentacion productoPresentacionid = productoSucursal.getProductoPresentacionid();
            if (productoPresentacionid != null) {
                productoPresentacionid = em.getReference(productoPresentacionid.getClass(), productoPresentacionid.getId());
                productoSucursal.setProductoPresentacionid(productoPresentacionid);
            }
            List<Lote> attachedLoteList = new ArrayList<Lote>();
            for (Lote loteListLoteToAttach : productoSucursal.getLoteList()) {
                loteListLoteToAttach = em.getReference(loteListLoteToAttach.getClass(), loteListLoteToAttach.getId());
                attachedLoteList.add(loteListLoteToAttach);
            }
            productoSucursal.setLoteList(attachedLoteList);
            List<DetalleVenta> attachedDetalleVentaList = new ArrayList<DetalleVenta>();
            for (DetalleVenta detalleVentaListDetalleVentaToAttach : productoSucursal.getDetalleVentaList()) {
                detalleVentaListDetalleVentaToAttach = em.getReference(detalleVentaListDetalleVentaToAttach.getClass(), detalleVentaListDetalleVentaToAttach.getId());
                attachedDetalleVentaList.add(detalleVentaListDetalleVentaToAttach);
            }
            productoSucursal.setDetalleVentaList(attachedDetalleVentaList);
            em.persist(productoSucursal);
            if (sucursalid != null) {
                sucursalid.getProductoSucursalList().add(productoSucursal);
                sucursalid = em.merge(sucursalid);
            }
            if (productoPresentacionid != null) {
                productoPresentacionid.getProductoSucursalList().add(productoSucursal);
                productoPresentacionid = em.merge(productoPresentacionid);
            }
            for (Lote loteListLote : productoSucursal.getLoteList()) {
                ProductoSucursal oldProductoSucursalidOfLoteListLote = loteListLote.getProductoSucursalid();
                loteListLote.setProductoSucursalid(productoSucursal);
                loteListLote = em.merge(loteListLote);
                if (oldProductoSucursalidOfLoteListLote != null) {
                    oldProductoSucursalidOfLoteListLote.getLoteList().remove(loteListLote);
                    oldProductoSucursalidOfLoteListLote = em.merge(oldProductoSucursalidOfLoteListLote);
                }
            }
            for (DetalleVenta detalleVentaListDetalleVenta : productoSucursal.getDetalleVentaList()) {
                ProductoSucursal oldProductoSucursalidOfDetalleVentaListDetalleVenta = detalleVentaListDetalleVenta.getProductoSucursalid();
                detalleVentaListDetalleVenta.setProductoSucursalid(productoSucursal);
                detalleVentaListDetalleVenta = em.merge(detalleVentaListDetalleVenta);
                if (oldProductoSucursalidOfDetalleVentaListDetalleVenta != null) {
                    oldProductoSucursalidOfDetalleVentaListDetalleVenta.getDetalleVentaList().remove(detalleVentaListDetalleVenta);
                    oldProductoSucursalidOfDetalleVentaListDetalleVenta = em.merge(oldProductoSucursalidOfDetalleVentaListDetalleVenta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductoSucursal productoSucursal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoSucursal persistentProductoSucursal = em.find(ProductoSucursal.class, productoSucursal.getId());
            Sucursal sucursalidOld = persistentProductoSucursal.getSucursalid();
            Sucursal sucursalidNew = productoSucursal.getSucursalid();
            ProductoPresentacion productoPresentacionidOld = persistentProductoSucursal.getProductoPresentacionid();
            ProductoPresentacion productoPresentacionidNew = productoSucursal.getProductoPresentacionid();
            List<Lote> loteListOld = persistentProductoSucursal.getLoteList();
            List<Lote> loteListNew = productoSucursal.getLoteList();
            List<DetalleVenta> detalleVentaListOld = persistentProductoSucursal.getDetalleVentaList();
            List<DetalleVenta> detalleVentaListNew = productoSucursal.getDetalleVentaList();
            List<String> illegalOrphanMessages = null;
            for (Lote loteListOldLote : loteListOld) {
                if (!loteListNew.contains(loteListOldLote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Lote " + loteListOldLote + " since its productoSucursalid field is not nullable.");
                }
            }
            for (DetalleVenta detalleVentaListOldDetalleVenta : detalleVentaListOld) {
                if (!detalleVentaListNew.contains(detalleVentaListOldDetalleVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleVenta " + detalleVentaListOldDetalleVenta + " since its productoSucursalid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sucursalidNew != null) {
                sucursalidNew = em.getReference(sucursalidNew.getClass(), sucursalidNew.getId());
                productoSucursal.setSucursalid(sucursalidNew);
            }
            if (productoPresentacionidNew != null) {
                productoPresentacionidNew = em.getReference(productoPresentacionidNew.getClass(), productoPresentacionidNew.getId());
                productoSucursal.setProductoPresentacionid(productoPresentacionidNew);
            }
            List<Lote> attachedLoteListNew = new ArrayList<Lote>();
            for (Lote loteListNewLoteToAttach : loteListNew) {
                loteListNewLoteToAttach = em.getReference(loteListNewLoteToAttach.getClass(), loteListNewLoteToAttach.getId());
                attachedLoteListNew.add(loteListNewLoteToAttach);
            }
            loteListNew = attachedLoteListNew;
            productoSucursal.setLoteList(loteListNew);
            List<DetalleVenta> attachedDetalleVentaListNew = new ArrayList<DetalleVenta>();
            for (DetalleVenta detalleVentaListNewDetalleVentaToAttach : detalleVentaListNew) {
                detalleVentaListNewDetalleVentaToAttach = em.getReference(detalleVentaListNewDetalleVentaToAttach.getClass(), detalleVentaListNewDetalleVentaToAttach.getId());
                attachedDetalleVentaListNew.add(detalleVentaListNewDetalleVentaToAttach);
            }
            detalleVentaListNew = attachedDetalleVentaListNew;
            productoSucursal.setDetalleVentaList(detalleVentaListNew);
            productoSucursal = em.merge(productoSucursal);
            if (sucursalidOld != null && !sucursalidOld.equals(sucursalidNew)) {
                sucursalidOld.getProductoSucursalList().remove(productoSucursal);
                sucursalidOld = em.merge(sucursalidOld);
            }
            if (sucursalidNew != null && !sucursalidNew.equals(sucursalidOld)) {
                sucursalidNew.getProductoSucursalList().add(productoSucursal);
                sucursalidNew = em.merge(sucursalidNew);
            }
            if (productoPresentacionidOld != null && !productoPresentacionidOld.equals(productoPresentacionidNew)) {
                productoPresentacionidOld.getProductoSucursalList().remove(productoSucursal);
                productoPresentacionidOld = em.merge(productoPresentacionidOld);
            }
            if (productoPresentacionidNew != null && !productoPresentacionidNew.equals(productoPresentacionidOld)) {
                productoPresentacionidNew.getProductoSucursalList().add(productoSucursal);
                productoPresentacionidNew = em.merge(productoPresentacionidNew);
            }
            for (Lote loteListNewLote : loteListNew) {
                if (!loteListOld.contains(loteListNewLote)) {
                    ProductoSucursal oldProductoSucursalidOfLoteListNewLote = loteListNewLote.getProductoSucursalid();
                    loteListNewLote.setProductoSucursalid(productoSucursal);
                    loteListNewLote = em.merge(loteListNewLote);
                    if (oldProductoSucursalidOfLoteListNewLote != null && !oldProductoSucursalidOfLoteListNewLote.equals(productoSucursal)) {
                        oldProductoSucursalidOfLoteListNewLote.getLoteList().remove(loteListNewLote);
                        oldProductoSucursalidOfLoteListNewLote = em.merge(oldProductoSucursalidOfLoteListNewLote);
                    }
                }
            }
            for (DetalleVenta detalleVentaListNewDetalleVenta : detalleVentaListNew) {
                if (!detalleVentaListOld.contains(detalleVentaListNewDetalleVenta)) {
                    ProductoSucursal oldProductoSucursalidOfDetalleVentaListNewDetalleVenta = detalleVentaListNewDetalleVenta.getProductoSucursalid();
                    detalleVentaListNewDetalleVenta.setProductoSucursalid(productoSucursal);
                    detalleVentaListNewDetalleVenta = em.merge(detalleVentaListNewDetalleVenta);
                    if (oldProductoSucursalidOfDetalleVentaListNewDetalleVenta != null && !oldProductoSucursalidOfDetalleVentaListNewDetalleVenta.equals(productoSucursal)) {
                        oldProductoSucursalidOfDetalleVentaListNewDetalleVenta.getDetalleVentaList().remove(detalleVentaListNewDetalleVenta);
                        oldProductoSucursalidOfDetalleVentaListNewDetalleVenta = em.merge(oldProductoSucursalidOfDetalleVentaListNewDetalleVenta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productoSucursal.getId();
                if (findProductoSucursal(id) == null) {
                    throw new NonexistentEntityException("The productoSucursal with id " + id + " no longer exists.");
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
            ProductoSucursal productoSucursal;
            try {
                productoSucursal = em.getReference(ProductoSucursal.class, id);
                productoSucursal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoSucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Lote> loteListOrphanCheck = productoSucursal.getLoteList();
            for (Lote loteListOrphanCheckLote : loteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProductoSucursal (" + productoSucursal + ") cannot be destroyed since the Lote " + loteListOrphanCheckLote + " in its loteList field has a non-nullable productoSucursalid field.");
            }
            List<DetalleVenta> detalleVentaListOrphanCheck = productoSucursal.getDetalleVentaList();
            for (DetalleVenta detalleVentaListOrphanCheckDetalleVenta : detalleVentaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProductoSucursal (" + productoSucursal + ") cannot be destroyed since the DetalleVenta " + detalleVentaListOrphanCheckDetalleVenta + " in its detalleVentaList field has a non-nullable productoSucursalid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Sucursal sucursalid = productoSucursal.getSucursalid();
            if (sucursalid != null) {
                sucursalid.getProductoSucursalList().remove(productoSucursal);
                sucursalid = em.merge(sucursalid);
            }
            ProductoPresentacion productoPresentacionid = productoSucursal.getProductoPresentacionid();
            if (productoPresentacionid != null) {
                productoPresentacionid.getProductoSucursalList().remove(productoSucursal);
                productoPresentacionid = em.merge(productoPresentacionid);
            }
            em.remove(productoSucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductoSucursal> findProductoSucursalEntities() {
        return findProductoSucursalEntities(true, -1, -1);
    }

    public List<ProductoSucursal> findProductoSucursalEntities(int maxResults, int firstResult) {
        return findProductoSucursalEntities(false, maxResults, firstResult);
    }

    private List<ProductoSucursal> findProductoSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductoSucursal.class));
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

    public ProductoSucursal findProductoSucursal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoSucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductoSucursal> rt = cq.from(ProductoSucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
