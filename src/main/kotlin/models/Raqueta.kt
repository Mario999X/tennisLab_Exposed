package models

import models.usuario.Cliente
import java.util.*

data class Raqueta(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val marca: String,
    val modelo: String,
    val cliente: Cliente
) {
    override fun toString(): String {
        return "Raqueta(uuid=$uuid, marca='$marca', modelo='$modelo', cliente=${cliente.uuid})"
    }
}