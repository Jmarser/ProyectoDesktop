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

    private String consulta = "";

    public AlumnoDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Alumno a) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareCall(consulta);

            ps.setString(1, a.getNombre());
            ps.setString(2, a.getPrimerApellido());
            ps.setString(3, a.getSegundoApellido());
            ps.setString(4, a.getEmail());
            ps.setString(5, a.getPassword());
            ps.setBoolean(6, a.isActivo());
            ps.setString(7, a.getCiclo());
            ps.setLong(8, a.getProfesor().getId());
            ps.setLong(9, a.getTutor().getId());

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
            ps = conn.prepareCall(consulta);
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
            ps = conn.prepareCall(consulta);
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
            rs = st.executeQuery(consulta);

            while (rs.next()) {
                Alumno al = new Alumno();
                al.setId(rs.getLong(1));
                al.setNombre(rs.getString(2));
                al.setPrimerApellido(rs.getString(3));
                al.setSegundoApellido(rs.getString(4));
                al.setEmail(rs.getString(5));
                al.setActivo(rs.getBoolean(6));
                
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
