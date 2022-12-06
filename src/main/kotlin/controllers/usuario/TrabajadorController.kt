package controllers.usuario

import exceptions.GenericException
import models.usuario.Trabajador
import mu.KotlinLogging
import repositories.usuario.TrabajadorRepository

private val log = KotlinLogging.logger { }

class TrabajadorController(private val trabajadorRepository: TrabajadorRepository) {
    fun getTrabajadores(): List<Trabajador> {
        log.info { "Obteniendo trabajadores" }
        return trabajadorRepository.findAll()
    }

    fun getTrabajadorById(id: Long): Trabajador {
        log.info { "Obteniendo trabajador por ID $id" }
        return trabajadorRepository.findById(id) ?: throw GenericException("Trabajador con id $id no encontrado")
    }

    fun updateTrabajador(trabajador: Trabajador) {
        log.info { "Actualizando trabajador $trabajador" }
        trabajadorRepository.save(trabajador)
    }

    fun deleteTrabajador(it: Trabajador): Boolean {
        log.info { "Borrando trabajador $it" }
        return if (trabajadorRepository.delete(it))
            true
        else
            throw GenericException("Trabajador con id ${it.id} no encontrado")
    }

    fun createTrabajador(trabajador: Trabajador): Trabajador {
        log.info { "Insertando nuevo trabajador $trabajador" }
        trabajadorRepository.save(trabajador)
        return trabajador
    }
}