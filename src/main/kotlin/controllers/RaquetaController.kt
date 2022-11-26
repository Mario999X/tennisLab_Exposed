package controllers

import models.Raqueta
import mu.KotlinLogging
import repositories.raqueta.RaquetaRepository

private val log = KotlinLogging.logger { }

class RaquetaController(private val raquetaRepository: RaquetaRepository) {
    fun getRaquetas(): List<Raqueta> {
        log.info("Obteniendo raquetas")
        return raquetaRepository.findAll()
    }

    fun createRaqueta(raqueta: Raqueta): Raqueta {
        log.info("Insertando nueva raqueta")
        raquetaRepository.save(raqueta)
        return raqueta
    }
}