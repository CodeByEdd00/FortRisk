package com.mycompany.fortrisk.logica;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EquipoProteccion implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int referencia;
    private String equipo;
    private String proveedor;
    private String fechaCompra;
    private String talla;
    private String observaciones;
    @ManyToOne
    @JoinColumn(name = "id_trabajador", nullable = true) // Clave foránea en la tabla "Empleado"
    private Empleado empleado;

    public EquipoProteccion() {
    }

    public EquipoProteccion(int referencia, String equipo, String proveedor, String fechaCompra, String talla, String observaciones, Empleado empleado) {
    this.referencia = referencia;
    this.equipo = equipo;
    this.proveedor = proveedor;
    this.fechaCompra = fechaCompra;
    this.talla = talla;
    this.observaciones = observaciones;
    this.empleado = empleado;  // Asignando el empleado al objeto
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setUnEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

}
