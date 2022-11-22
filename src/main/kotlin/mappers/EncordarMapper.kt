package mappers

import entities.EncordarDao
import models.Encordar

fun EncordarDao.fromEncordarDaoToEncordar(): Encordar {
    return Encordar(
        id = id.value,
        uuid = uuid,
        tensionCuerdasHorizontales = tenCuHori,
        cordajeHorizontal = cordajeHorizontal,
        tensionCuerdasVerticales = tenCuVerti,
        cordajeVertical = cordajeVertical,
        nudos = nudos,
        precio = precio
    )
}