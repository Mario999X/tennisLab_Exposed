package models

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.usuario.Cliente
import java.time.LocalDate
import java.util.UUID

data class Pedido(
    val id: Long,
    @Expose val uuid: UUID = UUID.randomUUID(),
    @Expose var estado: TipoEstado,
    val fechaEntrada: LocalDate,
    @Expose var fechaJsonEntrada: String = fechaEntrada.toString(),
    var fechaProgramada: LocalDate,
    @Expose var fechaJsonProgramada: String = fechaProgramada.toString(),
    var fechaSalida: LocalDate,
    @Expose var fechaJsonSalida: String = fechaSalida.toString(),
    @Expose val cliente: Cliente
) {
    enum class TipoEstado(val estado: String) {
        RECIBIDO("RECIBIDO"),
        PROCESANDO("PROCESANDO"),
        TERMINADO("TERMINADO");

        companion object {
            fun from(estado: String): TipoEstado {
                return when (estado.uppercase()) {
                    "RECIBIDO" -> RECIBIDO
                    "PROCESANDO" -> PROCESANDO
                    "TERMINADO" -> TERMINADO
                    else -> throw IllegalArgumentException("Estado no reconocido")
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