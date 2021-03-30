/*
 * Con esta clase accedemos a los diferentes métodos para realizar un CRUD de nuestros
 * diferentes objetos.
 * Al utilizar esta clase ya no necesitamos instanciar cada una de nuestras clases
 * por separado, las tenemos todas aquí.
 */
package modelsDao;

import conexion.ConMySQL;
import java.sql.Connection;

/**
 *
 * @author JMARSER
 */
public class ManagerDaoImpl implements ManagerDao{
    
    //obtenemos la conexión a la base de datos
    private Connection conn = ConMySQL.getConexion();
    
    private ProfesorDao profesores;
    
    private TutorDao tutores;
    
    private AlumnoDao alumnos;

    @Override
    public ProfesorDao getProfesorDao() {
        if(profesores == null){
            profesores = new ProfesorDaoImpl(conn);
        }
        return profesores;
    }

    @Override
    public TutorDao getTutorDao() {
        if(tutores == null){
            tutores = new TutorDaoImpl(conn);
        }
        return tutores;
    }

    @Override
    public AlumnoDao getAlumnoDao() {
        if(alumnos == null){
            alumnos = new AlumnoDaoImpl(conn);
        }
        return alumnos;
    }
}
