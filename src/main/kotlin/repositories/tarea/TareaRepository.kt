package repositories.tarea

import models.Tarea
import repositories.CrudRepository

interface TareaRepository : CrudRepository<Tarea, Long> {
}