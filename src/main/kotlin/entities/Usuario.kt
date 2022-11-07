package entities

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object UsuarioTable : LongIdTable("usuarios") {
    val uuid = uuid("uuid")
    val nombre = varchar("nombre", 100)
    val apellido = varchar("apellido", 100)
    val email = varchar("email", 50)
    val password = varchar("password", 500)
    val perfil = varchar("rol", 50)
}

class UsuarioDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<UsuarioDao>(UsuarioTable)

    var uuid by UsuarioTable.uuid
    var nombre by UsuarioTable.nombre
    var apellido by UsuarioTable.apellido
    var email by UsuarioTable.email
    var password by UsuarioTable.password
    var perfil by UsuarioTable.perfil
}

