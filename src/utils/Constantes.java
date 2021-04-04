package utils;

/**
 * Clase que contiene las constantes, expresiones regulares y datos de acceso a
 * la base de datos
 *
 * @author Jmarser
 * @version 1.0, 07/01/2021
 */
public class Constantes {

    /* --- --- Rutas de las imagenes --- --- */
    public static final String RUTA_LOGO = "/recursos/logo.jpg";
    public static final String RUTA_ICON_DESKTOP = "/recursos/icono_desktop.png";

    /* ----- Constantes para la conexión con la base de datos ----- */
    public static final String DRIVER_MYSQL = "com.mysql.cj.jdbc.Driver";
    public static final String BASE_DATOS = "proyecto_api";
    public static final String URL_MYSQL = "jdbc:mysql://localhost:3306/"+ BASE_DATOS +"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    //"jdbc:mysql://localhost:3306/" + BASE_DATOS + "?useTimezone=true&serverTimeZone=UTC";
    public static final String USER_MYSQL = "root";
    public static final String PASS_MYSQL = "1234";

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

    //Patrón para claves de BAJO nivel
    public static final String PATRON_LOW = "^(?=\\w*[A-Z])(?=\\w*[a-z])\\S{6,}$";
    //Patrón para claves de nivel MEDIO
    public static final String PATRON_MEDIUM = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,}$";
    //Patrón para claves de nivel ALTO
    public static final String PATRON_HIGH = "^(?=.*\\d{2,})(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z]{2,})(?=.*[a-z]{2,})\\S{10,}$";

    //array para rellenar los niveles de seguridad.
    public static final String[] NIVELES = {"Seleccione nivel", "Bajo", "Medio", "Alto"};

    //Diferentes niveles de seguridad
    public static final String BAJO = "Bajo";
    public static final String MEDIO = "Medio";
    public static final String ALTO = "Alto";
}
