package models

import kotlinx.datetime.LocalDate
import java.util.UUID

data class Pedido(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val estado: Estado,
    val fechaEntrada: LocalDate,
    val fechaProgramada: LocalDate,
    val fechaSalida: LocalDate,
    val tareas: List<Tarea>,
    val total: Double

) {
    enum class Estado(val estado: String) {
        RECIBIDO("RECIBIDO"),
        PROCESANDO("PROCESANDO"),
        TERMINADO("TERMINADO");

        companion object {
            fun from(estado: String): Estado {
                return when (estado.uppercase()) {
                    "RECIBIDO" -> RECIBIDO
                    "PROCESANDO" -> PROCESANDO
                    "TERMINADO" -> TERMINADO
                    else -> throw IllegalArgumentException("Estado no reconocido")
                }
            }
        }
    }
}