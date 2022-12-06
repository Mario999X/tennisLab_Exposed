package models

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import java.util.*

data class Adquisicion(
    val id: Long,
    @Expose val uuid: UUID = UUID.randomUUID(),
    @Expose var cantidad: Int,
    @Expose var producto: Producto?,
    @Expose var descripcion: String? = producto?.modelo,
    @Expose var precio: Double? = producto?.precio
){
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}