/**
 * Clase que nos permite realizar la encriptación/desencriptación con el 
 * algoritmo AES
 */
package utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Jmarser
 */
public class EncriptadorAES {

    /**
     * Crea la clave de encriptación que usará internamente nuestra aplicación
     *
     * @param clave clave que usaremos para generar la clave de
     * encriptación/desencriptación
     * @return clave de encriptación/desencriptación
     */
    private SecretKeySpec crearClave(String clave) {

        try {
            //para asegurarnos de que admite caracteres especiales usamos UTF-8
            byte[] cadena = clave.getBytes("UTF-8");
            
            //SHA-1 es un algoritmo de encriptacion
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            /*Las siguientes líneas son lo mismo que el return que hay más abajo
            pero desarrollandolo paso a paso*/
            //cadena = md.digest(cadena);
            //cadena = Arrays.copyOf( cadena, 16);
            
            //El 16 indica la cantidad de bytes
            return new SecretKeySpec(Arrays.copyOf(md.digest(cadena), 16), "AES");

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Realiza la encriptación AES sobre el texto que deseemos usando la clave
     * indicada
     *
     * @param datos, cadena que deseamos encriptar.
     * @param claveEncriptacion, clave con la que deseamos encriptar el texto.
     * @return cadena encriptada
     */
    public String encriptar(String datos, String claveEncriptacion) {

        try {
            SecretKeySpec sks = crearClave(claveEncriptacion);

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sks);

            /*Las siguientes líneas son lo mismo que el return que hay más abajo
            pero desarrollandolo paso a paso*/
            //byte[] cadena = claveEncriptacion.getBytes("UTF-8");
            //byte[] encriptada =  cipher.doFinal(cadena);
            //String cadenaEncriptada = Base64.getEncoder().encodeToString(encriptada);
            //return cadenaEncriptada;
            
            return Base64.getEncoder().encodeToString(cipher.doFinal(claveEncriptacion.getBytes("UTF-8")));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    /**
     * Realiza la desencriptación del texto que le pasamos encriptado con AES
     * usando la misma clave que se utilizó para realizar la encriptación.
     *
     * @param datos, cadena encriptada en AES que se desea desencriptar.
     * @param clave, clave que usaremos para desencriptar, debe ser la misma que
     * se uso para encriptar.
     * @return texto desencriptado.
     */
    public String desencriptar(String datos, String clave) {

        try {
            SecretKeySpec sks = crearClave(clave);

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, sks);
            
            /*Las siguientes líneas son lo mismo que el return que hay más abajo
            pero desarrollandolo paso a paso*/
            //byte[] cadena = Base64.getDecoder().decode(datos);
            //byte[] cadenaDesencriptada = cipher.doFinal(cadena);
            //String datosDesencriptados = new String(cadenaDesencriptada);
            //return datosDesencriptados;
            
            return new String(cipher.doFinal(Base64.getDecoder().decode(datos)));
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
}
