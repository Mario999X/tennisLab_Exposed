package repositories.pedido

import entities.PedidoDao
import entities.usuario.ClienteDao
import mappers.fromPedidoDaoToPedido
import models.Pedido
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class PedidoRepositoryImpl(private val pedidoDao: LongEntityClass<PedidoDao>) : PedidoRepository {
    override fun findAll(): List<Pedido> = transaction {
        log.debug { "findAll()" }
        pedidoDao.all().map { it.fromPedidoDaoToPedido() }

    }

    override fun findById(id: Long): Pedido? = transaction {
        log.debug { "findById($id" }
        pedidoDao.findById(id)?.fromPedidoDaoToPedido()
    }

    override fun save(entity: Pedido): Pedido = transaction {
        val existe = pedidoDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun insert(entity: Pedido): Pedido {
        log.debug { "save($entity) - creando" }
        return pedidoDao.new(entity.id) {
            uuid = entity.uuid
            estado = entity.estado.estado
            fechaEntrada = entity.fechaEntrada
            fechaProgramada = entity.fechaProgramada
            fechaSalida = entity.fechaSalida
            cliente = ClienteDao.findById(entity.cliente.id)!!
        }.fromPedidoDaoToPedido()
    }

    private fun update(entity: Pedido, existe: PedidoDao): Pedido {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            estado = entity.estado.estado
            fechaEntrada = entity.fechaEntrada
            fechaProgramada = entity.fechaProgramada
            fechaSalida = entity.fechaSalida
            cliente = ClienteDao.findById(entity.cliente.id)!!
        }.fromPedidoDaoToPedido()
    }

    override fun delete(entity: Pedido): Boolean = transaction {
        val existe = pedidoDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}