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
import java.util.ArrayList;
import java.util.Collection;
import ModeloDB.Costo;
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
        if (certificadoPago.getFojaCollection() == null) {
            certificadoPago.setFojaCollection(new ArrayList<Foja>());
        }
        if (certificadoPago.getCostoCollection() == null) {
            certificadoPago.setCostoCollection(new ArrayList<Costo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Foja> attachedFojaCollection = new ArrayList<Foja>();
            for (Foja fojaCollectionFojaToAttach : certificadoPago.getFojaCollection()) {
                fojaCollectionFojaToAttach = em.getReference(fojaCollectionFojaToAttach.getClass(), fojaCollectionFojaToAttach.getIdFoja());
                attachedFojaCollection.add(fojaCollectionFojaToAttach);
            }
            certificadoPago.setFojaCollection(attachedFojaCollection);
            Collection<Costo> attachedCostoCollection = new ArrayList<Costo>();
            for (Costo costoCollectionCostoToAttach : certificadoPago.getCostoCollection()) {
                costoCollectionCostoToAttach = em.getReference(costoCollectionCostoToAttach.getClass(), costoCollectionCostoToAttach.getIdCosto());
                attachedCostoCollection.add(costoCollectionCostoToAttach);
            }
            certificadoPago.setCostoCollection(attachedCostoCollection);
            em.persist(certificadoPago);
            for (Foja fojaCollectionFoja : certificadoPago.getFojaCollection()) {
                CertificadoPago oldCertificadoPagoOfFojaCollectionFoja = fojaCollectionFoja.getCertificadoPago();
                fojaCollectionFoja.setCertificadoPago(certificadoPago);
                fojaCollectionFoja = em.merge(fojaCollectionFoja);
                if (oldCertificadoPagoOfFojaCollectionFoja != null) {
                    oldCertificadoPagoOfFojaCollectionFoja.getFojaCollection().remove(fojaCollectionFoja);
                    oldCertificadoPagoOfFojaCollectionFoja = em.merge(oldCertificadoPagoOfFojaCollectionFoja);
                }
            }
            for (Costo costoCollectionCosto : certificadoPago.getCostoCollection()) {
                CertificadoPago oldCertificadoFinVigenciaOfCostoCollectionCosto = costoCollectionCosto.getCertificadoFinVigencia();
                costoCollectionCosto.setCertificadoFinVigencia(certificadoPago);
                costoCollectionCosto = em.merge(costoCollectionCosto);
                if (oldCertificadoFinVigenciaOfCostoCollectionCosto != null) {
                    oldCertificadoFinVigenciaOfCostoCollectionCosto.getCostoCollection().remove(costoCollectionCosto);
                    oldCertificadoFinVigenciaOfCostoCollectionCosto = em.merge(oldCertificadoFinVigenciaOfCostoCollectionCosto);
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
            Collection<Foja> fojaCollectionOld = persistentCertificadoPago.getFojaCollection();
            Collection<Foja> fojaCollectionNew = certificadoPago.getFojaCollection();
            Collection<Costo> costoCollectionOld = persistentCertificadoPago.getCostoCollection();
            Collection<Costo> costoCollectionNew = certificadoPago.getCostoCollection();
            List<String> illegalOrphanMessages = null;
            for (Foja fojaCollectionOldFoja : fojaCollectionOld) {
                if (!fojaCollectionNew.contains(fojaCollectionOldFoja)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Foja " + fojaCollectionOldFoja + " since its certificadoPago field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Foja> attachedFojaCollectionNew = new ArrayList<Foja>();
            for (Foja fojaCollectionNewFojaToAttach : fojaCollectionNew) {
                fojaCollectionNewFojaToAttach = em.getReference(fojaCollectionNewFojaToAttach.getClass(), fojaCollectionNewFojaToAttach.getIdFoja());
                attachedFojaCollectionNew.add(fojaCollectionNewFojaToAttach);
            }
            fojaCollectionNew = attachedFojaCollectionNew;
            certificadoPago.setFojaCollection(fojaCollectionNew);
            Collection<Costo> attachedCostoCollectionNew = new ArrayList<Costo>();
            for (Costo costoCollectionNewCostoToAttach : costoCollectionNew) {
                costoCollectionNewCostoToAttach = em.getReference(costoCollectionNewCostoToAttach.getClass(), costoCollectionNewCostoToAttach.getIdCosto());
                attachedCostoCollectionNew.add(costoCollectionNewCostoToAttach);
            }
            costoCollectionNew = attachedCostoCollectionNew;
            certificadoPago.setCostoCollection(costoCollectionNew);
            certificadoPago = em.merge(certificadoPago);
            for (Foja fojaCollectionNewFoja : fojaCollectionNew) {
                if (!fojaCollectionOld.contains(fojaCollectionNewFoja)) {
                    CertificadoPago oldCertificadoPagoOfFojaCollectionNewFoja = fojaCollectionNewFoja.getCertificadoPago();
                    fojaCollectionNewFoja.setCertificadoPago(certificadoPago);
                    fojaCollectionNewFoja = em.merge(fojaCollectionNewFoja);
                    if (oldCertificadoPagoOfFojaCollectionNewFoja != null && !oldCertificadoPagoOfFojaCollectionNewFoja.equals(certificadoPago)) {
                        oldCertificadoPagoOfFojaCollectionNewFoja.getFojaCollection().remove(fojaCollectionNewFoja);
                        oldCertificadoPagoOfFojaCollectionNewFoja = em.merge(oldCertificadoPagoOfFojaCollectionNewFoja);
                    }
                }
            }
            for (Costo costoCollectionOldCosto : costoCollectionOld) {
                if (!costoCollectionNew.contains(costoCollectionOldCosto)) {
                    costoCollectionOldCosto.setCertificadoFinVigencia(null);
                    costoCollectionOldCosto = em.merge(costoCollectionOldCosto);
                }
            }
            for (Costo costoCollectionNewCosto : costoCollectionNew) {
                if (!costoCollectionOld.contains(costoCollectionNewCosto)) {
                    CertificadoPago oldCertificadoFinVigenciaOfCostoCollectionNewCosto = costoCollectionNewCosto.getCertificadoFinVigencia();
                    costoCollectionNewCosto.setCertificadoFinVigencia(certificadoPago);
                    costoCollectionNewCosto = em.merge(costoCollectionNewCosto);
                    if (oldCertificadoFinVigenciaOfCostoCollectionNewCosto != null && !oldCertificadoFinVigenciaOfCostoCollectionNewCosto.equals(certificadoPago)) {
                        oldCertificadoFinVigenciaOfCostoCollectionNewCosto.getCostoCollection().remove(costoCollectionNewCosto);
                        oldCertificadoFinVigenciaOfCostoCollectionNewCosto = em.merge(oldCertificadoFinVigenciaOfCostoCollectionNewCosto);
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
            Collection<Foja> fojaCollectionOrphanCheck = certificadoPago.getFojaCollection();
            for (Foja fojaCollectionOrphanCheckFoja : fojaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CertificadoPago (" + certificadoPago + ") cannot be destroyed since the Foja " + fojaCollectionOrphanCheckFoja + " in its fojaCollection field has a non-nullable certificadoPago field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Costo> costoCollection = certificadoPago.getCostoCollection();
            for (Costo costoCollectionCosto : costoCollection) {
                costoCollectionCosto.setCertificadoFinVigencia(null);
                costoCollectionCosto = em.merge(costoCollectionCosto);
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
