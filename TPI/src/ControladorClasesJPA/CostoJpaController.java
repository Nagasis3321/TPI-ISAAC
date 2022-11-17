/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControladorClasesJPA;

import ControladorClasesJPA.exceptions.IllegalOrphanException;
import ControladorClasesJPA.exceptions.NonexistentEntityException;
import ModeloDB.Costo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ModeloDB.Item;
import ModeloDB.DetalleCertificadoPago;
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
public class CostoJpaController implements Serializable {

    public CostoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Costo costo) {
        if (costo.getDetalleCertificadoPagoCollection() == null) {
            costo.setDetalleCertificadoPagoCollection(new ArrayList<DetalleCertificadoPago>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Item item = costo.getItem();
            if (item != null) {
                item = em.getReference(item.getClass(), item.getIdItem());
                costo.setItem(item);
            }
            Collection<DetalleCertificadoPago> attachedDetalleCertificadoPagoCollection = new ArrayList<DetalleCertificadoPago>();
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach : costo.getDetalleCertificadoPagoCollection()) {
                detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach = em.getReference(detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach.getClass(), detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach.getIdDetalleCertificadoPago());
                attachedDetalleCertificadoPagoCollection.add(detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach);
            }
            costo.setDetalleCertificadoPagoCollection(attachedDetalleCertificadoPagoCollection);
            em.persist(costo);
            if (item != null) {
                item.getCostoCollection().add(costo);
                item = em.merge(item);
            }
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionDetalleCertificadoPago : costo.getDetalleCertificadoPagoCollection()) {
                Costo oldCostoOfDetalleCertificadoPagoCollectionDetalleCertificadoPago = detalleCertificadoPagoCollectionDetalleCertificadoPago.getCosto();
                detalleCertificadoPagoCollectionDetalleCertificadoPago.setCosto(costo);
                detalleCertificadoPagoCollectionDetalleCertificadoPago = em.merge(detalleCertificadoPagoCollectionDetalleCertificadoPago);
                if (oldCostoOfDetalleCertificadoPagoCollectionDetalleCertificadoPago != null) {
                    oldCostoOfDetalleCertificadoPagoCollectionDetalleCertificadoPago.getDetalleCertificadoPagoCollection().remove(detalleCertificadoPagoCollectionDetalleCertificadoPago);
                    oldCostoOfDetalleCertificadoPagoCollectionDetalleCertificadoPago = em.merge(oldCostoOfDetalleCertificadoPagoCollectionDetalleCertificadoPago);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Costo costo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Costo persistentCosto = em.find(Costo.class, costo.getIdCosto());
            Item itemOld = persistentCosto.getItem();
            Item itemNew = costo.getItem();
            Collection<DetalleCertificadoPago> detalleCertificadoPagoCollectionOld = persistentCosto.getDetalleCertificadoPagoCollection();
            Collection<DetalleCertificadoPago> detalleCertificadoPagoCollectionNew = costo.getDetalleCertificadoPagoCollection();
            List<String> illegalOrphanMessages = null;
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionOldDetalleCertificadoPago : detalleCertificadoPagoCollectionOld) {
                if (!detalleCertificadoPagoCollectionNew.contains(detalleCertificadoPagoCollectionOldDetalleCertificadoPago)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleCertificadoPago " + detalleCertificadoPagoCollectionOldDetalleCertificadoPago + " since its costo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemNew != null) {
                itemNew = em.getReference(itemNew.getClass(), itemNew.getIdItem());
                costo.setItem(itemNew);
            }
            Collection<DetalleCertificadoPago> attachedDetalleCertificadoPagoCollectionNew = new ArrayList<DetalleCertificadoPago>();
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach : detalleCertificadoPagoCollectionNew) {
                detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach = em.getReference(detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach.getClass(), detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach.getIdDetalleCertificadoPago());
                attachedDetalleCertificadoPagoCollectionNew.add(detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach);
            }
            detalleCertificadoPagoCollectionNew = attachedDetalleCertificadoPagoCollectionNew;
            costo.setDetalleCertificadoPagoCollection(detalleCertificadoPagoCollectionNew);
            costo = em.merge(costo);
            if (itemOld != null && !itemOld.equals(itemNew)) {
                itemOld.getCostoCollection().remove(costo);
                itemOld = em.merge(itemOld);
            }
            if (itemNew != null && !itemNew.equals(itemOld)) {
                itemNew.getCostoCollection().add(costo);
                itemNew = em.merge(itemNew);
            }
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionNewDetalleCertificadoPago : detalleCertificadoPagoCollectionNew) {
                if (!detalleCertificadoPagoCollectionOld.contains(detalleCertificadoPagoCollectionNewDetalleCertificadoPago)) {
                    Costo oldCostoOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago = detalleCertificadoPagoCollectionNewDetalleCertificadoPago.getCosto();
                    detalleCertificadoPagoCollectionNewDetalleCertificadoPago.setCosto(costo);
                    detalleCertificadoPagoCollectionNewDetalleCertificadoPago = em.merge(detalleCertificadoPagoCollectionNewDetalleCertificadoPago);
                    if (oldCostoOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago != null && !oldCostoOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago.equals(costo)) {
                        oldCostoOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago.getDetalleCertificadoPagoCollection().remove(detalleCertificadoPagoCollectionNewDetalleCertificadoPago);
                        oldCostoOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago = em.merge(oldCostoOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = costo.getIdCosto();
                if (findCosto(id) == null) {
                    throw new NonexistentEntityException("The costo with id " + id + " no longer exists.");
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
            Costo costo;
            try {
                costo = em.getReference(Costo.class, id);
                costo.getIdCosto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The costo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DetalleCertificadoPago> detalleCertificadoPagoCollectionOrphanCheck = costo.getDetalleCertificadoPagoCollection();
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionOrphanCheckDetalleCertificadoPago : detalleCertificadoPagoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Costo (" + costo + ") cannot be destroyed since the DetalleCertificadoPago " + detalleCertificadoPagoCollectionOrphanCheckDetalleCertificadoPago + " in its detalleCertificadoPagoCollection field has a non-nullable costo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Item item = costo.getItem();
            if (item != null) {
                item.getCostoCollection().remove(costo);
                item = em.merge(item);
            }
            em.remove(costo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Costo> findCostoEntities() {
        return findCostoEntities(true, -1, -1);
    }

    public List<Costo> findCostoEntities(int maxResults, int firstResult) {
        return findCostoEntities(false, maxResults, firstResult);
    }

    private List<Costo> findCostoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Costo.class));
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

    public Costo findCosto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Costo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCostoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Costo> rt = cq.from(Costo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
