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
import entidades.Banco;
import entidades.Cuenta;
import entidades.Movimiento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class CuentaJpaController implements Serializable {

    public CuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuenta cuenta) {
        if (cuenta.getMovimientoList() == null) {
            cuenta.setMovimientoList(new ArrayList<Movimiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Banco bancoid = cuenta.getBancoid();
            if (bancoid != null) {
                bancoid = em.getReference(bancoid.getClass(), bancoid.getId());
                cuenta.setBancoid(bancoid);
            }
            List<Movimiento> attachedMovimientoList = new ArrayList<Movimiento>();
            for (Movimiento movimientoListMovimientoToAttach : cuenta.getMovimientoList()) {
                movimientoListMovimientoToAttach = em.getReference(movimientoListMovimientoToAttach.getClass(), movimientoListMovimientoToAttach.getId());
                attachedMovimientoList.add(movimientoListMovimientoToAttach);
            }
            cuenta.setMovimientoList(attachedMovimientoList);
            em.persist(cuenta);
            if (bancoid != null) {
                bancoid.getCuentaList().add(cuenta);
                bancoid = em.merge(bancoid);
            }
            for (Movimiento movimientoListMovimiento : cuenta.getMovimientoList()) {
                Cuenta oldBancoidOfMovimientoListMovimiento = movimientoListMovimiento.getBancoid();
                movimientoListMovimiento.setBancoid(cuenta);
                movimientoListMovimiento = em.merge(movimientoListMovimiento);
                if (oldBancoidOfMovimientoListMovimiento != null) {
                    oldBancoidOfMovimientoListMovimiento.getMovimientoList().remove(movimientoListMovimiento);
                    oldBancoidOfMovimientoListMovimiento = em.merge(oldBancoidOfMovimientoListMovimiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuenta cuenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getId());
            Banco bancoidOld = persistentCuenta.getBancoid();
            Banco bancoidNew = cuenta.getBancoid();
            List<Movimiento> movimientoListOld = persistentCuenta.getMovimientoList();
            List<Movimiento> movimientoListNew = cuenta.getMovimientoList();
            List<String> illegalOrphanMessages = null;
            for (Movimiento movimientoListOldMovimiento : movimientoListOld) {
                if (!movimientoListNew.contains(movimientoListOldMovimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Movimiento " + movimientoListOldMovimiento + " since its bancoid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (bancoidNew != null) {
                bancoidNew = em.getReference(bancoidNew.getClass(), bancoidNew.getId());
                cuenta.setBancoid(bancoidNew);
            }
            List<Movimiento> attachedMovimientoListNew = new ArrayList<Movimiento>();
            for (Movimiento movimientoListNewMovimientoToAttach : movimientoListNew) {
                movimientoListNewMovimientoToAttach = em.getReference(movimientoListNewMovimientoToAttach.getClass(), movimientoListNewMovimientoToAttach.getId());
                attachedMovimientoListNew.add(movimientoListNewMovimientoToAttach);
            }
            movimientoListNew = attachedMovimientoListNew;
            cuenta.setMovimientoList(movimientoListNew);
            cuenta = em.merge(cuenta);
            if (bancoidOld != null && !bancoidOld.equals(bancoidNew)) {
                bancoidOld.getCuentaList().remove(cuenta);
                bancoidOld = em.merge(bancoidOld);
            }
            if (bancoidNew != null && !bancoidNew.equals(bancoidOld)) {
                bancoidNew.getCuentaList().add(cuenta);
                bancoidNew = em.merge(bancoidNew);
            }
            for (Movimiento movimientoListNewMovimiento : movimientoListNew) {
                if (!movimientoListOld.contains(movimientoListNewMovimiento)) {
                    Cuenta oldBancoidOfMovimientoListNewMovimiento = movimientoListNewMovimiento.getBancoid();
                    movimientoListNewMovimiento.setBancoid(cuenta);
                    movimientoListNewMovimiento = em.merge(movimientoListNewMovimiento);
                    if (oldBancoidOfMovimientoListNewMovimiento != null && !oldBancoidOfMovimientoListNewMovimiento.equals(cuenta)) {
                        oldBancoidOfMovimientoListNewMovimiento.getMovimientoList().remove(movimientoListNewMovimiento);
                        oldBancoidOfMovimientoListNewMovimiento = em.merge(oldBancoidOfMovimientoListNewMovimiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cuenta.getId();
                if (findCuenta(id) == null) {
                    throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.");
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
            Cuenta cuenta;
            try {
                cuenta = em.getReference(Cuenta.class, id);
                cuenta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Movimiento> movimientoListOrphanCheck = cuenta.getMovimientoList();
            for (Movimiento movimientoListOrphanCheckMovimiento : movimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Movimiento " + movimientoListOrphanCheckMovimiento + " in its movimientoList field has a non-nullable bancoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Banco bancoid = cuenta.getBancoid();
            if (bancoid != null) {
                bancoid.getCuentaList().remove(cuenta);
                bancoid = em.merge(bancoid);
            }
            em.remove(cuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuenta> findCuentaEntities() {
        return findCuentaEntities(true, -1, -1);
    }

    public List<Cuenta> findCuentaEntities(int maxResults, int firstResult) {
        return findCuentaEntities(false, maxResults, firstResult);
    }

    private List<Cuenta> findCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuenta.class));
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

    public Cuenta findCuenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuenta> rt = cq.from(Cuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
