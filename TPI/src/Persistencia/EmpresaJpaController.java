/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo1.Empresa;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo1.Obra;
import Persistencia.exceptions.IllegalOrphanException;
import Persistencia.exceptions.NonexistentEntityException;
import Persistencia.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Isaac
 */
public class EmpresaJpaController implements Serializable {

    public EmpresaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EmpresaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("TPIPU");
    }
    
    
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresa empresa) throws PreexistingEntityException, Exception {
        if (empresa.getObraList() == null) {
            empresa.setObraList(new ArrayList<Obra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Obra> attachedObraList = new ArrayList<Obra>();
            for (Obra obraListObraToAttach : empresa.getObraList()) {
                obraListObraToAttach = em.getReference(obraListObraToAttach.getClass(), obraListObraToAttach.getIdObra());
                attachedObraList.add(obraListObraToAttach);
            }
            empresa.setObraList(attachedObraList);
            em.persist(empresa);
            for (Obra obraListObra : empresa.getObraList()) {
                Empresa oldEmpresacuitOfObraListObra = obraListObra.getEmpresacuit();
                obraListObra.setEmpresacuit(empresa);
                obraListObra = em.merge(obraListObra);
                if (oldEmpresacuitOfObraListObra != null) {
                    oldEmpresacuitOfObraListObra.getObraList().remove(obraListObra);
                    oldEmpresacuitOfObraListObra = em.merge(oldEmpresacuitOfObraListObra);
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
            List<Obra> obraListOld = persistentEmpresa.getObraList();
            List<Obra> obraListNew = empresa.getObraList();
            List<String> illegalOrphanMessages = null;
            for (Obra obraListOldObra : obraListOld) {
                if (!obraListNew.contains(obraListOldObra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Obra " + obraListOldObra + " since its empresacuit field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Obra> attachedObraListNew = new ArrayList<Obra>();
            for (Obra obraListNewObraToAttach : obraListNew) {
                obraListNewObraToAttach = em.getReference(obraListNewObraToAttach.getClass(), obraListNewObraToAttach.getIdObra());
                attachedObraListNew.add(obraListNewObraToAttach);
            }
            obraListNew = attachedObraListNew;
            empresa.setObraList(obraListNew);
            empresa = em.merge(empresa);
            for (Obra obraListNewObra : obraListNew) {
                if (!obraListOld.contains(obraListNewObra)) {
                    Empresa oldEmpresacuitOfObraListNewObra = obraListNewObra.getEmpresacuit();
                    obraListNewObra.setEmpresacuit(empresa);
                    obraListNewObra = em.merge(obraListNewObra);
                    if (oldEmpresacuitOfObraListNewObra != null && !oldEmpresacuitOfObraListNewObra.equals(empresa)) {
                        oldEmpresacuitOfObraListNewObra.getObraList().remove(obraListNewObra);
                        oldEmpresacuitOfObraListNewObra = em.merge(oldEmpresacuitOfObraListNewObra);
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
            List<Obra> obraListOrphanCheck = empresa.getObraList();
            for (Obra obraListOrphanCheckObra : obraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresa (" + empresa + ") cannot be destroyed since the Obra " + obraListOrphanCheckObra + " in its obraList field has a non-nullable empresacuit field.");
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
