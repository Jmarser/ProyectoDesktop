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
import models.Tutor;

/**
 *
 * @author JMARSER
 */
public class TutorDaoImpl implements TutorDao {

    private final Connection conn;

    //consultas para la tabla tutores
    private static final String ADD_TUTOR = "INSERT INTO tutores (id, nombre, primer_apellido, segundo_apellido, email, empresa) VALUES (?,?,?,?,?,?)";
    private static final String GET_ALL_TUTORES = "SELECT * FROM tutores";
    private static final String MAX_ID_TUTORES = "SELECT MAX(id) FROM tutores";
    private static final String UPDATE_TUTOR = "UPDATE tutores SET email=?, nombre=?, primer_apellido=?, segundo_apellido=?, empresa=? WHERE id=?";

    public TutorDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean insert(Tutor a) {
        boolean insertado = false;
        PreparedStatement ps = null;

        //obtenemos el mayor id de la tabla de tutores
        Long idTutor = maxId();

        try {
            ps = conn.prepareStatement(ADD_TUTOR);
            ps.setLong(1, idTutor + 1);
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getPrimerApellido());
            ps.setString(4, a.getSegundoApellido());
            ps.setString(5, a.getEmail());
            ps.setString(6, a.getEmpresa());

            if (ps.executeUpdate() > 0) {
                insertado = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(TutorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TutorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return insertado;
    }

    @Override
    public boolean update(Tutor a) {
        boolean editado = false;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(UPDATE_TUTOR);
            ps.setString(1, a.getEmail());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getPrimerApellido());
            ps.setString(4, a.getSegundoApellido());
            ps.setString(5, a.getEmpresa());
            ps.setLong(6, a.getId());

            if (ps.executeUpdate() > 0) {
                editado = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(TutorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TutorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return editado;
    }

    @Override
    public void delete(Tutor a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tutor> getAll() {
        List<Tutor> listado = new ArrayList<>();

        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(GET_ALL_TUTORES);

            while (rs.next()) {
                Tutor tutor = new Tutor();
                tutor.setId(rs.getLong(1));
                tutor.setEmail(rs.getString(3));
                tutor.setNombre(rs.getString(4));
                tutor.setPrimerApellido(rs.getString(5));
                tutor.setSegundoApellido(rs.getString(6));
                tutor.setEmpresa(rs.getString(7));

                listado.add(tutor);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TutorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TutorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return listado;
    }

    @Override
    public Tutor getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long maxId() {
        Long idMax = 0L;

        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(MAX_ID_TUTORES);

            while (rs.next()) {
                idMax = rs.getLong(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(TutorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TutorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return idMax;
    }

}
