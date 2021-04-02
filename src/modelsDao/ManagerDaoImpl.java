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
    
    private CicloDao ciclos;
    
    private EmpresaDao empresas;
    
    private LoginDao login;

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

    @Override
    public LoginDao getLoginDao() {
        if(login == null){
            login = new LoginDaoImpl(conn);
        }
        return login;
    }

    @Override
    public CicloDao getCicloDao() {
        if(ciclos == null){
            ciclos = new CicloDaoImpl(conn);
        }
        return ciclos;
    }

    @Override
    public EmpresaDao getEmpresaDao() {
        if(empresas == null){
            empresas = new EmpresaDaoImpl(conn);
        }
        return empresas;
    }
}
