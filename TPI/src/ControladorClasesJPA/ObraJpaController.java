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
import ModeloDB.Empresa;
import ModeloDB.Foja;
import java.util.ArrayList;
import java.util.Collection;
import ModeloDB.Item;
import ModeloDB.Obra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author matia
 */
public class ObraJpaController implements Serializable {

    public ObraJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Obra obra) {
        if (obra.getFojaCollection() == null) {
            obra.setFojaCollection(new ArrayList<Foja>());
        }
        if (obra.getItemCollection() == null) {
            obra.setItemCollection(new ArrayList<Item>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa cuitEmpresa = obra.getCuitEmpresa();
            if (cuitEmpresa != null) {
                cuitEmpresa = em.getReference(cuitEmpresa.getClass(), cuitEmpresa.getCuit());
                obra.setCuitEmpresa(cuitEmpresa);
            }
            Collection<Foja> attachedFojaCollection = new ArrayList<Foja>();
            for (Foja fojaCollectionFojaToAttach : obra.getFojaCollection()) {
                fojaCollectionFojaToAttach = em.getReference(fojaCollectionFojaToAttach.getClass(), fojaCollectionFojaToAttach.getIdFoja());
                attachedFojaCollection.add(fojaCollectionFojaToAttach);
            }
            obra.setFojaCollection(attachedFojaCollection);
            Collection<Item> attachedItemCollection = new ArrayList<Item>();
            for (Item itemCollectionItemToAttach : obra.getItemCollection()) {
                itemCollectionItemToAttach = em.getReference(itemCollectionItemToAttach.getClass(), itemCollectionItemToAttach.getIdItem());
                attachedItemCollection.add(itemCollectionItemToAttach);
            }
            obra.setItemCollection(attachedItemCollection);
            em.persist(obra);
            if (cuitEmpresa != null) {
                cuitEmpresa.getObraCollection().add(obra);
                cuitEmpresa = em.merge(cuitEmpresa);
            }
            for (Foja fojaCollectionFoja : obra.getFojaCollection()) {
                Obra oldObraOfFojaCollectionFoja = fojaCollectionFoja.getObra();
                fojaCollectionFoja.setObra(obra);
                fojaCollectionFoja = em.merge(fojaCollectionFoja);
                if (oldObraOfFojaCollectionFoja != null) {
                    oldObraOfFojaCollectionFoja.getFojaCollection().remove(fojaCollectionFoja);
                    oldObraOfFojaCollectionFoja = em.merge(oldObraOfFojaCollectionFoja);
                }
            }
            for (Item itemCollectionItem : obra.getItemCollection()) {
                Obra oldObraOfItemCollectionItem = itemCollectionItem.getObra();
                itemCollectionItem.setObra(obra);
                itemCollectionItem = em.merge(itemCollectionItem);
                if (oldObraOfItemCollectionItem != null) {
                    oldObraOfItemCollectionItem.getItemCollection().remove(itemCollectionItem);
                    oldObraOfItemCollectionItem = em.merge(oldObraOfItemCollectionItem);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Obra obra) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Obra persistentObra = em.find(Obra.class, obra.getIdObra());
            Empresa cuitEmpresaOld = persistentObra.getCuitEmpresa();
            Empresa cuitEmpresaNew = obra.getCuitEmpresa();
            Collection<Foja> fojaCollectionOld = persistentObra.getFojaCollection();
            Collection<Foja> fojaCollectionNew = obra.getFojaCollection();
            Collection<Item> itemCollectionOld = persistentObra.getItemCollection();
            Collection<Item> itemCollectionNew = obra.getItemCollection();
            List<String> illegalOrphanMessages = null;
            for (Foja fojaCollectionOldFoja : fojaCollectionOld) {
                if (!fojaCollectionNew.contains(fojaCollectionOldFoja)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Foja " + fojaCollectionOldFoja + " since its obra field is not nullable.");
                }
            }
            for (Item itemCollectionOldItem : itemCollectionOld) {
                if (!itemCollectionNew.contains(itemCollectionOldItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Item " + itemCollectionOldItem + " since its obra field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cuitEmpresaNew != null) {
                cuitEmpresaNew = em.getReference(cuitEmpresaNew.getClass(), cuitEmpresaNew.getCuit());
                obra.setCuitEmpresa(cuitEmpresaNew);
            }
            Collection<Foja> attachedFojaCollectionNew = new ArrayList<Foja>();
            for (Foja fojaCollectionNewFojaToAttach : fojaCollectionNew) {
                fojaCollectionNewFojaToAttach = em.getReference(fojaCollectionNewFojaToAttach.getClass(), fojaCollectionNewFojaToAttach.getIdFoja());
                attachedFojaCollectionNew.add(fojaCollectionNewFojaToAttach);
            }
            fojaCollectionNew = attachedFojaCollectionNew;
            obra.setFojaCollection(fojaCollectionNew);
            Collection<Item> attachedItemCollectionNew = new ArrayList<Item>();
            for (Item itemCollectionNewItemToAttach : itemCollectionNew) {
                itemCollectionNewItemToAttach = em.getReference(itemCollectionNewItemToAttach.getClass(), itemCollectionNewItemToAttach.getIdItem());
                attachedItemCollectionNew.add(itemCollectionNewItemToAttach);
            }
            itemCollectionNew = attachedItemCollectionNew;
            obra.setItemCollection(itemCollectionNew);
            obra = em.merge(obra);
            if (cuitEmpresaOld != null && !cuitEmpresaOld.equals(cuitEmpresaNew)) {
                cuitEmpresaOld.getObraCollection().remove(obra);
                cuitEmpresaOld = em.merge(cuitEmpresaOld);
            }
            if (cuitEmpresaNew != null && !cuitEmpresaNew.equals(cuitEmpresaOld)) {
                cuitEmpresaNew.getObraCollection().add(obra);
                cuitEmpresaNew = em.merge(cuitEmpresaNew);
            }
            for (Foja fojaCollectionNewFoja : fojaCollectionNew) {
                if (!fojaCollectionOld.contains(fojaCollectionNewFoja)) {
                    Obra oldObraOfFojaCollectionNewFoja = fojaCollectionNewFoja.getObra();
                    fojaCollectionNewFoja.setObra(obra);
                    fojaCollectionNewFoja = em.merge(fojaCollectionNewFoja);
                    if (oldObraOfFojaCollectionNewFoja != null && !oldObraOfFojaCollectionNewFoja.equals(obra)) {
                        oldObraOfFojaCollectionNewFoja.getFojaCollection().remove(fojaCollectionNewFoja);
                        oldObraOfFojaCollectionNewFoja = em.merge(oldObraOfFojaCollectionNewFoja);
                    }
                }
            }
            for (Item itemCollectionNewItem : itemCollectionNew) {
                if (!itemCollectionOld.contains(itemCollectionNewItem)) {
                    Obra oldObraOfItemCollectionNewItem = itemCollectionNewItem.getObra();
                    itemCollectionNewItem.setObra(obra);
                    itemCollectionNewItem = em.merge(itemCollectionNewItem);
                    if (oldObraOfItemCollectionNewItem != null && !oldObraOfItemCollectionNewItem.equals(obra)) {
                        oldObraOfItemCollectionNewItem.getItemCollection().remove(itemCollectionNewItem);
                        oldObraOfItemCollectionNewItem = em.merge(oldObraOfItemCollectionNewItem);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = obra.getIdObra();
                if (findObra(id) == null) {
                    throw new NonexistentEntityException("The obra with id " + id + " no longer exists.");
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
            Obra obra;
            try {
                obra = em.getReference(Obra.class, id);
                obra.getIdObra();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The obra with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Foja> fojaCollectionOrphanCheck = obra.getFojaCollection();
            for (Foja fojaCollectionOrphanCheckFoja : fojaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Obra (" + obra + ") cannot be destroyed since the Foja " + fojaCollectionOrphanCheckFoja + " in its fojaCollection field has a non-nullable obra field.");
            }
            Collection<Item> itemCollectionOrphanCheck = obra.getItemCollection();
            for (Item itemCollectionOrphanCheckItem : itemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Obra (" + obra + ") cannot be destroyed since the Item " + itemCollectionOrphanCheckItem + " in its itemCollection field has a non-nullable obra field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresa cuitEmpresa = obra.getCuitEmpresa();
            if (cuitEmpresa != null) {
                cuitEmpresa.getObraCollection().remove(obra);
                cuitEmpresa = em.merge(cuitEmpresa);
            }
            em.remove(obra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Obra> findObraEntities() {
        return findObraEntities(true, -1, -1);
    }

    public List<Obra> findObraEntities(int maxResults, int firstResult) {
        return findObraEntities(false, maxResults, firstResult);
    }

    private List<Obra> findObraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Obra.class));
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

    public Obra findObra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Obra.class, id);
        } finally {
            em.close();
        }
    }

    public int getObraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Obra> rt = cq.from(Obra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
