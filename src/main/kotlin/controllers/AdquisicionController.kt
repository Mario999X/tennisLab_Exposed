package controllers

import models.Adquisicion
import mu.KotlinLogging
import repositories.AdquisicionRepository

private val log = KotlinLogging.logger { }

class AdquisicionController(private val adquisicionRepository: AdquisicionRepository) {
    fun getAdquisiciones(): List<Adquisicion> {
        log.info("Obteniendo adquisiciones")
        return adquisicionRepository.findAll()
    }

    fun createAdquisicion(adquisicion: Adquisicion): Adquisicion {
        log.info("Insertando nueva adquisicion")
        adquisicionRepository.save(adquisicion)
        return adquisicion
    }
}