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
import models.Ciclo;

/**
 *
 * @author Jmarser
 */
public class CicloDaoImpl implements CicloDao {

    private Connection conn;

    public CicloDaoImpl(Connection conn) {
        this.conn = conn;
    }

    String consulta = "";

    @Override
    public void insert(Ciclo a) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareCall(consulta);
            ps.setString(1, a.getNombre());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Ciclo insertado correctamente.");

        } catch (SQLException ex) {
            Logger.getLogger(CicloDaoImpl.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(CicloDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Ciclo a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Ciclo a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Ciclo> getAll() {
        List<Ciclo> listado = new ArrayList<>();

        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(consulta);

            while (rs.next()) {
                Ciclo ciclo = new Ciclo();
                ciclo.setId(rs.getLong(1));
                ciclo.setNombre(rs.getString(2));

                listado.add(ciclo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CicloDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(CicloDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return listado;
    }

    @Override
    public Ciclo getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
