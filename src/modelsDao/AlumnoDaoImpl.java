/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import models.Alumno;

/**
 *
 * @author JMARSER
 */
public class AlumnoDaoImpl implements AlumnoDao {

    private Connection conn;

    private final String ADD_ALUMNO = "INSERT INTO alumnos (id, email, nombre, primer_apellido, segundo_apellido ,profesor_id, tutor_id) VALUES (?,?,?,?,?,?,?)";
    private final String GET_ALL_ALUMNOS = "SELECT * FROM alumnos";
    private final String MAX_ID_ALUMNOS = "SELECT MAX(id) FROM alumnos";

    public AlumnoDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Alumno a) {
        PreparedStatement ps = null;

        //obtenemos el id mayor de la tabla alumnos
        Long idAlumno = maxId();

        try {
            ps = conn.prepareCall(ADD_ALUMNO);

            ps.setLong(1, idAlumno + 1);
            ps.setString(2, a.getEmail());
            ps.setString(3, a.getNombre());
            ps.setString(4, a.getPrimerApellido());
            ps.setString(5, a.getSegundoApellido());
            //ps.setString(6, a.getCiclo());
            ps.setLong(6, a.getProfesorID());
            ps.setLong(7, a.getTutorID());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Alumno insertado correctamente.");
        } catch (Exception e) {
            Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Alumno a) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareCall("");
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
                ps.close();
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
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listado;
    }

    @Override
    public Alumno getOne(int id) {

        return null;
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
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(AlumnoDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return idMax;
    }

}
