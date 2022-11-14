package models

import java.util.*

data class Usuario(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val nombre: String,
    val apellido: String,
    val email: String,
    val password: String,
    val perfil: Perfil
) {
    enum class Perfil(val rol: String) {
        ADMIN("ENCARGADO"),
        ENCORDADOR("TRABAJADOR"),
        TENISTA("CLIENTE");

        companion object {
            fun from(perfil: String): Perfil {
                return when (perfil.uppercase()) {
                    "ENCARGADO" -> ADMIN
                    "TRABAJADOR" -> ENCORDADOR
                    "CLIENTE" -> TENISTA
                    else -> throw IllegalArgumentException("Rol no reconocido")
                }
            }
        }
    }
}