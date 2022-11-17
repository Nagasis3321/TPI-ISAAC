/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControladorClasesJPA;

import ControladorClasesJPA.exceptions.IllegalOrphanException;
import ControladorClasesJPA.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ModeloDB.Foja;
import ModeloDB.Item;
import ModeloDB.DetalleCertificadoPago;
import ModeloDB.DetalleFoja;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author matia
 */
public class DetalleFojaJpaController implements Serializable {

    public DetalleFojaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleFoja detalleFoja) {
        if (detalleFoja.getDetalleCertificadoPagoCollection() == null) {
            detalleFoja.setDetalleCertificadoPagoCollection(new ArrayList<DetalleCertificadoPago>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Foja foja = detalleFoja.getFoja();
            if (foja != null) {
                foja = em.getReference(foja.getClass(), foja.getIdFoja());
                detalleFoja.setFoja(foja);
            }
            Item item = detalleFoja.getItem();
            if (item != null) {
                item = em.getReference(item.getClass(), item.getIdItem());
                detalleFoja.setItem(item);
            }
            Collection<DetalleCertificadoPago> attachedDetalleCertificadoPagoCollection = new ArrayList<DetalleCertificadoPago>();
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach : detalleFoja.getDetalleCertificadoPagoCollection()) {
                detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach = em.getReference(detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach.getClass(), detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach.getIdDetalleCertificadoPago());
                attachedDetalleCertificadoPagoCollection.add(detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach);
            }
            detalleFoja.setDetalleCertificadoPagoCollection(attachedDetalleCertificadoPagoCollection);
            em.persist(detalleFoja);
            if (foja != null) {
                foja.getDetalleFojaCollection().add(detalleFoja);
                foja = em.merge(foja);
            }
            if (item != null) {
                item.getDetalleFojaCollection().add(detalleFoja);
                item = em.merge(item);
            }
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionDetalleCertificadoPago : detalleFoja.getDetalleCertificadoPagoCollection()) {
                DetalleFoja oldDetalleFojaOfDetalleCertificadoPagoCollectionDetalleCertificadoPago = detalleCertificadoPagoCollectionDetalleCertificadoPago.getDetalleFoja();
                detalleCertificadoPagoCollectionDetalleCertificadoPago.setDetalleFoja(detalleFoja);
                detalleCertificadoPagoCollectionDetalleCertificadoPago = em.merge(detalleCertificadoPagoCollectionDetalleCertificadoPago);
                if (oldDetalleFojaOfDetalleCertificadoPagoCollectionDetalleCertificadoPago != null) {
                    oldDetalleFojaOfDetalleCertificadoPagoCollectionDetalleCertificadoPago.getDetalleCertificadoPagoCollection().remove(detalleCertificadoPagoCollectionDetalleCertificadoPago);
                    oldDetalleFojaOfDetalleCertificadoPagoCollectionDetalleCertificadoPago = em.merge(oldDetalleFojaOfDetalleCertificadoPagoCollectionDetalleCertificadoPago);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleFoja detalleFoja) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleFoja persistentDetalleFoja = em.find(DetalleFoja.class, detalleFoja.getIdDetalleFoja());
            Foja fojaOld = persistentDetalleFoja.getFoja();
            Foja fojaNew = detalleFoja.getFoja();
            Item itemOld = persistentDetalleFoja.getItem();
            Item itemNew = detalleFoja.getItem();
            Collection<DetalleCertificadoPago> detalleCertificadoPagoCollectionOld = persistentDetalleFoja.getDetalleCertificadoPagoCollection();
            Collection<DetalleCertificadoPago> detalleCertificadoPagoCollectionNew = detalleFoja.getDetalleCertificadoPagoCollection();
            List<String> illegalOrphanMessages = null;
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionOldDetalleCertificadoPago : detalleCertificadoPagoCollectionOld) {
                if (!detalleCertificadoPagoCollectionNew.contains(detalleCertificadoPagoCollectionOldDetalleCertificadoPago)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleCertificadoPago " + detalleCertificadoPagoCollectionOldDetalleCertificadoPago + " since its detalleFoja field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fojaNew != null) {
                fojaNew = em.getReference(fojaNew.getClass(), fojaNew.getIdFoja());
                detalleFoja.setFoja(fojaNew);
            }
            if (itemNew != null) {
                itemNew = em.getReference(itemNew.getClass(), itemNew.getIdItem());
                detalleFoja.setItem(itemNew);
            }
            Collection<DetalleCertificadoPago> attachedDetalleCertificadoPagoCollectionNew = new ArrayList<DetalleCertificadoPago>();
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach : detalleCertificadoPagoCollectionNew) {
                detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach = em.getReference(detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach.getClass(), detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach.getIdDetalleCertificadoPago());
                attachedDetalleCertificadoPagoCollectionNew.add(detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach);
            }
            detalleCertificadoPagoCollectionNew = attachedDetalleCertificadoPagoCollectionNew;
            detalleFoja.setDetalleCertificadoPagoCollection(detalleCertificadoPagoCollectionNew);
            detalleFoja = em.merge(detalleFoja);
            if (fojaOld != null && !fojaOld.equals(fojaNew)) {
                fojaOld.getDetalleFojaCollection().remove(detalleFoja);
                fojaOld = em.merge(fojaOld);
            }
            if (fojaNew != null && !fojaNew.equals(fojaOld)) {
                fojaNew.getDetalleFojaCollection().add(detalleFoja);
                fojaNew = em.merge(fojaNew);
            }
            if (itemOld != null && !itemOld.equals(itemNew)) {
                itemOld.getDetalleFojaCollection().remove(detalleFoja);
                itemOld = em.merge(itemOld);
            }
            if (itemNew != null && !itemNew.equals(itemOld)) {
                itemNew.getDetalleFojaCollection().add(detalleFoja);
                itemNew = em.merge(itemNew);
            }
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionNewDetalleCertificadoPago : detalleCertificadoPagoCollectionNew) {
                if (!detalleCertificadoPagoCollectionOld.contains(detalleCertificadoPagoCollectionNewDetalleCertificadoPago)) {
                    DetalleFoja oldDetalleFojaOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago = detalleCertificadoPagoCollectionNewDetalleCertificadoPago.getDetalleFoja();
                    detalleCertificadoPagoCollectionNewDetalleCertificadoPago.setDetalleFoja(detalleFoja);
                    detalleCertificadoPagoCollectionNewDetalleCertificadoPago = em.merge(detalleCertificadoPagoCollectionNewDetalleCertificadoPago);
                    if (oldDetalleFojaOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago != null && !oldDetalleFojaOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago.equals(detalleFoja)) {
                        oldDetalleFojaOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago.getDetalleCertificadoPagoCollection().remove(detalleCertificadoPagoCollectionNewDetalleCertificadoPago);
                        oldDetalleFojaOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago = em.merge(oldDetalleFojaOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleFoja.getIdDetalleFoja();
                if (findDetalleFoja(id) == null) {
                    throw new NonexistentEntityException("The detalleFoja with id " + id + " no longer exists.");
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
            DetalleFoja detalleFoja;
            try {
                detalleFoja = em.getReference(DetalleFoja.class, id);
                detalleFoja.getIdDetalleFoja();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleFoja with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DetalleCertificadoPago> detalleCertificadoPagoCollectionOrphanCheck = detalleFoja.getDetalleCertificadoPagoCollection();
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionOrphanCheckDetalleCertificadoPago : detalleCertificadoPagoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DetalleFoja (" + detalleFoja + ") cannot be destroyed since the DetalleCertificadoPago " + detalleCertificadoPagoCollectionOrphanCheckDetalleCertificadoPago + " in its detalleCertificadoPagoCollection field has a non-nullable detalleFoja field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Foja foja = detalleFoja.getFoja();
            if (foja != null) {
                foja.getDetalleFojaCollection().remove(detalleFoja);
                foja = em.merge(foja);
            }
            Item item = detalleFoja.getItem();
            if (item != null) {
                item.getDetalleFojaCollection().remove(detalleFoja);
                item = em.merge(item);
            }
            em.remove(detalleFoja);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleFoja> findDetalleFojaEntities() {
        return findDetalleFojaEntities(true, -1, -1);
    }

    public List<DetalleFoja> findDetalleFojaEntities(int maxResults, int firstResult) {
        return findDetalleFojaEntities(false, maxResults, firstResult);
    }

    private List<DetalleFoja> findDetalleFojaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleFoja.class));
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

    public DetalleFoja findDetalleFoja(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleFoja.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleFojaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleFoja> rt = cq.from(DetalleFoja.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
