package repositories.encordar

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import models.Encordar
import repositories.CrudRepository

interface EncordarRepository : CrudRepository<Encordar, Long> {
}