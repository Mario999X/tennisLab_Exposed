package models.maquina

import java.time.LocalDate
import java.util.UUID

class Encordadora(
    id: Long,
    uuid: UUID = UUID.randomUUID(),
    marca: String,
    modelo: String,
    fechaAdquisicion: LocalDate,
    numSerie: Long,
    val isManual: Boolean,
    val tensionMax: Double,
    val tensionMin: Double
) : Maquina(id, uuid, marca, modelo, fechaAdquisicion, numSerie) {

    override fun toString(): String {
        return "Encordadora(isManual=$isManual, tensionMax=$tensionMax, tensionMin=$tensionMin) ${super.toString()}"
    }
}
