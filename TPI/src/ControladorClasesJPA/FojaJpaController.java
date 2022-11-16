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
import ModeloDB.CertificadoPago;
import ModeloDB.Obra;
import ModeloDB.DetalleFoja;
import ModeloDB.Foja;
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
public class FojaJpaController implements Serializable {

    public FojaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Foja foja) {
        if (foja.getDetalleFojaCollection() == null) {
            foja.setDetalleFojaCollection(new ArrayList<DetalleFoja>());
        }
        if (foja.getCertificadoPagoCollection() == null) {
            foja.setCertificadoPagoCollection(new ArrayList<CertificadoPago>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CertificadoPago certificadoPago = foja.getCertificadoPago();
            if (certificadoPago != null) {
                certificadoPago = em.getReference(certificadoPago.getClass(), certificadoPago.getIdCertificadoPago());
                foja.setCertificadoPago(certificadoPago);
            }
            Obra obra = foja.getObra();
            if (obra != null) {
                obra = em.getReference(obra.getClass(), obra.getIdObra());
                foja.setObra(obra);
            }
            Collection<DetalleFoja> attachedDetalleFojaCollection = new ArrayList<DetalleFoja>();
            for (DetalleFoja detalleFojaCollectionDetalleFojaToAttach : foja.getDetalleFojaCollection()) {
                detalleFojaCollectionDetalleFojaToAttach = em.getReference(detalleFojaCollectionDetalleFojaToAttach.getClass(), detalleFojaCollectionDetalleFojaToAttach.getIdDetalleFoja());
                attachedDetalleFojaCollection.add(detalleFojaCollectionDetalleFojaToAttach);
            }
            foja.setDetalleFojaCollection(attachedDetalleFojaCollection);
            Collection<CertificadoPago> attachedCertificadoPagoCollection = new ArrayList<CertificadoPago>();
            for (CertificadoPago certificadoPagoCollectionCertificadoPagoToAttach : foja.getCertificadoPagoCollection()) {
                certificadoPagoCollectionCertificadoPagoToAttach = em.getReference(certificadoPagoCollectionCertificadoPagoToAttach.getClass(), certificadoPagoCollectionCertificadoPagoToAttach.getIdCertificadoPago());
                attachedCertificadoPagoCollection.add(certificadoPagoCollectionCertificadoPagoToAttach);
            }
            foja.setCertificadoPagoCollection(attachedCertificadoPagoCollection);
            em.persist(foja);
            if (certificadoPago != null) {
                certificadoPago.getFojaCollection().add(foja);
                certificadoPago = em.merge(certificadoPago);
            }
            if (obra != null) {
                obra.getFojaCollection().add(foja);
                obra = em.merge(obra);
            }
            for (DetalleFoja detalleFojaCollectionDetalleFoja : foja.getDetalleFojaCollection()) {
                Foja oldFojaOfDetalleFojaCollectionDetalleFoja = detalleFojaCollectionDetalleFoja.getFoja();
                detalleFojaCollectionDetalleFoja.setFoja(foja);
                detalleFojaCollectionDetalleFoja = em.merge(detalleFojaCollectionDetalleFoja);
                if (oldFojaOfDetalleFojaCollectionDetalleFoja != null) {
                    oldFojaOfDetalleFojaCollectionDetalleFoja.getDetalleFojaCollection().remove(detalleFojaCollectionDetalleFoja);
                    oldFojaOfDetalleFojaCollectionDetalleFoja = em.merge(oldFojaOfDetalleFojaCollectionDetalleFoja);
                }
            }
            for (CertificadoPago certificadoPagoCollectionCertificadoPago : foja.getCertificadoPagoCollection()) {
                Foja oldFojaOfCertificadoPagoCollectionCertificadoPago = certificadoPagoCollectionCertificadoPago.getFoja();
                certificadoPagoCollectionCertificadoPago.setFoja(foja);
                certificadoPagoCollectionCertificadoPago = em.merge(certificadoPagoCollectionCertificadoPago);
                if (oldFojaOfCertificadoPagoCollectionCertificadoPago != null) {
                    oldFojaOfCertificadoPagoCollectionCertificadoPago.getCertificadoPagoCollection().remove(certificadoPagoCollectionCertificadoPago);
                    oldFojaOfCertificadoPagoCollectionCertificadoPago = em.merge(oldFojaOfCertificadoPagoCollectionCertificadoPago);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Foja foja) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Foja persistentFoja = em.find(Foja.class, foja.getIdFoja());
            CertificadoPago certificadoPagoOld = persistentFoja.getCertificadoPago();
            CertificadoPago certificadoPagoNew = foja.getCertificadoPago();
            Obra obraOld = persistentFoja.getObra();
            Obra obraNew = foja.getObra();
            Collection<DetalleFoja> detalleFojaCollectionOld = persistentFoja.getDetalleFojaCollection();
            Collection<DetalleFoja> detalleFojaCollectionNew = foja.getDetalleFojaCollection();
            Collection<CertificadoPago> certificadoPagoCollectionOld = persistentFoja.getCertificadoPagoCollection();
            Collection<CertificadoPago> certificadoPagoCollectionNew = foja.getCertificadoPagoCollection();
            List<String> illegalOrphanMessages = null;
            for (DetalleFoja detalleFojaCollectionOldDetalleFoja : detalleFojaCollectionOld) {
                if (!detalleFojaCollectionNew.contains(detalleFojaCollectionOldDetalleFoja)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleFoja " + detalleFojaCollectionOldDetalleFoja + " since its foja field is not nullable.");
                }
            }
            for (CertificadoPago certificadoPagoCollectionOldCertificadoPago : certificadoPagoCollectionOld) {
                if (!certificadoPagoCollectionNew.contains(certificadoPagoCollectionOldCertificadoPago)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CertificadoPago " + certificadoPagoCollectionOldCertificadoPago + " since its foja field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (certificadoPagoNew != null) {
                certificadoPagoNew = em.getReference(certificadoPagoNew.getClass(), certificadoPagoNew.getIdCertificadoPago());
                foja.setCertificadoPago(certificadoPagoNew);
            }
            if (obraNew != null) {
                obraNew = em.getReference(obraNew.getClass(), obraNew.getIdObra());
                foja.setObra(obraNew);
            }
            Collection<DetalleFoja> attachedDetalleFojaCollectionNew = new ArrayList<DetalleFoja>();
            for (DetalleFoja detalleFojaCollectionNewDetalleFojaToAttach : detalleFojaCollectionNew) {
                detalleFojaCollectionNewDetalleFojaToAttach = em.getReference(detalleFojaCollectionNewDetalleFojaToAttach.getClass(), detalleFojaCollectionNewDetalleFojaToAttach.getIdDetalleFoja());
                attachedDetalleFojaCollectionNew.add(detalleFojaCollectionNewDetalleFojaToAttach);
            }
            detalleFojaCollectionNew = attachedDetalleFojaCollectionNew;
            foja.setDetalleFojaCollection(detalleFojaCollectionNew);
            Collection<CertificadoPago> attachedCertificadoPagoCollectionNew = new ArrayList<CertificadoPago>();
            for (CertificadoPago certificadoPagoCollectionNewCertificadoPagoToAttach : certificadoPagoCollectionNew) {
                certificadoPagoCollectionNewCertificadoPagoToAttach = em.getReference(certificadoPagoCollectionNewCertificadoPagoToAttach.getClass(), certificadoPagoCollectionNewCertificadoPagoToAttach.getIdCertificadoPago());
                attachedCertificadoPagoCollectionNew.add(certificadoPagoCollectionNewCertificadoPagoToAttach);
            }
            certificadoPagoCollectionNew = attachedCertificadoPagoCollectionNew;
            foja.setCertificadoPagoCollection(certificadoPagoCollectionNew);
            foja = em.merge(foja);
            if (certificadoPagoOld != null && !certificadoPagoOld.equals(certificadoPagoNew)) {
                certificadoPagoOld.getFojaCollection().remove(foja);
                certificadoPagoOld = em.merge(certificadoPagoOld);
            }
            if (certificadoPagoNew != null && !certificadoPagoNew.equals(certificadoPagoOld)) {
                certificadoPagoNew.getFojaCollection().add(foja);
                certificadoPagoNew = em.merge(certificadoPagoNew);
            }
            if (obraOld != null && !obraOld.equals(obraNew)) {
                obraOld.getFojaCollection().remove(foja);
                obraOld = em.merge(obraOld);
            }
            if (obraNew != null && !obraNew.equals(obraOld)) {
                obraNew.getFojaCollection().add(foja);
                obraNew = em.merge(obraNew);
            }
            for (DetalleFoja detalleFojaCollectionNewDetalleFoja : detalleFojaCollectionNew) {
                if (!detalleFojaCollectionOld.contains(detalleFojaCollectionNewDetalleFoja)) {
                    Foja oldFojaOfDetalleFojaCollectionNewDetalleFoja = detalleFojaCollectionNewDetalleFoja.getFoja();
                    detalleFojaCollectionNewDetalleFoja.setFoja(foja);
                    detalleFojaCollectionNewDetalleFoja = em.merge(detalleFojaCollectionNewDetalleFoja);
                    if (oldFojaOfDetalleFojaCollectionNewDetalleFoja != null && !oldFojaOfDetalleFojaCollectionNewDetalleFoja.equals(foja)) {
                        oldFojaOfDetalleFojaCollectionNewDetalleFoja.getDetalleFojaCollection().remove(detalleFojaCollectionNewDetalleFoja);
                        oldFojaOfDetalleFojaCollectionNewDetalleFoja = em.merge(oldFojaOfDetalleFojaCollectionNewDetalleFoja);
                    }
                }
            }
            for (CertificadoPago certificadoPagoCollectionNewCertificadoPago : certificadoPagoCollectionNew) {
                if (!certificadoPagoCollectionOld.contains(certificadoPagoCollectionNewCertificadoPago)) {
                    Foja oldFojaOfCertificadoPagoCollectionNewCertificadoPago = certificadoPagoCollectionNewCertificadoPago.getFoja();
                    certificadoPagoCollectionNewCertificadoPago.setFoja(foja);
                    certificadoPagoCollectionNewCertificadoPago = em.merge(certificadoPagoCollectionNewCertificadoPago);
                    if (oldFojaOfCertificadoPagoCollectionNewCertificadoPago != null && !oldFojaOfCertificadoPagoCollectionNewCertificadoPago.equals(foja)) {
                        oldFojaOfCertificadoPagoCollectionNewCertificadoPago.getCertificadoPagoCollection().remove(certificadoPagoCollectionNewCertificadoPago);
                        oldFojaOfCertificadoPagoCollectionNewCertificadoPago = em.merge(oldFojaOfCertificadoPagoCollectionNewCertificadoPago);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = foja.getIdFoja();
                if (findFoja(id) == null) {
                    throw new NonexistentEntityException("The foja with id " + id + " no longer exists.");
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
            Foja foja;
            try {
                foja = em.getReference(Foja.class, id);
                foja.getIdFoja();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The foja with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DetalleFoja> detalleFojaCollectionOrphanCheck = foja.getDetalleFojaCollection();
            for (DetalleFoja detalleFojaCollectionOrphanCheckDetalleFoja : detalleFojaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Foja (" + foja + ") cannot be destroyed since the DetalleFoja " + detalleFojaCollectionOrphanCheckDetalleFoja + " in its detalleFojaCollection field has a non-nullable foja field.");
            }
            Collection<CertificadoPago> certificadoPagoCollectionOrphanCheck = foja.getCertificadoPagoCollection();
            for (CertificadoPago certificadoPagoCollectionOrphanCheckCertificadoPago : certificadoPagoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Foja (" + foja + ") cannot be destroyed since the CertificadoPago " + certificadoPagoCollectionOrphanCheckCertificadoPago + " in its certificadoPagoCollection field has a non-nullable foja field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CertificadoPago certificadoPago = foja.getCertificadoPago();
            if (certificadoPago != null) {
                certificadoPago.getFojaCollection().remove(foja);
                certificadoPago = em.merge(certificadoPago);
            }
            Obra obra = foja.getObra();
            if (obra != null) {
                obra.getFojaCollection().remove(foja);
                obra = em.merge(obra);
            }
            em.remove(foja);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Foja> findFojaEntities() {
        return findFojaEntities(true, -1, -1);
    }

    public List<Foja> findFojaEntities(int maxResults, int firstResult) {
        return findFojaEntities(false, maxResults, firstResult);
    }

    private List<Foja> findFojaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Foja.class));
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

    public Foja findFoja(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Foja.class, id);
        } finally {
            em.close();
        }
    }

    public int getFojaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Foja> rt = cq.from(Foja.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
