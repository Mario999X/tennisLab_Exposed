package mappers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.TurnoDao
import models.Turno

/**
 * FromTurnoDaoToTurno(), funcion que obtiene el dato y lo pasa a objeto
 *
 * @return Turno
 */
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