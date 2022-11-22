package controllers

import models.Encordar
import mu.KotlinLogging
import repositories.encordar.EncordarRepository

private val log = KotlinLogging.logger { }

class EncordarController(private val encordarRepository: EncordarRepository) {
    fun getEncordados(): List<Encordar> {
        log.info("Obteniendo encordados")
        return encordarRepository.findAll()
    }

    fun createEncordado(encordado: Encordar): Encordar {
        log.info("Insertando nuevo encordado")
        encordarRepository.save(encordado)
        return encordado
    }
}