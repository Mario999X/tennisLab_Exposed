package mappers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.PedidoDao
import models.Pedido

/**
 * FromPedidoDaoToPedido(), funcion que obtiene el dato y lo pasa a objeto
 *
 * @return Pedido
 */
fun PedidoDao.fromPedidoDaoToPedido(): Pedido {
    return Pedido(
        id = id.value,
        uuid = uuid,
        estado = Pedido.TipoEstado.from(estado),
        fechaEntrada = fechaEntrada,
        fechaJsonEntrada = fechaEntrada.toString(),
        fechaProgramada = fechaProgramada,
        fechaJsonProgramada = fechaProgramada.toString(),
        fechaSalida = fechaSalida,
        fechaJsonSalida = fechaSalida.toString(),
        cliente = cliente.fromClienteDaoToCliente()
    )
}