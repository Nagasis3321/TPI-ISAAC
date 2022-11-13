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
import ModeloDB.Item;
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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CertificadoPago certificadoFinVigencia = costo.getCertificadoFinVigencia();
            if (certificadoFinVigencia != null) {
                certificadoFinVigencia = em.getReference(certificadoFinVigencia.getClass(), certificadoFinVigencia.getIdCertificadoPago());
                costo.setCertificadoFinVigencia(certificadoFinVigencia);
            }
            Item item = costo.getItem();
            if (item != null) {
                item = em.getReference(item.getClass(), item.getIdItem());
                costo.setItem(item);
            }
            em.persist(costo);
            if (certificadoFinVigencia != null) {
                certificadoFinVigencia.getCostoCollection().add(costo);
                certificadoFinVigencia = em.merge(certificadoFinVigencia);
            }
            if (item != null) {
                item.getCostoCollection().add(costo);
                item = em.merge(item);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Costo costo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Costo persistentCosto = em.find(Costo.class, costo.getIdCosto());
            CertificadoPago certificadoFinVigenciaOld = persistentCosto.getCertificadoFinVigencia();
            CertificadoPago certificadoFinVigenciaNew = costo.getCertificadoFinVigencia();
            Item itemOld = persistentCosto.getItem();
            Item itemNew = costo.getItem();
            if (certificadoFinVigenciaNew != null) {
                certificadoFinVigenciaNew = em.getReference(certificadoFinVigenciaNew.getClass(), certificadoFinVigenciaNew.getIdCertificadoPago());
                costo.setCertificadoFinVigencia(certificadoFinVigenciaNew);
            }
            if (itemNew != null) {
                itemNew = em.getReference(itemNew.getClass(), itemNew.getIdItem());
                costo.setItem(itemNew);
            }
            costo = em.merge(costo);
            if (certificadoFinVigenciaOld != null && !certificadoFinVigenciaOld.equals(certificadoFinVigenciaNew)) {
                certificadoFinVigenciaOld.getCostoCollection().remove(costo);
                certificadoFinVigenciaOld = em.merge(certificadoFinVigenciaOld);
            }
            if (certificadoFinVigenciaNew != null && !certificadoFinVigenciaNew.equals(certificadoFinVigenciaOld)) {
                certificadoFinVigenciaNew.getCostoCollection().add(costo);
                certificadoFinVigenciaNew = em.merge(certificadoFinVigenciaNew);
            }
            if (itemOld != null && !itemOld.equals(itemNew)) {
                itemOld.getCostoCollection().remove(costo);
                itemOld = em.merge(itemOld);
            }
            if (itemNew != null && !itemNew.equals(itemOld)) {
                itemNew.getCostoCollection().add(costo);
                itemNew = em.merge(itemNew);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            CertificadoPago certificadoFinVigencia = costo.getCertificadoFinVigencia();
            if (certificadoFinVigencia != null) {
                certificadoFinVigencia.getCostoCollection().remove(costo);
                certificadoFinVigencia = em.merge(certificadoFinVigencia);
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
