package entities

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.usuario.TrabajadorDao
import entities.usuario.TrabajadorTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * TareaTable, clase objeto que genera una tabla
 */
object TareaTable : LongIdTable("tareas") {
    val uuid = uuid("uuid")
    val adquisicion = reference("adquisicion_uuid", AdquisicionTable, onDelete = ReferenceOption.SET_NULL).nullable()
    val personalizar = reference("personalizar_uuid", PersonalizarTable, onDelete = ReferenceOption.SET_NULL).nullable()
    val encordar = reference("encordar_uuid", EncordarTable, onDelete = ReferenceOption.SET_NULL).nullable()
    val raqueta = reference("raqueta_uuid", RaquetaTable, ReferenceOption.SET_NULL).nullable()
    val precio = double("precio")
    val pedidoId = reference("pedido_id", PedidoTable)
    val trabajadorId = reference("trabajador_id", TrabajadorTable)
}

/**
 * TareaDao, clase de paso objeto a dato de la tabla
 *
 * @constructor ID
 *
 * @param id EntityId<Long>
 */
class TareaDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TareaDao>(TareaTable)

    var uuid by TareaTable.uuid
    var adquisicion by AdquisicionDao optionalReferencedOn TareaTable.adquisicion
    var personalizar by PersonalizarDao optionalReferencedOn TareaTable.personalizar
    var encordar by EncordarDao optionalReferencedOn TareaTable.encordar
    var raqueta by RaquetaDao optionalReferencedOn TareaTable.raqueta
    var precio by TareaTable.precio
    var pedidoId by PedidoDao referencedOn TareaTable.pedidoId
    var trabajadorId by TrabajadorDao referencedOn TareaTable.trabajadorId
}