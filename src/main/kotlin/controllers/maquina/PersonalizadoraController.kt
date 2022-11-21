package controllers.maquina

import models.maquina.Personalizadora
import mu.KotlinLogging
import repositories.personalizadora.PersonalizadoraRepository

private val log = KotlinLogging.logger { }

class PersonalizadoraController(private val personalizadoraRepository: PersonalizadoraRepository) {

    fun getPersonalizadoras(): List<Personalizadora> {
        log.info { "Obteniendo personalizadoras" }
        return personalizadoraRepository.findAll()
    }

    fun getPersonalizadoraById(id: Long): Personalizadora? {
        log.info { "Obteniendo personalizadora por ID $id" }
        return personalizadoraRepository.findById(id)
    }

    fun updatePersonalizadora(personalizadora: Personalizadora) {
        log.info { "Actualizado personalizadora $personalizadora" }
        personalizadoraRepository.save(personalizadora)
    }

    fun deletePersonalizadora(it: Personalizadora): Boolean {
        log.info { "Borrando personalizadora $it" }
        return personalizadoraRepository.delete(it)
    }

    fun createPersonalizadora(personalizadora: Personalizadora): Personalizadora {
        log.info { "Insertando nueva personalizadora $personalizadora" }
        personalizadoraRepository.save(personalizadora)
        return personalizadora
    }

}