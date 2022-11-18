package controllers

import models.Tarea
import mu.KotlinLogging
import repositories.TareaRepository

private val log = KotlinLogging.logger { }

class TareaController(private val tareaRepository: TareaRepository) {
    fun getTareas(): List<Tarea> {
        log.info("Obteniendo tareas")
        return tareaRepository.findAll()
    }

    fun createTarea(tarea: Tarea): Tarea {
        log.debug { "Insertando nueva tarea" }
        tareaRepository.save(tarea)
        return tarea
    }
}