package repositories.usuario

import models.usuario.Encargado
import repositories.CrudRepository

interface EncargadoRepository : CrudRepository<Encargado, Long> {
}