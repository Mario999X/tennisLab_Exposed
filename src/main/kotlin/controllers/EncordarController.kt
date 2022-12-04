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

    fun getEncordadoById(id: Long): Encordar? {
        log.info("Obteniendo encordado por id: $id")
        return encordarRepository.findById(id)
    }

    fun updateEncordado(encordado: Encordar) {
        log.info("Actualizado encordado $encordado")
        encordarRepository.save(encordado)
    }

    fun deleteEncordado(it: Encordar): Boolean {
        log.info("Borrando encordado $it")
        return encordarRepository.delete(it)
    }

    fun createEncordado(encordado: Encordar): Encordar {
        log.info("Insertando nuevo encordado")
        encordarRepository.save(encordado)
        return encordado
    }
}