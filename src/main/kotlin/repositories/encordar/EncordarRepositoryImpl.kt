package repositories.encordar

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.EncordarDao
import mappers.fromEncordarDaoToEncordar
import models.Encordar
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

/**
 * EncordarRepositoryImpl, Clase que realiza operaciones CRUD, encordar.
 *
 * @property encordarDao
 */
class EncordarRepositoryImpl(private val encordarDao: LongEntityClass<EncordarDao>) : EncordarRepository {
    /**
     * FindAll()
     *
     * @return Lista de encordados
     */
    override fun findAll(): List<Encordar> = transaction {
        log.debug { "findAll()" }
        encordarDao.all().map { it.fromEncordarDaoToEncordar() }
    }

    /**
     * FindById()
     *
     * @param id Identificador de encordar
     * @return Encordar o Null
     */
    override fun findById(id: Long): Encordar? = transaction {
        log.debug { "findById($id)" }
        encordarDao.findById(id)?.fromEncordarDaoToEncordar()
    }

    /**
     * Save(), guarda o actualiza el entity
     *
     * @param entity Encordar
     * @return Encordar
     */
    override fun save(entity: Encordar): Encordar = transaction {
        val existe = encordarDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Insert(), se introduce el dato
     *
     * @param entity Encordar
     * @return Encordar
     */
    private fun insert(entity: Encordar): Encordar {
        log.debug { "save($entity) - creando" }
        return encordarDao.new(entity.id) {
            uuid = entity.uuid
            tenCuHori = entity.tensionCuerdasHorizontales
            cordajeHorizontal = entity.cordajeHorizontal
            tenCuVerti = entity.tensionCuerdasVerticales
            cordajeVertical = entity.cordajeVertical
            nudos = entity.nudos
            precio = entity.precio
        }.fromEncordarDaoToEncordar()
    }

    /**
     * Update(), se actualiza el dato
     *
     * @param entity Encordar
     * @param existe EncordarDao
     * @return Encordar
     */
    private fun update(entity: Encordar, existe: EncordarDao): Encordar {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            tenCuHori = entity.tensionCuerdasHorizontales
            cordajeHorizontal = entity.cordajeHorizontal
            tenCuVerti = entity.tensionCuerdasVerticales
            cordajeVertical = entity.cordajeVertical
            nudos = entity.nudos
            precio = entity.precio
        }.fromEncordarDaoToEncordar()
    }

    /**
     * Delete(), se elimina el dato
     *
     * @param entity Encordar
     * @return Boolean
     */
    override fun delete(entity: Encordar): Boolean = transaction {
        val existe = encordarDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}