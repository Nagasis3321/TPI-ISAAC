/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControladorClasesJPA;

import ControladorClasesJPA.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ModeloDB.CertificadoPago;
import ModeloDB.Costo;
import ModeloDB.DetalleCertificadoPago;
import ModeloDB.DetalleFoja;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author matia
 */
public class DetalleCertificadoPagoJpaController implements Serializable {

    public DetalleCertificadoPagoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleCertificadoPago detalleCertificadoPago) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CertificadoPago certificadoPago = detalleCertificadoPago.getCertificadoPago();
            if (certificadoPago != null) {
                certificadoPago = em.getReference(certificadoPago.getClass(), certificadoPago.getIdCertificadoPago());
                detalleCertificadoPago.setCertificadoPago(certificadoPago);
            }
            Costo costo = detalleCertificadoPago.getCosto();
            if (costo != null) {
                costo = em.getReference(costo.getClass(), costo.getIdCosto());
                detalleCertificadoPago.setCosto(costo);
            }
            DetalleFoja detalleFoja = detalleCertificadoPago.getDetalleFoja();
            if (detalleFoja != null) {
                detalleFoja = em.getReference(detalleFoja.getClass(), detalleFoja.getIdDetalleFoja());
                detalleCertificadoPago.setDetalleFoja(detalleFoja);
            }
            em.persist(detalleCertificadoPago);
            if (certificadoPago != null) {
                certificadoPago.getDetalleCertificadoPagoCollection().add(detalleCertificadoPago);
                certificadoPago = em.merge(certificadoPago);
            }
            if (costo != null) {
                costo.getDetalleCertificadoPagoCollection().add(detalleCertificadoPago);
                costo = em.merge(costo);
            }
            if (detalleFoja != null) {
                detalleFoja.getDetalleCertificadoPagoCollection().add(detalleCertificadoPago);
                detalleFoja = em.merge(detalleFoja);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleCertificadoPago detalleCertificadoPago) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleCertificadoPago persistentDetalleCertificadoPago = em.find(DetalleCertificadoPago.class, detalleCertificadoPago.getIdDetalleCertificadoPago());
            CertificadoPago certificadoPagoOld = persistentDetalleCertificadoPago.getCertificadoPago();
            CertificadoPago certificadoPagoNew = detalleCertificadoPago.getCertificadoPago();
            Costo costoOld = persistentDetalleCertificadoPago.getCosto();
            Costo costoNew = detalleCertificadoPago.getCosto();
            DetalleFoja detalleFojaOld = persistentDetalleCertificadoPago.getDetalleFoja();
            DetalleFoja detalleFojaNew = detalleCertificadoPago.getDetalleFoja();
            if (certificadoPagoNew != null) {
                certificadoPagoNew = em.getReference(certificadoPagoNew.getClass(), certificadoPagoNew.getIdCertificadoPago());
                detalleCertificadoPago.setCertificadoPago(certificadoPagoNew);
            }
            if (costoNew != null) {
                costoNew = em.getReference(costoNew.getClass(), costoNew.getIdCosto());
                detalleCertificadoPago.setCosto(costoNew);
            }
            if (detalleFojaNew != null) {
                detalleFojaNew = em.getReference(detalleFojaNew.getClass(), detalleFojaNew.getIdDetalleFoja());
                detalleCertificadoPago.setDetalleFoja(detalleFojaNew);
            }
            detalleCertificadoPago = em.merge(detalleCertificadoPago);
            if (certificadoPagoOld != null && !certificadoPagoOld.equals(certificadoPagoNew)) {
                certificadoPagoOld.getDetalleCertificadoPagoCollection().remove(detalleCertificadoPago);
                certificadoPagoOld = em.merge(certificadoPagoOld);
            }
            if (certificadoPagoNew != null && !certificadoPagoNew.equals(certificadoPagoOld)) {
                certificadoPagoNew.getDetalleCertificadoPagoCollection().add(detalleCertificadoPago);
                certificadoPagoNew = em.merge(certificadoPagoNew);
            }
            if (costoOld != null && !costoOld.equals(costoNew)) {
                costoOld.getDetalleCertificadoPagoCollection().remove(detalleCertificadoPago);
                costoOld = em.merge(costoOld);
            }
            if (costoNew != null && !costoNew.equals(costoOld)) {
                costoNew.getDetalleCertificadoPagoCollection().add(detalleCertificadoPago);
                costoNew = em.merge(costoNew);
            }
            if (detalleFojaOld != null && !detalleFojaOld.equals(detalleFojaNew)) {
                detalleFojaOld.getDetalleCertificadoPagoCollection().remove(detalleCertificadoPago);
                detalleFojaOld = em.merge(detalleFojaOld);
            }
            if (detalleFojaNew != null && !detalleFojaNew.equals(detalleFojaOld)) {
                detalleFojaNew.getDetalleCertificadoPagoCollection().add(detalleCertificadoPago);
                detalleFojaNew = em.merge(detalleFojaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleCertificadoPago.getIdDetalleCertificadoPago();
                if (findDetalleCertificadoPago(id) == null) {
                    throw new NonexistentEntityException("The detalleCertificadoPago with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleCertificadoPago detalleCertificadoPago;
            try {
                detalleCertificadoPago = em.getReference(DetalleCertificadoPago.class, id);
                detalleCertificadoPago.getIdDetalleCertificadoPago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleCertificadoPago with id " + id + " no longer exists.", enfe);
            }
            CertificadoPago certificadoPago = detalleCertificadoPago.getCertificadoPago();
            if (certificadoPago != null) {
                certificadoPago.getDetalleCertificadoPagoCollection().remove(detalleCertificadoPago);
                certificadoPago = em.merge(certificadoPago);
            }
            Costo costo = detalleCertificadoPago.getCosto();
            if (costo != null) {
                costo.getDetalleCertificadoPagoCollection().remove(detalleCertificadoPago);
                costo = em.merge(costo);
            }
            DetalleFoja detalleFoja = detalleCertificadoPago.getDetalleFoja();
            if (detalleFoja != null) {
                detalleFoja.getDetalleCertificadoPagoCollection().remove(detalleCertificadoPago);
                detalleFoja = em.merge(detalleFoja);
            }
            em.remove(detalleCertificadoPago);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleCertificadoPago> findDetalleCertificadoPagoEntities() {
        return findDetalleCertificadoPagoEntities(true, -1, -1);
    }

    public List<DetalleCertificadoPago> findDetalleCertificadoPagoEntities(int maxResults, int firstResult) {
        return findDetalleCertificadoPagoEntities(false, maxResults, firstResult);
    }

    private List<DetalleCertificadoPago> findDetalleCertificadoPagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleCertificadoPago.class));
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

    public DetalleCertificadoPago findDetalleCertificadoPago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleCertificadoPago.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleCertificadoPagoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleCertificadoPago> rt = cq.from(DetalleCertificadoPago.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
