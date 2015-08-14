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
import entidades.ProductoSucursal;
import entidades.Sucursal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class SucursalJpaController implements Serializable {

    public SucursalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sucursal sucursal) {
        if (sucursal.getProductoSucursalList() == null) {
            sucursal.setProductoSucursalList(new ArrayList<ProductoSucursal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ProductoSucursal> attachedProductoSucursalList = new ArrayList<ProductoSucursal>();
            for (ProductoSucursal productoSucursalListProductoSucursalToAttach : sucursal.getProductoSucursalList()) {
                productoSucursalListProductoSucursalToAttach = em.getReference(productoSucursalListProductoSucursalToAttach.getClass(), productoSucursalListProductoSucursalToAttach.getId());
                attachedProductoSucursalList.add(productoSucursalListProductoSucursalToAttach);
            }
            sucursal.setProductoSucursalList(attachedProductoSucursalList);
            em.persist(sucursal);
            for (ProductoSucursal productoSucursalListProductoSucursal : sucursal.getProductoSucursalList()) {
                Sucursal oldSucursalidOfProductoSucursalListProductoSucursal = productoSucursalListProductoSucursal.getSucursalid();
                productoSucursalListProductoSucursal.setSucursalid(sucursal);
                productoSucursalListProductoSucursal = em.merge(productoSucursalListProductoSucursal);
                if (oldSucursalidOfProductoSucursalListProductoSucursal != null) {
                    oldSucursalidOfProductoSucursalListProductoSucursal.getProductoSucursalList().remove(productoSucursalListProductoSucursal);
                    oldSucursalidOfProductoSucursalListProductoSucursal = em.merge(oldSucursalidOfProductoSucursalListProductoSucursal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sucursal sucursal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sucursal persistentSucursal = em.find(Sucursal.class, sucursal.getId());
            List<ProductoSucursal> productoSucursalListOld = persistentSucursal.getProductoSucursalList();
            List<ProductoSucursal> productoSucursalListNew = sucursal.getProductoSucursalList();
            List<String> illegalOrphanMessages = null;
            for (ProductoSucursal productoSucursalListOldProductoSucursal : productoSucursalListOld) {
                if (!productoSucursalListNew.contains(productoSucursalListOldProductoSucursal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoSucursal " + productoSucursalListOldProductoSucursal + " since its sucursalid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ProductoSucursal> attachedProductoSucursalListNew = new ArrayList<ProductoSucursal>();
            for (ProductoSucursal productoSucursalListNewProductoSucursalToAttach : productoSucursalListNew) {
                productoSucursalListNewProductoSucursalToAttach = em.getReference(productoSucursalListNewProductoSucursalToAttach.getClass(), productoSucursalListNewProductoSucursalToAttach.getId());
                attachedProductoSucursalListNew.add(productoSucursalListNewProductoSucursalToAttach);
            }
            productoSucursalListNew = attachedProductoSucursalListNew;
            sucursal.setProductoSucursalList(productoSucursalListNew);
            sucursal = em.merge(sucursal);
            for (ProductoSucursal productoSucursalListNewProductoSucursal : productoSucursalListNew) {
                if (!productoSucursalListOld.contains(productoSucursalListNewProductoSucursal)) {
                    Sucursal oldSucursalidOfProductoSucursalListNewProductoSucursal = productoSucursalListNewProductoSucursal.getSucursalid();
                    productoSucursalListNewProductoSucursal.setSucursalid(sucursal);
                    productoSucursalListNewProductoSucursal = em.merge(productoSucursalListNewProductoSucursal);
                    if (oldSucursalidOfProductoSucursalListNewProductoSucursal != null && !oldSucursalidOfProductoSucursalListNewProductoSucursal.equals(sucursal)) {
                        oldSucursalidOfProductoSucursalListNewProductoSucursal.getProductoSucursalList().remove(productoSucursalListNewProductoSucursal);
                        oldSucursalidOfProductoSucursalListNewProductoSucursal = em.merge(oldSucursalidOfProductoSucursalListNewProductoSucursal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = sucursal.getId();
                if (findSucursal(id) == null) {
                    throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.");
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
            Sucursal sucursal;
            try {
                sucursal = em.getReference(Sucursal.class, id);
                sucursal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sucursal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ProductoSucursal> productoSucursalListOrphanCheck = sucursal.getProductoSucursalList();
            for (ProductoSucursal productoSucursalListOrphanCheckProductoSucursal : productoSucursalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Sucursal (" + sucursal + ") cannot be destroyed since the ProductoSucursal " + productoSucursalListOrphanCheckProductoSucursal + " in its productoSucursalList field has a non-nullable sucursalid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(sucursal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sucursal> findSucursalEntities() {
        return findSucursalEntities(true, -1, -1);
    }

    public List<Sucursal> findSucursalEntities(int maxResults, int firstResult) {
        return findSucursalEntities(false, maxResults, firstResult);
    }

    private List<Sucursal> findSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sucursal.class));
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

    public Sucursal findSucursal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sucursal.class, id);
        } finally {
            em.close();
        }
    }

    public int getSucursalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sucursal> rt = cq.from(Sucursal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
