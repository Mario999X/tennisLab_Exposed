package models.usuario

import com.google.gson.annotations.Expose
import java.util.*

open class Usuario(
    open val id: Long,
    @Expose val uuid: UUID = UUID.randomUUID(),
    @Expose var nombre: String,
    @Expose val apellido: String,
    @Expose val email: String,
    val password: String
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