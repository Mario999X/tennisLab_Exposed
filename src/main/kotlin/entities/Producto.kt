package entities

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * ProductoTable, clase objeto que genera una tabla
 */
object ProductoTable : LongIdTable("productos") {
    val uuid = uuid("uuid")
    val tipo = varchar("tipo", 50)
    val marca = varchar("marca", 50)
    val modelo = varchar("modelo", 50)
    val stock = integer("stock")
    val precio = double("precio")
}

/**
 * ProductoDao, clase de paso objeto a dato de la tabla
 *
 * @constructor ID
 *
 * @param id EntityId<Long>
 */
class ProductoDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<ProductoDao>(ProductoTable)

    var uuid by ProductoTable.uuid
    var tipo by ProductoTable.tipo
    var marca by ProductoTable.marca
    var modelo by ProductoTable.modelo
    var stock by ProductoTable.stock
    var precio by ProductoTable.precio
}