/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.List;

/**
 *
 * @author Jmarser
 */
public class Tutor extends Persona{

    private static final long serialVersionUID = 1L;
    
    private String empresa;
    private List<Alumno> listaAlumnos;

    public Tutor() {
    }

    public Tutor(String empresa, List<Alumno> listaAlumnos, Long id, String nombre, String primerApellido, String segundoApellido, String email, String password, boolean activo) {
        super(id, nombre, primerApellido, segundoApellido, email, password, activo);
        this.empresa = empresa;
        this.listaAlumnos = listaAlumnos;
    }
    
    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public List<Alumno> getListaAlumnos() {
        return listaAlumnos;
    }

    public void setListaAlumnos(List<Alumno> listaAlumnos) {
        this.listaAlumnos = listaAlumnos;
    }
    
    
}
