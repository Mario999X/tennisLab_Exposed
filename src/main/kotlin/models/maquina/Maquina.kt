package models.maquina

import com.google.gson.annotations.Expose
import java.time.LocalDate
import java.util.*

open class Maquina(
    @Expose val id: Long,
    @Expose val uuid: UUID,
    @Expose val marca: String,
    @Expose val modelo: String,
    @Expose val fechaAdquisicion: LocalDate,
    @Expose val numSerie: Long
)