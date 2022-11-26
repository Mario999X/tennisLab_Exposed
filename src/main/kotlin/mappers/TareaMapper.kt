package mappers

import entities.TareaDao
import models.Tarea

fun TareaDao.fromTareaDaoToTarea(): Tarea {
    return Tarea(
        id = id.value,
        uuid = uuid,
        adquisicion = adquisicion?.fromAdquisicionDaoToAdquisicion(),
        personalizar = personalizar?.fromPersonalizarDaoToPersonalizar(),
        encordar = encordar?.fromEncordarDaoToEncordar(),
        raqueta = raqueta?.fromRaquetaDaoToRaqueta(),
        precio = precio,
        pedidoId = pedidoId.fromPedidoDaoToPedido()
    )
}