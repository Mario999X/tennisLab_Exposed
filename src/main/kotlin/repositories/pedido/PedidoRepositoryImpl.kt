package repositories.pedido

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.PedidoDao
import entities.usuario.ClienteDao
import exceptions.GenericException
import mappers.fromPedidoDaoToPedido
import models.Pedido
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

/**
 * PedidoRepositoryImpl, Clase que realiza operaciones CRUD, pedidos.
 *
 * @property pedidoDao PedidoMapper
 */
class PedidoRepositoryImpl(private val pedidoDao: LongEntityClass<PedidoDao>) : PedidoRepository {
    /**
     * FindAll()
     *
     * @return Lista de pedidos
     */
    override fun findAll(): List<Pedido> = transaction {
        log.debug { "findAll()" }
        pedidoDao.all().map { it.fromPedidoDaoToPedido() }

    }

    /**
     * FindById()
     *
     * @param id Identificador de pedido
     * @return Pedido o Null
     */
    override fun findById(id: Long): Pedido? = transaction {
        log.debug { "findById($id" }
        pedidoDao.findById(id)?.fromPedidoDaoToPedido()
    }

    /**
     * Save(), guarda o actualiza el entity
     *
     * @param entity Pedido
     * @return Pedido
     */
    override fun save(entity: Pedido): Pedido = transaction {
        val existe = pedidoDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Insert(), se introduce el dato
     *
     * @param entity Pedido
     * @return Pedido
     */
    private fun insert(entity: Pedido): Pedido {
        log.debug { "save($entity) - creando" }
        return pedidoDao.new(entity.id) {
            uuid = entity.uuid
            estado = entity.estado.estado
            fechaEntrada = entity.fechaEntrada
            fechaProgramada = entity.fechaProgramada
            fechaSalida = entity.fechaSalida
            cliente = ClienteDao.findById(entity.cliente.id)
                ?: throw GenericException("Cliente no encontrado")
        }.fromPedidoDaoToPedido()
    }

    /**
     * Update(), se actualiza el dato
     *
     * @param entity Pedido
     * @param existe PedidoDao
     * @return Pedido
     */
    private fun update(entity: Pedido, existe: PedidoDao): Pedido {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            estado = entity.estado.estado
            fechaEntrada = entity.fechaEntrada
            fechaProgramada = entity.fechaProgramada
            fechaSalida = entity.fechaSalida
            cliente = ClienteDao.findById(entity.cliente.id)
                ?: throw GenericException("Cliente no encontrado")
        }.fromPedidoDaoToPedido()
    }

    /**
     * Delete(), se elimina el dato
     *
     * @param entity Pedido
     * @return Boolean
     */
    override fun delete(entity: Pedido): Boolean = transaction {
        val existe = pedidoDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}