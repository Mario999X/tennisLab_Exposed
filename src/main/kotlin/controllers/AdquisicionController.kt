package controllers

import models.Adquisicion
import mu.KotlinLogging
import repositories.adquisicion.AdquisicionRepository

private val log = KotlinLogging.logger { }

class AdquisicionController(private val adquisicionRepository: AdquisicionRepository) {
    fun getAdquisiciones(): List<Adquisicion> {
        log.info("Obteniendo adquisiciones")
        return adquisicionRepository.findAll()
    }

    fun getAdquisicionById(id: Long): Adquisicion? {
        log.info("Obteniendo adquisicion por id: $id")
        return adquisicionRepository.findById(id)
    }

    fun updateAdquisicion(adquisicion: Adquisicion) {
        log.info { "Actualizado adquisicion $adquisicion" }
        adquisicionRepository.save(adquisicion)
    }

    fun deleteAdquisicion(it: Adquisicion): Boolean {
        log.info { "Borrando adquisicion $it" }
        return adquisicionRepository.delete(it)
    }

    fun createAdquisicion(adquisicion: Adquisicion): Adquisicion {
        log.info("Insertando nueva adquisicion")
        adquisicionRepository.save(adquisicion)
        return adquisicion
    }
}