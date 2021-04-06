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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import models.Login;

/**
 *
 * @author JMARSER
 */
public class LoginDaoImpl implements LoginDao {

    private Connection conn;

    private final String insertLogin = "INSERT INTO login (id, activo, email, password, rol) VALUES (?,?,?,?,?)";
    private final String MAX_ID_LOGIN = "SELECT MAX(id) FROM login";
    private final String LOGIN_BY_EMAIL = "SELECT * FROM login WHERE email=";

    public LoginDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Login a) {
        PreparedStatement ps = null;

        Long idLogin = maxId();

        try {
            ps = conn.prepareStatement(insertLogin);
            ps.setLong(1, idLogin + 1);
            ps.setBoolean(2, a.isActivo());
            ps.setString(3, a.getEmail());
            ps.setString(4, a.getPassword());
            ps.setString(5, a.getRol());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Login insertado correctamente.");

        } catch (SQLException ex) {
            Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Login a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Login a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Login> getAll() {

        return null;
    }

    @Override
    public Login getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long maxId() {
        Long idMax = 0L;

        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(MAX_ID_LOGIN);

            while (rs.next()) {
                idMax = rs.getLong(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return idMax;
    }

    @Override
    public Login getLoginByEmail(String email) {
        Login login = new Login();

        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(LOGIN_BY_EMAIL + "'" + email + "'");

            while (rs.next()) {
                login.setId(rs.getLong(1));
                login.setActivo(rs.getBoolean(2));
                login.setEmail(rs.getString(3));
                login.setPassword(rs.getString(4));
                login.setRol(rs.getString(5));

            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return login;
    }

}
