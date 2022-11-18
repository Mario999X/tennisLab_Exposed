package repositories

import models.Tarea

interface TareaRepository : CrudRepository<Tarea, Long> {
}