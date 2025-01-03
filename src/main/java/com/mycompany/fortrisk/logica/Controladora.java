package com.mycompany.fortrisk.logica;

import com.mycompany.fortrisk.persistencia.ControladoraPersistencia;
import java.util.List;


public class Controladora {
    ControladoraPersistencia controlPersi = new ControladoraPersistencia();

    public void guardar(String equipo, String fechaCom, String proveedor, String tal, String obser) {
        
        //Creamos un equipo de protección
        EquipoProteccion equipoProteccion = new EquipoProteccion();
        equipoProteccion.setEquipo(equipo);
        equipoProteccion.setFechaCompra(fechaCom);
        equipoProteccion.setTalla(tal);
        equipoProteccion.setProveedor(proveedor);
        equipoProteccion.setObservaciones(obser);
        
        //Llamar a la persistencia
        controlPersi.guardar(equipoProteccion);
    }
    
    public List<EquipoProteccion> traerDatos() {
        return controlPersi.traerDatos();
    }

    public void borrarRegistro(int referencia) {
        controlPersi.borrarRegistro(referencia);
    }
    
    
    public void guardarEmpleado(String nombre, String apellido, String numDocumento, String departamento, String cargo) {
        
        //Creamos un equipo de protección
        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setNumDocumento(numDocumento);
        empleado.setDepartamento(departamento);
        empleado.setCargo(cargo);
        
        //Llamar a la persistencia
        controlPersi.guardarEmpleado(empleado);
        }
    
    public List<Empleado> traerDatosEmpleado() {
        return controlPersi.traerDatosEmpleado();
    }

    public void borrarRegistroEmpleado(int referencia) {
        controlPersi.borrarRegistroEmpleado(referencia);
    }

    public EquipoProteccion traerEpp(int referencia) {
        return controlPersi.traerEpp(referencia);
    }

    public void modificarEpp(EquipoProteccion epp, String equipo, String fechaCom, String proveedor, String tal, String obser) {
        epp.setEquipo(equipo);
        epp.setFechaCompra(fechaCom);
        epp.setProveedor(proveedor);
        epp.setTalla(tal);
        epp.setObservaciones(obser);
        
        controlPersi.modificarEpp(epp);
        
    }

    public Empleado traerEmpleado(int id_trabajador) {
        return controlPersi.traerEmpleado(id_trabajador);
    }

    public void modificarEmpleado(Empleado empleado, String nombre, String apellido, String numDocumento, String departamento, String cargo) {
        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setNumDocumento(numDocumento);
        empleado.setDepartamento(departamento);
        empleado.setCargo(cargo);
        
        controlPersi.modificarEmpleado(empleado);
    }

    //Accidentes
    public void guardarAccidente(String tipoAccidente, String fecha, String idEmpleado, int probabilidad, int impacto, String descripcion) {
        
        Accidente accidente = new Accidente();
        
        accidente.setTipoAccidente(tipoAccidente);
        accidente.setFecha(fecha);
        accidente.setProbabilidad(probabilidad);
        accidente.setImpacto(impacto);
        accidente.setDescripcion(descripcion);
        accidente.setUnEmpleado(controlPersi.traerEmpleado(Integer.parseInt(idEmpleado)));
               
        controlPersi.guardarAccidente(accidente);
    }
        
}
