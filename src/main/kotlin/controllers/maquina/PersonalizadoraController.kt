package controllers.maquina

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import exceptions.GenericException
import models.maquina.Personalizadora
import mu.KotlinLogging
import repositories.personalizadora.PersonalizadoraRepository

private val log = KotlinLogging.logger { }

/**
 * PersonalizadoraController, clase que usa los metodos del respectivo repositorio.
 *
 * @property personalizadoraRepository PersonalizadoraRepository
 */
class PersonalizadoraController(private val personalizadoraRepository: PersonalizadoraRepository) {

    fun getPersonalizadoras(): List<Personalizadora> {
        log.info { "Obteniendo personalizadoras" }
        return personalizadoraRepository.findAll()
    }

    fun getPersonalizadoraById(id: Long): Personalizadora {
        log.info { "Obteniendo personalizadora por ID $id" }
        return personalizadoraRepository.findById(id)
            ?: throw GenericException("Personalizadora con id $id no encontrada")
    }

    fun updatePersonalizadora(personalizadora: Personalizadora) {
        log.info { "Actualizado personalizadora $personalizadora" }
        personalizadoraRepository.save(personalizadora)
    }

    fun deletePersonalizadora(it: Personalizadora): Boolean {
        log.info { "Borrando personalizadora $it" }
        return if (personalizadoraRepository.delete(it))
            true
        else
            throw GenericException("Personalizadora con id ${it.id} no encontrada")
    }

    fun createPersonalizadora(personalizadora: Personalizadora): Personalizadora {
        log.info { "Insertando nueva personalizadora $personalizadora" }
        personalizadoraRepository.save(personalizadora)
        return personalizadora
    }

}