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
import entidades.PermisoUsuario;
import entidades.TipoUsuario;
import java.util.ArrayList;
import entidades.Usuario;
import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class TipoUsuarioJpaController implements Serializable {

    public TipoUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoUsuario tipoUsuario) {
        if (tipoUsuario.getPermisoUsuarioList() == null) {
            tipoUsuario.setPermisoUsuarioList(new ArrayList<PermisoUsuario>());
        }
        if (tipoUsuario.getUsuarioList() == null) {
            tipoUsuario.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            List<PermisoUsuario> attachedPermisoUsuarioList = new ArrayList<PermisoUsuario>();
//            for (PermisoUsuario permisoUsuarioListPermisoUsuarioToAttach : tipoUsuario.getPermisoUsuarioList()) {
//                permisoUsuarioListPermisoUsuarioToAttach = em.getReference(permisoUsuarioListPermisoUsuarioToAttach.getClass(), permisoUsuarioListPermisoUsuarioToAttach.getId());
//                attachedPermisoUsuarioList.add(permisoUsuarioListPermisoUsuarioToAttach);
//            }
//            tipoUsuario.setPermisoUsuarioList(attachedPermisoUsuarioList);
//            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
//            for (Usuario usuarioListUsuarioToAttach : tipoUsuario.getUsuarioList()) {
//                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getId());
//                attachedUsuarioList.add(usuarioListUsuarioToAttach);
//            }
//            tipoUsuario.setUsuarioList(attachedUsuarioList);
            em.persist(tipoUsuario);
//            for (PermisoUsuario permisoUsuarioListPermisoUsuario : tipoUsuario.getPermisoUsuarioList()) {
//                TipoUsuario oldTipoUsuarioidOfPermisoUsuarioListPermisoUsuario = permisoUsuarioListPermisoUsuario.getTipoUsuarioid();
//                permisoUsuarioListPermisoUsuario.setTipoUsuarioid(tipoUsuario);
//                permisoUsuarioListPermisoUsuario = em.merge(permisoUsuarioListPermisoUsuario);
//                if (oldTipoUsuarioidOfPermisoUsuarioListPermisoUsuario != null) {
//                    oldTipoUsuarioidOfPermisoUsuarioListPermisoUsuario.getPermisoUsuarioList().remove(permisoUsuarioListPermisoUsuario);
//                    oldTipoUsuarioidOfPermisoUsuarioListPermisoUsuario = em.merge(oldTipoUsuarioidOfPermisoUsuarioListPermisoUsuario);
//                }
//            }
//            for (Usuario usuarioListUsuario : tipoUsuario.getUsuarioList()) {
//                TipoUsuario oldTipoUsuarioidOfUsuarioListUsuario = usuarioListUsuario.getTipoUsuarioid();
//                usuarioListUsuario.setTipoUsuarioid(tipoUsuario);
//                usuarioListUsuario = em.merge(usuarioListUsuario);
//                if (oldTipoUsuarioidOfUsuarioListUsuario != null) {
//                    oldTipoUsuarioidOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
//                    oldTipoUsuarioidOfUsuarioListUsuario = em.merge(oldTipoUsuarioidOfUsuarioListUsuario);
//                }
//            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoUsuario tipoUsuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            TipoUsuario persistentTipoUsuario = em.find(TipoUsuario.class, tipoUsuario.getId());
//            List<PermisoUsuario> permisoUsuarioListOld = persistentTipoUsuario.getPermisoUsuarioList();
//            List<PermisoUsuario> permisoUsuarioListNew = tipoUsuario.getPermisoUsuarioList();
//            List<Usuario> usuarioListOld = persistentTipoUsuario.getUsuarioList();
//            List<Usuario> usuarioListNew = tipoUsuario.getUsuarioList();
//            List<String> illegalOrphanMessages = null;
//            for (PermisoUsuario permisoUsuarioListOldPermisoUsuario : permisoUsuarioListOld) {
//                if (!permisoUsuarioListNew.contains(permisoUsuarioListOldPermisoUsuario)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain PermisoUsuario " + permisoUsuarioListOldPermisoUsuario + " since its tipoUsuarioid field is not nullable.");
//                }
//            }
//            for (Usuario usuarioListOldUsuario : usuarioListOld) {
//                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its tipoUsuarioid field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            List<PermisoUsuario> attachedPermisoUsuarioListNew = new ArrayList<PermisoUsuario>();
//            for (PermisoUsuario permisoUsuarioListNewPermisoUsuarioToAttach : permisoUsuarioListNew) {
//                permisoUsuarioListNewPermisoUsuarioToAttach = em.getReference(permisoUsuarioListNewPermisoUsuarioToAttach.getClass(), permisoUsuarioListNewPermisoUsuarioToAttach.getId());
//                attachedPermisoUsuarioListNew.add(permisoUsuarioListNewPermisoUsuarioToAttach);
//            }
//            permisoUsuarioListNew = attachedPermisoUsuarioListNew;
//            tipoUsuario.setPermisoUsuarioList(permisoUsuarioListNew);
//            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
//            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
//                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getId());
//                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
//            }
//            usuarioListNew = attachedUsuarioListNew;
//            tipoUsuario.setUsuarioList(usuarioListNew);
            tipoUsuario = em.merge(tipoUsuario);
//            for (PermisoUsuario permisoUsuarioListNewPermisoUsuario : permisoUsuarioListNew) {
//                if (!permisoUsuarioListOld.contains(permisoUsuarioListNewPermisoUsuario)) {
//                    TipoUsuario oldTipoUsuarioidOfPermisoUsuarioListNewPermisoUsuario = permisoUsuarioListNewPermisoUsuario.getTipoUsuarioid();
//                    permisoUsuarioListNewPermisoUsuario.setTipoUsuarioid(tipoUsuario);
//                    permisoUsuarioListNewPermisoUsuario = em.merge(permisoUsuarioListNewPermisoUsuario);
//                    if (oldTipoUsuarioidOfPermisoUsuarioListNewPermisoUsuario != null && !oldTipoUsuarioidOfPermisoUsuarioListNewPermisoUsuario.equals(tipoUsuario)) {
//                        oldTipoUsuarioidOfPermisoUsuarioListNewPermisoUsuario.getPermisoUsuarioList().remove(permisoUsuarioListNewPermisoUsuario);
//                        oldTipoUsuarioidOfPermisoUsuarioListNewPermisoUsuario = em.merge(oldTipoUsuarioidOfPermisoUsuarioListNewPermisoUsuario);
//                    }
//                }
//            }
//            for (Usuario usuarioListNewUsuario : usuarioListNew) {
//                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
//                    TipoUsuario oldTipoUsuarioidOfUsuarioListNewUsuario = usuarioListNewUsuario.getTipoUsuarioid();
//                    usuarioListNewUsuario.setTipoUsuarioid(tipoUsuario);
//                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
//                    if (oldTipoUsuarioidOfUsuarioListNewUsuario != null && !oldTipoUsuarioidOfUsuarioListNewUsuario.equals(tipoUsuario)) {
//                        oldTipoUsuarioidOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
//                        oldTipoUsuarioidOfUsuarioListNewUsuario = em.merge(oldTipoUsuarioidOfUsuarioListNewUsuario);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoUsuario.getId();
                if (findTipoUsuario(id) == null) {
                    throw new NonexistentEntityException("The tipoUsuario with id " + id + " no longer exists.");
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
            TipoUsuario tipoUsuario;
            try {
                tipoUsuario = em.getReference(TipoUsuario.class, id);
                tipoUsuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoUsuario with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<PermisoUsuario> permisoUsuarioListOrphanCheck = tipoUsuario.getPermisoUsuarioList();
//            for (PermisoUsuario permisoUsuarioListOrphanCheckPermisoUsuario : permisoUsuarioListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This TipoUsuario (" + tipoUsuario + ") cannot be destroyed since the PermisoUsuario " + permisoUsuarioListOrphanCheckPermisoUsuario + " in its permisoUsuarioList field has a non-nullable tipoUsuarioid field.");
//            }
//            List<Usuario> usuarioListOrphanCheck = tipoUsuario.getUsuarioList();
//            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This TipoUsuario (" + tipoUsuario + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable tipoUsuarioid field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            em.remove(tipoUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoUsuario> findTipoUsuarioEntities() {
        return findTipoUsuarioEntities(true, -1, -1);
    }

    public List<TipoUsuario> findTipoUsuarioEntities(int maxResults, int firstResult) {
        return findTipoUsuarioEntities(false, maxResults, firstResult);
    }

    private List<TipoUsuario> findTipoUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoUsuario.class));
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

    public TipoUsuario findTipoUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoUsuario> rt = cq.from(TipoUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
