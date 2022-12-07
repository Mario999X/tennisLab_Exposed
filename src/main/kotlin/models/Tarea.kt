package models

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import models.usuario.Trabajador
import java.util.UUID

data class Tarea(
    val id: Long,
    @Expose val uuid: UUID = UUID.randomUUID(),
    @Expose val adquisicion: Adquisicion? = null,
    @Expose val personalizar: Personalizar? = null,
    @Expose val encordar: Encordar? = null,
    @Expose val raqueta: Raqueta? = null,
    @Expose var precio: Double,
    @Expose val pedido: Pedido,
    @Expose val trabajador: Trabajador
) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}