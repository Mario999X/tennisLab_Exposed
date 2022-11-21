package repositories.usuario

import models.usuario.Trabajador
import repositories.CrudRepository

interface TrabajadorRepository : CrudRepository<Trabajador, Long> {
}