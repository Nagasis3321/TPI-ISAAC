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
import Modelo1.Empresa;
import Modelo1.Foja;
import java.util.ArrayList;
import java.util.List;
import Modelo1.Item;
import Modelo1.Obra;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Isaac
 */
public class ObraJpaController implements Serializable {

    public ObraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ObraJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }

    
    
    
    public void create(Obra obra) {
        if (obra.getFojaList() == null) {
            obra.setFojaList(new ArrayList<Foja>());
        }
        if (obra.getItemList() == null) {
            obra.setItemList(new ArrayList<Item>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa empresacuit = obra.getEmpresacuit();
            if (empresacuit != null) {
                empresacuit = em.getReference(empresacuit.getClass(), empresacuit.getCuit());
                obra.setEmpresacuit(empresacuit);
            }
            List<Foja> attachedFojaList = new ArrayList<Foja>();
            for (Foja fojaListFojaToAttach : obra.getFojaList()) {
                fojaListFojaToAttach = em.getReference(fojaListFojaToAttach.getClass(), fojaListFojaToAttach.getIdFoja());
                attachedFojaList.add(fojaListFojaToAttach);
            }
            obra.setFojaList(attachedFojaList);
            List<Item> attachedItemList = new ArrayList<Item>();
            for (Item itemListItemToAttach : obra.getItemList()) {
                itemListItemToAttach = em.getReference(itemListItemToAttach.getClass(), itemListItemToAttach.getIdItem());
                attachedItemList.add(itemListItemToAttach);
            }
            obra.setItemList(attachedItemList);
            em.persist(obra);
            if (empresacuit != null) {
                empresacuit.getObraList().add(obra);
                empresacuit = em.merge(empresacuit);
            }
            for (Foja fojaListFoja : obra.getFojaList()) {
                Obra oldObraidObraOfFojaListFoja = fojaListFoja.getObraidObra();
                fojaListFoja.setObraidObra(obra);
                fojaListFoja = em.merge(fojaListFoja);
                if (oldObraidObraOfFojaListFoja != null) {
                    oldObraidObraOfFojaListFoja.getFojaList().remove(fojaListFoja);
                    oldObraidObraOfFojaListFoja = em.merge(oldObraidObraOfFojaListFoja);
                }
            }
            for (Item itemListItem : obra.getItemList()) {
                Obra oldIdObraOfItemListItem = itemListItem.getIdObra();
                itemListItem.setIdObra(obra);
                itemListItem = em.merge(itemListItem);
                if (oldIdObraOfItemListItem != null) {
                    oldIdObraOfItemListItem.getItemList().remove(itemListItem);
                    oldIdObraOfItemListItem = em.merge(oldIdObraOfItemListItem);
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
            Empresa empresacuitOld = persistentObra.getEmpresacuit();
            Empresa empresacuitNew = obra.getEmpresacuit();
            List<Foja> fojaListOld = persistentObra.getFojaList();
            List<Foja> fojaListNew = obra.getFojaList();
            List<Item> itemListOld = persistentObra.getItemList();
            List<Item> itemListNew = obra.getItemList();
            List<String> illegalOrphanMessages = null;
            for (Foja fojaListOldFoja : fojaListOld) {
                if (!fojaListNew.contains(fojaListOldFoja)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Foja " + fojaListOldFoja + " since its obraidObra field is not nullable.");
                }
            }
            for (Item itemListOldItem : itemListOld) {
                if (!itemListNew.contains(itemListOldItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Item " + itemListOldItem + " since its idObra field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empresacuitNew != null) {
                empresacuitNew = em.getReference(empresacuitNew.getClass(), empresacuitNew.getCuit());
                obra.setEmpresacuit(empresacuitNew);
            }
            List<Foja> attachedFojaListNew = new ArrayList<Foja>();
            for (Foja fojaListNewFojaToAttach : fojaListNew) {
                fojaListNewFojaToAttach = em.getReference(fojaListNewFojaToAttach.getClass(), fojaListNewFojaToAttach.getIdFoja());
                attachedFojaListNew.add(fojaListNewFojaToAttach);
            }
            fojaListNew = attachedFojaListNew;
            obra.setFojaList(fojaListNew);
            List<Item> attachedItemListNew = new ArrayList<Item>();
            for (Item itemListNewItemToAttach : itemListNew) {
                itemListNewItemToAttach = em.getReference(itemListNewItemToAttach.getClass(), itemListNewItemToAttach.getIdItem());
                attachedItemListNew.add(itemListNewItemToAttach);
            }
            itemListNew = attachedItemListNew;
            obra.setItemList(itemListNew);
            obra = em.merge(obra);
            if (empresacuitOld != null && !empresacuitOld.equals(empresacuitNew)) {
                empresacuitOld.getObraList().remove(obra);
                empresacuitOld = em.merge(empresacuitOld);
            }
            if (empresacuitNew != null && !empresacuitNew.equals(empresacuitOld)) {
                empresacuitNew.getObraList().add(obra);
                empresacuitNew = em.merge(empresacuitNew);
            }
            for (Foja fojaListNewFoja : fojaListNew) {
                if (!fojaListOld.contains(fojaListNewFoja)) {
                    Obra oldObraidObraOfFojaListNewFoja = fojaListNewFoja.getObraidObra();
                    fojaListNewFoja.setObraidObra(obra);
                    fojaListNewFoja = em.merge(fojaListNewFoja);
                    if (oldObraidObraOfFojaListNewFoja != null && !oldObraidObraOfFojaListNewFoja.equals(obra)) {
                        oldObraidObraOfFojaListNewFoja.getFojaList().remove(fojaListNewFoja);
                        oldObraidObraOfFojaListNewFoja = em.merge(oldObraidObraOfFojaListNewFoja);
                    }
                }
            }
            for (Item itemListNewItem : itemListNew) {
                if (!itemListOld.contains(itemListNewItem)) {
                    Obra oldIdObraOfItemListNewItem = itemListNewItem.getIdObra();
                    itemListNewItem.setIdObra(obra);
                    itemListNewItem = em.merge(itemListNewItem);
                    if (oldIdObraOfItemListNewItem != null && !oldIdObraOfItemListNewItem.equals(obra)) {
                        oldIdObraOfItemListNewItem.getItemList().remove(itemListNewItem);
                        oldIdObraOfItemListNewItem = em.merge(oldIdObraOfItemListNewItem);
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
            List<Foja> fojaListOrphanCheck = obra.getFojaList();
            for (Foja fojaListOrphanCheckFoja : fojaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Obra (" + obra + ") cannot be destroyed since the Foja " + fojaListOrphanCheckFoja + " in its fojaList field has a non-nullable obraidObra field.");
            }
            List<Item> itemListOrphanCheck = obra.getItemList();
            for (Item itemListOrphanCheckItem : itemListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Obra (" + obra + ") cannot be destroyed since the Item " + itemListOrphanCheckItem + " in its itemList field has a non-nullable idObra field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresa empresacuit = obra.getEmpresacuit();
            if (empresacuit != null) {
                empresacuit.getObraList().remove(obra);
                empresacuit = em.merge(empresacuit);
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
