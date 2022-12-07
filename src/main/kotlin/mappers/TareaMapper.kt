package mappers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.TareaDao
import models.Tarea

/**
 * FromTareaDaoToTarea(), funcion que obtiene el dato y lo pasa a objeto
 *
 * @return Tarea
 */
fun TareaDao.fromTareaDaoToTarea(): Tarea {
    return Tarea(
        id = id.value,
        uuid = uuid,
        adquisicion = adquisicion?.fromAdquisicionDaoToAdquisicion(),
        personalizar = personalizar?.fromPersonalizarDaoToPersonalizar(),
        encordar = encordar?.fromEncordarDaoToEncordar(),
        raqueta = raqueta?.fromRaquetaDaoToRaqueta(),
        precio = precio,
        pedido = pedidoId.fromPedidoDaoToPedido(),
        trabajador = trabajadorId.fromTrabajadorDaoToTrabajador()
    )
}