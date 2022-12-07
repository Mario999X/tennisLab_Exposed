package models.usuario

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import java.util.*

class Cliente(
    id: Long,
    uuid: UUID = UUID.randomUUID(),
    nombre: String,
    apellido: String,
    email: String,
    password: String,
    @Expose val perfil: Perfil = Perfil.TENISTA

) : Usuario(id, uuid, nombre, apellido, email, password) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}