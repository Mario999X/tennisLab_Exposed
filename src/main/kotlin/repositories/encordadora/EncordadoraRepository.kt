package repositories.encordadora

import models.maquina.Encordadora
import org.apache.poi.ss.formula.functions.T
import repositories.CrudRepository
import java.util.UUID

interface EncordadoraRepository : CrudRepository<Encordadora, Long> {
    fun findByUuid(uuid: UUID): Encordadora?
}