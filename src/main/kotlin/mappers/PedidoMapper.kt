package mappers

import entities.PedidoDao
import models.Pedido

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