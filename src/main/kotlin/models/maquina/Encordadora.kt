package models.maquina

import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import java.time.LocalDate
import java.util.UUID

class Encordadora(
    id: Long,
    uuid: UUID = UUID.randomUUID(),
    marca: String,
    modelo: String,
    fechaAdquisicion: LocalDate,
    fechaJson: String = fechaAdquisicion.toString(),
    numSerie: Long,
    @Expose var isManual: Boolean,
    @Expose var tensionMax: Double,
    @Expose var tensionMin: Double,
) : Maquina(id, uuid, marca, modelo, fechaAdquisicion, fechaJson, numSerie) {

    override fun toString(): String {
        return GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation()
            .create().toJson(this)
    }
}
