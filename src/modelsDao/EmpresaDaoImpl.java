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
import models.Empresa;

/**
 *
 * @author Jmarser
 */
public class EmpresaDaoImpl implements EmpresaDao{
    
    private Connection conn;
    
    //consultas para el modelo
    private final String INSERT_EMPRESA = "INSERT INTO empresas (id, nombre) VALUES (?,?)";
    private final String GET_ALL_EMPRESAS = "SELECT * FROM empresas";
    private final String MAX_ID_EMPRESAS = "SELECT MAX(id) FROM empresas";

    public EmpresaDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Empresa a) {
        PreparedStatement ps = null;
        
        //obtenemos el mayor id que hay en la tabla.
        Long idEmpresa = maxId();
        
        try{
            ps = conn.prepareStatement(INSERT_EMPRESA);
            ps.setLong(1, idEmpresa + 1);
            ps.setString(2, a.getNombre());
            
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Empresa insertada correctamente.");
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Empresa a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Empresa a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Empresa> getAll() {
        List<Empresa> listado = new ArrayList<>();
        Statement st = null;
        ResultSet rs = null;
        
        try{
            st = conn.createStatement();
            rs = st.executeQuery(GET_ALL_EMPRESAS);
            
            while(rs.next()){
                Empresa emp = new Empresa();
                emp.setId(rs.getLong(1));
                emp.setNombre(rs.getString(2));
                
                listado.add(emp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listado;
    }

    @Override
    public Empresa getOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long maxId() {
        Long idMax = 0L;
        
        Statement st = null;
        ResultSet rs = null;
        
        try{
            st = conn.createStatement();
            rs = st.executeQuery(MAX_ID_EMPRESAS);
            
            while(rs.next()){
                idMax = rs.getLong(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EmpresaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                rs.close();
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(EmpresaDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return idMax;
    }
}
