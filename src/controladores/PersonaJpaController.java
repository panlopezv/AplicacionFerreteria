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
import entidades.TipoPersona;
import entidades.Compra;
import entidades.Persona;
import java.util.ArrayList;
import java.util.List;
import entidades.Venta;
import entidades.PersonaProveedor;
import entidades.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        if (persona.getCompraList() == null) {
            persona.setCompraList(new ArrayList<Compra>());
        }
        if (persona.getVentaList() == null) {
            persona.setVentaList(new ArrayList<Venta>());
        }
        if (persona.getPersonaProveedorList() == null) {
            persona.setPersonaProveedorList(new ArrayList<PersonaProveedor>());
        }
        if (persona.getUsuarioList() == null) {
            persona.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPersona tipoPersonaid = persona.getTipoPersonaid();
            if (tipoPersonaid != null) {
                tipoPersonaid = em.getReference(tipoPersonaid.getClass(), tipoPersonaid.getId());
                persona.setTipoPersonaid(tipoPersonaid);
            }
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : persona.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getId());
                attachedCompraList.add(compraListCompraToAttach);
            }
            persona.setCompraList(attachedCompraList);
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : persona.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getId());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            persona.setVentaList(attachedVentaList);
            List<PersonaProveedor> attachedPersonaProveedorList = new ArrayList<PersonaProveedor>();
            for (PersonaProveedor personaProveedorListPersonaProveedorToAttach : persona.getPersonaProveedorList()) {
                personaProveedorListPersonaProveedorToAttach = em.getReference(personaProveedorListPersonaProveedorToAttach.getClass(), personaProveedorListPersonaProveedorToAttach.getId());
                attachedPersonaProveedorList.add(personaProveedorListPersonaProveedorToAttach);
            }
            persona.setPersonaProveedorList(attachedPersonaProveedorList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : persona.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getId());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            persona.setUsuarioList(attachedUsuarioList);
            em.persist(persona);
            if (tipoPersonaid != null) {
                tipoPersonaid.getPersonaList().add(persona);
                tipoPersonaid = em.merge(tipoPersonaid);
            }
            for (Compra compraListCompra : persona.getCompraList()) {
                Persona oldDelegadoProveedoridOfCompraListCompra = compraListCompra.getDelegadoProveedorid();
                compraListCompra.setDelegadoProveedorid(persona);
                compraListCompra = em.merge(compraListCompra);
                if (oldDelegadoProveedoridOfCompraListCompra != null) {
                    oldDelegadoProveedoridOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldDelegadoProveedoridOfCompraListCompra = em.merge(oldDelegadoProveedoridOfCompraListCompra);
                }
            }
            for (Venta ventaListVenta : persona.getVentaList()) {
                Persona oldPersonaidOfVentaListVenta = ventaListVenta.getPersonaid();
                ventaListVenta.setPersonaid(persona);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldPersonaidOfVentaListVenta != null) {
                    oldPersonaidOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldPersonaidOfVentaListVenta = em.merge(oldPersonaidOfVentaListVenta);
                }
            }
            for (PersonaProveedor personaProveedorListPersonaProveedor : persona.getPersonaProveedorList()) {
                Persona oldPersonaidOfPersonaProveedorListPersonaProveedor = personaProveedorListPersonaProveedor.getPersonaid();
                personaProveedorListPersonaProveedor.setPersonaid(persona);
                personaProveedorListPersonaProveedor = em.merge(personaProveedorListPersonaProveedor);
                if (oldPersonaidOfPersonaProveedorListPersonaProveedor != null) {
                    oldPersonaidOfPersonaProveedorListPersonaProveedor.getPersonaProveedorList().remove(personaProveedorListPersonaProveedor);
                    oldPersonaidOfPersonaProveedorListPersonaProveedor = em.merge(oldPersonaidOfPersonaProveedorListPersonaProveedor);
                }
            }
            for (Usuario usuarioListUsuario : persona.getUsuarioList()) {
                Persona oldPersonaidOfUsuarioListUsuario = usuarioListUsuario.getPersonaid();
                usuarioListUsuario.setPersonaid(persona);
                usuarioListUsuario = em.merge(usuarioListUsuario);
                if (oldPersonaidOfUsuarioListUsuario != null) {
                    oldPersonaidOfUsuarioListUsuario.getUsuarioList().remove(usuarioListUsuario);
                    oldPersonaidOfUsuarioListUsuario = em.merge(oldPersonaidOfUsuarioListUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getId());
            TipoPersona tipoPersonaidOld = persistentPersona.getTipoPersonaid();
            TipoPersona tipoPersonaidNew = persona.getTipoPersonaid();
            List<Compra> compraListOld = persistentPersona.getCompraList();
            List<Compra> compraListNew = persona.getCompraList();
            List<Venta> ventaListOld = persistentPersona.getVentaList();
            List<Venta> ventaListNew = persona.getVentaList();
            List<PersonaProveedor> personaProveedorListOld = persistentPersona.getPersonaProveedorList();
            List<PersonaProveedor> personaProveedorListNew = persona.getPersonaProveedorList();
            List<Usuario> usuarioListOld = persistentPersona.getUsuarioList();
            List<Usuario> usuarioListNew = persona.getUsuarioList();
            List<String> illegalOrphanMessages = null;
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraListOldCompra + " since its delegadoProveedorid field is not nullable.");
                }
            }
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Venta " + ventaListOldVenta + " since its personaid field is not nullable.");
                }
            }
            for (PersonaProveedor personaProveedorListOldPersonaProveedor : personaProveedorListOld) {
                if (!personaProveedorListNew.contains(personaProveedorListOldPersonaProveedor)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PersonaProveedor " + personaProveedorListOldPersonaProveedor + " since its personaid field is not nullable.");
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuario " + usuarioListOldUsuario + " since its personaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoPersonaidNew != null) {
                tipoPersonaidNew = em.getReference(tipoPersonaidNew.getClass(), tipoPersonaidNew.getId());
                persona.setTipoPersonaid(tipoPersonaidNew);
            }
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getId());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            persona.setCompraList(compraListNew);
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getId());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            persona.setVentaList(ventaListNew);
            List<PersonaProveedor> attachedPersonaProveedorListNew = new ArrayList<PersonaProveedor>();
            for (PersonaProveedor personaProveedorListNewPersonaProveedorToAttach : personaProveedorListNew) {
                personaProveedorListNewPersonaProveedorToAttach = em.getReference(personaProveedorListNewPersonaProveedorToAttach.getClass(), personaProveedorListNewPersonaProveedorToAttach.getId());
                attachedPersonaProveedorListNew.add(personaProveedorListNewPersonaProveedorToAttach);
            }
            personaProveedorListNew = attachedPersonaProveedorListNew;
            persona.setPersonaProveedorList(personaProveedorListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getId());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            persona.setUsuarioList(usuarioListNew);
            persona = em.merge(persona);
            if (tipoPersonaidOld != null && !tipoPersonaidOld.equals(tipoPersonaidNew)) {
                tipoPersonaidOld.getPersonaList().remove(persona);
                tipoPersonaidOld = em.merge(tipoPersonaidOld);
            }
            if (tipoPersonaidNew != null && !tipoPersonaidNew.equals(tipoPersonaidOld)) {
                tipoPersonaidNew.getPersonaList().add(persona);
                tipoPersonaidNew = em.merge(tipoPersonaidNew);
            }
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    Persona oldDelegadoProveedoridOfCompraListNewCompra = compraListNewCompra.getDelegadoProveedorid();
                    compraListNewCompra.setDelegadoProveedorid(persona);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldDelegadoProveedoridOfCompraListNewCompra != null && !oldDelegadoProveedoridOfCompraListNewCompra.equals(persona)) {
                        oldDelegadoProveedoridOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldDelegadoProveedoridOfCompraListNewCompra = em.merge(oldDelegadoProveedoridOfCompraListNewCompra);
                    }
                }
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Persona oldPersonaidOfVentaListNewVenta = ventaListNewVenta.getPersonaid();
                    ventaListNewVenta.setPersonaid(persona);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldPersonaidOfVentaListNewVenta != null && !oldPersonaidOfVentaListNewVenta.equals(persona)) {
                        oldPersonaidOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldPersonaidOfVentaListNewVenta = em.merge(oldPersonaidOfVentaListNewVenta);
                    }
                }
            }
            for (PersonaProveedor personaProveedorListNewPersonaProveedor : personaProveedorListNew) {
                if (!personaProveedorListOld.contains(personaProveedorListNewPersonaProveedor)) {
                    Persona oldPersonaidOfPersonaProveedorListNewPersonaProveedor = personaProveedorListNewPersonaProveedor.getPersonaid();
                    personaProveedorListNewPersonaProveedor.setPersonaid(persona);
                    personaProveedorListNewPersonaProveedor = em.merge(personaProveedorListNewPersonaProveedor);
                    if (oldPersonaidOfPersonaProveedorListNewPersonaProveedor != null && !oldPersonaidOfPersonaProveedorListNewPersonaProveedor.equals(persona)) {
                        oldPersonaidOfPersonaProveedorListNewPersonaProveedor.getPersonaProveedorList().remove(personaProveedorListNewPersonaProveedor);
                        oldPersonaidOfPersonaProveedorListNewPersonaProveedor = em.merge(oldPersonaidOfPersonaProveedorListNewPersonaProveedor);
                    }
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Persona oldPersonaidOfUsuarioListNewUsuario = usuarioListNewUsuario.getPersonaid();
                    usuarioListNewUsuario.setPersonaid(persona);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldPersonaidOfUsuarioListNewUsuario != null && !oldPersonaidOfUsuarioListNewUsuario.equals(persona)) {
                        oldPersonaidOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldPersonaidOfUsuarioListNewUsuario = em.merge(oldPersonaidOfUsuarioListNewUsuario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getId();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Compra> compraListOrphanCheck = persona.getCompraList();
            for (Compra compraListOrphanCheckCompra : compraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Compra " + compraListOrphanCheckCompra + " in its compraList field has a non-nullable delegadoProveedorid field.");
            }
            List<Venta> ventaListOrphanCheck = persona.getVentaList();
            for (Venta ventaListOrphanCheckVenta : ventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Venta " + ventaListOrphanCheckVenta + " in its ventaList field has a non-nullable personaid field.");
            }
            List<PersonaProveedor> personaProveedorListOrphanCheck = persona.getPersonaProveedorList();
            for (PersonaProveedor personaProveedorListOrphanCheckPersonaProveedor : personaProveedorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the PersonaProveedor " + personaProveedorListOrphanCheckPersonaProveedor + " in its personaProveedorList field has a non-nullable personaid field.");
            }
            List<Usuario> usuarioListOrphanCheck = persona.getUsuarioList();
            for (Usuario usuarioListOrphanCheckUsuario : usuarioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Usuario " + usuarioListOrphanCheckUsuario + " in its usuarioList field has a non-nullable personaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoPersona tipoPersonaid = persona.getTipoPersonaid();
            if (tipoPersonaid != null) {
                tipoPersonaid.getPersonaList().remove(persona);
                tipoPersonaid = em.merge(tipoPersonaid);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
