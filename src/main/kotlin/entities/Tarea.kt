package entities

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object TareaTable : LongIdTable("tareas") {
    val uuid = uuid("uuid")
    val adquisicion = reference("adquisicion_uuid", AdquisicionTable)
    val personalizar = reference("personalizar_uuid", PersonalizarTable)
    val encordar = reference("encordar_uuid", EncordarTable)
    val precio = double("precio")
}

class TareaDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TareaDao>(TareaTable)

    var uuid by TareaTable.uuid
    var adquisicion by AdquisicionDao referencedOn TareaTable.adquisicion
    var personalizar by PersonalizarDao referencedOn TareaTable.personalizar
    var encordar by EncordarDao referencedOn TareaTable.encordar
    var precio by TareaTable.precio
}