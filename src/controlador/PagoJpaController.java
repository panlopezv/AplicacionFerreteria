/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Venta;
import entidades.ModoPago;
import entidades.Pago;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class PagoJpaController implements Serializable {

    public PagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pago pago) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venta ventaid = pago.getVentaid();
            if (ventaid != null) {
                ventaid = em.getReference(ventaid.getClass(), ventaid.getId());
                pago.setVentaid(ventaid);
            }
            ModoPago modoPagoid = pago.getModoPagoid();
            if (modoPagoid != null) {
                modoPagoid = em.getReference(modoPagoid.getClass(), modoPagoid.getId());
                pago.setModoPagoid(modoPagoid);
            }
            em.persist(pago);
            if (ventaid != null) {
                ventaid.getPagoList().add(pago);
                ventaid = em.merge(ventaid);
            }
            if (modoPagoid != null) {
                modoPagoid.getPagoList().add(pago);
                modoPagoid = em.merge(modoPagoid);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pago pago) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pago persistentPago = em.find(Pago.class, pago.getIdPago());
            Venta ventaidOld = persistentPago.getVentaid();
            Venta ventaidNew = pago.getVentaid();
            ModoPago modoPagoidOld = persistentPago.getModoPagoid();
            ModoPago modoPagoidNew = pago.getModoPagoid();
            if (ventaidNew != null) {
                ventaidNew = em.getReference(ventaidNew.getClass(), ventaidNew.getId());
                pago.setVentaid(ventaidNew);
            }
            if (modoPagoidNew != null) {
                modoPagoidNew = em.getReference(modoPagoidNew.getClass(), modoPagoidNew.getId());
                pago.setModoPagoid(modoPagoidNew);
            }
            pago = em.merge(pago);
            if (ventaidOld != null && !ventaidOld.equals(ventaidNew)) {
                ventaidOld.getPagoList().remove(pago);
                ventaidOld = em.merge(ventaidOld);
            }
            if (ventaidNew != null && !ventaidNew.equals(ventaidOld)) {
                ventaidNew.getPagoList().add(pago);
                ventaidNew = em.merge(ventaidNew);
            }
            if (modoPagoidOld != null && !modoPagoidOld.equals(modoPagoidNew)) {
                modoPagoidOld.getPagoList().remove(pago);
                modoPagoidOld = em.merge(modoPagoidOld);
            }
            if (modoPagoidNew != null && !modoPagoidNew.equals(modoPagoidOld)) {
                modoPagoidNew.getPagoList().add(pago);
                modoPagoidNew = em.merge(modoPagoidNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pago.getIdPago();
                if (findPago(id) == null) {
                    throw new NonexistentEntityException("The pago with id " + id + " no longer exists.");
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
            Pago pago;
            try {
                pago = em.getReference(Pago.class, id);
                pago.getIdPago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pago with id " + id + " no longer exists.", enfe);
            }
            Venta ventaid = pago.getVentaid();
            if (ventaid != null) {
                ventaid.getPagoList().remove(pago);
                ventaid = em.merge(ventaid);
            }
            ModoPago modoPagoid = pago.getModoPagoid();
            if (modoPagoid != null) {
                modoPagoid.getPagoList().remove(pago);
                modoPagoid = em.merge(modoPagoid);
            }
            em.remove(pago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pago> findPagoEntities() {
        return findPagoEntities(true, -1, -1);
    }

    public List<Pago> findPagoEntities(int maxResults, int firstResult) {
        return findPagoEntities(false, maxResults, firstResult);
    }

    private List<Pago> findPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pago.class));
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

    public Pago findPago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pago.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pago> rt = cq.from(Pago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
