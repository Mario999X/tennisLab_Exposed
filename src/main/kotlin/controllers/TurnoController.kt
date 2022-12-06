package controllers

import exceptions.GenericException
import models.Turno
import mu.KotlinLogging
import repositories.turno.TurnoRepository

private val log = KotlinLogging.logger { }

class TurnoController(private val turnoRepository: TurnoRepository) {

    fun getTurnos(): List<Turno> {
        log.info { "Obteniendo turnos" }
        return turnoRepository.findAll()
    }

    fun getTurnoById(id: Long): Turno {
        log.info { "Obteniendo turno por ID $id" }
        return turnoRepository.findById(id) ?: throw GenericException("Turno con id $id no encontrado")
    }

    fun updateTurno(turno: Turno) {
        log.info { "Actualizado turno $turno" }
        turnoRepository.save(turno)
    }

    fun deleteTurno(it: Turno): Boolean {
        log.info { "Borrando turno $it" }
        return if (turnoRepository.delete(it))
            true
        else
            throw GenericException("Turno con id ${it.id} no encontrado")
    }

    fun createTurno(turno: Turno): Turno {
        log.info { "Insertando nuevo turno $turno" }
        turnoRepository.save(turno)
        return turno
    }
}