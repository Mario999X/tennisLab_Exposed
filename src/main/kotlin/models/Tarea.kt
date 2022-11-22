package models

import java.util.UUID

data class Tarea(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val uuidAdquisicion: Adquisicion?,
    val uuidPersonalizacion: Personalizar?,
    val uuidEncordar: Encordar?,
    val precio: Double
) {
    override fun toString(): String {
        return "Tarea(uuid=$uuid, uuidAdquisicion=${uuidAdquisicion?.uuid}, uuidPersonalizar=${uuidPersonalizacion?.uuid}, uuidEncordar=${uuidEncordar?.uuid}, precio=$precio)"
    }
}