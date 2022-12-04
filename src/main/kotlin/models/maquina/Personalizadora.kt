package models.maquina

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import java.time.LocalDate
import java.util.*

class Personalizadora(
    id: Long,
    uuid: UUID = UUID.randomUUID(),
    marca: String,
    modelo: String,
    fechaAdquisicion: LocalDate,
    numSerie: Long,
    @Expose val maniobrabilidad: Boolean,
    @Expose val balance: Boolean,
    @Expose val rigidez: Boolean
) : Maquina(id, uuid, marca, modelo, fechaAdquisicion, numSerie) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}