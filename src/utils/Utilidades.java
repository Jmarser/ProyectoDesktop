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

    /*Método con el que validamos si el email introducido por el usuario es válido
    según una expresión regular*/
    public static boolean validarCorreo(String email) {
        Pattern pattern = Pattern.compile(Constantes.PATTERN_CORREO);
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
     * @param jtf JTextField que queremos que sólo acepte números.
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
}
