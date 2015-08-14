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
import entidades.Abono;
import entidades.ModoPago;
import java.util.ArrayList;
import java.util.List;
import entidades.Pago;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class ModoPagoJpaController implements Serializable {

    public ModoPagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ModoPago modoPago) {
        if (modoPago.getAbonoList() == null) {
            modoPago.setAbonoList(new ArrayList<Abono>());
        }
        if (modoPago.getPagoList() == null) {
            modoPago.setPagoList(new ArrayList<Pago>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Abono> attachedAbonoList = new ArrayList<Abono>();
            for (Abono abonoListAbonoToAttach : modoPago.getAbonoList()) {
                abonoListAbonoToAttach = em.getReference(abonoListAbonoToAttach.getClass(), abonoListAbonoToAttach.getId());
                attachedAbonoList.add(abonoListAbonoToAttach);
            }
            modoPago.setAbonoList(attachedAbonoList);
            List<Pago> attachedPagoList = new ArrayList<Pago>();
            for (Pago pagoListPagoToAttach : modoPago.getPagoList()) {
                pagoListPagoToAttach = em.getReference(pagoListPagoToAttach.getClass(), pagoListPagoToAttach.getIdPago());
                attachedPagoList.add(pagoListPagoToAttach);
            }
            modoPago.setPagoList(attachedPagoList);
            em.persist(modoPago);
            for (Abono abonoListAbono : modoPago.getAbonoList()) {
                ModoPago oldModoPagoidOfAbonoListAbono = abonoListAbono.getModoPagoid();
                abonoListAbono.setModoPagoid(modoPago);
                abonoListAbono = em.merge(abonoListAbono);
                if (oldModoPagoidOfAbonoListAbono != null) {
                    oldModoPagoidOfAbonoListAbono.getAbonoList().remove(abonoListAbono);
                    oldModoPagoidOfAbonoListAbono = em.merge(oldModoPagoidOfAbonoListAbono);
                }
            }
            for (Pago pagoListPago : modoPago.getPagoList()) {
                ModoPago oldModoPagoidOfPagoListPago = pagoListPago.getModoPagoid();
                pagoListPago.setModoPagoid(modoPago);
                pagoListPago = em.merge(pagoListPago);
                if (oldModoPagoidOfPagoListPago != null) {
                    oldModoPagoidOfPagoListPago.getPagoList().remove(pagoListPago);
                    oldModoPagoidOfPagoListPago = em.merge(oldModoPagoidOfPagoListPago);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ModoPago modoPago) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ModoPago persistentModoPago = em.find(ModoPago.class, modoPago.getId());
            List<Abono> abonoListOld = persistentModoPago.getAbonoList();
            List<Abono> abonoListNew = modoPago.getAbonoList();
            List<Pago> pagoListOld = persistentModoPago.getPagoList();
            List<Pago> pagoListNew = modoPago.getPagoList();
            List<String> illegalOrphanMessages = null;
            for (Abono abonoListOldAbono : abonoListOld) {
                if (!abonoListNew.contains(abonoListOldAbono)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Abono " + abonoListOldAbono + " since its modoPagoid field is not nullable.");
                }
            }
            for (Pago pagoListOldPago : pagoListOld) {
                if (!pagoListNew.contains(pagoListOldPago)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pago " + pagoListOldPago + " since its modoPagoid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Abono> attachedAbonoListNew = new ArrayList<Abono>();
            for (Abono abonoListNewAbonoToAttach : abonoListNew) {
                abonoListNewAbonoToAttach = em.getReference(abonoListNewAbonoToAttach.getClass(), abonoListNewAbonoToAttach.getId());
                attachedAbonoListNew.add(abonoListNewAbonoToAttach);
            }
            abonoListNew = attachedAbonoListNew;
            modoPago.setAbonoList(abonoListNew);
            List<Pago> attachedPagoListNew = new ArrayList<Pago>();
            for (Pago pagoListNewPagoToAttach : pagoListNew) {
                pagoListNewPagoToAttach = em.getReference(pagoListNewPagoToAttach.getClass(), pagoListNewPagoToAttach.getIdPago());
                attachedPagoListNew.add(pagoListNewPagoToAttach);
            }
            pagoListNew = attachedPagoListNew;
            modoPago.setPagoList(pagoListNew);
            modoPago = em.merge(modoPago);
            for (Abono abonoListNewAbono : abonoListNew) {
                if (!abonoListOld.contains(abonoListNewAbono)) {
                    ModoPago oldModoPagoidOfAbonoListNewAbono = abonoListNewAbono.getModoPagoid();
                    abonoListNewAbono.setModoPagoid(modoPago);
                    abonoListNewAbono = em.merge(abonoListNewAbono);
                    if (oldModoPagoidOfAbonoListNewAbono != null && !oldModoPagoidOfAbonoListNewAbono.equals(modoPago)) {
                        oldModoPagoidOfAbonoListNewAbono.getAbonoList().remove(abonoListNewAbono);
                        oldModoPagoidOfAbonoListNewAbono = em.merge(oldModoPagoidOfAbonoListNewAbono);
                    }
                }
            }
            for (Pago pagoListNewPago : pagoListNew) {
                if (!pagoListOld.contains(pagoListNewPago)) {
                    ModoPago oldModoPagoidOfPagoListNewPago = pagoListNewPago.getModoPagoid();
                    pagoListNewPago.setModoPagoid(modoPago);
                    pagoListNewPago = em.merge(pagoListNewPago);
                    if (oldModoPagoidOfPagoListNewPago != null && !oldModoPagoidOfPagoListNewPago.equals(modoPago)) {
                        oldModoPagoidOfPagoListNewPago.getPagoList().remove(pagoListNewPago);
                        oldModoPagoidOfPagoListNewPago = em.merge(oldModoPagoidOfPagoListNewPago);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = modoPago.getId();
                if (findModoPago(id) == null) {
                    throw new NonexistentEntityException("The modoPago with id " + id + " no longer exists.");
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
            ModoPago modoPago;
            try {
                modoPago = em.getReference(ModoPago.class, id);
                modoPago.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modoPago with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Abono> abonoListOrphanCheck = modoPago.getAbonoList();
            for (Abono abonoListOrphanCheckAbono : abonoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ModoPago (" + modoPago + ") cannot be destroyed since the Abono " + abonoListOrphanCheckAbono + " in its abonoList field has a non-nullable modoPagoid field.");
            }
            List<Pago> pagoListOrphanCheck = modoPago.getPagoList();
            for (Pago pagoListOrphanCheckPago : pagoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ModoPago (" + modoPago + ") cannot be destroyed since the Pago " + pagoListOrphanCheckPago + " in its pagoList field has a non-nullable modoPagoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(modoPago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ModoPago> findModoPagoEntities() {
        return findModoPagoEntities(true, -1, -1);
    }

    public List<ModoPago> findModoPagoEntities(int maxResults, int firstResult) {
        return findModoPagoEntities(false, maxResults, firstResult);
    }

    private List<ModoPago> findModoPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ModoPago.class));
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

    public ModoPago findModoPago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ModoPago.class, id);
        } finally {
            em.close();
        }
    }

    public int getModoPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ModoPago> rt = cq.from(ModoPago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
