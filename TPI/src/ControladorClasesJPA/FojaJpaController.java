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
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
            em.persist(foja);
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
            Obra obraOld = persistentFoja.getObra();
            Obra obraNew = foja.getObra();
            Collection<DetalleFoja> detalleFojaCollectionOld = persistentFoja.getDetalleFojaCollection();
            Collection<DetalleFoja> detalleFojaCollectionNew = foja.getDetalleFojaCollection();
            List<String> illegalOrphanMessages = null;
            for (DetalleFoja detalleFojaCollectionOldDetalleFoja : detalleFojaCollectionOld) {
                if (!detalleFojaCollectionNew.contains(detalleFojaCollectionOldDetalleFoja)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleFoja " + detalleFojaCollectionOldDetalleFoja + " since its foja field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
            foja = em.merge(foja);
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
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
