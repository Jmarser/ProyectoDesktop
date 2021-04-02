/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Jmarser
 */
public class Alumno extends Persona{

    private static final long serialVersionUID = 1L;
    
    private String ciclo;
    private Profesor profesor;
    private Tutor tutor;

    public Alumno() {
    }

    public Alumno(String ciclo, Profesor profesor, Tutor tutor, Long id, String nombre, String primerApellido, String segundoApellido, String email, String password, boolean activo) {
        super(id, nombre, primerApellido, segundoApellido, email, password, activo);
        this.ciclo = ciclo;
        this.profesor = profesor;
        this.tutor = tutor;
    }
 
    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
    
    
}
