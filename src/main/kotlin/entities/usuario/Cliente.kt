package entities.usuario

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object ClienteTable : LongIdTable("clientes") {
    val uuid = uuid("uuid")
    val nombre = varchar("nombre", 100)
    val apellido = varchar("apellido", 100)
    val email = varchar("email", 50)
    val password = varchar("password", 500)
    val perfil = varchar("rol", 50)
}

class ClienteDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ClienteDao>(ClienteTable)

    var uuid by ClienteTable.uuid
    var nombre by ClienteTable.nombre
    var apellido by ClienteTable.apellido
    var email by ClienteTable.email
    var password by ClienteTable.password
    var perfil by ClienteTable.perfil
}

