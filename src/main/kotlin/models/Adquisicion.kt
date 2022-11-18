package models

import java.util.*

data class Adquisicion(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val cantidad: Int,
    val uuidProducto: Producto,
    val precio: Double = uuidProducto.precio
){
    override fun toString(): String {
        return "Adquisicion(uuid=$uuid, cantidad=$cantidad, uuidProducto=${uuidProducto.uuid}, precio=$precio)"
    }
}