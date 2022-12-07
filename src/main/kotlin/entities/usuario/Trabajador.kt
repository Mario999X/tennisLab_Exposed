package entities.usuario

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * TrabajadorTable, clase objeto que genera una tabla
 */
object TrabajadorTable : LongIdTable("trabajadores") {
    val uuid = uuid("uuid")
    val nombre = varchar("nombre", 100)
    val apellido = varchar("apellido", 100)
    val email = varchar("email", 50)
    val password = varchar("password", 500)
    val perfil = varchar("rol", 50)
}

/**
 * TrabajadorDao, clase de paso objeto a dato de la tabla
 *
 * @constructor ID
 *
 * @param id EntityID<Long>
 */
class TrabajadorDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TrabajadorDao>(TrabajadorTable)

    var uuid by TrabajadorTable.uuid
    var nombre by TrabajadorTable.nombre
    var apellido by TrabajadorTable.apellido
    var email by TrabajadorTable.email
    var password by TrabajadorTable.password
    var perfil by TrabajadorTable.perfil
}