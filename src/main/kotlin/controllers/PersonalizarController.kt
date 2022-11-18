package controllers

import models.Personalizar
import mu.KotlinLogging
import repositories.personalizar.PersonalizarRepository

private val log = KotlinLogging.logger { }

class PersonalizarController(private val personalizarRepository: PersonalizarRepository) {
    fun getPersonalizaciones(): List<Personalizar> {
        log.info("Obteniendo personalizaciones")
        return personalizarRepository.findAll()
    }

    fun createPersonalizacion(personalizar: Personalizar): Personalizar {
        log.info("Insertando nueva personalizacion")
        personalizarRepository.save(personalizar)
        return personalizar
    }
}