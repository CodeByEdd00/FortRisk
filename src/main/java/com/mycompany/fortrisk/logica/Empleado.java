package com.mycompany.fortrisk.logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Empleado implements Serializable {
   
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    Integer id_trabajador;
    String nombre;
    String apellido;
    String numDocumento;
    String cargo;
    String departamento;
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<Accidente> accidentes = new ArrayList<>();
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<EquipoProteccion> epp = new ArrayList<>();
    
    
    
    

    public Empleado() {
    }

    public Empleado(String nombre, String apellido, String numDocumento, String departamento, String cargo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numDocumento = numDocumento;
        this.departamento = departamento;
        this.cargo = cargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Integer getId_trabajador() {
        return id_trabajador;
    }

    public List<Accidente> getAccidente() {
        return accidentes;
    }

    public void setAccidente(List<Accidente> accidentes) {
        this.accidentes = accidentes;
    }

    public List<Accidente> getAccidentes() {
        return accidentes;
    }

    public void setAccidentes(List<Accidente> accidentes) {
        this.accidentes = accidentes;
    }

    public List<EquipoProteccion> getEpp() {
        return epp;
    }

    public void setEpp(List<EquipoProteccion> epp) {
        this.epp = epp;
    }
    
    
}
