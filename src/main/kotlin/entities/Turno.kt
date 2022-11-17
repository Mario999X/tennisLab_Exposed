package entities

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object TurnoTable : LongIdTable("turnos") {
    val horario = varchar("tipo_horario", 7)
}

class TurnoDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TurnoDao>(TurnoTable)

    var horario by TurnoTable.horario
}