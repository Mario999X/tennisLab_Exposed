package models.maquina

import models.maquina.Maquina
import java.time.LocalDate
import java.util.*

class Personalizadora(
    id: Long,
    uuid: UUID = UUID.randomUUID(),
    marca: String,
    modelo: String,
    fechaAdquisicion: LocalDate,
    numSerie: Long,
    val maniobrabilidad: Boolean,
    val balance: Boolean,
    val rigidez: Boolean
) : Maquina(id, uuid, marca, modelo, fechaAdquisicion, numSerie)