package entities.usuario

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * ClienteTable, clase objeto que genera una tabla
 */
object ClienteTable : LongIdTable("clientes") {
    val uuid = uuid("uuid")
    val nombre = varchar("nombre", 100)
    val apellido = varchar("apellido", 100)
    val email = varchar("email", 50)
    val password = varchar("password", 500)
    val perfil = varchar("rol", 50)
}

/**
 * ClienteDao, clase de paso objeto a dato de la tabla
 *
 * @constructor ID
 *
 * @param id EntityID<Long>
 */
class ClienteDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ClienteDao>(ClienteTable)

    var uuid by ClienteTable.uuid
    var nombre by ClienteTable.nombre
    var apellido by ClienteTable.apellido
    var email by ClienteTable.email
    var password by ClienteTable.password
    var perfil by ClienteTable.perfil
}

