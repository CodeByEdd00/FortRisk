/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.fortrisk.persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.fortrisk.logica.Empleado;
import com.mycompany.fortrisk.logica.EquipoProteccion;
import com.mycompany.fortrisk.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author egarc
 */
public class EquipoProteccionJpaController implements Serializable {

    public EquipoProteccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public EquipoProteccionJpaController() {
         emf = Persistence.createEntityManagerFactory("FortRiskPU");
    }

    public void create(EquipoProteccion equipoProteccion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado empleado = equipoProteccion.getEmpleado();
            if (empleado != null) {
                empleado = em.getReference(empleado.getClass(), empleado.getId_trabajador());
            }
            em.persist(equipoProteccion);
            if (empleado != null) {
                empleado.getEpp().add(equipoProteccion);
                empleado = em.merge(empleado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EquipoProteccion equipoProteccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EquipoProteccion persistentEquipoProteccion = em.find(EquipoProteccion.class, equipoProteccion.getReferencia());
            Empleado empleadoOld = persistentEquipoProteccion.getEmpleado();
            Empleado empleadoNew = equipoProteccion.getEmpleado();
            if (empleadoNew != null) {
                empleadoNew = em.getReference(empleadoNew.getClass(), empleadoNew.getId_trabajador());
            }
            equipoProteccion = em.merge(equipoProteccion);
            if (empleadoOld != null && !empleadoOld.equals(empleadoNew)) {
                empleadoOld.getEpp().remove(equipoProteccion);
                empleadoOld = em.merge(empleadoOld);
            }
            if (empleadoNew != null && !empleadoNew.equals(empleadoOld)) {
                empleadoNew.getEpp().add(equipoProteccion);
                empleadoNew = em.merge(empleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = equipoProteccion.getReferencia();
                if (findEquipoProteccion(id) == null) {
                    throw new NonexistentEntityException("The equipoProteccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EquipoProteccion equipoProteccion;
            try {
                equipoProteccion = em.getReference(EquipoProteccion.class, id);
                equipoProteccion.getReferencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipoProteccion with id " + id + " no longer exists.", enfe);
            }
            Empleado empleado = equipoProteccion.getEmpleado();
            if (empleado != null) {
                empleado.getEpp().remove(equipoProteccion);
                empleado = em.merge(empleado);
            }
            em.remove(equipoProteccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EquipoProteccion> findEquipoProteccionEntities() {
        return findEquipoProteccionEntities(true, -1, -1);
    }

    public List<EquipoProteccion> findEquipoProteccionEntities(int maxResults, int firstResult) {
        return findEquipoProteccionEntities(false, maxResults, firstResult);
    }

    private List<EquipoProteccion> findEquipoProteccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EquipoProteccion.class));
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

    public EquipoProteccion findEquipoProteccion(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EquipoProteccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquipoProteccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EquipoProteccion> rt = cq.from(EquipoProteccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
