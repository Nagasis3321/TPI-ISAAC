/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo1.CertificadoPago;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo1.Foja;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Isaac
 */
public class CertificadoPagoJpaController implements Serializable {

    public CertificadoPagoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public CertificadoPagoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CertificadoPago certificadoPago) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Foja fojaidFoja = certificadoPago.getFojaidFoja();
            if (fojaidFoja != null) {
                fojaidFoja = em.getReference(fojaidFoja.getClass(), fojaidFoja.getIdFoja());
                certificadoPago.setFojaidFoja(fojaidFoja);
            }
            em.persist(certificadoPago);
            if (fojaidFoja != null) {
                fojaidFoja.getCertificadoPagoList().add(certificadoPago);
                fojaidFoja = em.merge(fojaidFoja);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CertificadoPago certificadoPago) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CertificadoPago persistentCertificadoPago = em.find(CertificadoPago.class, certificadoPago.getIdCertificadopago());
            Foja fojaidFojaOld = persistentCertificadoPago.getFojaidFoja();
            Foja fojaidFojaNew = certificadoPago.getFojaidFoja();
            if (fojaidFojaNew != null) {
                fojaidFojaNew = em.getReference(fojaidFojaNew.getClass(), fojaidFojaNew.getIdFoja());
                certificadoPago.setFojaidFoja(fojaidFojaNew);
            }
            certificadoPago = em.merge(certificadoPago);
            if (fojaidFojaOld != null && !fojaidFojaOld.equals(fojaidFojaNew)) {
                fojaidFojaOld.getCertificadoPagoList().remove(certificadoPago);
                fojaidFojaOld = em.merge(fojaidFojaOld);
            }
            if (fojaidFojaNew != null && !fojaidFojaNew.equals(fojaidFojaOld)) {
                fojaidFojaNew.getCertificadoPagoList().add(certificadoPago);
                fojaidFojaNew = em.merge(fojaidFojaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = certificadoPago.getIdCertificadopago();
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CertificadoPago certificadoPago;
            try {
                certificadoPago = em.getReference(CertificadoPago.class, id);
                certificadoPago.getIdCertificadopago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The certificadoPago with id " + id + " no longer exists.", enfe);
            }
            Foja fojaidFoja = certificadoPago.getFojaidFoja();
            if (fojaidFoja != null) {
                fojaidFoja.getCertificadoPagoList().remove(certificadoPago);
                fojaidFoja = em.merge(fojaidFoja);
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
