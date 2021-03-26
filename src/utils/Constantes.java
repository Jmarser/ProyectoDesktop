package utils;
/**
 * Clase que contiene las constantes y expresiones regulares utillizadas para 
 * generar las claves de seguridad
 *
 * @author Jmarser
 * @version 1.0, 07/01/2021
 */

public class Constantes {

    /* --- --- Rutas de las imagenes --- --- */
    public static final String RUTA_LOGO = "/recursos/logo.jpg";
    public static final String RUTA_ICON_DESKTOP = "/recursos/icono_desktop.png";
    
    /* ----- Constantes para la conexión con la base de datos ----- */
    
    /* ----- Patrones de verificación ----- */
    
    /* ----- ----- */

    /* ----- constantes para la generación de las claves ----- */
    
    //conjunto de caracteres para la generación de las claves.
    public static final String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
    public static final String NUMEROS = "0123456789";
    public static final String ESPECIALES = "!@#$%&*()_+-=[]|,./?><";
    
    //longitudes de las claves.
    public static final int LONG_LOW = 6;
    public static final int LONG_MEDIUM = 8;
    public static final int LONG_HIGH = 10;
    public static final int LONG_PIN_CARD = 4;

    //Patrón para claves de bajo nivel
    public static final String PATRON_LOW = "^(?=\\w*[A-Z])(?=\\w*[a-z])\\S{6,}$";
    //Patrón para claves de nivel medio
    public static final String PATRON_MEDIUM = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,}$";
    //Patrón para claves de nivel alto
    public static final String PATRON_HIGH = "^(?=.*\\d{2,})(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z]{2,})(?=.*[a-z]{2,})\\S{10,}$";

}
