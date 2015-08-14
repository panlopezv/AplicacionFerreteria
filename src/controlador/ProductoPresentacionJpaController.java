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
import entidades.Presentacion;
import entidades.Producto;
import entidades.ProductoPresentacion;
import entidades.ProductoSucursal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class ProductoPresentacionJpaController implements Serializable {

    public ProductoPresentacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductoPresentacion productoPresentacion) {
        if (productoPresentacion.getProductoSucursalList() == null) {
            productoPresentacion.setProductoSucursalList(new ArrayList<ProductoSucursal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presentacion presentacionid = productoPresentacion.getPresentacionid();
            if (presentacionid != null) {
                presentacionid = em.getReference(presentacionid.getClass(), presentacionid.getId());
                productoPresentacion.setPresentacionid(presentacionid);
            }
            Producto productoid = productoPresentacion.getProductoid();
            if (productoid != null) {
                productoid = em.getReference(productoid.getClass(), productoid.getId());
                productoPresentacion.setProductoid(productoid);
            }
            List<ProductoSucursal> attachedProductoSucursalList = new ArrayList<ProductoSucursal>();
            for (ProductoSucursal productoSucursalListProductoSucursalToAttach : productoPresentacion.getProductoSucursalList()) {
                productoSucursalListProductoSucursalToAttach = em.getReference(productoSucursalListProductoSucursalToAttach.getClass(), productoSucursalListProductoSucursalToAttach.getId());
                attachedProductoSucursalList.add(productoSucursalListProductoSucursalToAttach);
            }
            productoPresentacion.setProductoSucursalList(attachedProductoSucursalList);
            em.persist(productoPresentacion);
            if (presentacionid != null) {
                presentacionid.getProductoPresentacionList().add(productoPresentacion);
                presentacionid = em.merge(presentacionid);
            }
            if (productoid != null) {
                productoid.getProductoPresentacionList().add(productoPresentacion);
                productoid = em.merge(productoid);
            }
            for (ProductoSucursal productoSucursalListProductoSucursal : productoPresentacion.getProductoSucursalList()) {
                ProductoPresentacion oldProductoPresentacionidOfProductoSucursalListProductoSucursal = productoSucursalListProductoSucursal.getProductoPresentacionid();
                productoSucursalListProductoSucursal.setProductoPresentacionid(productoPresentacion);
                productoSucursalListProductoSucursal = em.merge(productoSucursalListProductoSucursal);
                if (oldProductoPresentacionidOfProductoSucursalListProductoSucursal != null) {
                    oldProductoPresentacionidOfProductoSucursalListProductoSucursal.getProductoSucursalList().remove(productoSucursalListProductoSucursal);
                    oldProductoPresentacionidOfProductoSucursalListProductoSucursal = em.merge(oldProductoPresentacionidOfProductoSucursalListProductoSucursal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductoPresentacion productoPresentacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ProductoPresentacion persistentProductoPresentacion = em.find(ProductoPresentacion.class, productoPresentacion.getId());
            Presentacion presentacionidOld = persistentProductoPresentacion.getPresentacionid();
            Presentacion presentacionidNew = productoPresentacion.getPresentacionid();
            Producto productoidOld = persistentProductoPresentacion.getProductoid();
            Producto productoidNew = productoPresentacion.getProductoid();
            List<ProductoSucursal> productoSucursalListOld = persistentProductoPresentacion.getProductoSucursalList();
            List<ProductoSucursal> productoSucursalListNew = productoPresentacion.getProductoSucursalList();
            List<String> illegalOrphanMessages = null;
            for (ProductoSucursal productoSucursalListOldProductoSucursal : productoSucursalListOld) {
                if (!productoSucursalListNew.contains(productoSucursalListOldProductoSucursal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoSucursal " + productoSucursalListOldProductoSucursal + " since its productoPresentacionid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (presentacionidNew != null) {
                presentacionidNew = em.getReference(presentacionidNew.getClass(), presentacionidNew.getId());
                productoPresentacion.setPresentacionid(presentacionidNew);
            }
            if (productoidNew != null) {
                productoidNew = em.getReference(productoidNew.getClass(), productoidNew.getId());
                productoPresentacion.setProductoid(productoidNew);
            }
            List<ProductoSucursal> attachedProductoSucursalListNew = new ArrayList<ProductoSucursal>();
            for (ProductoSucursal productoSucursalListNewProductoSucursalToAttach : productoSucursalListNew) {
                productoSucursalListNewProductoSucursalToAttach = em.getReference(productoSucursalListNewProductoSucursalToAttach.getClass(), productoSucursalListNewProductoSucursalToAttach.getId());
                attachedProductoSucursalListNew.add(productoSucursalListNewProductoSucursalToAttach);
            }
            productoSucursalListNew = attachedProductoSucursalListNew;
            productoPresentacion.setProductoSucursalList(productoSucursalListNew);
            productoPresentacion = em.merge(productoPresentacion);
            if (presentacionidOld != null && !presentacionidOld.equals(presentacionidNew)) {
                presentacionidOld.getProductoPresentacionList().remove(productoPresentacion);
                presentacionidOld = em.merge(presentacionidOld);
            }
            if (presentacionidNew != null && !presentacionidNew.equals(presentacionidOld)) {
                presentacionidNew.getProductoPresentacionList().add(productoPresentacion);
                presentacionidNew = em.merge(presentacionidNew);
            }
            if (productoidOld != null && !productoidOld.equals(productoidNew)) {
                productoidOld.getProductoPresentacionList().remove(productoPresentacion);
                productoidOld = em.merge(productoidOld);
            }
            if (productoidNew != null && !productoidNew.equals(productoidOld)) {
                productoidNew.getProductoPresentacionList().add(productoPresentacion);
                productoidNew = em.merge(productoidNew);
            }
            for (ProductoSucursal productoSucursalListNewProductoSucursal : productoSucursalListNew) {
                if (!productoSucursalListOld.contains(productoSucursalListNewProductoSucursal)) {
                    ProductoPresentacion oldProductoPresentacionidOfProductoSucursalListNewProductoSucursal = productoSucursalListNewProductoSucursal.getProductoPresentacionid();
                    productoSucursalListNewProductoSucursal.setProductoPresentacionid(productoPresentacion);
                    productoSucursalListNewProductoSucursal = em.merge(productoSucursalListNewProductoSucursal);
                    if (oldProductoPresentacionidOfProductoSucursalListNewProductoSucursal != null && !oldProductoPresentacionidOfProductoSucursalListNewProductoSucursal.equals(productoPresentacion)) {
                        oldProductoPresentacionidOfProductoSucursalListNewProductoSucursal.getProductoSucursalList().remove(productoSucursalListNewProductoSucursal);
                        oldProductoPresentacionidOfProductoSucursalListNewProductoSucursal = em.merge(oldProductoPresentacionidOfProductoSucursalListNewProductoSucursal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = productoPresentacion.getId();
                if (findProductoPresentacion(id) == null) {
                    throw new NonexistentEntityException("The productoPresentacion with id " + id + " no longer exists.");
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
            ProductoPresentacion productoPresentacion;
            try {
                productoPresentacion = em.getReference(ProductoPresentacion.class, id);
                productoPresentacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoPresentacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ProductoSucursal> productoSucursalListOrphanCheck = productoPresentacion.getProductoSucursalList();
            for (ProductoSucursal productoSucursalListOrphanCheckProductoSucursal : productoSucursalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ProductoPresentacion (" + productoPresentacion + ") cannot be destroyed since the ProductoSucursal " + productoSucursalListOrphanCheckProductoSucursal + " in its productoSucursalList field has a non-nullable productoPresentacionid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Presentacion presentacionid = productoPresentacion.getPresentacionid();
            if (presentacionid != null) {
                presentacionid.getProductoPresentacionList().remove(productoPresentacion);
                presentacionid = em.merge(presentacionid);
            }
            Producto productoid = productoPresentacion.getProductoid();
            if (productoid != null) {
                productoid.getProductoPresentacionList().remove(productoPresentacion);
                productoid = em.merge(productoid);
            }
            em.remove(productoPresentacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductoPresentacion> findProductoPresentacionEntities() {
        return findProductoPresentacionEntities(true, -1, -1);
    }

    public List<ProductoPresentacion> findProductoPresentacionEntities(int maxResults, int firstResult) {
        return findProductoPresentacionEntities(false, maxResults, firstResult);
    }

    private List<ProductoPresentacion> findProductoPresentacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductoPresentacion.class));
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

    public ProductoPresentacion findProductoPresentacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoPresentacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoPresentacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductoPresentacion> rt = cq.from(ProductoPresentacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
