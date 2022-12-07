package repositories.adquisicion

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import models.Adquisicion
import repositories.CrudRepository

interface AdquisicionRepository : CrudRepository<Adquisicion, Long> {
}