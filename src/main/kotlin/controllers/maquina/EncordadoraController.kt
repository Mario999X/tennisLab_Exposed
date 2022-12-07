package controllers.maquina

import exceptions.GenericException
import models.maquina.Encordadora
import mu.KotlinLogging
import repositories.encordadora.EncordadoraRepository

private val log = KotlinLogging.logger { }

class EncordadoraController(private val encordadoraRepository: EncordadoraRepository) {

    fun getEncordadoras(): List<Encordadora> {
        log.info { "Obteniendo encordadoras" }
        return encordadoraRepository.findAll()
    }

    fun getEncordadoraById(id: Long): Encordadora {
        log.info { "Obteniendo encordadora por ID $id" }
        return encordadoraRepository.findById(id) ?: throw GenericException("Encordadora con id $id no encontrada")
    }

    fun updateEncordadora(encordadora: Encordadora) {
        log.info { "Actualizado encordadora $encordadora" }
        encordadoraRepository.save(encordadora)
    }

    fun deleteEncordadora(it: Encordadora): Boolean {
        log.info { "Borrando encordadoras $it" }
        return if (encordadoraRepository.delete(it))
            true
        else
            throw GenericException("Encordadora con id ${it.id} no encontrada")
    }

    fun createEncordadora(encordadora: Encordadora): Encordadora {
        log.info { "Insertando nueva encordadora $encordadora" }
        encordadoraRepository.save(encordadora)
        return encordadora
    }
}