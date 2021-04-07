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

    private final Connection conn;

    //consultas para la tabla ciclos
    private final String INSERT_CICLO = "INSERT INTO ciclos (id, nombre) VALUES (?,?)";
    private final String GET_ALL_CICLOS = "SELECT * FROM ciclos";
    private final String MAX_ID_CICLO = "SELECT MAX(id) FROM ciclos";

    public CicloDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean insert(Ciclo a) {
        boolean insertado = false;
        PreparedStatement ps = null;

        //obtenemos el mayor id que hay en la tabla.
        Long idCiclo = maxId();

        try {
            ps = conn.prepareStatement(INSERT_CICLO);
            ps.setLong(1, idCiclo + 1);
            ps.setString(2, a.getNombre());

            if (ps.executeUpdate() > 0) {
                insertado = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(CicloDaoImpl.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CicloDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return insertado;
    }

    @Override
    public boolean update(Ciclo a) {
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
            rs = st.executeQuery(GET_ALL_CICLOS);

            while (rs.next()) {
                Ciclo ciclo = new Ciclo();
                ciclo.setId(rs.getLong(1));
                ciclo.setNombre(rs.getString(2));

                listado.add(ciclo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CicloDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
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

    @Override
    public Long maxId() {

        Long idMax = 0L;

        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(MAX_ID_CICLO);

            while (rs.next()) {
                idMax = rs.getLong(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CicloDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CicloDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return idMax;
    }
}
