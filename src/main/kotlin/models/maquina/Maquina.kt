package models.maquina

import com.google.gson.annotations.Expose
import java.time.LocalDate
import java.util.*

open class Maquina(
    @Expose val id: Long,
    @Expose val uuid: UUID,
    @Expose var marca: String,
    @Expose var modelo: String,
    var fechaAdquisicion: LocalDate,
    @Expose var fechaJson: String,
    @Expose var numSerie: Long
)