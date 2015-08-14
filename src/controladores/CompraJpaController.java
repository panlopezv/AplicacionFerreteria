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
import entidades.Usuario;
import entidades.Persona;
import entidades.DetalleCompra;
import java.util.ArrayList;
import java.util.List;
import entidades.Abono;
import entidades.Compra;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class CompraJpaController implements Serializable {

    public CompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Compra compra) {
        if (compra.getDetalleCompraList() == null) {
            compra.setDetalleCompraList(new ArrayList<DetalleCompra>());
        }
        if (compra.getAbonoList() == null) {
            compra.setAbonoList(new ArrayList<Abono>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuarioid = compra.getUsuarioid();
            if (usuarioid != null) {
                usuarioid = em.getReference(usuarioid.getClass(), usuarioid.getId());
                compra.setUsuarioid(usuarioid);
            }
            Persona delegadoProveedorid = compra.getDelegadoProveedorid();
            if (delegadoProveedorid != null) {
                delegadoProveedorid = em.getReference(delegadoProveedorid.getClass(), delegadoProveedorid.getId());
                compra.setDelegadoProveedorid(delegadoProveedorid);
            }
            List<DetalleCompra> attachedDetalleCompraList = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListDetalleCompraToAttach : compra.getDetalleCompraList()) {
                detalleCompraListDetalleCompraToAttach = em.getReference(detalleCompraListDetalleCompraToAttach.getClass(), detalleCompraListDetalleCompraToAttach.getId());
                attachedDetalleCompraList.add(detalleCompraListDetalleCompraToAttach);
            }
            compra.setDetalleCompraList(attachedDetalleCompraList);
            List<Abono> attachedAbonoList = new ArrayList<Abono>();
            for (Abono abonoListAbonoToAttach : compra.getAbonoList()) {
                abonoListAbonoToAttach = em.getReference(abonoListAbonoToAttach.getClass(), abonoListAbonoToAttach.getId());
                attachedAbonoList.add(abonoListAbonoToAttach);
            }
            compra.setAbonoList(attachedAbonoList);
            em.persist(compra);
            if (usuarioid != null) {
                usuarioid.getCompraList().add(compra);
                usuarioid = em.merge(usuarioid);
            }
            if (delegadoProveedorid != null) {
                delegadoProveedorid.getCompraList().add(compra);
                delegadoProveedorid = em.merge(delegadoProveedorid);
            }
            for (DetalleCompra detalleCompraListDetalleCompra : compra.getDetalleCompraList()) {
                Compra oldCompraidOfDetalleCompraListDetalleCompra = detalleCompraListDetalleCompra.getCompraid();
                detalleCompraListDetalleCompra.setCompraid(compra);
                detalleCompraListDetalleCompra = em.merge(detalleCompraListDetalleCompra);
                if (oldCompraidOfDetalleCompraListDetalleCompra != null) {
                    oldCompraidOfDetalleCompraListDetalleCompra.getDetalleCompraList().remove(detalleCompraListDetalleCompra);
                    oldCompraidOfDetalleCompraListDetalleCompra = em.merge(oldCompraidOfDetalleCompraListDetalleCompra);
                }
            }
            for (Abono abonoListAbono : compra.getAbonoList()) {
                Compra oldCompraidOfAbonoListAbono = abonoListAbono.getCompraid();
                abonoListAbono.setCompraid(compra);
                abonoListAbono = em.merge(abonoListAbono);
                if (oldCompraidOfAbonoListAbono != null) {
                    oldCompraidOfAbonoListAbono.getAbonoList().remove(abonoListAbono);
                    oldCompraidOfAbonoListAbono = em.merge(oldCompraidOfAbonoListAbono);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Compra compra) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Compra persistentCompra = em.find(Compra.class, compra.getId());
            Usuario usuarioidOld = persistentCompra.getUsuarioid();
            Usuario usuarioidNew = compra.getUsuarioid();
            Persona delegadoProveedoridOld = persistentCompra.getDelegadoProveedorid();
            Persona delegadoProveedoridNew = compra.getDelegadoProveedorid();
            List<DetalleCompra> detalleCompraListOld = persistentCompra.getDetalleCompraList();
            List<DetalleCompra> detalleCompraListNew = compra.getDetalleCompraList();
            List<Abono> abonoListOld = persistentCompra.getAbonoList();
            List<Abono> abonoListNew = compra.getAbonoList();
            List<String> illegalOrphanMessages = null;
            for (DetalleCompra detalleCompraListOldDetalleCompra : detalleCompraListOld) {
                if (!detalleCompraListNew.contains(detalleCompraListOldDetalleCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleCompra " + detalleCompraListOldDetalleCompra + " since its compraid field is not nullable.");
                }
            }
            for (Abono abonoListOldAbono : abonoListOld) {
                if (!abonoListNew.contains(abonoListOldAbono)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Abono " + abonoListOldAbono + " since its compraid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioidNew != null) {
                usuarioidNew = em.getReference(usuarioidNew.getClass(), usuarioidNew.getId());
                compra.setUsuarioid(usuarioidNew);
            }
            if (delegadoProveedoridNew != null) {
                delegadoProveedoridNew = em.getReference(delegadoProveedoridNew.getClass(), delegadoProveedoridNew.getId());
                compra.setDelegadoProveedorid(delegadoProveedoridNew);
            }
            List<DetalleCompra> attachedDetalleCompraListNew = new ArrayList<DetalleCompra>();
            for (DetalleCompra detalleCompraListNewDetalleCompraToAttach : detalleCompraListNew) {
                detalleCompraListNewDetalleCompraToAttach = em.getReference(detalleCompraListNewDetalleCompraToAttach.getClass(), detalleCompraListNewDetalleCompraToAttach.getId());
                attachedDetalleCompraListNew.add(detalleCompraListNewDetalleCompraToAttach);
            }
            detalleCompraListNew = attachedDetalleCompraListNew;
            compra.setDetalleCompraList(detalleCompraListNew);
            List<Abono> attachedAbonoListNew = new ArrayList<Abono>();
            for (Abono abonoListNewAbonoToAttach : abonoListNew) {
                abonoListNewAbonoToAttach = em.getReference(abonoListNewAbonoToAttach.getClass(), abonoListNewAbonoToAttach.getId());
                attachedAbonoListNew.add(abonoListNewAbonoToAttach);
            }
            abonoListNew = attachedAbonoListNew;
            compra.setAbonoList(abonoListNew);
            compra = em.merge(compra);
            if (usuarioidOld != null && !usuarioidOld.equals(usuarioidNew)) {
                usuarioidOld.getCompraList().remove(compra);
                usuarioidOld = em.merge(usuarioidOld);
            }
            if (usuarioidNew != null && !usuarioidNew.equals(usuarioidOld)) {
                usuarioidNew.getCompraList().add(compra);
                usuarioidNew = em.merge(usuarioidNew);
            }
            if (delegadoProveedoridOld != null && !delegadoProveedoridOld.equals(delegadoProveedoridNew)) {
                delegadoProveedoridOld.getCompraList().remove(compra);
                delegadoProveedoridOld = em.merge(delegadoProveedoridOld);
            }
            if (delegadoProveedoridNew != null && !delegadoProveedoridNew.equals(delegadoProveedoridOld)) {
                delegadoProveedoridNew.getCompraList().add(compra);
                delegadoProveedoridNew = em.merge(delegadoProveedoridNew);
            }
            for (DetalleCompra detalleCompraListNewDetalleCompra : detalleCompraListNew) {
                if (!detalleCompraListOld.contains(detalleCompraListNewDetalleCompra)) {
                    Compra oldCompraidOfDetalleCompraListNewDetalleCompra = detalleCompraListNewDetalleCompra.getCompraid();
                    detalleCompraListNewDetalleCompra.setCompraid(compra);
                    detalleCompraListNewDetalleCompra = em.merge(detalleCompraListNewDetalleCompra);
                    if (oldCompraidOfDetalleCompraListNewDetalleCompra != null && !oldCompraidOfDetalleCompraListNewDetalleCompra.equals(compra)) {
                        oldCompraidOfDetalleCompraListNewDetalleCompra.getDetalleCompraList().remove(detalleCompraListNewDetalleCompra);
                        oldCompraidOfDetalleCompraListNewDetalleCompra = em.merge(oldCompraidOfDetalleCompraListNewDetalleCompra);
                    }
                }
            }
            for (Abono abonoListNewAbono : abonoListNew) {
                if (!abonoListOld.contains(abonoListNewAbono)) {
                    Compra oldCompraidOfAbonoListNewAbono = abonoListNewAbono.getCompraid();
                    abonoListNewAbono.setCompraid(compra);
                    abonoListNewAbono = em.merge(abonoListNewAbono);
                    if (oldCompraidOfAbonoListNewAbono != null && !oldCompraidOfAbonoListNewAbono.equals(compra)) {
                        oldCompraidOfAbonoListNewAbono.getAbonoList().remove(abonoListNewAbono);
                        oldCompraidOfAbonoListNewAbono = em.merge(oldCompraidOfAbonoListNewAbono);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = compra.getId();
                if (findCompra(id) == null) {
                    throw new NonexistentEntityException("The compra with id " + id + " no longer exists.");
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
            Compra compra;
            try {
                compra = em.getReference(Compra.class, id);
                compra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The compra with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleCompra> detalleCompraListOrphanCheck = compra.getDetalleCompraList();
            for (DetalleCompra detalleCompraListOrphanCheckDetalleCompra : detalleCompraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Compra (" + compra + ") cannot be destroyed since the DetalleCompra " + detalleCompraListOrphanCheckDetalleCompra + " in its detalleCompraList field has a non-nullable compraid field.");
            }
            List<Abono> abonoListOrphanCheck = compra.getAbonoList();
            for (Abono abonoListOrphanCheckAbono : abonoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Compra (" + compra + ") cannot be destroyed since the Abono " + abonoListOrphanCheckAbono + " in its abonoList field has a non-nullable compraid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuarioid = compra.getUsuarioid();
            if (usuarioid != null) {
                usuarioid.getCompraList().remove(compra);
                usuarioid = em.merge(usuarioid);
            }
            Persona delegadoProveedorid = compra.getDelegadoProveedorid();
            if (delegadoProveedorid != null) {
                delegadoProveedorid.getCompraList().remove(compra);
                delegadoProveedorid = em.merge(delegadoProveedorid);
            }
            em.remove(compra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Compra> findCompraEntities() {
        return findCompraEntities(true, -1, -1);
    }

    public List<Compra> findCompraEntities(int maxResults, int firstResult) {
        return findCompraEntities(false, maxResults, firstResult);
    }

    private List<Compra> findCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Compra.class));
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

    public Compra findCompra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Compra.class, id);
        } finally {
            em.close();
        }
    }

    public int getCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Compra> rt = cq.from(Compra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
