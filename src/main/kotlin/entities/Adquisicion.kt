package entities

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * AdquisicionTable, clase objeto que genera una tabla
 */
object AdquisicionTable : LongIdTable("adquisiciones") {
    val uuid = uuid("uuid")
    val producto = reference("producto_uuid", ProductoTable, onDelete = ReferenceOption.SET_NULL).nullable()
    val descripcion = varchar("descripcion", 50)
    val cantidad = integer("cantidad")
    val precio = double("precio")
}

/**
 * AdquisicionDao, clase de paso objeto a dato de la tabla
 *
 * @constructor ID
 *
 * @param id EntityID<Long>
 */
class AdquisicionDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<AdquisicionDao>(AdquisicionTable)

    var uuid by AdquisicionTable.uuid
    var producto by ProductoDao optionalReferencedOn AdquisicionTable.producto
    var descripcion by AdquisicionTable.descripcion
    var cantidad by AdquisicionTable.cantidad
    var precio by AdquisicionTable.precio
}