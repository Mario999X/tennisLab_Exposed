package models

import java.util.UUID

data class Tarea(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val adquisicion: Adquisicion? = null,
    val personalizar: Personalizar? = null,
    val encordar: Encordar? = null,
    val raqueta: Raqueta? = null,
    val precio: Double,
    val pedidoId: Pedido
) {
    override fun toString(): String {
        return "Tarea(uuid=$uuid, uuidAdquisicion=${adquisicion?.uuid}, uuidPersonalizar=${personalizar?.uuid}, uuidEncordar=${encordar?.uuid}, raqueta=${raqueta?.uuid}, precio=$precio, pedidoId=${pedidoId.uuid})"
    }
}