/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;

/**
 *
 * @author Jmarser
 */
public class Alumno extends Persona{

    private static final long serialVersionUID = 1L;
    
    private String ciclo;
    private Long profesorID;
    private Long tutorID;

    public Alumno() {
    }

    public Alumno(String ciclo, Long profesorID, Long tutorID, Long id, Date creado, String nombre, String primerApellido, String segundoApellido, String email, String password, boolean activo) {
        super(id, creado, nombre, primerApellido, segundoApellido, email, password, activo);
        this.ciclo = ciclo;
        this.profesorID = profesorID;
        this.tutorID = tutorID;
    }


 
    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Long getProfesorID() {
        return profesorID;
    }

    public void setProfesorID(Long profesorID) {
        this.profesorID = profesorID;
    }

    public Long getTutorID() {
        return tutorID;
    }

    public void setTutorID(Long tutorID) {
        this.tutorID = tutorID;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
