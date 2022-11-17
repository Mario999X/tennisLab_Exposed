package models

import java.util.*

class Adquisicion(
    id: Long,
    uuid: UUID = UUID.randomUUID(),
    val cantidad: Int,
    val producto: Producto,
    precio: Double = producto.precio
) : Tarea(id, uuid, precio) {
    override fun toString(): String {
        return "Adquisicion(uuid=$uuid,cantidad=$cantidad, producto=${producto.tipo}, precio=$precio)"
    }
}