/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelsDao;

import models.Login;

/**
 *
 * @author JMARSER
 */
public interface LoginDao extends DAO<Login>{
    
    Login getLoginByEmail(String email);
    
    Long getIdByEmail(String email);
}
