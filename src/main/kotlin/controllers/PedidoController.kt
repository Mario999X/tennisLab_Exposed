package controllers

import models.Pedido
import mu.KotlinLogging
import repositories.pedido.PedidoRepository

private val log = KotlinLogging.logger { }

class PedidoController(private val pedidoRepository: PedidoRepository) {

    fun getPedidos(): List<Pedido>{
        log.debug { "Obteniendo pedidos" }
        return pedidoRepository.findAll()
    }

    fun getPedidoById(id: Long): Pedido? {
        log.info { "Obteniendo pedido por ID $id" }
        return pedidoRepository.findById(id)
    }

    fun updatePedido(pedido: Pedido) {
        log.info { "Actualizado pedido $pedido" }
        pedidoRepository.save(pedido)
    }

    fun deletePedido(it: Pedido): Boolean {
        log.info { "Borrando pedido $it" }
        return pedidoRepository.delete(it)
    }

    fun createPedido(pedido: Pedido): Pedido {
        log.info { "Insertando nuevo pedido $pedido" }
        pedidoRepository.save(pedido)
        return pedido
    }
}