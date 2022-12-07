package repositories.usuario

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.usuario.ClienteDao
import mappers.fromClienteDaoToCliente
import models.usuario.Cliente
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

/**
 * ClienteRepositoryImpl Clase que realiza operaciones CRUD, clientes.
 *
 * @property clienteDao ClienteMapper.
 */
class ClienteRepositoryImpl(private val clienteDao: LongEntityClass<ClienteDao>) : ClienteRepository {
    /**
     * FindAll()
     *
     * @return Lista de clientes
     */
    override fun findAll(): List<Cliente> = transaction {
        log.debug { "findAll()" }
        clienteDao.all().map { it.fromClienteDaoToCliente() }
    }

    /**
     * FindById()
     *
     * @param id Identificador de Cliente
     * @return Cliente o Null
     */
    override fun findById(id: Long): Cliente? = transaction {
        log.debug { "findById($id)" }
        clienteDao.findById(id)?.fromClienteDaoToCliente()
    }

    /**
     * Save(), guarda o actualiza el entity
     *
     * @param entity Cliente
     * @return Cliente
     */
    override fun save(entity: Cliente): Cliente = transaction {
        val existe = clienteDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Insert(), Se introduce un dato
     *
     * @param entity Cliente
     * @return Cliente
     */
    private fun insert(entity: Cliente): Cliente {
        log.debug { "save($entity) - creando" }
        return clienteDao.new(entity.id) {
            uuid = entity.uuid
            nombre = entity.nombre
            apellido = entity.apellido
            email = entity.email
            password = entity.password
            perfil = entity.perfil.rol
        }.fromClienteDaoToCliente()
    }

    /**
     * Update(), se actualiza el dato
     *
     * @param entity Cliente
     * @param existe ClienteDao
     * @return Cliente
     */
    private fun update(entity: Cliente, existe: ClienteDao): Cliente {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            nombre = entity.nombre
            apellido = entity.apellido
            email = entity.email
            password = entity.password
            perfil = entity.perfil.rol
        }.fromClienteDaoToCliente()
    }

    /**
     * Delete(), se elimina el dato
     *
     * @param entity Cliente
     * @return Boolean.
     */
    override fun delete(entity: Cliente): Boolean = transaction {
        val existe = clienteDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}