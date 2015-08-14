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
import entidades.Persona;
import entidades.TipoUsuario;
import entidades.Compra;
import java.util.ArrayList;
import java.util.List;
import entidades.Bitacora;
import entidades.Venta;
import entidades.Movimiento;
import entidades.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Pablo
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getCompraList() == null) {
            usuario.setCompraList(new ArrayList<Compra>());
        }
        if (usuario.getBitacoraList() == null) {
            usuario.setBitacoraList(new ArrayList<Bitacora>());
        }
        if (usuario.getVentaList() == null) {
            usuario.setVentaList(new ArrayList<Venta>());
        }
        if (usuario.getMovimientoList() == null) {
            usuario.setMovimientoList(new ArrayList<Movimiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona personaid = usuario.getPersonaid();
            if (personaid != null) {
                personaid = em.getReference(personaid.getClass(), personaid.getId());
                usuario.setPersonaid(personaid);
            }
            TipoUsuario tipoUsuarioid = usuario.getTipoUsuarioid();
            if (tipoUsuarioid != null) {
                tipoUsuarioid = em.getReference(tipoUsuarioid.getClass(), tipoUsuarioid.getId());
                usuario.setTipoUsuarioid(tipoUsuarioid);
            }
            List<Compra> attachedCompraList = new ArrayList<Compra>();
            for (Compra compraListCompraToAttach : usuario.getCompraList()) {
                compraListCompraToAttach = em.getReference(compraListCompraToAttach.getClass(), compraListCompraToAttach.getId());
                attachedCompraList.add(compraListCompraToAttach);
            }
            usuario.setCompraList(attachedCompraList);
            List<Bitacora> attachedBitacoraList = new ArrayList<Bitacora>();
            for (Bitacora bitacoraListBitacoraToAttach : usuario.getBitacoraList()) {
                bitacoraListBitacoraToAttach = em.getReference(bitacoraListBitacoraToAttach.getClass(), bitacoraListBitacoraToAttach.getId());
                attachedBitacoraList.add(bitacoraListBitacoraToAttach);
            }
            usuario.setBitacoraList(attachedBitacoraList);
            List<Venta> attachedVentaList = new ArrayList<Venta>();
            for (Venta ventaListVentaToAttach : usuario.getVentaList()) {
                ventaListVentaToAttach = em.getReference(ventaListVentaToAttach.getClass(), ventaListVentaToAttach.getId());
                attachedVentaList.add(ventaListVentaToAttach);
            }
            usuario.setVentaList(attachedVentaList);
            List<Movimiento> attachedMovimientoList = new ArrayList<Movimiento>();
            for (Movimiento movimientoListMovimientoToAttach : usuario.getMovimientoList()) {
                movimientoListMovimientoToAttach = em.getReference(movimientoListMovimientoToAttach.getClass(), movimientoListMovimientoToAttach.getId());
                attachedMovimientoList.add(movimientoListMovimientoToAttach);
            }
            usuario.setMovimientoList(attachedMovimientoList);
            em.persist(usuario);
            if (personaid != null) {
                personaid.getUsuarioList().add(usuario);
                personaid = em.merge(personaid);
            }
            if (tipoUsuarioid != null) {
                tipoUsuarioid.getUsuarioList().add(usuario);
                tipoUsuarioid = em.merge(tipoUsuarioid);
            }
            for (Compra compraListCompra : usuario.getCompraList()) {
                Usuario oldUsuarioidOfCompraListCompra = compraListCompra.getUsuarioid();
                compraListCompra.setUsuarioid(usuario);
                compraListCompra = em.merge(compraListCompra);
                if (oldUsuarioidOfCompraListCompra != null) {
                    oldUsuarioidOfCompraListCompra.getCompraList().remove(compraListCompra);
                    oldUsuarioidOfCompraListCompra = em.merge(oldUsuarioidOfCompraListCompra);
                }
            }
            for (Bitacora bitacoraListBitacora : usuario.getBitacoraList()) {
                Usuario oldUsuarioidOfBitacoraListBitacora = bitacoraListBitacora.getUsuarioid();
                bitacoraListBitacora.setUsuarioid(usuario);
                bitacoraListBitacora = em.merge(bitacoraListBitacora);
                if (oldUsuarioidOfBitacoraListBitacora != null) {
                    oldUsuarioidOfBitacoraListBitacora.getBitacoraList().remove(bitacoraListBitacora);
                    oldUsuarioidOfBitacoraListBitacora = em.merge(oldUsuarioidOfBitacoraListBitacora);
                }
            }
            for (Venta ventaListVenta : usuario.getVentaList()) {
                Usuario oldUsuarioidOfVentaListVenta = ventaListVenta.getUsuarioid();
                ventaListVenta.setUsuarioid(usuario);
                ventaListVenta = em.merge(ventaListVenta);
                if (oldUsuarioidOfVentaListVenta != null) {
                    oldUsuarioidOfVentaListVenta.getVentaList().remove(ventaListVenta);
                    oldUsuarioidOfVentaListVenta = em.merge(oldUsuarioidOfVentaListVenta);
                }
            }
            for (Movimiento movimientoListMovimiento : usuario.getMovimientoList()) {
                Usuario oldUsuarioidOfMovimientoListMovimiento = movimientoListMovimiento.getUsuarioid();
                movimientoListMovimiento.setUsuarioid(usuario);
                movimientoListMovimiento = em.merge(movimientoListMovimiento);
                if (oldUsuarioidOfMovimientoListMovimiento != null) {
                    oldUsuarioidOfMovimientoListMovimiento.getMovimientoList().remove(movimientoListMovimiento);
                    oldUsuarioidOfMovimientoListMovimiento = em.merge(oldUsuarioidOfMovimientoListMovimiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Persona personaidOld = persistentUsuario.getPersonaid();
            Persona personaidNew = usuario.getPersonaid();
            TipoUsuario tipoUsuarioidOld = persistentUsuario.getTipoUsuarioid();
            TipoUsuario tipoUsuarioidNew = usuario.getTipoUsuarioid();
            List<Compra> compraListOld = persistentUsuario.getCompraList();
            List<Compra> compraListNew = usuario.getCompraList();
            List<Bitacora> bitacoraListOld = persistentUsuario.getBitacoraList();
            List<Bitacora> bitacoraListNew = usuario.getBitacoraList();
            List<Venta> ventaListOld = persistentUsuario.getVentaList();
            List<Venta> ventaListNew = usuario.getVentaList();
            List<Movimiento> movimientoListOld = persistentUsuario.getMovimientoList();
            List<Movimiento> movimientoListNew = usuario.getMovimientoList();
            List<String> illegalOrphanMessages = null;
            for (Compra compraListOldCompra : compraListOld) {
                if (!compraListNew.contains(compraListOldCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Compra " + compraListOldCompra + " since its usuarioid field is not nullable.");
                }
            }
            for (Bitacora bitacoraListOldBitacora : bitacoraListOld) {
                if (!bitacoraListNew.contains(bitacoraListOldBitacora)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Bitacora " + bitacoraListOldBitacora + " since its usuarioid field is not nullable.");
                }
            }
            for (Venta ventaListOldVenta : ventaListOld) {
                if (!ventaListNew.contains(ventaListOldVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Venta " + ventaListOldVenta + " since its usuarioid field is not nullable.");
                }
            }
            for (Movimiento movimientoListOldMovimiento : movimientoListOld) {
                if (!movimientoListNew.contains(movimientoListOldMovimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Movimiento " + movimientoListOldMovimiento + " since its usuarioid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaidNew != null) {
                personaidNew = em.getReference(personaidNew.getClass(), personaidNew.getId());
                usuario.setPersonaid(personaidNew);
            }
            if (tipoUsuarioidNew != null) {
                tipoUsuarioidNew = em.getReference(tipoUsuarioidNew.getClass(), tipoUsuarioidNew.getId());
                usuario.setTipoUsuarioid(tipoUsuarioidNew);
            }
            List<Compra> attachedCompraListNew = new ArrayList<Compra>();
            for (Compra compraListNewCompraToAttach : compraListNew) {
                compraListNewCompraToAttach = em.getReference(compraListNewCompraToAttach.getClass(), compraListNewCompraToAttach.getId());
                attachedCompraListNew.add(compraListNewCompraToAttach);
            }
            compraListNew = attachedCompraListNew;
            usuario.setCompraList(compraListNew);
            List<Bitacora> attachedBitacoraListNew = new ArrayList<Bitacora>();
            for (Bitacora bitacoraListNewBitacoraToAttach : bitacoraListNew) {
                bitacoraListNewBitacoraToAttach = em.getReference(bitacoraListNewBitacoraToAttach.getClass(), bitacoraListNewBitacoraToAttach.getId());
                attachedBitacoraListNew.add(bitacoraListNewBitacoraToAttach);
            }
            bitacoraListNew = attachedBitacoraListNew;
            usuario.setBitacoraList(bitacoraListNew);
            List<Venta> attachedVentaListNew = new ArrayList<Venta>();
            for (Venta ventaListNewVentaToAttach : ventaListNew) {
                ventaListNewVentaToAttach = em.getReference(ventaListNewVentaToAttach.getClass(), ventaListNewVentaToAttach.getId());
                attachedVentaListNew.add(ventaListNewVentaToAttach);
            }
            ventaListNew = attachedVentaListNew;
            usuario.setVentaList(ventaListNew);
            List<Movimiento> attachedMovimientoListNew = new ArrayList<Movimiento>();
            for (Movimiento movimientoListNewMovimientoToAttach : movimientoListNew) {
                movimientoListNewMovimientoToAttach = em.getReference(movimientoListNewMovimientoToAttach.getClass(), movimientoListNewMovimientoToAttach.getId());
                attachedMovimientoListNew.add(movimientoListNewMovimientoToAttach);
            }
            movimientoListNew = attachedMovimientoListNew;
            usuario.setMovimientoList(movimientoListNew);
            usuario = em.merge(usuario);
            if (personaidOld != null && !personaidOld.equals(personaidNew)) {
                personaidOld.getUsuarioList().remove(usuario);
                personaidOld = em.merge(personaidOld);
            }
            if (personaidNew != null && !personaidNew.equals(personaidOld)) {
                personaidNew.getUsuarioList().add(usuario);
                personaidNew = em.merge(personaidNew);
            }
            if (tipoUsuarioidOld != null && !tipoUsuarioidOld.equals(tipoUsuarioidNew)) {
                tipoUsuarioidOld.getUsuarioList().remove(usuario);
                tipoUsuarioidOld = em.merge(tipoUsuarioidOld);
            }
            if (tipoUsuarioidNew != null && !tipoUsuarioidNew.equals(tipoUsuarioidOld)) {
                tipoUsuarioidNew.getUsuarioList().add(usuario);
                tipoUsuarioidNew = em.merge(tipoUsuarioidNew);
            }
            for (Compra compraListNewCompra : compraListNew) {
                if (!compraListOld.contains(compraListNewCompra)) {
                    Usuario oldUsuarioidOfCompraListNewCompra = compraListNewCompra.getUsuarioid();
                    compraListNewCompra.setUsuarioid(usuario);
                    compraListNewCompra = em.merge(compraListNewCompra);
                    if (oldUsuarioidOfCompraListNewCompra != null && !oldUsuarioidOfCompraListNewCompra.equals(usuario)) {
                        oldUsuarioidOfCompraListNewCompra.getCompraList().remove(compraListNewCompra);
                        oldUsuarioidOfCompraListNewCompra = em.merge(oldUsuarioidOfCompraListNewCompra);
                    }
                }
            }
            for (Bitacora bitacoraListNewBitacora : bitacoraListNew) {
                if (!bitacoraListOld.contains(bitacoraListNewBitacora)) {
                    Usuario oldUsuarioidOfBitacoraListNewBitacora = bitacoraListNewBitacora.getUsuarioid();
                    bitacoraListNewBitacora.setUsuarioid(usuario);
                    bitacoraListNewBitacora = em.merge(bitacoraListNewBitacora);
                    if (oldUsuarioidOfBitacoraListNewBitacora != null && !oldUsuarioidOfBitacoraListNewBitacora.equals(usuario)) {
                        oldUsuarioidOfBitacoraListNewBitacora.getBitacoraList().remove(bitacoraListNewBitacora);
                        oldUsuarioidOfBitacoraListNewBitacora = em.merge(oldUsuarioidOfBitacoraListNewBitacora);
                    }
                }
            }
            for (Venta ventaListNewVenta : ventaListNew) {
                if (!ventaListOld.contains(ventaListNewVenta)) {
                    Usuario oldUsuarioidOfVentaListNewVenta = ventaListNewVenta.getUsuarioid();
                    ventaListNewVenta.setUsuarioid(usuario);
                    ventaListNewVenta = em.merge(ventaListNewVenta);
                    if (oldUsuarioidOfVentaListNewVenta != null && !oldUsuarioidOfVentaListNewVenta.equals(usuario)) {
                        oldUsuarioidOfVentaListNewVenta.getVentaList().remove(ventaListNewVenta);
                        oldUsuarioidOfVentaListNewVenta = em.merge(oldUsuarioidOfVentaListNewVenta);
                    }
                }
            }
            for (Movimiento movimientoListNewMovimiento : movimientoListNew) {
                if (!movimientoListOld.contains(movimientoListNewMovimiento)) {
                    Usuario oldUsuarioidOfMovimientoListNewMovimiento = movimientoListNewMovimiento.getUsuarioid();
                    movimientoListNewMovimiento.setUsuarioid(usuario);
                    movimientoListNewMovimiento = em.merge(movimientoListNewMovimiento);
                    if (oldUsuarioidOfMovimientoListNewMovimiento != null && !oldUsuarioidOfMovimientoListNewMovimiento.equals(usuario)) {
                        oldUsuarioidOfMovimientoListNewMovimiento.getMovimientoList().remove(movimientoListNewMovimiento);
                        oldUsuarioidOfMovimientoListNewMovimiento = em.merge(oldUsuarioidOfMovimientoListNewMovimiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Compra> compraListOrphanCheck = usuario.getCompraList();
            for (Compra compraListOrphanCheckCompra : compraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Compra " + compraListOrphanCheckCompra + " in its compraList field has a non-nullable usuarioid field.");
            }
            List<Bitacora> bitacoraListOrphanCheck = usuario.getBitacoraList();
            for (Bitacora bitacoraListOrphanCheckBitacora : bitacoraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Bitacora " + bitacoraListOrphanCheckBitacora + " in its bitacoraList field has a non-nullable usuarioid field.");
            }
            List<Venta> ventaListOrphanCheck = usuario.getVentaList();
            for (Venta ventaListOrphanCheckVenta : ventaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Venta " + ventaListOrphanCheckVenta + " in its ventaList field has a non-nullable usuarioid field.");
            }
            List<Movimiento> movimientoListOrphanCheck = usuario.getMovimientoList();
            for (Movimiento movimientoListOrphanCheckMovimiento : movimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Movimiento " + movimientoListOrphanCheckMovimiento + " in its movimientoList field has a non-nullable usuarioid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona personaid = usuario.getPersonaid();
            if (personaid != null) {
                personaid.getUsuarioList().remove(usuario);
                personaid = em.merge(personaid);
            }
            TipoUsuario tipoUsuarioid = usuario.getTipoUsuarioid();
            if (tipoUsuarioid != null) {
                tipoUsuarioid.getUsuarioList().remove(usuario);
                tipoUsuarioid = em.merge(tipoUsuarioid);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
