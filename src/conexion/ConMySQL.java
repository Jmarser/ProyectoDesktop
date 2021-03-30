/**
 *  ********* Clase conexión a bbdd con patrón singletón *********
 * con esta clase garantizamos que nuestro programa sólo realice una conexión a
 * la bbdd independientemente de las veces que llamemos a la clase conexión.
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Constantes;

/**
 *
 * @author JMARSER
 */
public class ConMySQL {
    
    //variable estática que almacenará la conexión.
    private static Connection conn = null; 
    
    /*Constructor privado con lo que garantizamos que sólo tendremos una única 
    instancia de la conexión*/
    private ConMySQL(){
        try {
            Class.forName(Constantes.DRIVER_MYSQL);
            conn = DriverManager.getConnection(Constantes.URL_MYSQL, Constantes.USER_MYSQL, Constantes.PASS_MYSQL);
            if (conn != null) {
                System.out.println("Conexión establecida con la base de datos.");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ConMySQL.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No se ha podido establecer conexión con la base de datos.");
        }
    }
    
    /*Método con el que realizamos la conexión, primero comprueba la conexión y
    en el caso de ser nula llama al constructor para establecerla y luego la
    devuelve. En caso de no ser nula, directamente nos la devuelve.*/
    public static Connection getConexion(){
        if(conn == null){
            new ConMySQL();
        }
        return conn;
    }
    
    /*Método con el que cerramos la conexión a la base de datos.*/
    public static void desconectar(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConMySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
