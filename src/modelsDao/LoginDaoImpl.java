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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Login;
import utils.EncriptadorAES;

/**
 *
 * @author JMARSER
 */
public class LoginDaoImpl implements LoginDao {

    private final Connection conn;
    private EncriptadorAES encriptador;

    //consultas para la tabla de login
    private static final String INSERT_LOGIN = "INSERT INTO login (id, activo, email, password, rol) VALUES (?,?,?,?,?)";
    private static final String MAX_ID_LOGIN = "SELECT MAX(id) FROM login";
    private static final String LOGIN_BY_EMAIL = "SELECT * FROM login WHERE email=";
    private static final String ID_BY_EMAIL = "SELECT id FROM login WHERE email=";
    private static final String UPDATE_LOGIN = "UPDATE login SET email=?, password=?,activo=?,rol=? WHERE id=?";
    private static final String DELETE_LOGIN = "DELETE FROM login WHERE email=?";
    
    private static final String CLAVE_ENCRIPTACION = "gestiondelaspracticas";

    public LoginDaoImpl(Connection conn) {
        this.conn = conn;
        encriptador = new EncriptadorAES();
    }

    @Override
    public boolean insert(Login a) {
        boolean insertado = false;
        PreparedStatement ps = null;

            Long idLogin = maxId();

            try {
                ps = conn.prepareStatement(INSERT_LOGIN);
                ps.setLong(1, idLogin + 1);
                ps.setBoolean(2, a.isActivo());
                ps.setString(3, a.getEmail());
                ps.setString(4, encriptador.encriptar(a.getPassword(), CLAVE_ENCRIPTACION));
                ps.setString(5, a.getRol());

                if (ps.executeUpdate() > 0) {
                    insertado = true;
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
        return insertado;
    }

    @Override
    public boolean update(Login a) {
        boolean editado = false;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(UPDATE_LOGIN);
            ps.setString(1, a.getEmail());
            ps.setString(2, encriptador.encriptar(a.getPassword(), CLAVE_ENCRIPTACION));
            ps.setBoolean(3, a.isActivo());
            ps.setString(4, a.getRol());
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
    public void delete(Login a) {
        PreparedStatement ps = null;
        
        try{
            ps = conn.prepareStatement(DELETE_LOGIN);
            ps.setString(1, a.getEmail());
            
            ps.executeUpdate();
            
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
    }

    @Override
    public List<Login> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
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
                login.setPassword(encriptador.desencriptar(rs.getString(4), CLAVE_ENCRIPTACION));
                login.setRol(rs.getString(5));

            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return login;
    }

    @Override
    public Long getIdByEmail(String email) {
        Long id = 0L;

        Statement st = null;
        ResultSet rs = null;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(ID_BY_EMAIL + "'" + email + "'");

            while (rs.next()) {
                id = rs.getLong(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(LoginDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
    }
}
