package mappers

import entities.TareaDao
import models.Tarea

fun TareaDao.fromTareaDaoToTarea(): Tarea {
    return Tarea(
        id = id.value,
        uuid = uuid,
        uuidAdquisicion = adquisicion.fromAdquisicionDaoToAdquisicion(),
        precio = precio
    )
}