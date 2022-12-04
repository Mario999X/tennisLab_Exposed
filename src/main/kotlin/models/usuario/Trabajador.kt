package models.usuario

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import java.util.*

class Trabajador(
    id: Long,
    uuid: UUID = UUID.randomUUID(),
    nombre: String,
    apellido: String,
    email: String,
    password: String,
    @Expose val perfil: Perfil = Perfil.ENCORDADOR
) : Usuario(id, uuid, nombre, apellido, email, password) {

    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}