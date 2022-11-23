package repositories.pedido

import entities.PedidoDao
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

    override fun delete(entity: Pedido): Boolean = transaction {
        val existe = pedidoDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }

    private fun insert(entity: Pedido): Pedido {
        log.debug { "save($entity) - creando" }
        return pedidoDao.new(entity.id) {
            uuid = entity.uuid
            estado = entity.estado.estado
            fechaEntrada = entity.fechaEntrada
            fechaProgramada = entity.fechaProgramada
            fechaSalida = entity.fechaSalida
            // Pide cambiarlo a var, pero si no no funciona, hay que revisar esta parte.
            // Pero claro, no tiene una columna en la tabla...
            //tareas = SizedCollection(entity.tareas.map { TareaDao.findById(it.id) })
            total = entity.total
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
            //tareas = SizedCollection(entity.tareas.map { TareaDao.findById(it.id) })
            total = entity.total
        }.fromPedidoDaoToPedido()
    }
}
// COMENTARIO ORIGINAL en el dao (entities)
/*
*   Relación inversa donde soy referenciado (tenistas) referrersOn, solo si quiero acceder a ellos
// Si le pongo val no dejo asignar tenistas desde aquí
// 1 - M (1 raqueta - muchos tenistas que la usan)
* */