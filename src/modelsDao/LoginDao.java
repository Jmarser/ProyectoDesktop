/**
 * Interface que implementa la interface genérida DAO, pero ya indicandole que
 * objeto específico va a recibir.
 * Es en esta interface donde deberemos declarar los métodos específicos que necesitemos
 * para las consultas de este modelo.
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
