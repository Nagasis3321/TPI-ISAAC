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
import ModeloDB.Item;
import ModeloDB.TipoItem;
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
public class TipoItemJpaController implements Serializable {

    public TipoItemJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoItem tipoItem) {
        if (tipoItem.getItemCollection() == null) {
            tipoItem.setItemCollection(new ArrayList<Item>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Item> attachedItemCollection = new ArrayList<Item>();
            for (Item itemCollectionItemToAttach : tipoItem.getItemCollection()) {
                itemCollectionItemToAttach = em.getReference(itemCollectionItemToAttach.getClass(), itemCollectionItemToAttach.getIdItem());
                attachedItemCollection.add(itemCollectionItemToAttach);
            }
            tipoItem.setItemCollection(attachedItemCollection);
            em.persist(tipoItem);
            for (Item itemCollectionItem : tipoItem.getItemCollection()) {
                TipoItem oldTipoItemOfItemCollectionItem = itemCollectionItem.getTipoItem();
                itemCollectionItem.setTipoItem(tipoItem);
                itemCollectionItem = em.merge(itemCollectionItem);
                if (oldTipoItemOfItemCollectionItem != null) {
                    oldTipoItemOfItemCollectionItem.getItemCollection().remove(itemCollectionItem);
                    oldTipoItemOfItemCollectionItem = em.merge(oldTipoItemOfItemCollectionItem);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoItem tipoItem) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoItem persistentTipoItem = em.find(TipoItem.class, tipoItem.getIdTipoItem());
            Collection<Item> itemCollectionOld = persistentTipoItem.getItemCollection();
            Collection<Item> itemCollectionNew = tipoItem.getItemCollection();
            List<String> illegalOrphanMessages = null;
            for (Item itemCollectionOldItem : itemCollectionOld) {
                if (!itemCollectionNew.contains(itemCollectionOldItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Item " + itemCollectionOldItem + " since its tipoItem field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Item> attachedItemCollectionNew = new ArrayList<Item>();
            for (Item itemCollectionNewItemToAttach : itemCollectionNew) {
                itemCollectionNewItemToAttach = em.getReference(itemCollectionNewItemToAttach.getClass(), itemCollectionNewItemToAttach.getIdItem());
                attachedItemCollectionNew.add(itemCollectionNewItemToAttach);
            }
            itemCollectionNew = attachedItemCollectionNew;
            tipoItem.setItemCollection(itemCollectionNew);
            tipoItem = em.merge(tipoItem);
            for (Item itemCollectionNewItem : itemCollectionNew) {
                if (!itemCollectionOld.contains(itemCollectionNewItem)) {
                    TipoItem oldTipoItemOfItemCollectionNewItem = itemCollectionNewItem.getTipoItem();
                    itemCollectionNewItem.setTipoItem(tipoItem);
                    itemCollectionNewItem = em.merge(itemCollectionNewItem);
                    if (oldTipoItemOfItemCollectionNewItem != null && !oldTipoItemOfItemCollectionNewItem.equals(tipoItem)) {
                        oldTipoItemOfItemCollectionNewItem.getItemCollection().remove(itemCollectionNewItem);
                        oldTipoItemOfItemCollectionNewItem = em.merge(oldTipoItemOfItemCollectionNewItem);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoItem.getIdTipoItem();
                if (findTipoItem(id) == null) {
                    throw new NonexistentEntityException("The tipoItem with id " + id + " no longer exists.");
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
            TipoItem tipoItem;
            try {
                tipoItem = em.getReference(TipoItem.class, id);
                tipoItem.getIdTipoItem();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoItem with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Item> itemCollectionOrphanCheck = tipoItem.getItemCollection();
            for (Item itemCollectionOrphanCheckItem : itemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoItem (" + tipoItem + ") cannot be destroyed since the Item " + itemCollectionOrphanCheckItem + " in its itemCollection field has a non-nullable tipoItem field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoItem);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoItem> findTipoItemEntities() {
        return findTipoItemEntities(true, -1, -1);
    }

    public List<TipoItem> findTipoItemEntities(int maxResults, int firstResult) {
        return findTipoItemEntities(false, maxResults, firstResult);
    }

    private List<TipoItem> findTipoItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoItem.class));
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

    public TipoItem findTipoItem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoItem.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoItem> rt = cq.from(TipoItem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
