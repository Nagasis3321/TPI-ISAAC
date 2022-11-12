/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo1.Obra;
import Modelo1.CertificadoPago;
import java.util.ArrayList;
import java.util.List;
import Modelo1.DetallesFoja;
import Modelo1.Foja;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Isaac
 */
public class FojaJpaController implements Serializable {

    public FojaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public FojaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    
    

    public void create(Foja foja) {
        if (foja.getCertificadoPagoList() == null) {
            foja.setCertificadoPagoList(new ArrayList<CertificadoPago>());
        }
        if (foja.getDetallesFojaList() == null) {
            foja.setDetallesFojaList(new ArrayList<DetallesFoja>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Obra obraidObra = foja.getObraidObra();
            if (obraidObra != null) {
                obraidObra = em.getReference(obraidObra.getClass(), obraidObra.getIdObra());
                foja.setObraidObra(obraidObra);
            }
            List<CertificadoPago> attachedCertificadoPagoList = new ArrayList<CertificadoPago>();
            for (CertificadoPago certificadoPagoListCertificadoPagoToAttach : foja.getCertificadoPagoList()) {
                certificadoPagoListCertificadoPagoToAttach = em.getReference(certificadoPagoListCertificadoPagoToAttach.getClass(), certificadoPagoListCertificadoPagoToAttach.getIdCertificadopago());
                attachedCertificadoPagoList.add(certificadoPagoListCertificadoPagoToAttach);
            }
            foja.setCertificadoPagoList(attachedCertificadoPagoList);
            List<DetallesFoja> attachedDetallesFojaList = new ArrayList<DetallesFoja>();
            for (DetallesFoja detallesFojaListDetallesFojaToAttach : foja.getDetallesFojaList()) {
                detallesFojaListDetallesFojaToAttach = em.getReference(detallesFojaListDetallesFojaToAttach.getClass(), detallesFojaListDetallesFojaToAttach.getIdDetallesfoja());
                attachedDetallesFojaList.add(detallesFojaListDetallesFojaToAttach);
            }
            foja.setDetallesFojaList(attachedDetallesFojaList);
            em.persist(foja);
            if (obraidObra != null) {
                obraidObra.getFojaList().add(foja);
                obraidObra = em.merge(obraidObra);
            }
            for (CertificadoPago certificadoPagoListCertificadoPago : foja.getCertificadoPagoList()) {
                Foja oldFojaidFojaOfCertificadoPagoListCertificadoPago = certificadoPagoListCertificadoPago.getFojaidFoja();
                certificadoPagoListCertificadoPago.setFojaidFoja(foja);
                certificadoPagoListCertificadoPago = em.merge(certificadoPagoListCertificadoPago);
                if (oldFojaidFojaOfCertificadoPagoListCertificadoPago != null) {
                    oldFojaidFojaOfCertificadoPagoListCertificadoPago.getCertificadoPagoList().remove(certificadoPagoListCertificadoPago);
                    oldFojaidFojaOfCertificadoPagoListCertificadoPago = em.merge(oldFojaidFojaOfCertificadoPagoListCertificadoPago);
                }
            }
            for (DetallesFoja detallesFojaListDetallesFoja : foja.getDetallesFojaList()) {
                Foja oldFojaidFojaOfDetallesFojaListDetallesFoja = detallesFojaListDetallesFoja.getFojaidFoja();
                detallesFojaListDetallesFoja.setFojaidFoja(foja);
                detallesFojaListDetallesFoja = em.merge(detallesFojaListDetallesFoja);
                if (oldFojaidFojaOfDetallesFojaListDetallesFoja != null) {
                    oldFojaidFojaOfDetallesFojaListDetallesFoja.getDetallesFojaList().remove(detallesFojaListDetallesFoja);
                    oldFojaidFojaOfDetallesFojaListDetallesFoja = em.merge(oldFojaidFojaOfDetallesFojaListDetallesFoja);
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
            Obra obraidObraOld = persistentFoja.getObraidObra();
            Obra obraidObraNew = foja.getObraidObra();
            List<CertificadoPago> certificadoPagoListOld = persistentFoja.getCertificadoPagoList();
            List<CertificadoPago> certificadoPagoListNew = foja.getCertificadoPagoList();
            List<DetallesFoja> detallesFojaListOld = persistentFoja.getDetallesFojaList();
            List<DetallesFoja> detallesFojaListNew = foja.getDetallesFojaList();
            List<String> illegalOrphanMessages = null;
            for (CertificadoPago certificadoPagoListOldCertificadoPago : certificadoPagoListOld) {
                if (!certificadoPagoListNew.contains(certificadoPagoListOldCertificadoPago)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CertificadoPago " + certificadoPagoListOldCertificadoPago + " since its fojaidFoja field is not nullable.");
                }
            }
            for (DetallesFoja detallesFojaListOldDetallesFoja : detallesFojaListOld) {
                if (!detallesFojaListNew.contains(detallesFojaListOldDetallesFoja)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetallesFoja " + detallesFojaListOldDetallesFoja + " since its fojaidFoja field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (obraidObraNew != null) {
                obraidObraNew = em.getReference(obraidObraNew.getClass(), obraidObraNew.getIdObra());
                foja.setObraidObra(obraidObraNew);
            }
            List<CertificadoPago> attachedCertificadoPagoListNew = new ArrayList<CertificadoPago>();
            for (CertificadoPago certificadoPagoListNewCertificadoPagoToAttach : certificadoPagoListNew) {
                certificadoPagoListNewCertificadoPagoToAttach = em.getReference(certificadoPagoListNewCertificadoPagoToAttach.getClass(), certificadoPagoListNewCertificadoPagoToAttach.getIdCertificadopago());
                attachedCertificadoPagoListNew.add(certificadoPagoListNewCertificadoPagoToAttach);
            }
            certificadoPagoListNew = attachedCertificadoPagoListNew;
            foja.setCertificadoPagoList(certificadoPagoListNew);
            List<DetallesFoja> attachedDetallesFojaListNew = new ArrayList<DetallesFoja>();
            for (DetallesFoja detallesFojaListNewDetallesFojaToAttach : detallesFojaListNew) {
                detallesFojaListNewDetallesFojaToAttach = em.getReference(detallesFojaListNewDetallesFojaToAttach.getClass(), detallesFojaListNewDetallesFojaToAttach.getIdDetallesfoja());
                attachedDetallesFojaListNew.add(detallesFojaListNewDetallesFojaToAttach);
            }
            detallesFojaListNew = attachedDetallesFojaListNew;
            foja.setDetallesFojaList(detallesFojaListNew);
            foja = em.merge(foja);
            if (obraidObraOld != null && !obraidObraOld.equals(obraidObraNew)) {
                obraidObraOld.getFojaList().remove(foja);
                obraidObraOld = em.merge(obraidObraOld);
            }
            if (obraidObraNew != null && !obraidObraNew.equals(obraidObraOld)) {
                obraidObraNew.getFojaList().add(foja);
                obraidObraNew = em.merge(obraidObraNew);
            }
            for (CertificadoPago certificadoPagoListNewCertificadoPago : certificadoPagoListNew) {
                if (!certificadoPagoListOld.contains(certificadoPagoListNewCertificadoPago)) {
                    Foja oldFojaidFojaOfCertificadoPagoListNewCertificadoPago = certificadoPagoListNewCertificadoPago.getFojaidFoja();
                    certificadoPagoListNewCertificadoPago.setFojaidFoja(foja);
                    certificadoPagoListNewCertificadoPago = em.merge(certificadoPagoListNewCertificadoPago);
                    if (oldFojaidFojaOfCertificadoPagoListNewCertificadoPago != null && !oldFojaidFojaOfCertificadoPagoListNewCertificadoPago.equals(foja)) {
                        oldFojaidFojaOfCertificadoPagoListNewCertificadoPago.getCertificadoPagoList().remove(certificadoPagoListNewCertificadoPago);
                        oldFojaidFojaOfCertificadoPagoListNewCertificadoPago = em.merge(oldFojaidFojaOfCertificadoPagoListNewCertificadoPago);
                    }
                }
            }
            for (DetallesFoja detallesFojaListNewDetallesFoja : detallesFojaListNew) {
                if (!detallesFojaListOld.contains(detallesFojaListNewDetallesFoja)) {
                    Foja oldFojaidFojaOfDetallesFojaListNewDetallesFoja = detallesFojaListNewDetallesFoja.getFojaidFoja();
                    detallesFojaListNewDetallesFoja.setFojaidFoja(foja);
                    detallesFojaListNewDetallesFoja = em.merge(detallesFojaListNewDetallesFoja);
                    if (oldFojaidFojaOfDetallesFojaListNewDetallesFoja != null && !oldFojaidFojaOfDetallesFojaListNewDetallesFoja.equals(foja)) {
                        oldFojaidFojaOfDetallesFojaListNewDetallesFoja.getDetallesFojaList().remove(detallesFojaListNewDetallesFoja);
                        oldFojaidFojaOfDetallesFojaListNewDetallesFoja = em.merge(oldFojaidFojaOfDetallesFojaListNewDetallesFoja);
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
            List<CertificadoPago> certificadoPagoListOrphanCheck = foja.getCertificadoPagoList();
            for (CertificadoPago certificadoPagoListOrphanCheckCertificadoPago : certificadoPagoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Foja (" + foja + ") cannot be destroyed since the CertificadoPago " + certificadoPagoListOrphanCheckCertificadoPago + " in its certificadoPagoList field has a non-nullable fojaidFoja field.");
            }
            List<DetallesFoja> detallesFojaListOrphanCheck = foja.getDetallesFojaList();
            for (DetallesFoja detallesFojaListOrphanCheckDetallesFoja : detallesFojaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Foja (" + foja + ") cannot be destroyed since the DetallesFoja " + detallesFojaListOrphanCheckDetallesFoja + " in its detallesFojaList field has a non-nullable fojaidFoja field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Obra obraidObra = foja.getObraidObra();
            if (obraidObra != null) {
                obraidObra.getFojaList().remove(foja);
                obraidObra = em.merge(obraidObra);
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
