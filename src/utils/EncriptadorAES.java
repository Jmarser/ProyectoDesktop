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
 * Clase que nos permite realizar la encriptación/desencriptación con el 
 * algoritmo AES
 * 
 * @author Jmarser
 * 
 * @since 12/04/2021
 */
public class EncriptadorAES {

    //formato de codificación que admite caracteres especiales.
    private static final String UNICODE = "UTF-8";
    //SHA-256 es un algoritmo de encriptacion
    private static final String HASH_ALGORITM = "SHA-256";
    
    private static final String ESQUEMA_CIFRADO = "AES";
    
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
            byte[] cadena = clave.getBytes(UNICODE);
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITM);
            md.update(cadena, 0, cadena.length);
            
            return new SecretKeySpec(md.digest(), ESQUEMA_CIFRADO);

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

            Cipher cipher = Cipher.getInstance(ESQUEMA_CIFRADO);
            cipher.init(Cipher.ENCRYPT_MODE, sks);

            /*Las siguientes líneas son lo mismo que el return que hay más abajo
            pero desarrollandolo paso a paso*/
            //byte[] cadena = datos.getBytes("UTF-8");
            //byte[] encriptada =  cipher.doFinal(cadena);
            //String cadenaEncriptada = Base64.getEncoder().encodeToString(encriptada);
            //return cadenaEncriptada;
            
            return Base64.getEncoder().encodeToString(cipher.doFinal(datos.getBytes(UNICODE)));
             

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
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

            Cipher cipher = Cipher.getInstance(ESQUEMA_CIFRADO);
            cipher.init(Cipher.DECRYPT_MODE, sks);
            
            /*Las siguientes líneas son lo mismo que el return que hay más abajo
            pero desarrollandolo paso a paso*/
            //byte[] cadena = Base64.getDecoder().decode(datos);
            //byte[] cadenaDesencriptada = cipher.doFinal(cadena);
            //String datosDesencriptados = new String(cadenaDesencriptada);
            //return datosDesencriptados;
            
            return new String(cipher.doFinal(Base64.getDecoder().decode(datos)), UNICODE);
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException |UnsupportedEncodingException ex) {
            Logger.getLogger(EncriptadorAES.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
