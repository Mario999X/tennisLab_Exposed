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
import org.jetbrains.exposed.sql.javatime.date

/**
 * PedidoTable, clase objeto que genera una tabla
 */
object PedidoTable : LongIdTable("pedidos") {
    val uuid = uuid("uuid")
    val estado = varchar("estado", 50)
    val fechaEntrada = date("fecha_entrada")
    val fechaProgramada = date("fecha_programada")
    val fechaSalida = date("fecha_salida")
    val cliente = reference("cliente_id", ClienteTable)
}

/**
 * PedidoDao, clase de paso objeto a dato de la tabla
 *
 * @constructor ID
 *
 * @param id EntityID<Long>
 */
class PedidoDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PedidoDao>(PedidoTable)

    var uuid by PedidoTable.uuid
    var estado by PedidoTable.estado
    var fechaEntrada by PedidoTable.fechaEntrada
    var fechaProgramada by PedidoTable.fechaProgramada
    var fechaSalida by PedidoTable.fechaSalida
    var cliente by ClienteDao referencedOn PedidoTable.cliente
}