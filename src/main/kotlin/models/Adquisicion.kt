package models

import java.util.*

data class Adquisicion(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val cantidad: Int,
    val producto: Producto,
    val precio: Double = producto.precio
){
    override fun toString(): String {
        return "Adquisicion(uuid=$uuid, cantidad=$cantidad, producto=${producto.uuid}, precio=$precio)"
    }
}