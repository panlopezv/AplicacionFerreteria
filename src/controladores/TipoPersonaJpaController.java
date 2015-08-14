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
import entidades.PermisoUsuario;
import java.util.ArrayList;
import java.util.List;
import entidades.Persona;
import entidades.TipoPersona;
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
        if (tipoPersona.getPermisoUsuarioList() == null) {
            tipoPersona.setPermisoUsuarioList(new ArrayList<PermisoUsuario>());
        }
        if (tipoPersona.getPersonaList() == null) {
            tipoPersona.setPersonaList(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PermisoUsuario> attachedPermisoUsuarioList = new ArrayList<PermisoUsuario>();
            for (PermisoUsuario permisoUsuarioListPermisoUsuarioToAttach : tipoPersona.getPermisoUsuarioList()) {
                permisoUsuarioListPermisoUsuarioToAttach = em.getReference(permisoUsuarioListPermisoUsuarioToAttach.getClass(), permisoUsuarioListPermisoUsuarioToAttach.getId());
                attachedPermisoUsuarioList.add(permisoUsuarioListPermisoUsuarioToAttach);
            }
            tipoPersona.setPermisoUsuarioList(attachedPermisoUsuarioList);
            List<Persona> attachedPersonaList = new ArrayList<Persona>();
            for (Persona personaListPersonaToAttach : tipoPersona.getPersonaList()) {
                personaListPersonaToAttach = em.getReference(personaListPersonaToAttach.getClass(), personaListPersonaToAttach.getId());
                attachedPersonaList.add(personaListPersonaToAttach);
            }
            tipoPersona.setPersonaList(attachedPersonaList);
            em.persist(tipoPersona);
            for (PermisoUsuario permisoUsuarioListPermisoUsuario : tipoPersona.getPermisoUsuarioList()) {
                TipoPersona oldTipoPersonaidOfPermisoUsuarioListPermisoUsuario = permisoUsuarioListPermisoUsuario.getTipoPersonaid();
                permisoUsuarioListPermisoUsuario.setTipoPersonaid(tipoPersona);
                permisoUsuarioListPermisoUsuario = em.merge(permisoUsuarioListPermisoUsuario);
                if (oldTipoPersonaidOfPermisoUsuarioListPermisoUsuario != null) {
                    oldTipoPersonaidOfPermisoUsuarioListPermisoUsuario.getPermisoUsuarioList().remove(permisoUsuarioListPermisoUsuario);
                    oldTipoPersonaidOfPermisoUsuarioListPermisoUsuario = em.merge(oldTipoPersonaidOfPermisoUsuarioListPermisoUsuario);
                }
            }
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
            List<PermisoUsuario> permisoUsuarioListOld = persistentTipoPersona.getPermisoUsuarioList();
            List<PermisoUsuario> permisoUsuarioListNew = tipoPersona.getPermisoUsuarioList();
            List<Persona> personaListOld = persistentTipoPersona.getPersonaList();
            List<Persona> personaListNew = tipoPersona.getPersonaList();
            List<String> illegalOrphanMessages = null;
            for (PermisoUsuario permisoUsuarioListOldPermisoUsuario : permisoUsuarioListOld) {
                if (!permisoUsuarioListNew.contains(permisoUsuarioListOldPermisoUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PermisoUsuario " + permisoUsuarioListOldPermisoUsuario + " since its tipoPersonaid field is not nullable.");
                }
            }
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
            List<PermisoUsuario> attachedPermisoUsuarioListNew = new ArrayList<PermisoUsuario>();
            for (PermisoUsuario permisoUsuarioListNewPermisoUsuarioToAttach : permisoUsuarioListNew) {
                permisoUsuarioListNewPermisoUsuarioToAttach = em.getReference(permisoUsuarioListNewPermisoUsuarioToAttach.getClass(), permisoUsuarioListNewPermisoUsuarioToAttach.getId());
                attachedPermisoUsuarioListNew.add(permisoUsuarioListNewPermisoUsuarioToAttach);
            }
            permisoUsuarioListNew = attachedPermisoUsuarioListNew;
            tipoPersona.setPermisoUsuarioList(permisoUsuarioListNew);
            List<Persona> attachedPersonaListNew = new ArrayList<Persona>();
            for (Persona personaListNewPersonaToAttach : personaListNew) {
                personaListNewPersonaToAttach = em.getReference(personaListNewPersonaToAttach.getClass(), personaListNewPersonaToAttach.getId());
                attachedPersonaListNew.add(personaListNewPersonaToAttach);
            }
            personaListNew = attachedPersonaListNew;
            tipoPersona.setPersonaList(personaListNew);
            tipoPersona = em.merge(tipoPersona);
            for (PermisoUsuario permisoUsuarioListNewPermisoUsuario : permisoUsuarioListNew) {
                if (!permisoUsuarioListOld.contains(permisoUsuarioListNewPermisoUsuario)) {
                    TipoPersona oldTipoPersonaidOfPermisoUsuarioListNewPermisoUsuario = permisoUsuarioListNewPermisoUsuario.getTipoPersonaid();
                    permisoUsuarioListNewPermisoUsuario.setTipoPersonaid(tipoPersona);
                    permisoUsuarioListNewPermisoUsuario = em.merge(permisoUsuarioListNewPermisoUsuario);
                    if (oldTipoPersonaidOfPermisoUsuarioListNewPermisoUsuario != null && !oldTipoPersonaidOfPermisoUsuarioListNewPermisoUsuario.equals(tipoPersona)) {
                        oldTipoPersonaidOfPermisoUsuarioListNewPermisoUsuario.getPermisoUsuarioList().remove(permisoUsuarioListNewPermisoUsuario);
                        oldTipoPersonaidOfPermisoUsuarioListNewPermisoUsuario = em.merge(oldTipoPersonaidOfPermisoUsuarioListNewPermisoUsuario);
                    }
                }
            }
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
            List<PermisoUsuario> permisoUsuarioListOrphanCheck = tipoPersona.getPermisoUsuarioList();
            for (PermisoUsuario permisoUsuarioListOrphanCheckPermisoUsuario : permisoUsuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoPersona (" + tipoPersona + ") cannot be destroyed since the PermisoUsuario " + permisoUsuarioListOrphanCheckPermisoUsuario + " in its permisoUsuarioList field has a non-nullable tipoPersonaid field.");
            }
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
