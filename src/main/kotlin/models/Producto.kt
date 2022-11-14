package models

import java.util.UUID

data class Producto(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val tipo: Tipo,
    val marca: String,
    val modelo: String,
    val stock: Int,
    val precio: Double
) {
    enum class Tipo(val item: String) {
        RAQUETA("RAQUETA"),
        CORDAJE("CORDAJE"),
        COMPLEMENTO("COMPLEMENTO");

        companion object {
            fun from(tipo: String): Tipo {
                return when (tipo.uppercase()) {
                    "RAQUETA" -> RAQUETA
                    "CORDAJE" -> CORDAJE
                    "COMPLEMENTO" -> COMPLEMENTO
                    else -> throw IllegalArgumentException("Tipo no reconocido")
                }
            }
        }
    }

}