package entities

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.usuario.ClienteDao
import entities.usuario.ClienteTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * RaquetaTable, clase objeto que genera una tabla
 */
object RaquetaTable : LongIdTable("raquetas") {
    val uuid = uuid("uuid")
    val marca = varchar("marca", 50)
    val modelo = varchar("modelo", 50)
    val cliente = reference("cliente_uuid", ClienteTable, onDelete = ReferenceOption.RESTRICT)
}

/**
 * RaquetaDao, clase de paso objeto a dato de la tabla
 *
 * @constructor ID
 *
 * @param id EntityID<Long>
 */
class RaquetaDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<RaquetaDao>(RaquetaTable)

    var uuid by RaquetaTable.uuid
    var marca by RaquetaTable.marca
    var modelo by RaquetaTable.modelo
    var cliente by ClienteDao referencedOn RaquetaTable.cliente
}