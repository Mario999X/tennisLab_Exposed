package controllers.maquina

import models.maquina.Encordadora
import mu.KotlinLogging
import repositories.encordadora.EncordadoraRepository

private val log = KotlinLogging.logger { }

class EncordadoraController(private val encordadoraRepository: EncordadoraRepository) {

    fun getEncordadoras(): List<Encordadora> {
        log.info { "Obteniendo encordadoras" }
        return encordadoraRepository.findAll()
    }

    fun getEncordadoraById(id: Long): Encordadora? {
        log.info { "Obteniendo encordadora por ID $id" }
        return encordadoraRepository.findById(id)
    }

    fun updateEncordadora(encordadora: Encordadora) {
        log.info { "Actualizado encordadora $encordadora" }
        encordadoraRepository.save(encordadora)
    }

    fun deleteEncordadora(it: Encordadora): Boolean {
        log.info { "Borrando encordadoras $it" }
        return encordadoraRepository.delete(it)
    }

    fun createEncordadora(encordadora: Encordadora): Encordadora {
        log.info { "Insertando nueva encordadora $encordadora" }
        encordadoraRepository.save(encordadora)
        return encordadora
    }
}