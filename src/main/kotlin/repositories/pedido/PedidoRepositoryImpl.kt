package repositories.pedido

import entities.PedidoDao
import models.Pedido
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass

private val log = KotlinLogging.logger { }

class PedidoRepositoryImpl(private val pedidoDao: LongEntityClass<PedidoDao>) : PedidoRepository {
    override fun findAll(): List<Pedido> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Pedido? {
        TODO("Not yet implemented")
    }

    override fun save(entity: Pedido): Pedido {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Pedido): Boolean {
        TODO("Not yet implemented")
    }
}