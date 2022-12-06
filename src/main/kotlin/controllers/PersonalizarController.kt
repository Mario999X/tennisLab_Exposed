package controllers

import exceptions.GenericException
import models.Personalizar
import mu.KotlinLogging
import repositories.personalizar.PersonalizarRepository

private val log = KotlinLogging.logger { }

class PersonalizarController(private val personalizarRepository: PersonalizarRepository) {
    fun getPersonalizaciones(): List<Personalizar> {
        log.info("Obteniendo personalizaciones")
        return personalizarRepository.findAll()
    }

    fun getPersonalizadoById(id: Long): Personalizar? {
        log.info("Obteniendo personalizado por id: $id")
        return personalizarRepository.findById(id) ?: throw GenericException("Personalizacion con id $id no encontrada")
    }

    fun updatePersonalizado(personalizado: Personalizar) {
        log.info("Actualizado personalizado $personalizado")
        personalizarRepository.save(personalizado)
    }

    fun deletePersonalizado(it: Personalizar): Boolean {
        log.info("Borrando personalizado $it")
        return personalizarRepository.delete(it)
    }

    fun createPersonalizacion(personalizar: Personalizar): Personalizar {
        log.info("Insertando nueva personalizacion")
        personalizarRepository.save(personalizar)
        return personalizar
    }
}