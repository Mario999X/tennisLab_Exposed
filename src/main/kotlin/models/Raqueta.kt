package models

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.usuario.Cliente
import java.util.*

data class Raqueta(
    val id: Long,
    @Expose val uuid: UUID = UUID.randomUUID(),
    @Expose var marca: String,
    @Expose var modelo: String,
    @Expose var cliente: Cliente
) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}