package entities

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object AdquisicionTable : LongIdTable("adquisiciones") {
    val uuid = uuid("uuid")
    val producto = reference("producto_id", ProductoTable)
    val cantidad = integer("cantidad")
    val precio = double("precio")
}

class AdquisicionDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<AdquisicionDao>(AdquisicionTable)

    var uuid by AdquisicionTable.uuid
    var producto by ProductoDao referencedOn AdquisicionTable.producto
    var cantidad by AdquisicionTable.cantidad
    var precio by AdquisicionTable.precio
}