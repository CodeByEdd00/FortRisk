package com.mycompany.fortrisk.persistencia;

import com.mycompany.fortrisk.logica.Accidente;
import com.mycompany.fortrisk.logica.Empleado;
import com.mycompany.fortrisk.logica.EquipoProteccion;
import com.mycompany.fortrisk.persistencia.exceptions.NonexistentEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladoraPersistencia {
    
    EquipoProteccionJpaController eppJpa = new  EquipoProteccionJpaController();
    EmpleadoJpaController empleadoJpa = new EmpleadoJpaController();
    AccidenteJpaController accidenteJpa = new AccidenteJpaController();

    //Equipos de protección
    public void guardar(EquipoProteccion equipoProteccion) {
        //Crear equipo de proteccion en la BD
        eppJpa.create(equipoProteccion);
    }

    public List<EquipoProteccion> traerDatos() {
        return eppJpa.findEquipoProteccionEntities();
    }

    public void borrarRegistro(int referencia) {
        try{
            eppJpa.destroy(referencia);
        }catch(NonexistentEntityException ex){
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null,ex);
        }
    }
    
    public void modificarEpp(EquipoProteccion epp) {
        try {
            eppJpa.edit(epp);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public EquipoProteccion traerEpp(int referencia) {
        return eppJpa.findEquipoProteccion(referencia);
    }

    
    //Empleados
    public void guardarEmpleado(Empleado empleado) {
        empleadoJpa.create(empleado);
    }

    public List<Empleado> traerDatosEmpleado() {
        return empleadoJpa.findEmpleadoEntities();
    }

    public void borrarRegistroEmpleado(int referencia) {
        try{
            empleadoJpa.destroy(referencia);
        }catch(NonexistentEntityException ex){
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null,ex);
        }
    }

    public Empleado traerEmpleado(int id_trabajador) {
        return empleadoJpa.findEmpleado(id_trabajador);
    }

    public void modificarEmpleado(Empleado empleado) {
        try {
            empleadoJpa.edit(empleado);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   //Accidentes
    public void guardarAccidente(Accidente accidente) {
    accidenteJpa.create(accidente);    
    }
    
    
}
