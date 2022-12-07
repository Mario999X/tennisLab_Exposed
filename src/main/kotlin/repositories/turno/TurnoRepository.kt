package repositories.turno

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import models.Turno
import repositories.CrudRepository

interface TurnoRepository : CrudRepository<Turno, Long> {
}