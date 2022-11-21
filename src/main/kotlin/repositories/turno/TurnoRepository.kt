package repositories.turno

import models.Turno
import repositories.CrudRepository

interface TurnoRepository : CrudRepository<Turno, Long> {
}