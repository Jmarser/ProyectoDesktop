/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author JMARSER
 */
public class Empresa extends ClaseBaseExtra{
   
    private static final long serialVersionUID = 1L;

    public Empresa() {
    }

    public Empresa(Long id, String nombre) {
        super(id, nombre);
    }

    @Override
    public String toString() {
        return super.toString();
    } 
}
