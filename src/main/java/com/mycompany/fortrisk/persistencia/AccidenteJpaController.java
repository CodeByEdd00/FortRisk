package com.mycompany.fortrisk.persistencia;

import com.mycompany.fortrisk.logica.Accidente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.fortrisk.logica.Empleado;
import com.mycompany.fortrisk.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AccidenteJpaController implements Serializable {

    public AccidenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
    public AccidenteJpaController() {
         emf = Persistence.createEntityManagerFactory("FortRiskPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Accidente accidente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = accidente.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getId_trabajador());
            }
            em.persist(accidente);
            if (empleado != null) {
                empleado.getAccidente().add(accidente);
                empleado = em.merge(empleado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Accidente accidente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Accidente persistentAccidente = em.find(Accidente.class, accidente.getReferencia());
            Empleado empleadoOld = persistentAccidente.getEmpleado();
            Empleado empleadoNew = accidente.getEmpleado();
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getId_trabajador());
            }
            accidente = em.merge(accidente);
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                empleadoOld.getAccidente().remove(accidente);
                empleadoOld = em.merge(empleadoOld);
            }
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                empleadoNew.getAccidente().add(accidente);
                empleadoNew = em.merge(empleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = accidente.getReferencia();
                if (findAccidente(id) == null) {
                    throw new NonexistentEntityException("The accidente with id " + id + " no longer exists.");
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
            Accidente accidente;
            try {
                accidente = em.getReference(Accidente.class, id);
                accidente.getReferencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accidente with id " + id + " no longer exists.", enfe);
            }
            Empleado empleado = accidente.getEmpleado();
            if (empleado != null) {
                empleado.getAccidente().remove(accidente);
                empleado = em.merge(empleado);
            }
            em.remove(accidente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Accidente> findAccidenteEntities() {
        return findAccidenteEntities(true, -1, -1);
    }

    public List<Accidente> findAccidenteEntities(int maxResults, int firstResult) {
        return findAccidenteEntities(false, maxResults, firstResult);
    }

    private List<Accidente> findAccidenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Accidente.class));
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

    public Accidente findAccidente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Accidente.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccidenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Accidente> rt = cq.from(Accidente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
