/**
 * Clase en la que se crearán métodos que serán usados de manera recurrente
 * en nuestra aplicación con lo que reduciremos el código escrito por medio de
 * la reutilización de código.
 */
package utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;

/**
 *
 * @author Jmarser
 */
public class Utilidades {

    /**
     * Método con el que validamos si el email introducido por el usuario es 
     * válido según una expresión regular.
     * 
     * @param email, string con el email que queremos validar.
     * @param patron, patrón con el que queremos validar la cadena.
     * 
     * @return boolean, true en el caso de que cumpla el patrón, false en caso 
     * contrario.
     */
    public static boolean validarCadena(String email, String patron) {
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(email);

        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método con el que obligamos al JTextField que le pasamos por parámetro
     * que sólo acepte letras, por lo que si en el JTextField es posible que se
     * inserten números es mejor no usar éste método.
     * 
     * @param jtf JTextField que queremos que sólo acepte letras.
     */
    public static void soloLetras(JTextField jtf) {
        jtf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                char c = evt.getKeyChar();
                if (Character.isDigit(c)) {
                    evt.consume();
                }
            }
        });
    }
    
    /**
     * Método con el que obligamos al JTextField que le pasamos por parámetro
     * que sólo acepte números, por lo que si en el JTextField es posible que se
     * inserten letras es mejor no usar éste método.
     * 
     * @param jtf JTextField que queremos que sólo acepte letras.
     */
    public static void soloNumeros(JTextField jtf){
        jtf.addKeyListener(new KeyAdapter(){
            @Override
            public void keyTyped(KeyEvent evt){
                char c = evt.getKeyChar();
                if(!Character.isDigit(c)){
                    evt.consume();
                }
            }
        });
    }
    
    public static java.sql.Date fechaActual(){
        java.util.Date fecha = new java.util.Date();
        java.sql.Date fecha2 = new java.sql.Date(fecha.getTime());
        
        return fecha2;
    }
}
