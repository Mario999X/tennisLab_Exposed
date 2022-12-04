package models

import models.usuario.Trabajador
import java.util.UUID

data class Tarea(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val adquisicion: Adquisicion? = null,
    val personalizar: Personalizar? = null,
    val encordar: Encordar? = null,
    val raqueta: Raqueta? = null,
    val precio: Double,
    val pedido: Pedido,
    val trabajador: Trabajador
) {
    override fun toString(): String {
        return "Tarea(uuid=$uuid, AdquisicionUUID=${adquisicion?.uuid}, PersonalizarUUID=${personalizar?.uuid}, EncordarUUID=${encordar?.uuid}, raqueta=${raqueta?.uuid}, precio=$precio, pedidoUUID=${pedido.uuid}, trabajadorUUID=${trabajador.uuid})"
    }
}