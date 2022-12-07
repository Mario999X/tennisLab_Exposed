package controllers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import exceptions.GenericException
import models.Raqueta
import mu.KotlinLogging
import repositories.raqueta.RaquetaRepository

private val log = KotlinLogging.logger { }

/**
 * RaquetaController, clase que usa los metodos del respectivo repositorio.
 *
 * @property raquetaRepository RaquetaRepository
 */
class RaquetaController(private val raquetaRepository: RaquetaRepository) {
    fun getRaquetas(): List<Raqueta> {
        log.info("Obteniendo raquetas")
        return raquetaRepository.findAll()
    }

    fun getRaquetaById(id: Long): Raqueta {
        log.info { "Obteniendo raqueta por ID $id" }
        return raquetaRepository.findById(id) ?: throw GenericException("Raqueta con id $id no encontrada")
    }

    fun updateRaqueta(raqueta: Raqueta) {
        log.info { "Actualizada raqueta $raqueta"}
        raquetaRepository.save(raqueta)
    }

    fun deleteRaqueta(it: Raqueta) : Boolean {
        log.info { "Borrando raqueta $it"}
        return if (raquetaRepository.delete(it))
            true
        else
            throw GenericException("Raqueta con id ${it.id} no encontrada")
    }

    fun createRaqueta(raqueta: Raqueta): Raqueta {
        log.info("Insertando nueva raqueta")
        raquetaRepository.save(raqueta)
        return raqueta
    }
}