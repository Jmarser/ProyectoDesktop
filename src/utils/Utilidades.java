/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Jmarser
 */
public class Utilidades {
    
    /*Método con el que validamos si el email introducido por el usuario es válido
    según una expresión regular*/
    public static boolean validarCorreo(String email){
        Pattern pattern = Pattern.compile(Constantes.PATTERN_CORREO);
        Matcher matcher = pattern.matcher(email);
        
        if(matcher.find()){
            return true;
        }else{
            return false;
        }
    }
}
