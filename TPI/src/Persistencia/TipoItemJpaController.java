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
import Modelo1.Item;
import Modelo1.TipoItem;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Isaac
 */
public class TipoItemJpaController implements Serializable {

    public TipoItemJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public TipoItemJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    
    
    
    public void create(TipoItem tipoItem) {
        if (tipoItem.getItemList() == null) {
            tipoItem.setItemList(new ArrayList<Item>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Item> attachedItemList = new ArrayList<Item>();
            for (Item itemListItemToAttach : tipoItem.getItemList()) {
                itemListItemToAttach = em.getReference(itemListItemToAttach.getClass(), itemListItemToAttach.getIdItem());
                attachedItemList.add(itemListItemToAttach);
            }
            tipoItem.setItemList(attachedItemList);
            em.persist(tipoItem);
            for (Item itemListItem : tipoItem.getItemList()) {
                TipoItem oldIdTipoitemOfItemListItem = itemListItem.getIdTipoitem();
                itemListItem.setIdTipoitem(tipoItem);
                itemListItem = em.merge(itemListItem);
                if (oldIdTipoitemOfItemListItem != null) {
                    oldIdTipoitemOfItemListItem.getItemList().remove(itemListItem);
                    oldIdTipoitemOfItemListItem = em.merge(oldIdTipoitemOfItemListItem);
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
            TipoItem persistentTipoItem = em.find(TipoItem.class, tipoItem.getIdTipoitem());
            List<Item> itemListOld = persistentTipoItem.getItemList();
            List<Item> itemListNew = tipoItem.getItemList();
            List<String> illegalOrphanMessages = null;
            for (Item itemListOldItem : itemListOld) {
                if (!itemListNew.contains(itemListOldItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Item " + itemListOldItem + " since its idTipoitem field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Item> attachedItemListNew = new ArrayList<Item>();
            for (Item itemListNewItemToAttach : itemListNew) {
                itemListNewItemToAttach = em.getReference(itemListNewItemToAttach.getClass(), itemListNewItemToAttach.getIdItem());
                attachedItemListNew.add(itemListNewItemToAttach);
            }
            itemListNew = attachedItemListNew;
            tipoItem.setItemList(itemListNew);
            tipoItem = em.merge(tipoItem);
            for (Item itemListNewItem : itemListNew) {
                if (!itemListOld.contains(itemListNewItem)) {
                    TipoItem oldIdTipoitemOfItemListNewItem = itemListNewItem.getIdTipoitem();
                    itemListNewItem.setIdTipoitem(tipoItem);
                    itemListNewItem = em.merge(itemListNewItem);
                    if (oldIdTipoitemOfItemListNewItem != null && !oldIdTipoitemOfItemListNewItem.equals(tipoItem)) {
                        oldIdTipoitemOfItemListNewItem.getItemList().remove(itemListNewItem);
                        oldIdTipoitemOfItemListNewItem = em.merge(oldIdTipoitemOfItemListNewItem);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoItem.getIdTipoitem();
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
                tipoItem.getIdTipoitem();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoItem with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Item> itemListOrphanCheck = tipoItem.getItemList();
            for (Item itemListOrphanCheckItem : itemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoItem (" + tipoItem + ") cannot be destroyed since the Item " + itemListOrphanCheckItem + " in its itemList field has a non-nullable idTipoitem field.");
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
