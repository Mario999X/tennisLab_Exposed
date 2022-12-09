package controllers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import exceptions.GenericException
import models.Tarea
import mu.KotlinLogging
import repositories.tarea.TareaRepository

private val log = KotlinLogging.logger { }

/**
 * TareaController, clase que usa los metodos del respectivo repositorio
 *
 * @property tareaRepository TareaRepository
 */
class TareaController(private val tareaRepository: TareaRepository) {
    fun getTareas(): List<Tarea> {
        log.info("Obteniendo tareas")
        return tareaRepository.findAll()
    }

    fun getTareaById(id: Long): Tarea {
        log.info("Obteniendo tarea por ID: $id")
        return tareaRepository.findById(id) ?: throw GenericException("Tarea con id $id no encontrada")
    }

    fun updateTarea(tarea: Tarea) {
        log.info { "Actualizado tarea $tarea" }
        tareaRepository.save(tarea)
    }

    fun deleteTarea(it: Tarea): Boolean {
        log.info { "Borrando tarea $it" }
        return if (tareaRepository.delete(it))
            true
        else
            throw GenericException("Tarea con id ${it.id} no encontrado")
    }

    fun createTarea(tarea: Tarea): Tarea {
        log.debug { "Insertando nueva tarea" }
        tareaRepository.save(tarea)
        return tarea
    }
}