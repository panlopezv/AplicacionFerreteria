/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Categoria;
import entidades.Producto;
import entidades.ProductoPresentacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getProductoPresentacionList() == null) {
            producto.setProductoPresentacionList(new ArrayList<ProductoPresentacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoriaid = producto.getCategoriaid();
            if (categoriaid != null) {
                categoriaid = em.getReference(categoriaid.getClass(), categoriaid.getId());
                producto.setCategoriaid(categoriaid);
            }
            List<ProductoPresentacion> attachedProductoPresentacionList = new ArrayList<ProductoPresentacion>();
            for (ProductoPresentacion productoPresentacionListProductoPresentacionToAttach : producto.getProductoPresentacionList()) {
                productoPresentacionListProductoPresentacionToAttach = em.getReference(productoPresentacionListProductoPresentacionToAttach.getClass(), productoPresentacionListProductoPresentacionToAttach.getId());
                attachedProductoPresentacionList.add(productoPresentacionListProductoPresentacionToAttach);
            }
            producto.setProductoPresentacionList(attachedProductoPresentacionList);
            em.persist(producto);
            if (categoriaid != null) {
                categoriaid.getProductoList().add(producto);
                categoriaid = em.merge(categoriaid);
            }
            for (ProductoPresentacion productoPresentacionListProductoPresentacion : producto.getProductoPresentacionList()) {
                Producto oldProductoidOfProductoPresentacionListProductoPresentacion = productoPresentacionListProductoPresentacion.getProductoid();
                productoPresentacionListProductoPresentacion.setProductoid(producto);
                productoPresentacionListProductoPresentacion = em.merge(productoPresentacionListProductoPresentacion);
                if (oldProductoidOfProductoPresentacionListProductoPresentacion != null) {
                    oldProductoidOfProductoPresentacionListProductoPresentacion.getProductoPresentacionList().remove(productoPresentacionListProductoPresentacion);
                    oldProductoidOfProductoPresentacionListProductoPresentacion = em.merge(oldProductoidOfProductoPresentacionListProductoPresentacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getId());
            Categoria categoriaidOld = persistentProducto.getCategoriaid();
            Categoria categoriaidNew = producto.getCategoriaid();
            List<ProductoPresentacion> productoPresentacionListOld = persistentProducto.getProductoPresentacionList();
            List<ProductoPresentacion> productoPresentacionListNew = producto.getProductoPresentacionList();
            List<String> illegalOrphanMessages = null;
            for (ProductoPresentacion productoPresentacionListOldProductoPresentacion : productoPresentacionListOld) {
                if (!productoPresentacionListNew.contains(productoPresentacionListOldProductoPresentacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoPresentacion " + productoPresentacionListOldProductoPresentacion + " since its productoid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoriaidNew != null) {
                categoriaidNew = em.getReference(categoriaidNew.getClass(), categoriaidNew.getId());
                producto.setCategoriaid(categoriaidNew);
            }
            List<ProductoPresentacion> attachedProductoPresentacionListNew = new ArrayList<ProductoPresentacion>();
            for (ProductoPresentacion productoPresentacionListNewProductoPresentacionToAttach : productoPresentacionListNew) {
                productoPresentacionListNewProductoPresentacionToAttach = em.getReference(productoPresentacionListNewProductoPresentacionToAttach.getClass(), productoPresentacionListNewProductoPresentacionToAttach.getId());
                attachedProductoPresentacionListNew.add(productoPresentacionListNewProductoPresentacionToAttach);
            }
            productoPresentacionListNew = attachedProductoPresentacionListNew;
            producto.setProductoPresentacionList(productoPresentacionListNew);
            producto = em.merge(producto);
            if (categoriaidOld != null && !categoriaidOld.equals(categoriaidNew)) {
                categoriaidOld.getProductoList().remove(producto);
                categoriaidOld = em.merge(categoriaidOld);
            }
            if (categoriaidNew != null && !categoriaidNew.equals(categoriaidOld)) {
                categoriaidNew.getProductoList().add(producto);
                categoriaidNew = em.merge(categoriaidNew);
            }
            for (ProductoPresentacion productoPresentacionListNewProductoPresentacion : productoPresentacionListNew) {
                if (!productoPresentacionListOld.contains(productoPresentacionListNewProductoPresentacion)) {
                    Producto oldProductoidOfProductoPresentacionListNewProductoPresentacion = productoPresentacionListNewProductoPresentacion.getProductoid();
                    productoPresentacionListNewProductoPresentacion.setProductoid(producto);
                    productoPresentacionListNewProductoPresentacion = em.merge(productoPresentacionListNewProductoPresentacion);
                    if (oldProductoidOfProductoPresentacionListNewProductoPresentacion != null && !oldProductoidOfProductoPresentacionListNewProductoPresentacion.equals(producto)) {
                        oldProductoidOfProductoPresentacionListNewProductoPresentacion.getProductoPresentacionList().remove(productoPresentacionListNewProductoPresentacion);
                        oldProductoidOfProductoPresentacionListNewProductoPresentacion = em.merge(oldProductoidOfProductoPresentacionListNewProductoPresentacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getId();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
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
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ProductoPresentacion> productoPresentacionListOrphanCheck = producto.getProductoPresentacionList();
            for (ProductoPresentacion productoPresentacionListOrphanCheckProductoPresentacion : productoPresentacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the ProductoPresentacion " + productoPresentacionListOrphanCheckProductoPresentacion + " in its productoPresentacionList field has a non-nullable productoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categoria categoriaid = producto.getCategoriaid();
            if (categoriaid != null) {
                categoriaid.getProductoList().remove(producto);
                categoriaid = em.merge(categoriaid);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
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

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
