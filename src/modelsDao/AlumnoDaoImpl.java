/**
 * Esta clase implementa la interface específica de su clase modelo, con lo que
 * implementaremos todos los métodos tanto generales como específicos a usar 
 * con este modelo.
 * Además recive como parámetro una instancia de la conexión a la base de datos
 */
package modelsDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Alumno;

/**
 *
 * @author JMARSER
 */
public class AlumnoDaoImpl implements AlumnoDao {

    //atributo que contendrá la conexión a la base de datos
    private final Connection conn;

    //consultas para la tabla alumnos
    private static final String ADD_ALUMNO = "INSERT INTO alumnos (id, email, nombre, primer_apellido, segundo_apellido ,profesor_id, tutor_id) VALUES (?,?,?,?,?,?,?)";
    private static final String GET_ALL_ALUMNOS = "SELECT * FROM alumnos";
    private static final String MAX_ID_ALUMNOS = "SELECT MAX(id) FROM alumnos";
    private static final String UPDATE_ALUMNO = "UPDATE alumnos SET email=?, nombre=?, primer_apellido=?, segundo_apellido=?, profesor_id=?, tutor_id=? WHERE id=?";

    public AlumnoDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean insert(Alumno a) {
        boolean insertado = false;
        PreparedStatement ps = null;

        //obtenemos el id mayor de la tabla alumnos
        Long idAlumno = maxId();

        try {
            ps = conn.prepareStatement(ADD_ALUMNO);

            ps.setLong(1, idAlumno + 1);
            ps.setString(2, a.getEmail());
            ps.setString(3, a.getNombre());
            ps.setString(4, a.getPrimerApellido());
            ps.setString(5, a.getSegundoApellido());
            //ps.setString(6, a.getCiclo());
            ps.setLong(6, a.getProfesorID());
            ps.setLong(7, a.getTutorID());

            if (ps.executeUpdate() > 0) {
                insertado = true;
            }

        } catch (Exception e) {
            Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return insertado;
    }

    @Override
    public boolean update(Alumno a) {
        boolean editado = false;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(UPDATE_ALUMNO);
            ps.setString(1, a.getEmail());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getPrimerApellido());
            ps.setString(4, a.getSegundoApellido());
            ps.setLong(5, a.getProfesorID());
            ps.setLong(6, a.getTutorID());
            ps.setLong(7, a.getId());

            if (ps.executeUpdate() > 0) {
                editado = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return editado;
    }

    @Override
    public void delete(Alumno a) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareCall("");
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public List<Alumno> getAll() {

        List<Alumno> listado = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(GET_ALL_ALUMNOS);

            while (rs.next()) {
                Alumno al = new Alumno();
                al.setId(rs.getLong(1));
                al.setEmail(rs.getString(3));
                al.setNombre(rs.getString(4));
                al.setPrimerApellido(rs.getString(5));
                al.setSegundoApellido(rs.getString(6));
                al.setProfesorID(rs.getLong(7));
                al.setTutorID(rs.getLong(8));

                listado.add(al);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listado;
    }

    @Override
    public Alumno getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long maxId() {
        Long idMax = 0L;

        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(MAX_ID_ALUMNOS);

            while (rs.next()) {
                idMax = rs.getLong(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return idMax;
    }

}
