/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControladorClasesJPA;

import ControladorClasesJPA.exceptions.IllegalOrphanException;
import ControladorClasesJPA.exceptions.NonexistentEntityException;
import ModeloDB.CertificadoPago;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ModeloDB.Foja;
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
public class CertificadoPagoJpaController implements Serializable {

    public CertificadoPagoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CertificadoPago certificadoPago) {
        if (certificadoPago.getDetalleCertificadoPagoCollection() == null) {
            certificadoPago.setDetalleCertificadoPagoCollection(new ArrayList<DetalleCertificadoPago>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Foja foja = certificadoPago.getFoja();
            if (foja != null) {
                foja = em.getReference(foja.getClass(), foja.getIdFoja());
                certificadoPago.setFoja(foja);
            }
            Collection<DetalleCertificadoPago> attachedDetalleCertificadoPagoCollection = new ArrayList<DetalleCertificadoPago>();
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach : certificadoPago.getDetalleCertificadoPagoCollection()) {
                detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach = em.getReference(detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach.getClass(), detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach.getIdDetalleCertificadoPago());
                attachedDetalleCertificadoPagoCollection.add(detalleCertificadoPagoCollectionDetalleCertificadoPagoToAttach);
            }
            certificadoPago.setDetalleCertificadoPagoCollection(attachedDetalleCertificadoPagoCollection);
            em.persist(certificadoPago);
            if (foja != null) {
                foja.getCertificadoPagoCollection().add(certificadoPago);
                foja = em.merge(foja);
            }
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionDetalleCertificadoPago : certificadoPago.getDetalleCertificadoPagoCollection()) {
                CertificadoPago oldCertificadoPagoOfDetalleCertificadoPagoCollectionDetalleCertificadoPago = detalleCertificadoPagoCollectionDetalleCertificadoPago.getCertificadoPago();
                detalleCertificadoPagoCollectionDetalleCertificadoPago.setCertificadoPago(certificadoPago);
                detalleCertificadoPagoCollectionDetalleCertificadoPago = em.merge(detalleCertificadoPagoCollectionDetalleCertificadoPago);
                if (oldCertificadoPagoOfDetalleCertificadoPagoCollectionDetalleCertificadoPago != null) {
                    oldCertificadoPagoOfDetalleCertificadoPagoCollectionDetalleCertificadoPago.getDetalleCertificadoPagoCollection().remove(detalleCertificadoPagoCollectionDetalleCertificadoPago);
                    oldCertificadoPagoOfDetalleCertificadoPagoCollectionDetalleCertificadoPago = em.merge(oldCertificadoPagoOfDetalleCertificadoPagoCollectionDetalleCertificadoPago);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CertificadoPago certificadoPago) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CertificadoPago persistentCertificadoPago = em.find(CertificadoPago.class, certificadoPago.getIdCertificadoPago());
            Foja fojaOld = persistentCertificadoPago.getFoja();
            Foja fojaNew = certificadoPago.getFoja();
            Collection<DetalleCertificadoPago> detalleCertificadoPagoCollectionOld = persistentCertificadoPago.getDetalleCertificadoPagoCollection();
            Collection<DetalleCertificadoPago> detalleCertificadoPagoCollectionNew = certificadoPago.getDetalleCertificadoPagoCollection();
            List<String> illegalOrphanMessages = null;
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionOldDetalleCertificadoPago : detalleCertificadoPagoCollectionOld) {
                if (!detalleCertificadoPagoCollectionNew.contains(detalleCertificadoPagoCollectionOldDetalleCertificadoPago)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleCertificadoPago " + detalleCertificadoPagoCollectionOldDetalleCertificadoPago + " since its certificadoPago field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fojaNew != null) {
                fojaNew = em.getReference(fojaNew.getClass(), fojaNew.getIdFoja());
                certificadoPago.setFoja(fojaNew);
            }
            Collection<DetalleCertificadoPago> attachedDetalleCertificadoPagoCollectionNew = new ArrayList<DetalleCertificadoPago>();
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach : detalleCertificadoPagoCollectionNew) {
                detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach = em.getReference(detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach.getClass(), detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach.getIdDetalleCertificadoPago());
                attachedDetalleCertificadoPagoCollectionNew.add(detalleCertificadoPagoCollectionNewDetalleCertificadoPagoToAttach);
            }
            detalleCertificadoPagoCollectionNew = attachedDetalleCertificadoPagoCollectionNew;
            certificadoPago.setDetalleCertificadoPagoCollection(detalleCertificadoPagoCollectionNew);
            certificadoPago = em.merge(certificadoPago);
            if (fojaOld != null && !fojaOld.equals(fojaNew)) {
                fojaOld.getCertificadoPagoCollection().remove(certificadoPago);
                fojaOld = em.merge(fojaOld);
            }
            if (fojaNew != null && !fojaNew.equals(fojaOld)) {
                fojaNew.getCertificadoPagoCollection().add(certificadoPago);
                fojaNew = em.merge(fojaNew);
            }
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionNewDetalleCertificadoPago : detalleCertificadoPagoCollectionNew) {
                if (!detalleCertificadoPagoCollectionOld.contains(detalleCertificadoPagoCollectionNewDetalleCertificadoPago)) {
                    CertificadoPago oldCertificadoPagoOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago = detalleCertificadoPagoCollectionNewDetalleCertificadoPago.getCertificadoPago();
                    detalleCertificadoPagoCollectionNewDetalleCertificadoPago.setCertificadoPago(certificadoPago);
                    detalleCertificadoPagoCollectionNewDetalleCertificadoPago = em.merge(detalleCertificadoPagoCollectionNewDetalleCertificadoPago);
                    if (oldCertificadoPagoOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago != null && !oldCertificadoPagoOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago.equals(certificadoPago)) {
                        oldCertificadoPagoOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago.getDetalleCertificadoPagoCollection().remove(detalleCertificadoPagoCollectionNewDetalleCertificadoPago);
                        oldCertificadoPagoOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago = em.merge(oldCertificadoPagoOfDetalleCertificadoPagoCollectionNewDetalleCertificadoPago);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = certificadoPago.getIdCertificadoPago();
                if (findCertificadoPago(id) == null) {
                    throw new NonexistentEntityException("The certificadoPago with id " + id + " no longer exists.");
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
            CertificadoPago certificadoPago;
            try {
                certificadoPago = em.getReference(CertificadoPago.class, id);
                certificadoPago.getIdCertificadoPago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The certificadoPago with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DetalleCertificadoPago> detalleCertificadoPagoCollectionOrphanCheck = certificadoPago.getDetalleCertificadoPagoCollection();
            for (DetalleCertificadoPago detalleCertificadoPagoCollectionOrphanCheckDetalleCertificadoPago : detalleCertificadoPagoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CertificadoPago (" + certificadoPago + ") cannot be destroyed since the DetalleCertificadoPago " + detalleCertificadoPagoCollectionOrphanCheckDetalleCertificadoPago + " in its detalleCertificadoPagoCollection field has a non-nullable certificadoPago field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Foja foja = certificadoPago.getFoja();
            if (foja != null) {
                foja.getCertificadoPagoCollection().remove(certificadoPago);
                foja = em.merge(foja);
            }
            em.remove(certificadoPago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CertificadoPago> findCertificadoPagoEntities() {
        return findCertificadoPagoEntities(true, -1, -1);
    }

    public List<CertificadoPago> findCertificadoPagoEntities(int maxResults, int firstResult) {
        return findCertificadoPagoEntities(false, maxResults, firstResult);
    }

    private List<CertificadoPago> findCertificadoPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CertificadoPago.class));
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

    public CertificadoPago findCertificadoPago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CertificadoPago.class, id);
        } finally {
            em.close();
        }
    }

    public int getCertificadoPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CertificadoPago> rt = cq.from(CertificadoPago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
