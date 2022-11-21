package models.maquina

import java.time.LocalDate
import java.util.*

open class Maquina(
    val id: Long,
    val uuid: UUID,
    val marca: String,
    val modelo: String,
    val fechaAdquisicion: LocalDate,
    val numSerie: Long
) {
    override fun toString(): String {
        return "Maquina(id=$id, uuid=$uuid, marca='$marca', modelo='$modelo', fechaAdquisicion=$fechaAdquisicion, numSerie=$numSerie)"
    }
}