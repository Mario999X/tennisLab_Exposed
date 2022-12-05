package models

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import java.util.*

data class Adquisicion(
    val id: Long,
    @Expose val uuid: UUID = UUID.randomUUID(),
    @Expose val cantidad: Int,
    @Expose val producto: Producto?,
    @Expose val descripcion: String? = producto?.modelo,
    @Expose val precio: Double? = producto?.precio
){
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}