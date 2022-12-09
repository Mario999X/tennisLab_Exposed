package controllers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import exceptions.GenericException
import models.Adquisicion
import mu.KotlinLogging
import repositories.adquisicion.AdquisicionRepository

private val log = KotlinLogging.logger { }

/**
 * AdquisicionController, clase que usa los metodos del respetivo repositorio.
 *
 * @property adquisicionRepository AdquisicionRepository
 */
class AdquisicionController(private val adquisicionRepository: AdquisicionRepository) {
    fun getAdquisiciones(): List<Adquisicion> {
        log.info("Obteniendo adquisiciones")
        return adquisicionRepository.findAll()
    }

    fun getAdquisicionById(id: Long): Adquisicion {
        log.info("Obteniendo adquisicion por id: $id")
        return adquisicionRepository.findById(id) ?: throw GenericException("Adquisicion con id $id no encontrada")
    }

    fun updateAdquisicion(adquisicion: Adquisicion) {
        log.info { "Actualizado adquisicion $adquisicion" }
        adquisicionRepository.save(adquisicion)
    }

    fun deleteAdquisicion(it: Adquisicion): Boolean {
        log.info { "Borrando adquisicion $it" }
        return if (adquisicionRepository.delete(it))
            true
        else
            throw GenericException("Adquisicion con id ${it.id} no encontrada")
    }

    fun createAdquisicion(adquisicion: Adquisicion): Adquisicion {
        log.info("Insertando nueva adquisicion")
        adquisicionRepository.save(adquisicion)
        return adquisicion
    }
}