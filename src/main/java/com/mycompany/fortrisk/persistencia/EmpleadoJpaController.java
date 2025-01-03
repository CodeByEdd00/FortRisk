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
import com.mycompany.fortrisk.logica.Accidente;
import com.mycompany.fortrisk.logica.Empleado;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.fortrisk.logica.EquipoProteccion;
import com.mycompany.fortrisk.persistencia.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author egarc
 */
public class EmpleadoJpaController implements Serializable {

    public EmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    public EmpleadoJpaController() {
         emf = Persistence.createEntityManagerFactory("FortRiskPU");
    }

    public void create(Empleado empleado) {
        if (empleado.getAccidentes() == null) {
            empleado.setAccidentes(new ArrayList<>());
        }
        if (empleado.getEpp() == null) {
            empleado.setEpp(new ArrayList<>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Accidente> attachedAccidentes = new ArrayList<>();
            for (Accidente accidentesAccidenteToAttach : empleado.getAccidentes()) {
                accidentesAccidenteToAttach = em.getReference(accidentesAccidenteToAttach.getClass(), accidentesAccidenteToAttach.getReferencia());
                attachedAccidentes.add(accidentesAccidenteToAttach);
            }
            empleado.setAccidentes(attachedAccidentes);
            List<EquipoProteccion> attachedEpp = new ArrayList<>();
            for (EquipoProteccion eppEquipoProteccionToAttach : empleado.getEpp()) {
                eppEquipoProteccionToAttach = em.getReference(eppEquipoProteccionToAttach.getClass(), eppEquipoProteccionToAttach.getReferencia());
                attachedEpp.add(eppEquipoProteccionToAttach);
            }
            empleado.setEpp(attachedEpp);
            em.persist(empleado);
            for (Accidente accidentesAccidente : empleado.getAccidentes()) {
                accidentesAccidente = em.merge(accidentesAccidente);
            }
            for (EquipoProteccion eppEquipoProteccion : empleado.getEpp()) {
                eppEquipoProteccion = em.merge(eppEquipoProteccion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleado empleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleado persistentEmpleado = em.find(Empleado.class, empleado.getId_trabajador());
            List<Accidente> accidentesOld = persistentEmpleado.getAccidentes();
            List<Accidente> accidentesNew = empleado.getAccidentes();
            List<EquipoProteccion> eppOld = persistentEmpleado.getEpp();
            List<EquipoProteccion> eppNew = empleado.getEpp();
            List<Accidente> attachedAccidentesNew = new ArrayList<>();
            for (Accidente accidentesNewAccidenteToAttach : accidentesNew) {
                accidentesNewAccidenteToAttach = em.getReference(accidentesNewAccidenteToAttach.getClass(), accidentesNewAccidenteToAttach.getReferencia());
                attachedAccidentesNew.add(accidentesNewAccidenteToAttach);
            }
            accidentesNew = attachedAccidentesNew;
            empleado.setAccidentes(accidentesNew);
            List<EquipoProteccion> attachedEppNew = new ArrayList<>();
            for (EquipoProteccion eppNewEquipoProteccionToAttach : eppNew) {
                eppNewEquipoProteccionToAttach = em.getReference(eppNewEquipoProteccionToAttach.getClass(), eppNewEquipoProteccionToAttach.getReferencia());
                attachedEppNew.add(eppNewEquipoProteccionToAttach);
            }
            eppNew = attachedEppNew;
            empleado.setEpp(eppNew);
            empleado = em.merge(empleado);
            for (Accidente accidentesOldAccidente : accidentesOld) {
                if (!accidentesNew.contains(accidentesOldAccidente)) {
                    accidentesOldAccidente = em.merge(accidentesOldAccidente);
                }
            }
            for (Accidente accidentesNewAccidente : accidentesNew) {
                if (!accidentesOld.contains(accidentesNewAccidente)) {
                    accidentesNewAccidente = em.merge(accidentesNewAccidente);
                }
            }
            for (EquipoProteccion eppOldEquipoProteccion : eppOld) {
                if (!eppNew.contains(eppOldEquipoProteccion)) {
                    eppOldEquipoProteccion = em.merge(eppOldEquipoProteccion);
                }
            }
            for (EquipoProteccion eppNewEquipoProteccion : eppNew) {
                if (!eppOld.contains(eppNewEquipoProteccion)) {
                    eppNewEquipoProteccion = em.merge(eppNewEquipoProteccion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleado.getId_trabajador();
                if (findEmpleado(id) == null) {
                    throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.");
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
            Empleado empleado;
            try {
                empleado = em.getReference(Empleado.class, id);
                empleado.getId_trabajador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleado with id " + id + " no longer exists.", enfe);
            }
            List<Accidente> accidentes = empleado.getAccidentes();
            for (Accidente accidentesAccidente : accidentes) {
                accidentesAccidente = em.merge(accidentesAccidente);
            }
            List<EquipoProteccion> epp = empleado.getEpp();
            for (EquipoProteccion eppEquipoProteccion : epp) {
                eppEquipoProteccion = em.merge(eppEquipoProteccion);
            }
            em.remove(empleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleado> findEmpleadoEntities() {
        return findEmpleadoEntities(true, -1, -1);
    }

    public List<Empleado> findEmpleadoEntities(int maxResults, int firstResult) {
        return findEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<Empleado> findEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleado.class));
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

    public Empleado findEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleado> rt = cq.from(Empleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
