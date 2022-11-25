package models

import java.util.UUID

data class Tarea(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val uuidAdquisicion: Adquisicion? = null,
    val uuidPersonalizacion: Personalizar? = null,
    val uuidEncordar: Encordar? = null,
    val precio: Double,
    val pedidoId: Pedido
) {
    override fun toString(): String {
        return "Tarea(id=$id, uuid=$uuid, uuidAdquisicion=$uuidAdquisicion, uuidPersonalizacion=$uuidPersonalizacion, uuidEncordar=$uuidEncordar, precio=$precio, pedidoId=$pedidoId)"
    }
}