package controllers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import exceptions.GenericException
import models.Pedido
import mu.KotlinLogging
import repositories.pedido.PedidoRepository

private val log = KotlinLogging.logger { }

/**
 * PedidoController, clase que usa los metodos del respectivo repositorio.
 *
 * @property pedidoRepository PedidoRepository
 */
class PedidoController(private val pedidoRepository: PedidoRepository) {

    fun getPedidos(): List<Pedido>{
        log.debug { "Obteniendo pedidos" }
        return pedidoRepository.findAll()
    }

    fun getPedidoById(id: Long): Pedido {
        log.info { "Obteniendo pedido por ID $id" }
        return pedidoRepository.findById(id) ?: throw GenericException("Pedido con id $id no encontrado")
    }

    fun updatePedido(pedido: Pedido) {
        log.info { "Actualizado pedido $pedido" }
        pedidoRepository.save(pedido)
    }

    fun deletePedido(it: Pedido): Boolean {
        log.info { "Borrando pedido $it" }
        return if (pedidoRepository.delete(it))
            true
        else
            throw GenericException("Pedido con id ${it.id} no encontrado")
    }

    fun createPedido(pedido: Pedido): Pedido {
        log.info { "Insertando nuevo pedido $pedido" }
        pedidoRepository.save(pedido)
        return pedido
    }
}