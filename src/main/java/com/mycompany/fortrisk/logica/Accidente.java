package com.mycompany.fortrisk.logica;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Accidente implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer referencia;
    private String tipoAccidente;
    private String fecha;
    private int probabilidad;
    private int impacto; 
    private String matrizRiesgo;
    private String descripcion;
    @ManyToOne
    @JoinColumn(name = "id_trabajador", nullable = true) // Clave foránea en la tabla "Empleado"
    private Empleado empleado;

    public Accidente() {
    }

    // Constructor parametrizado
    public Accidente(Integer referencia, String tipoAccidente, String fecha, int probabilidad, int impacto, String descripcion, Empleado Empleado) {
        this.referencia = referencia;
        this.tipoAccidente = tipoAccidente;
        this.fecha = fecha;
        this.probabilidad = probabilidad;
        this.impacto = impacto;
        this.descripcion = descripcion;
        this.empleado = Empleado;
        this.matrizRiesgo = calcularRiesgo(); // Calculo tras asignar valores
        
    }

    public Integer getReferencia() {
        return referencia;
    }

    public String getTipoAccidente() {
        return tipoAccidente;
    }

    public void setTipoAccidente(String tipoAccidente) {
        this.tipoAccidente = tipoAccidente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(int probabilidad) {
        if (probabilidad < 1 || probabilidad > 5) {
            throw new IllegalArgumentException("Probabilidad debe estar entre 1 y 5");
        }
        this.probabilidad = probabilidad;
        this.matrizRiesgo = calcularRiesgo(); // Actualiza la matriz
    }

    public int getImpacto() {
        return impacto;
    }

    public void setImpacto(int impacto) {
        if (impacto < 1 || impacto > 5) {
            throw new IllegalArgumentException("Impacto debe estar entre 1 y 5");
        }
        this.impacto = impacto;
        this.matrizRiesgo = calcularRiesgo(); // Actualiza la matriz
    }
    
    public String getMatrizRiesgo() {
        return matrizRiesgo;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String Descripccion) {
        this.descripcion = Descripccion;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setUnEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    
    public final String calcularRiesgo() {
        int riesgo = probabilidad * impacto;
        
         if (riesgo >= 15) {
            return "Riesgo Inaceptable";
        } else if (riesgo >= 8) {
            return "Riesgo Tolerable";
        } else {
            return "Riesgo Acceptable";
        }
    }

    
}
