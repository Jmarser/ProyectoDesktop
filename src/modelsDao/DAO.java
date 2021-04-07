/**
 * Interface en la que declaramos los métodos fundamentales para realizar un
 * CRUD.
 * Esta interface es genérica por lo que la podemos implementar en todas las
 * clases que compongan nuestro programa.
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
