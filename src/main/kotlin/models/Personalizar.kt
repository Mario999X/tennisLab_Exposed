package models

import java.util.*

data class Personalizar(
    val id: Long,
    val uuid: UUID,
    val peso: Double,
    val balance: Double,
    val rigidez: Int,
    val precio: Double = 60.0
)