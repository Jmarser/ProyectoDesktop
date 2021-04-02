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
public class Profesor extends Persona{

    private static final long serialVersionUID = 1L;
    
    private List<Alumno> listaAlumnos;

    public Profesor() {
    }

    public Profesor(List<Alumno> listaAlumnos, Long id, String nombre, String primerApellido, String segundoApellido, String email, String password, boolean activo) {
        super(id, nombre, primerApellido, segundoApellido, email, password, activo);
        this.listaAlumnos = listaAlumnos;
    }
    
}
