package models

import java.util.UUID

data class Tarea(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val uuidAdquisicion: Adquisicion,
    val precio: Double = uuidAdquisicion.precio
){
    override fun toString(): String {
        return "Tarea(uuid=$uuid, uuidAdquisicion=${uuidAdquisicion.uuid}, precio=$precio)"
    }
}