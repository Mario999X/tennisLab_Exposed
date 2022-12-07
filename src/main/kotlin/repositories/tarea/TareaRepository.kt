package repositories.tarea

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import models.Tarea
import repositories.CrudRepository

interface TareaRepository : CrudRepository<Tarea, Long> {
}