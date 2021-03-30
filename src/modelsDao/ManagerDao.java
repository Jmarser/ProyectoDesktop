/*
 * Interface en la que declaramos los diferentes objetos que tenemos guardados
 * en nuestra base de datos y de los que queremos hacer CRUD.
 */
package modelsDao;

/**
 *
 * @author JMARSER
 */
public interface ManagerDao {
    
    ProfesorDao getProfesorDao();
    
    TutorDao getTutorDao();
    
    AlumnoDao getAlumnoDao();
}
