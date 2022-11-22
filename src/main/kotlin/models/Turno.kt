package models

import models.maquina.Encordadora
import models.maquina.Personalizadora
import models.usuario.Trabajador
import java.util.UUID

data class Turno(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val horario: TipoHorario,
    var encordadora: Encordadora? = null,
    var personalizadora: Personalizadora? = null,
    val trabajador: Trabajador
) {


    enum class TipoHorario(val horario: String) {
        TEMPRANO("MAÑANA"),
        TARDE("TARDE");

        companion object {
            fun from(tipoHorario: String): TipoHorario {
                return when (tipoHorario.uppercase()) {
                    "MAÑANA" -> TEMPRANO
                    "TARDE" -> TARDE
                    else -> throw IllegalArgumentException("Turno no reconocido")
                }
            }
        }
    }

    override fun toString(): String {
        return "Turno(uuid=$uuid, horario=$horario, encordadora=${encordadora?.uuid}, personalizadora=${personalizadora?.uuid}, trabajador=${trabajador.uuid})"
    }
}