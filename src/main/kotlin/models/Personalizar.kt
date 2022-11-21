package models

import java.util.*

data class Personalizar(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val peso: Double,
    val balance: Double,
    val rigidez: Int,
    val precio: Double = 60.0
) {
    override fun toString(): String {
        return "Personalizar(uuid=$uuid, peso=$peso, balance=$balance, rigidez=$rigidez, precio=$precio)"
    }
}