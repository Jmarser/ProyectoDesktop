/**
 * Interface en la que declaramos los métodos fundamentales para realizar un
 * CRUD.
 * Esta interface es genérica por lo que otras ointerfaces pueden extender de ella 
 * indicando sólo el objeto específico que va a recibir.
 */
package modelsDao;

import java.util.List;

/**
 *
 * @author JMARSER
 */
public interface DAO<T> {

    boolean insert(T a);

    boolean update(T a);

    void delete(T a);

    List<T> getAll();

    T getOne(int id);

    Long maxId();
}
