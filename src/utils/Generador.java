/**
 * Clase que nos permite la generación de claves aleatorias.
 * nos permite la generación de claves por niveles de seguridad y de longitud
 * variable.
 */
package utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author Jmarser
 */
public class Generador {

    public Generador() {
    }

    public String getClave(String nivel, int longitud) {
        String password = "";
        String combinaciones;
        switch (nivel.toLowerCase()) {
            case "bajo":
                combinaciones = Constantes.MAYUSCULAS + Constantes.MINUSCULAS;
                password = generator(combinaciones, Constantes.PATRON_LOW, Constantes.LONG_LOW, longitud);
                break;
            case "medio":
                combinaciones = Constantes.MAYUSCULAS + Constantes.MINUSCULAS + Constantes.NUMEROS;
                password = generator(combinaciones, Constantes.PATRON_MEDIUM, Constantes.LONG_MEDIUM, longitud);
                break;
            case "alto":
                combinaciones = Constantes.MAYUSCULAS + Constantes.MINUSCULAS + Constantes.NUMEROS + Constantes.ESPECIALES;
                password = generator(combinaciones, Constantes.PATRON_HIGH, Constantes.LONG_HIGH, longitud);
                break;
            case "pincard":
                password += pinCardLevel(longitud);
                break;
            default:
                password += "";
        }
        return password;

    }

    private String generator(String combinaciones, String patron, int long_min, int longitud) {
        int indice = 0;

        /*en el caso de que la longitud establecida sea inferior a la longitud 
        mínima del nivel seleccionado modificamos la longitud.*/
        if (longitud < long_min) {
            longitud = long_min;
        }

        /*Nos aseguramos que la longitud no supere el tamaño del string de combinaciones*/
        while (longitud > combinaciones.length()) {
            combinaciones += combinaciones;
        }

        String aux = getCombinacion(combinaciones, longitud);

        /*Buscamos la subcadena que cumpla el patrón del nivel de seguridad*/
        while (!(aux.substring(indice, (longitud + indice))).matches(patron)) {
            indice++;
            //si no encontramos un fragmento que cumpla el patrón volvemos a mezclar
            if ((longitud + indice) == combinaciones.length()) {
                indice = 0;
                aux = getCombinacion(combinaciones, longitud);
            }
        }

        return aux.substring(indice, (longitud + indice));
    }

    /*Este método es parecido al "generador" pero en este caso no necesitamos
    que cumpla un patrón de verificación ya que sólo devolverá números.*/
    private String pinCardLevel(int longitud) {
        String aux;

        if (longitud < Constantes.LONG_PIN_CARD) {
            longitud = Constantes.LONG_PIN_CARD;
        }

        String combinaciones = Constantes.NUMEROS;
        
        /*En el caso que que se requiera un número mayor de elementos en la combinacion
        nos aseguramos de que no se produzca un error*/
        while(longitud > combinaciones.length()){
            combinaciones += combinaciones;
        }
        
        aux = getCombinacion(combinaciones, longitud);

        return aux.substring(0, longitud);
    }

    /*Método que convierte el String en un array de caracteres para poder ser
    mezclados y nos devuelve un nuevo string mezclado*/
    private String getCombinacion(String letras, int longitud) {
        char[] caracteres = letras.toCharArray();
        mezclar(caracteres, longitud);

        return String.valueOf(caracteres);
    }

    /*Realizamos la mezcla de los caracteres que componen la cadena para generar
    el password. Esta mezcla se realiza intercambiando dos posiciones de la cadena
    un número aleatorio de veces*/
    private void mezclar(char[] caracteres, int longitud) {
        Random rdn = new SecureRandom();

        int numCambios = caracteres.length + rdn.nextInt(longitud * caracteres.length);
        for (int i = 0; i < numCambios; i++) {
            int pos1 = rdn.nextInt(caracteres.length);
            int pos2 = rdn.nextInt(caracteres.length);
            char aux = caracteres[pos1];
            caracteres[pos1] = caracteres[pos2];
            caracteres[pos2] = aux;
        }
    }

}
