/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo1.DetallesFoja;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo1.Foja;
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
public class DetallesFojaJpaController implements Serializable {

    public DetallesFojaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public DetallesFojaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    
    
    
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetallesFoja detallesFoja) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Foja fojaidFoja = detallesFoja.getFojaidFoja();
            if (fojaidFoja != null) {
                fojaidFoja = em.getReference(fojaidFoja.getClass(), fojaidFoja.getIdFoja());
                detallesFoja.setFojaidFoja(fojaidFoja);
            }
            Item itemidItem = detallesFoja.getItemidItem();
            if (itemidItem != null) {
                itemidItem = em.getReference(itemidItem.getClass(), itemidItem.getIdItem());
                detallesFoja.setItemidItem(itemidItem);
            }
            em.persist(detallesFoja);
            if (fojaidFoja != null) {
                fojaidFoja.getDetallesFojaList().add(detallesFoja);
                fojaidFoja = em.merge(fojaidFoja);
            }
            if (itemidItem != null) {
                itemidItem.getDetallesFojaList().add(detallesFoja);
                itemidItem = em.merge(itemidItem);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetallesFoja detallesFoja) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetallesFoja persistentDetallesFoja = em.find(DetallesFoja.class, detallesFoja.getIdDetallesfoja());
            Foja fojaidFojaOld = persistentDetallesFoja.getFojaidFoja();
            Foja fojaidFojaNew = detallesFoja.getFojaidFoja();
            Item itemidItemOld = persistentDetallesFoja.getItemidItem();
            Item itemidItemNew = detallesFoja.getItemidItem();
            if (fojaidFojaNew != null) {
                fojaidFojaNew = em.getReference(fojaidFojaNew.getClass(), fojaidFojaNew.getIdFoja());
                detallesFoja.setFojaidFoja(fojaidFojaNew);
            }
            if (itemidItemNew != null) {
                itemidItemNew = em.getReference(itemidItemNew.getClass(), itemidItemNew.getIdItem());
                detallesFoja.setItemidItem(itemidItemNew);
            }
            detallesFoja = em.merge(detallesFoja);
            if (fojaidFojaOld != null && !fojaidFojaOld.equals(fojaidFojaNew)) {
                fojaidFojaOld.getDetallesFojaList().remove(detallesFoja);
                fojaidFojaOld = em.merge(fojaidFojaOld);
            }
            if (fojaidFojaNew != null && !fojaidFojaNew.equals(fojaidFojaOld)) {
                fojaidFojaNew.getDetallesFojaList().add(detallesFoja);
                fojaidFojaNew = em.merge(fojaidFojaNew);
            }
            if (itemidItemOld != null && !itemidItemOld.equals(itemidItemNew)) {
                itemidItemOld.getDetallesFojaList().remove(detallesFoja);
                itemidItemOld = em.merge(itemidItemOld);
            }
            if (itemidItemNew != null && !itemidItemNew.equals(itemidItemOld)) {
                itemidItemNew.getDetallesFojaList().add(detallesFoja);
                itemidItemNew = em.merge(itemidItemNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detallesFoja.getIdDetallesfoja();
                if (findDetallesFoja(id) == null) {
                    throw new NonexistentEntityException("The detallesFoja with id " + id + " no longer exists.");
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
            DetallesFoja detallesFoja;
            try {
                detallesFoja = em.getReference(DetallesFoja.class, id);
                detallesFoja.getIdDetallesfoja();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detallesFoja with id " + id + " no longer exists.", enfe);
            }
            Foja fojaidFoja = detallesFoja.getFojaidFoja();
            if (fojaidFoja != null) {
                fojaidFoja.getDetallesFojaList().remove(detallesFoja);
                fojaidFoja = em.merge(fojaidFoja);
            }
            Item itemidItem = detallesFoja.getItemidItem();
            if (itemidItem != null) {
                itemidItem.getDetallesFojaList().remove(detallesFoja);
                itemidItem = em.merge(itemidItem);
            }
            em.remove(detallesFoja);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetallesFoja> findDetallesFojaEntities() {
        return findDetallesFojaEntities(true, -1, -1);
    }

    public List<DetallesFoja> findDetallesFojaEntities(int maxResults, int firstResult) {
        return findDetallesFojaEntities(false, maxResults, firstResult);
    }

    private List<DetallesFoja> findDetallesFojaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetallesFoja.class));
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

    public DetallesFoja findDetallesFoja(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetallesFoja.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetallesFojaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetallesFoja> rt = cq.from(DetallesFoja.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
