package mappers

import entities.TurnoDao
import models.Turno

fun TurnoDao.fromTurnoDaoToTurno(): Turno {
    return Turno(
        id = id.value,
        uuid = uuid,
        horario = Turno.TipoHorario.from(horario),
        encordadora = encordadora?.fromEncordadoraDaoToEncordadora(),
        personalizadora = personalizadora?.fromPersonalizadoraDaoToPersonalizadora(),
        trabajador = trabajador.fromTrabajadorDaoToTrabajador()
    )
}