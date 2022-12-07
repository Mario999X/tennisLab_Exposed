package repositories

/**
 * @author Sebastian Mendoza y Mario Resa
 */

/**
 * Interfaz generica que usa las funciones basicas CRUD, esta sera implementada en cada uno de los repositorios.
 *
 * @param T generico
 * @param ID generico
 */
interface CrudRepository<T, ID> {
    /**
     * findAll()
     *
     * @return Devuelve una lista de un generico
     */
    fun findAll(): List<T>

    /**
     * findById()
     *
     * @param id Recibe un id
     * @return Devuelve un dato generico cuyo ID coincida con el introducido.
     */
    fun findById(id: ID): T?

    /**
     * save()
     *
     * @param entity Recibe un dato generico
     * @return Devuelve un generico
     */
    fun save(entity: T): T

    /**
     * delete()
     *
     * @param entity Recibe un dato generico
     * @return Devuelve un boolean.
     */
    fun delete(entity: T): Boolean
}