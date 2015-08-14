/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import entidades.Presentacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.ProductoPresentacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class PresentacionJpaController implements Serializable {

    public PresentacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Presentacion presentacion) {
        if (presentacion.getProductoPresentacionList() == null) {
            presentacion.setProductoPresentacionList(new ArrayList<ProductoPresentacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ProductoPresentacion> attachedProductoPresentacionList = new ArrayList<ProductoPresentacion>();
            for (ProductoPresentacion productoPresentacionListProductoPresentacionToAttach : presentacion.getProductoPresentacionList()) {
                productoPresentacionListProductoPresentacionToAttach = em.getReference(productoPresentacionListProductoPresentacionToAttach.getClass(), productoPresentacionListProductoPresentacionToAttach.getId());
                attachedProductoPresentacionList.add(productoPresentacionListProductoPresentacionToAttach);
            }
            presentacion.setProductoPresentacionList(attachedProductoPresentacionList);
            em.persist(presentacion);
            for (ProductoPresentacion productoPresentacionListProductoPresentacion : presentacion.getProductoPresentacionList()) {
                Presentacion oldPresentacionidOfProductoPresentacionListProductoPresentacion = productoPresentacionListProductoPresentacion.getPresentacionid();
                productoPresentacionListProductoPresentacion.setPresentacionid(presentacion);
                productoPresentacionListProductoPresentacion = em.merge(productoPresentacionListProductoPresentacion);
                if (oldPresentacionidOfProductoPresentacionListProductoPresentacion != null) {
                    oldPresentacionidOfProductoPresentacionListProductoPresentacion.getProductoPresentacionList().remove(productoPresentacionListProductoPresentacion);
                    oldPresentacionidOfProductoPresentacionListProductoPresentacion = em.merge(oldPresentacionidOfProductoPresentacionListProductoPresentacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Presentacion presentacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Presentacion persistentPresentacion = em.find(Presentacion.class, presentacion.getId());
            List<ProductoPresentacion> productoPresentacionListOld = persistentPresentacion.getProductoPresentacionList();
            List<ProductoPresentacion> productoPresentacionListNew = presentacion.getProductoPresentacionList();
            List<String> illegalOrphanMessages = null;
            for (ProductoPresentacion productoPresentacionListOldProductoPresentacion : productoPresentacionListOld) {
                if (!productoPresentacionListNew.contains(productoPresentacionListOldProductoPresentacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ProductoPresentacion " + productoPresentacionListOldProductoPresentacion + " since its presentacionid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ProductoPresentacion> attachedProductoPresentacionListNew = new ArrayList<ProductoPresentacion>();
            for (ProductoPresentacion productoPresentacionListNewProductoPresentacionToAttach : productoPresentacionListNew) {
                productoPresentacionListNewProductoPresentacionToAttach = em.getReference(productoPresentacionListNewProductoPresentacionToAttach.getClass(), productoPresentacionListNewProductoPresentacionToAttach.getId());
                attachedProductoPresentacionListNew.add(productoPresentacionListNewProductoPresentacionToAttach);
            }
            productoPresentacionListNew = attachedProductoPresentacionListNew;
            presentacion.setProductoPresentacionList(productoPresentacionListNew);
            presentacion = em.merge(presentacion);
            for (ProductoPresentacion productoPresentacionListNewProductoPresentacion : productoPresentacionListNew) {
                if (!productoPresentacionListOld.contains(productoPresentacionListNewProductoPresentacion)) {
                    Presentacion oldPresentacionidOfProductoPresentacionListNewProductoPresentacion = productoPresentacionListNewProductoPresentacion.getPresentacionid();
                    productoPresentacionListNewProductoPresentacion.setPresentacionid(presentacion);
                    productoPresentacionListNewProductoPresentacion = em.merge(productoPresentacionListNewProductoPresentacion);
                    if (oldPresentacionidOfProductoPresentacionListNewProductoPresentacion != null && !oldPresentacionidOfProductoPresentacionListNewProductoPresentacion.equals(presentacion)) {
                        oldPresentacionidOfProductoPresentacionListNewProductoPresentacion.getProductoPresentacionList().remove(productoPresentacionListNewProductoPresentacion);
                        oldPresentacionidOfProductoPresentacionListNewProductoPresentacion = em.merge(oldPresentacionidOfProductoPresentacionListNewProductoPresentacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = presentacion.getId();
                if (findPresentacion(id) == null) {
                    throw new NonexistentEntityException("The presentacion with id " + id + " no longer exists.");
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
            Presentacion presentacion;
            try {
                presentacion = em.getReference(Presentacion.class, id);
                presentacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The presentacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ProductoPresentacion> productoPresentacionListOrphanCheck = presentacion.getProductoPresentacionList();
            for (ProductoPresentacion productoPresentacionListOrphanCheckProductoPresentacion : productoPresentacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Presentacion (" + presentacion + ") cannot be destroyed since the ProductoPresentacion " + productoPresentacionListOrphanCheckProductoPresentacion + " in its productoPresentacionList field has a non-nullable presentacionid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(presentacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Presentacion> findPresentacionEntities() {
        return findPresentacionEntities(true, -1, -1);
    }

    public List<Presentacion> findPresentacionEntities(int maxResults, int firstResult) {
        return findPresentacionEntities(false, maxResults, firstResult);
    }

    private List<Presentacion> findPresentacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Presentacion.class));
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

    public Presentacion findPresentacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Presentacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPresentacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Presentacion> rt = cq.from(Presentacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
