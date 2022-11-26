package mappers

import entities.PedidoDao
import models.Pedido

fun PedidoDao.fromPedidoDaoToPedido(): Pedido {
    return Pedido(
        id = id.value,
        uuid = uuid,
        estado = Pedido.TipoEstado.from(estado),
        fechaEntrada = fechaEntrada,
        fechaProgramada = fechaProgramada,
        fechaSalida = fechaSalida,
        total = total
    )
}