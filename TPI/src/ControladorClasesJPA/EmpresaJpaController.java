/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControladorClasesJPA;

import ControladorClasesJPA.exceptions.IllegalOrphanException;
import ControladorClasesJPA.exceptions.NonexistentEntityException;
import ControladorClasesJPA.exceptions.PreexistingEntityException;
import ModeloDB.Empresa;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ModeloDB.Obra;
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
public class EmpresaJpaController implements Serializable {

    public EmpresaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresa empresa) throws PreexistingEntityException, Exception {
        if (empresa.getObraCollection() == null) {
            empresa.setObraCollection(new ArrayList<Obra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Obra> attachedObraCollection = new ArrayList<Obra>();
            for (Obra obraCollectionObraToAttach : empresa.getObraCollection()) {
                obraCollectionObraToAttach = em.getReference(obraCollectionObraToAttach.getClass(), obraCollectionObraToAttach.getIdObra());
                attachedObraCollection.add(obraCollectionObraToAttach);
            }
            empresa.setObraCollection(attachedObraCollection);
            em.persist(empresa);
            for (Obra obraCollectionObra : empresa.getObraCollection()) {
                Empresa oldCuitEmpresaOfObraCollectionObra = obraCollectionObra.getCuitEmpresa();
                obraCollectionObra.setCuitEmpresa(empresa);
                obraCollectionObra = em.merge(obraCollectionObra);
                if (oldCuitEmpresaOfObraCollectionObra != null) {
                    oldCuitEmpresaOfObraCollectionObra.getObraCollection().remove(obraCollectionObra);
                    oldCuitEmpresaOfObraCollectionObra = em.merge(oldCuitEmpresaOfObraCollectionObra);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpresa(empresa.getCuit()) != null) {
                throw new PreexistingEntityException("Empresa " + empresa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresa empresa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa persistentEmpresa = em.find(Empresa.class, empresa.getCuit());
            Collection<Obra> obraCollectionOld = persistentEmpresa.getObraCollection();
            Collection<Obra> obraCollectionNew = empresa.getObraCollection();
            List<String> illegalOrphanMessages = null;
            for (Obra obraCollectionOldObra : obraCollectionOld) {
                if (!obraCollectionNew.contains(obraCollectionOldObra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Obra " + obraCollectionOldObra + " since its cuitEmpresa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Obra> attachedObraCollectionNew = new ArrayList<Obra>();
            for (Obra obraCollectionNewObraToAttach : obraCollectionNew) {
                obraCollectionNewObraToAttach = em.getReference(obraCollectionNewObraToAttach.getClass(), obraCollectionNewObraToAttach.getIdObra());
                attachedObraCollectionNew.add(obraCollectionNewObraToAttach);
            }
            obraCollectionNew = attachedObraCollectionNew;
            empresa.setObraCollection(obraCollectionNew);
            empresa = em.merge(empresa);
            for (Obra obraCollectionNewObra : obraCollectionNew) {
                if (!obraCollectionOld.contains(obraCollectionNewObra)) {
                    Empresa oldCuitEmpresaOfObraCollectionNewObra = obraCollectionNewObra.getCuitEmpresa();
                    obraCollectionNewObra.setCuitEmpresa(empresa);
                    obraCollectionNewObra = em.merge(obraCollectionNewObra);
                    if (oldCuitEmpresaOfObraCollectionNewObra != null && !oldCuitEmpresaOfObraCollectionNewObra.equals(empresa)) {
                        oldCuitEmpresaOfObraCollectionNewObra.getObraCollection().remove(obraCollectionNewObra);
                        oldCuitEmpresaOfObraCollectionNewObra = em.merge(oldCuitEmpresaOfObraCollectionNewObra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empresa.getCuit();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
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
            Empresa empresa;
            try {
                empresa = em.getReference(Empresa.class, id);
                empresa.getCuit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Obra> obraCollectionOrphanCheck = empresa.getObraCollection();
            for (Obra obraCollectionOrphanCheckObra : obraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresa (" + empresa + ") cannot be destroyed since the Obra " + obraCollectionOrphanCheckObra + " in its obraCollection field has a non-nullable cuitEmpresa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresa> findEmpresaEntities() {
        return findEmpresaEntities(true, -1, -1);
    }

    public List<Empresa> findEmpresaEntities(int maxResults, int firstResult) {
        return findEmpresaEntities(false, maxResults, firstResult);
    }

    private List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresa.class));
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

    public Empresa findEmpresa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresa> rt = cq.from(Empresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
