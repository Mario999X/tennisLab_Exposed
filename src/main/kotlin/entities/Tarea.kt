package entities

import entities.usuario.TrabajadorDao
import entities.usuario.TrabajadorTable
import models.usuario.Trabajador
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object TareaTable : LongIdTable("tareas") {
    val uuid = uuid("uuid")
    val adquisicion = reference("adquisicion_uuid", AdquisicionTable).nullable()
    val personalizar = reference("personalizar_uuid", PersonalizarTable).nullable()
    val encordar = reference("encordar_uuid", EncordarTable).nullable()
    val raqueta = reference("raqueta_uuid", RaquetaTable).nullable()
    val precio = double("precio")
    val pedidoId = reference("pedido_id", PedidoTable)
    val trabajadorId = reference("trabajador_id", TrabajadorTable)
}

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