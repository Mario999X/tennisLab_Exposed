package controllers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import exceptions.GenericException
import models.Encordar
import mu.KotlinLogging
import repositories.encordar.EncordarRepository

private val log = KotlinLogging.logger { }

/**
 * EncordarController, clase que usa los metodos del respectivo repositorio.
 *
 * @property encordarRepository EncordarRepository
 */
class EncordarController(private val encordarRepository: EncordarRepository) {
    fun getEncordados(): List<Encordar> {
        log.info("Obteniendo encordados")
        return encordarRepository.findAll()
    }

    fun getEncordadoById(id: Long): Encordar {
        log.info("Obteniendo encordado por id: $id")
        return encordarRepository.findById(id) ?: throw GenericException("Encordado con id $id no encontrado")
    }

    fun updateEncordado(encordado: Encordar) {
        log.info("Actualizado encordado $encordado")
        encordarRepository.save(encordado)
    }

    fun deleteEncordado(it: Encordar): Boolean {
        log.info("Borrando encordado $it")
        return if (encordarRepository.delete(it))
            true
        else
            throw GenericException("Encordado con id ${it.id} no encontrado")
    }

    fun createEncordado(encordado: Encordar): Encordar {
        log.info("Insertando nuevo encordado")
        encordarRepository.save(encordado)
        return encordado
    }
}