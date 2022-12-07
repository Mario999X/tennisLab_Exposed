package repositories.raqueta

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.RaquetaDao
import entities.usuario.ClienteDao
import exceptions.GenericException
import mappers.fromRaquetaDaoToRaqueta
import models.Raqueta
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

/**
 * RaquetaRepositoryImpl, Clase que realiza operaciones CRUD, Raquetas.
 *
 * @property raquetaDao RaquetaMapper
 */
class RaquetaRepositoryImpl(private val raquetaDao: LongEntityClass<RaquetaDao>) : RaquetaRepository {
    /**
     * FindAll()
     *
     * @return Lista de raquetas
     */
    override fun findAll(): List<Raqueta> = transaction {
        log.debug { "findAll()" }
        raquetaDao.all().map { it.fromRaquetaDaoToRaqueta() }
    }

    /**
     * FindById()
     *
     * @param id Identificador de raqueta
     * @return Raqueta o Null
     */
    override fun findById(id: Long): Raqueta? = transaction {
        log.debug { "findById($id)" }
        raquetaDao.findById(id)?.fromRaquetaDaoToRaqueta()
    }

    /**
     * Save(), guarda o actualiza el entity
     *
     * @param entity Raqueta
     * @return Raqueta
     */
    override fun save(entity: Raqueta): Raqueta = transaction {
        val existe = raquetaDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Insert(), Se introduce el dato
     *
     * @param entity Raqueta
     * @return Raqueta
     */
    private fun insert(entity: Raqueta): Raqueta {
        log.debug { "save($entity) - creando" }
        return raquetaDao.new(entity.id) {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            cliente = ClienteDao.findById(entity.cliente.id)
                ?: throw GenericException("Cliente no encontrado")
        }.fromRaquetaDaoToRaqueta()
    }

    /**
     * Update(), se actualiza el dato
     *
     * @param entity Raqueta
     * @param existe RaquetaDao
     * @return Raqueta
     */
    private fun update(entity: Raqueta, existe: RaquetaDao): Raqueta {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            cliente = ClienteDao.findById(entity.cliente.id)
                ?: throw GenericException("Cliente no encontrado")
        }.fromRaquetaDaoToRaqueta()
    }

    /**
     * Delete(), se elimina el dato
     *
     * @param entity Raqueta
     * @return Boolean
     */
    override fun delete(entity: Raqueta): Boolean = transaction {
        val existe = raquetaDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}