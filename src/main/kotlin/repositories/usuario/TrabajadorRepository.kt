package repositories.usuario

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import models.usuario.Trabajador
import repositories.CrudRepository

interface TrabajadorRepository : CrudRepository<Trabajador, Long> {
}