package entities

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object TareaTable : LongIdTable("tareas") {
    val uuid = uuid("uuid")
    val adquisicion = reference("adquisicion_uuid", AdquisicionTable)
    val precio = double("precio")
}

class TareaDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TareaDao>(TareaTable)

    var uuid by TareaTable.uuid
    var adquisicion by AdquisicionDao referencedOn TareaTable.adquisicion
    var precio by TareaTable.precio
}