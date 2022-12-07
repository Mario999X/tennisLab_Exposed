package models.maquina

/**
 * @author Sebastian Mendoza y Mario Resa
 */

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
    fechaJson: String = fechaAdquisicion.toString(),
    numSerie: Long,
    @Expose var maniobrabilidad: Boolean,
    @Expose var balance: Boolean,
    @Expose var rigidez: Boolean
) : Maquina(id, uuid, marca, modelo, fechaAdquisicion, fechaJson, numSerie) {
    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}