/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Persona;
import entidades.TipoPersona;
import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class TipoPersonaJpaController implements Serializable {

    public TipoPersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoPersona tipoPersona) {
        if (tipoPersona.getPersonaList() == null) {
            tipoPersona.setPersonaList(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Persona> attachedPersonaList = new ArrayList<Persona>();
            for (Persona personaListPersonaToAttach : tipoPersona.getPersonaList()) {
                personaListPersonaToAttach = em.getReference(personaListPersonaToAttach.getClass(), personaListPersonaToAttach.getId());
                attachedPersonaList.add(personaListPersonaToAttach);
            }
            tipoPersona.setPersonaList(attachedPersonaList);
            em.persist(tipoPersona);
            for (Persona personaListPersona : tipoPersona.getPersonaList()) {
                TipoPersona oldTipoPersonaidOfPersonaListPersona = personaListPersona.getTipoPersonaid();
                personaListPersona.setTipoPersonaid(tipoPersona);
                personaListPersona = em.merge(personaListPersona);
                if (oldTipoPersonaidOfPersonaListPersona != null) {
                    oldTipoPersonaidOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldTipoPersonaidOfPersonaListPersona = em.merge(oldTipoPersonaidOfPersonaListPersona);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoPersona tipoPersona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPersona persistentTipoPersona = em.find(TipoPersona.class, tipoPersona.getId());
            List<Persona> personaListOld = persistentTipoPersona.getPersonaList();
            List<Persona> personaListNew = tipoPersona.getPersonaList();
            List<String> illegalOrphanMessages = null;
            for (Persona personaListOldPersona : personaListOld) {
                if (!personaListNew.contains(personaListOldPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Persona " + personaListOldPersona + " since its tipoPersonaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Persona> attachedPersonaListNew = new ArrayList<Persona>();
            for (Persona personaListNewPersonaToAttach : personaListNew) {
                personaListNewPersonaToAttach = em.getReference(personaListNewPersonaToAttach.getClass(), personaListNewPersonaToAttach.getId());
                attachedPersonaListNew.add(personaListNewPersonaToAttach);
            }
            personaListNew = attachedPersonaListNew;
            tipoPersona.setPersonaList(personaListNew);
            tipoPersona = em.merge(tipoPersona);
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    TipoPersona oldTipoPersonaidOfPersonaListNewPersona = personaListNewPersona.getTipoPersonaid();
                    personaListNewPersona.setTipoPersonaid(tipoPersona);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldTipoPersonaidOfPersonaListNewPersona != null && !oldTipoPersonaidOfPersonaListNewPersona.equals(tipoPersona)) {
                        oldTipoPersonaidOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldTipoPersonaidOfPersonaListNewPersona = em.merge(oldTipoPersonaidOfPersonaListNewPersona);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoPersona.getId();
                if (findTipoPersona(id) == null) {
                    throw new NonexistentEntityException("The tipoPersona with id " + id + " no longer exists.");
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
            TipoPersona tipoPersona;
            try {
                tipoPersona = em.getReference(TipoPersona.class, id);
                tipoPersona.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoPersona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Persona> personaListOrphanCheck = tipoPersona.getPersonaList();
            for (Persona personaListOrphanCheckPersona : personaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoPersona (" + tipoPersona + ") cannot be destroyed since the Persona " + personaListOrphanCheckPersona + " in its personaList field has a non-nullable tipoPersonaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoPersona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoPersona> findTipoPersonaEntities() {
        return findTipoPersonaEntities(true, -1, -1);
    }

    public List<TipoPersona> findTipoPersonaEntities(int maxResults, int firstResult) {
        return findTipoPersonaEntities(false, maxResults, firstResult);
    }

    private List<TipoPersona> findTipoPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoPersona.class));
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

    public TipoPersona findTipoPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoPersona.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoPersona> rt = cq.from(TipoPersona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
