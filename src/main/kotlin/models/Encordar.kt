package models

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import java.util.UUID

data class Encordar(
    val id: Long,
    @Expose val uuid: UUID = UUID.randomUUID(),
    @Expose val tensionCuerdasHorizontales: Double,
    @Expose val cordajeHorizontal: String,
    @Expose val tensionCuerdasVerticales: Double,
    @Expose val cordajeVertical: String,
    @Expose var nudos: Int,
    @Expose val precio: Double = 15.0
){
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}