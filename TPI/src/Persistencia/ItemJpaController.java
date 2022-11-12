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
import Modelo1.TipoItem;
import Modelo1.Costo;
import java.util.ArrayList;
import java.util.List;
import Modelo1.DetallesFoja;
import Modelo1.Item;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Isaac
 */
public class ItemJpaController implements Serializable {

    public ItemJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public ItemJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    
    

    public void create(Item item) {
        if (item.getCostoList() == null) {
            item.setCostoList(new ArrayList<Costo>());
        }
        if (item.getDetallesFojaList() == null) {
            item.setDetallesFojaList(new ArrayList<DetallesFoja>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Obra idObra = item.getIdObra();
            if (idObra != null) {
                idObra = em.getReference(idObra.getClass(), idObra.getIdObra());
                item.setIdObra(idObra);
            }
            TipoItem idTipoitem = item.getIdTipoitem();
            if (idTipoitem != null) {
                idTipoitem = em.getReference(idTipoitem.getClass(), idTipoitem.getIdTipoitem());
                item.setIdTipoitem(idTipoitem);
            }
            List<Costo> attachedCostoList = new ArrayList<Costo>();
            for (Costo costoListCostoToAttach : item.getCostoList()) {
                costoListCostoToAttach = em.getReference(costoListCostoToAttach.getClass(), costoListCostoToAttach.getIdCosto());
                attachedCostoList.add(costoListCostoToAttach);
            }
            item.setCostoList(attachedCostoList);
            List<DetallesFoja> attachedDetallesFojaList = new ArrayList<DetallesFoja>();
            for (DetallesFoja detallesFojaListDetallesFojaToAttach : item.getDetallesFojaList()) {
                detallesFojaListDetallesFojaToAttach = em.getReference(detallesFojaListDetallesFojaToAttach.getClass(), detallesFojaListDetallesFojaToAttach.getIdDetallesfoja());
                attachedDetallesFojaList.add(detallesFojaListDetallesFojaToAttach);
            }
            item.setDetallesFojaList(attachedDetallesFojaList);
            em.persist(item);
            if (idObra != null) {
                idObra.getItemList().add(item);
                idObra = em.merge(idObra);
            }
            if (idTipoitem != null) {
                idTipoitem.getItemList().add(item);
                idTipoitem = em.merge(idTipoitem);
            }
            for (Costo costoListCosto : item.getCostoList()) {
                Item oldItemidItemOfCostoListCosto = costoListCosto.getItemidItem();
                costoListCosto.setItemidItem(item);
                costoListCosto = em.merge(costoListCosto);
                if (oldItemidItemOfCostoListCosto != null) {
                    oldItemidItemOfCostoListCosto.getCostoList().remove(costoListCosto);
                    oldItemidItemOfCostoListCosto = em.merge(oldItemidItemOfCostoListCosto);
                }
            }
            for (DetallesFoja detallesFojaListDetallesFoja : item.getDetallesFojaList()) {
                Item oldItemidItemOfDetallesFojaListDetallesFoja = detallesFojaListDetallesFoja.getItemidItem();
                detallesFojaListDetallesFoja.setItemidItem(item);
                detallesFojaListDetallesFoja = em.merge(detallesFojaListDetallesFoja);
                if (oldItemidItemOfDetallesFojaListDetallesFoja != null) {
                    oldItemidItemOfDetallesFojaListDetallesFoja.getDetallesFojaList().remove(detallesFojaListDetallesFoja);
                    oldItemidItemOfDetallesFojaListDetallesFoja = em.merge(oldItemidItemOfDetallesFojaListDetallesFoja);
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
            Obra idObraOld = persistentItem.getIdObra();
            Obra idObraNew = item.getIdObra();
            TipoItem idTipoitemOld = persistentItem.getIdTipoitem();
            TipoItem idTipoitemNew = item.getIdTipoitem();
            List<Costo> costoListOld = persistentItem.getCostoList();
            List<Costo> costoListNew = item.getCostoList();
            List<DetallesFoja> detallesFojaListOld = persistentItem.getDetallesFojaList();
            List<DetallesFoja> detallesFojaListNew = item.getDetallesFojaList();
            List<String> illegalOrphanMessages = null;
            for (Costo costoListOldCosto : costoListOld) {
                if (!costoListNew.contains(costoListOldCosto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Costo " + costoListOldCosto + " since its itemidItem field is not nullable.");
                }
            }
            for (DetallesFoja detallesFojaListOldDetallesFoja : detallesFojaListOld) {
                if (!detallesFojaListNew.contains(detallesFojaListOldDetallesFoja)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetallesFoja " + detallesFojaListOldDetallesFoja + " since its itemidItem field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idObraNew != null) {
                idObraNew = em.getReference(idObraNew.getClass(), idObraNew.getIdObra());
                item.setIdObra(idObraNew);
            }
            if (idTipoitemNew != null) {
                idTipoitemNew = em.getReference(idTipoitemNew.getClass(), idTipoitemNew.getIdTipoitem());
                item.setIdTipoitem(idTipoitemNew);
            }
            List<Costo> attachedCostoListNew = new ArrayList<Costo>();
            for (Costo costoListNewCostoToAttach : costoListNew) {
                costoListNewCostoToAttach = em.getReference(costoListNewCostoToAttach.getClass(), costoListNewCostoToAttach.getIdCosto());
                attachedCostoListNew.add(costoListNewCostoToAttach);
            }
            costoListNew = attachedCostoListNew;
            item.setCostoList(costoListNew);
            List<DetallesFoja> attachedDetallesFojaListNew = new ArrayList<DetallesFoja>();
            for (DetallesFoja detallesFojaListNewDetallesFojaToAttach : detallesFojaListNew) {
                detallesFojaListNewDetallesFojaToAttach = em.getReference(detallesFojaListNewDetallesFojaToAttach.getClass(), detallesFojaListNewDetallesFojaToAttach.getIdDetallesfoja());
                attachedDetallesFojaListNew.add(detallesFojaListNewDetallesFojaToAttach);
            }
            detallesFojaListNew = attachedDetallesFojaListNew;
            item.setDetallesFojaList(detallesFojaListNew);
            item = em.merge(item);
            if (idObraOld != null && !idObraOld.equals(idObraNew)) {
                idObraOld.getItemList().remove(item);
                idObraOld = em.merge(idObraOld);
            }
            if (idObraNew != null && !idObraNew.equals(idObraOld)) {
                idObraNew.getItemList().add(item);
                idObraNew = em.merge(idObraNew);
            }
            if (idTipoitemOld != null && !idTipoitemOld.equals(idTipoitemNew)) {
                idTipoitemOld.getItemList().remove(item);
                idTipoitemOld = em.merge(idTipoitemOld);
            }
            if (idTipoitemNew != null && !idTipoitemNew.equals(idTipoitemOld)) {
                idTipoitemNew.getItemList().add(item);
                idTipoitemNew = em.merge(idTipoitemNew);
            }
            for (Costo costoListNewCosto : costoListNew) {
                if (!costoListOld.contains(costoListNewCosto)) {
                    Item oldItemidItemOfCostoListNewCosto = costoListNewCosto.getItemidItem();
                    costoListNewCosto.setItemidItem(item);
                    costoListNewCosto = em.merge(costoListNewCosto);
                    if (oldItemidItemOfCostoListNewCosto != null && !oldItemidItemOfCostoListNewCosto.equals(item)) {
                        oldItemidItemOfCostoListNewCosto.getCostoList().remove(costoListNewCosto);
                        oldItemidItemOfCostoListNewCosto = em.merge(oldItemidItemOfCostoListNewCosto);
                    }
                }
            }
            for (DetallesFoja detallesFojaListNewDetallesFoja : detallesFojaListNew) {
                if (!detallesFojaListOld.contains(detallesFojaListNewDetallesFoja)) {
                    Item oldItemidItemOfDetallesFojaListNewDetallesFoja = detallesFojaListNewDetallesFoja.getItemidItem();
                    detallesFojaListNewDetallesFoja.setItemidItem(item);
                    detallesFojaListNewDetallesFoja = em.merge(detallesFojaListNewDetallesFoja);
                    if (oldItemidItemOfDetallesFojaListNewDetallesFoja != null && !oldItemidItemOfDetallesFojaListNewDetallesFoja.equals(item)) {
                        oldItemidItemOfDetallesFojaListNewDetallesFoja.getDetallesFojaList().remove(detallesFojaListNewDetallesFoja);
                        oldItemidItemOfDetallesFojaListNewDetallesFoja = em.merge(oldItemidItemOfDetallesFojaListNewDetallesFoja);
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
            List<Costo> costoListOrphanCheck = item.getCostoList();
            for (Costo costoListOrphanCheckCosto : costoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Item (" + item + ") cannot be destroyed since the Costo " + costoListOrphanCheckCosto + " in its costoList field has a non-nullable itemidItem field.");
            }
            List<DetallesFoja> detallesFojaListOrphanCheck = item.getDetallesFojaList();
            for (DetallesFoja detallesFojaListOrphanCheckDetallesFoja : detallesFojaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Item (" + item + ") cannot be destroyed since the DetallesFoja " + detallesFojaListOrphanCheckDetallesFoja + " in its detallesFojaList field has a non-nullable itemidItem field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Obra idObra = item.getIdObra();
            if (idObra != null) {
                idObra.getItemList().remove(item);
                idObra = em.merge(idObra);
            }
            TipoItem idTipoitem = item.getIdTipoitem();
            if (idTipoitem != null) {
                idTipoitem.getItemList().remove(item);
                idTipoitem = em.merge(idTipoitem);
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
