/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo1.Costo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo1.Item;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Isaac
 */
public class CostoJpaController implements Serializable {

    public CostoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public CostoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }

    
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Costo costo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Item itemidItem = costo.getItemidItem();
            if (itemidItem != null) {
                itemidItem = em.getReference(itemidItem.getClass(), itemidItem.getIdItem());
                costo.setItemidItem(itemidItem);
            }
            em.persist(costo);
            if (itemidItem != null) {
                itemidItem.getCostoList().add(costo);
                itemidItem = em.merge(itemidItem);
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
            Item itemidItemOld = persistentCosto.getItemidItem();
            Item itemidItemNew = costo.getItemidItem();
            if (itemidItemNew != null) {
                itemidItemNew = em.getReference(itemidItemNew.getClass(), itemidItemNew.getIdItem());
                costo.setItemidItem(itemidItemNew);
            }
            costo = em.merge(costo);
            if (itemidItemOld != null && !itemidItemOld.equals(itemidItemNew)) {
                itemidItemOld.getCostoList().remove(costo);
                itemidItemOld = em.merge(itemidItemOld);
            }
            if (itemidItemNew != null && !itemidItemNew.equals(itemidItemOld)) {
                itemidItemNew.getCostoList().add(costo);
                itemidItemNew = em.merge(itemidItemNew);
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
            Item itemidItem = costo.getItemidItem();
            if (itemidItem != null) {
                itemidItem.getCostoList().remove(costo);
                itemidItem = em.merge(itemidItem);
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
