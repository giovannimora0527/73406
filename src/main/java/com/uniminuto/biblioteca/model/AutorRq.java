package com.uniminuto.biblioteca.model;

import java.time.LocalDate;

public class AutorRq {
    private String nombre;
    private String nacionalidad;
    private LocalDate fechaNacimiento;

    // Constructor vacío
    public AutorRq() {}

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
