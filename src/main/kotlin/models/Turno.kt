package models

data class Turno(
    val id: Long,
    val horario: TipoHorario
) {
    override fun toString(): String {
        return "Turno(id=$id, horario=$horario)"
    }

    enum class TipoHorario(val horario: String) {
        MAniANA("MAÑANA"),
        TARDE("TARDE");

        companion object {
            fun from(tipoHorario: String): TipoHorario {
                return when (tipoHorario.uppercase()) {
                    "MAÑANA" -> MAniANA
                    "TARDE" -> TARDE
                    else -> throw IllegalArgumentException("Turno no reconocido")
                }
            }
        }
    }
}