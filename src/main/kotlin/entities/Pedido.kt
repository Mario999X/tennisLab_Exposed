package entities

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.date

object PedidoTable : LongIdTable("pedidos") {
    val uuid = uuid("uuid")
    val estado = varchar("estado", 50)
    val fechaEntrada = date("fecha_entrada")
    val fechaProgramada = date("fecha_programada")
    val fechaSalida = date("fecha_salida")
    val total = double("total")
}

class PedidoDao(id: EntityID<Long>) : LongEntity(id){
    companion object : LongEntityClass<PedidoDao>(PedidoTable)

    var uuid by PedidoTable.uuid
    var estado by PedidoTable.estado
    var fechaEntrada by PedidoTable.fechaEntrada
    var fechaProgramada by PedidoTable.fechaProgramada
    var fechaSalida by PedidoTable.fechaSalida
    var total by PedidoTable.total
}