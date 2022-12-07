package repositories.personalizar

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import models.Personalizar
import repositories.CrudRepository

interface PersonalizarRepository : CrudRepository<Personalizar, Long> {
}