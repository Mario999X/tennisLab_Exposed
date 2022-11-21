package controllers.usuario

import models.usuario.Encargado
import mu.KotlinLogging
import repositories.usuario.EncargadoRepository

private val log = KotlinLogging.logger { }

class EncargadoController(private val encargadoRepository: EncargadoRepository) {
    fun getEncargados(): List<Encargado> {
        log.info { "Obteniendo encargados" }
        return encargadoRepository.findAll()
    }

    fun getEncargadoById(id: Long): Encargado? {
        log.info { "Obteniendo encargado por ID: $id" }
        return encargadoRepository.findById(id)
    }

    fun updateEncargado(encargado: Encargado) {
        log.info { "Actualizando encargado $encargado" }
        encargadoRepository.save(encargado)
    }

    fun deleteEncargado(it: Encargado): Boolean {
        log.info { "Borrando encargado $it" }
        return encargadoRepository.delete(it)
    }

    fun createEncargado(encargado: Encargado): Encargado {
        log.info { "Insertando nuevo encargado $encargado" }
        encargadoRepository.save(encargado)
        return encargado
    }
}