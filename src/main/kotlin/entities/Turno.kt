package entities

import entities.maquina.EncordadoraDao
import entities.maquina.EncordadoraTable
import entities.maquina.PersonalizadoraDao
import entities.maquina.PersonalizadoraTable
import entities.usuario.TrabajadorDao
import entities.usuario.TrabajadorTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object TurnoTable : LongIdTable("turnos") {
    val uuid = uuid("uuid")
    val horario = varchar("tipo_horario", 7)
    val encordadoraUuid = reference("encordadora_uuid", EncordadoraTable)
    val personalizadora = reference("personalizadora_uuid", PersonalizadoraTable)
    val trabajador = reference("trabajador_uuid", TrabajadorTable)
}

class TurnoDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TurnoDao>(TurnoTable)

    var uuid by TurnoTable.uuid
    var horario by TurnoTable.horario
    var encordadoraUuid by EncordadoraDao referencedOn TurnoTable.encordadoraUuid
    var personalizadora by PersonalizadoraDao referencedOn TurnoTable.personalizadora
    var trabajador by TrabajadorDao referencedOn TurnoTable.trabajador
}