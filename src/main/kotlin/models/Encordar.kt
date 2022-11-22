package models

import java.util.UUID

data class Encordar(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val tensionCuerdasHorizontales: Double,
    val cordajeHorizontal: String,
    val tensionCuerdasVerticales: Double,
    val cordajeVertical: String,
    val nudos: Int,
    val precio: Double = 15.0
)