package models

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import java.util.*

data class Personalizar(
    val id: Long,
    @Expose val uuid: UUID = UUID.randomUUID(),
    @Expose var peso: Double,
    @Expose val balance: Double,
    @Expose val rigidez: Int,
    @Expose val precio: Double = 60.0
) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}