package entities.usuario

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object EncargadoTable : LongIdTable("encargados") {
    val uuid = uuid("uuid")
    val nombre = varchar("nombre", 100)
    val apellido = varchar("apellido", 100)
    val email = varchar("email", 50)
    val password = varchar("password", 500)
    val perfil = varchar("rol", 50)
}

class EncargadoDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<EncargadoDao>(EncargadoTable)

    var uuid by EncargadoTable.uuid
    var nombre by EncargadoTable.nombre
    var apellido by EncargadoTable.apellido
    var email by EncargadoTable.email
    var password by EncargadoTable.password
    var perfil by EncargadoTable.perfil
}