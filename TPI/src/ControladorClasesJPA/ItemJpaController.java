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
import ModeloDB.TipoItem;
import ModeloDB.DetalleFoja;
import ModeloDB.Item;
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
public class ItemJpaController implements Serializable {

    public ItemJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Item item) {
        if (item.getDetalleFojaCollection() == null) {
            item.setDetalleFojaCollection(new ArrayList<DetalleFoja>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Obra obra = item.getObra();
            if (obra != null) {
                obra = em.getReference(obra.getClass(), obra.getIdObra());
                item.setObra(obra);
            }
            TipoItem tipoItem = item.getTipoItem();
            if (tipoItem != null) {
                tipoItem = em.getReference(tipoItem.getClass(), tipoItem.getIdTipoItem());
                item.setTipoItem(tipoItem);
            }
            Collection<DetalleFoja> attachedDetalleFojaCollection = new ArrayList<DetalleFoja>();
            for (DetalleFoja detalleFojaCollectionDetalleFojaToAttach : item.getDetalleFojaCollection()) {
                detalleFojaCollectionDetalleFojaToAttach = em.getReference(detalleFojaCollectionDetalleFojaToAttach.getClass(), detalleFojaCollectionDetalleFojaToAttach.getIdDetalleFoja());
                attachedDetalleFojaCollection.add(detalleFojaCollectionDetalleFojaToAttach);
            }
            item.setDetalleFojaCollection(attachedDetalleFojaCollection);
            em.persist(item);
            if (obra != null) {
                obra.getItemCollection().add(item);
                obra = em.merge(obra);
            }
            if (tipoItem != null) {
                tipoItem.getItemCollection().add(item);
                tipoItem = em.merge(tipoItem);
            }
            for (DetalleFoja detalleFojaCollectionDetalleFoja : item.getDetalleFojaCollection()) {
                Item oldItemOfDetalleFojaCollectionDetalleFoja = detalleFojaCollectionDetalleFoja.getItem();
                detalleFojaCollectionDetalleFoja.setItem(item);
                detalleFojaCollectionDetalleFoja = em.merge(detalleFojaCollectionDetalleFoja);
                if (oldItemOfDetalleFojaCollectionDetalleFoja != null) {
                    oldItemOfDetalleFojaCollectionDetalleFoja.getDetalleFojaCollection().remove(detalleFojaCollectionDetalleFoja);
                    oldItemOfDetalleFojaCollectionDetalleFoja = em.merge(oldItemOfDetalleFojaCollectionDetalleFoja);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Item item) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Item persistentItem = em.find(Item.class, item.getIdItem());
            Obra obraOld = persistentItem.getObra();
            Obra obraNew = item.getObra();
            TipoItem tipoItemOld = persistentItem.getTipoItem();
            TipoItem tipoItemNew = item.getTipoItem();
            Collection<DetalleFoja> detalleFojaCollectionOld = persistentItem.getDetalleFojaCollection();
            Collection<DetalleFoja> detalleFojaCollectionNew = item.getDetalleFojaCollection();
            List<String> illegalOrphanMessages = null;
            for (DetalleFoja detalleFojaCollectionOldDetalleFoja : detalleFojaCollectionOld) {
                if (!detalleFojaCollectionNew.contains(detalleFojaCollectionOldDetalleFoja)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleFoja " + detalleFojaCollectionOldDetalleFoja + " since its item field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (obraNew != null) {
                obraNew = em.getReference(obraNew.getClass(), obraNew.getIdObra());
                item.setObra(obraNew);
            }
            if (tipoItemNew != null) {
                tipoItemNew = em.getReference(tipoItemNew.getClass(), tipoItemNew.getIdTipoItem());
                item.setTipoItem(tipoItemNew);
            }
            Collection<DetalleFoja> attachedDetalleFojaCollectionNew = new ArrayList<DetalleFoja>();
            for (DetalleFoja detalleFojaCollectionNewDetalleFojaToAttach : detalleFojaCollectionNew) {
                detalleFojaCollectionNewDetalleFojaToAttach = em.getReference(detalleFojaCollectionNewDetalleFojaToAttach.getClass(), detalleFojaCollectionNewDetalleFojaToAttach.getIdDetalleFoja());
                attachedDetalleFojaCollectionNew.add(detalleFojaCollectionNewDetalleFojaToAttach);
            }
            detalleFojaCollectionNew = attachedDetalleFojaCollectionNew;
            item.setDetalleFojaCollection(detalleFojaCollectionNew);
            item = em.merge(item);
            if (obraOld != null && !obraOld.equals(obraNew)) {
                obraOld.getItemCollection().remove(item);
                obraOld = em.merge(obraOld);
            }
            if (obraNew != null && !obraNew.equals(obraOld)) {
                obraNew.getItemCollection().add(item);
                obraNew = em.merge(obraNew);
            }
            if (tipoItemOld != null && !tipoItemOld.equals(tipoItemNew)) {
                tipoItemOld.getItemCollection().remove(item);
                tipoItemOld = em.merge(tipoItemOld);
            }
            if (tipoItemNew != null && !tipoItemNew.equals(tipoItemOld)) {
                tipoItemNew.getItemCollection().add(item);
                tipoItemNew = em.merge(tipoItemNew);
            }
            for (DetalleFoja detalleFojaCollectionNewDetalleFoja : detalleFojaCollectionNew) {
                if (!detalleFojaCollectionOld.contains(detalleFojaCollectionNewDetalleFoja)) {
                    Item oldItemOfDetalleFojaCollectionNewDetalleFoja = detalleFojaCollectionNewDetalleFoja.getItem();
                    detalleFojaCollectionNewDetalleFoja.setItem(item);
                    detalleFojaCollectionNewDetalleFoja = em.merge(detalleFojaCollectionNewDetalleFoja);
                    if (oldItemOfDetalleFojaCollectionNewDetalleFoja != null && !oldItemOfDetalleFojaCollectionNewDetalleFoja.equals(item)) {
                        oldItemOfDetalleFojaCollectionNewDetalleFoja.getDetalleFojaCollection().remove(detalleFojaCollectionNewDetalleFoja);
                        oldItemOfDetalleFojaCollectionNewDetalleFoja = em.merge(oldItemOfDetalleFojaCollectionNewDetalleFoja);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = item.getIdItem();
                if (findItem(id) == null) {
                    throw new NonexistentEntityException("The item with id " + id + " no longer exists.");
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
            Item item;
            try {
                item = em.getReference(Item.class, id);
                item.getIdItem();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The item with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<DetalleFoja> detalleFojaCollectionOrphanCheck = item.getDetalleFojaCollection();
            for (DetalleFoja detalleFojaCollectionOrphanCheckDetalleFoja : detalleFojaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Item (" + item + ") cannot be destroyed since the DetalleFoja " + detalleFojaCollectionOrphanCheckDetalleFoja + " in its detalleFojaCollection field has a non-nullable item field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Obra obra = item.getObra();
            if (obra != null) {
                obra.getItemCollection().remove(item);
                obra = em.merge(obra);
            }
            TipoItem tipoItem = item.getTipoItem();
            if (tipoItem != null) {
                tipoItem.getItemCollection().remove(item);
                tipoItem = em.merge(tipoItem);
            }
            em.remove(item);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Item> findItemEntities() {
        return findItemEntities(true, -1, -1);
    }

    public List<Item> findItemEntities(int maxResults, int firstResult) {
        return findItemEntities(false, maxResults, firstResult);
    }

    private List<Item> findItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Item.class));
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

    public Item findItem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Item.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Item> rt = cq.from(Item.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
