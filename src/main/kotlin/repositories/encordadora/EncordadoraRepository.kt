package repositories.encordadora

import models.maquina.Encordadora
import repositories.CrudRepository

interface EncordadoraRepository : CrudRepository<Encordadora, Long> {
}