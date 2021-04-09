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
import javax.swing.JOptionPane;
import models.Profesor;

/**
 *
 * @author JMARSER
 */
public class ProfesorDaoImpl implements ProfesorDao {

    private final Connection conn;

    //Consultas para la tabla profesores.
    private static final String ADD_PROFESOR = "INSERT INTO profesores (id, nombre, primer_apellido, segundo_apellido, email) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ALL_PROFESORES = "SELECT * FROM profesores";
    private static final String MAX_ID_PROFESORES = "SELECT MAX(id) FROM profesores";
    private static final String UPDATE_PROFESOR = "UPDATE profesores SET email=?, nombre=?, primer_apellido=?, segundo_apellido=? WHERE id=?";

    public ProfesorDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean insert(Profesor a) {
        boolean insertado = false;
        PreparedStatement ps = null;

        //Obtenemos el mayor id que hay en la tabla
        Long idProfesor = maxId();

        try {
            ps = conn.prepareStatement(ADD_PROFESOR);
            ps.setLong(1, idProfesor + 1);
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getPrimerApellido());
            ps.setString(4, a.getSegundoApellido());
            ps.setString(5, a.getEmail());

            if (ps.executeUpdate() > 0) {
                insertado = true;
            }

            JOptionPane.showMessageDialog(null, "Profesor insertado correctamente.");

        } catch (SQLException ex) {
            Logger.getLogger(ProfesorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProfesorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return insertado;
    }

    @Override
    public boolean update(Profesor a) {
        boolean editado = false;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(UPDATE_PROFESOR);
            ps.setString(1, a.getEmail());
            ps.setString(2, a.getNombre());
            ps.setString(3, a.getPrimerApellido());
            ps.setString(4, a.getSegundoApellido());
            ps.setLong(5, a.getId());

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
    public void delete(Profesor a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Profesor> getAll() {
        List<Profesor> listado = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(GET_ALL_PROFESORES);

            while (rs.next()) {
                Profesor profesor = new Profesor();
                profesor.setId(rs.getLong(1));
                profesor.setNombre(rs.getString(4));
                profesor.setPrimerApellido(rs.getString(5));
                profesor.setSegundoApellido(rs.getString(6));
                profesor.setEmail(rs.getString(3));

                listado.add(profesor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfesorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProfesorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return listado;
    }

    @Override
    public Profesor getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long maxId() {
        Long idMax = 0L;

        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(MAX_ID_PROFESORES);

            while (rs.next()) {
                idMax = rs.getLong(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProfesorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProfesorDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return idMax;
    }

}
