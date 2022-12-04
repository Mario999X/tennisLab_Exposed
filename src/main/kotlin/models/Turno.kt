package models

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.maquina.Encordadora
import models.maquina.Personalizadora
import models.usuario.Trabajador
import java.util.UUID

data class Turno(
    val id: Long,
    @Expose val uuid: UUID = UUID.randomUUID(),
    @Expose val horario: TipoHorario,
    @Expose var encordadora: Encordadora? = null,
    @Expose var personalizadora: Personalizadora? = null,
    @Expose val trabajador: Trabajador
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
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}