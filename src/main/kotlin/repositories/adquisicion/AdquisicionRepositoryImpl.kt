package repositories.adquisicion

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.AdquisicionDao
import entities.ProductoDao
import exceptions.GenericException
import mappers.fromAdquisicionDaoToAdquisicion
import models.Adquisicion
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

/**
 * AdquisicionRepositoryImpl, Clase que realiza operaciones CRUD, adquisiciones.
 *
 * @property adquisicionDao AdquisicionMapper
 */
class AdquisicionRepositoryImpl(private val adquisicionDao: LongEntityClass<AdquisicionDao>) : AdquisicionRepository {
    /**
     * FindAll()
     *
     * @return Lista de adquisiciones
     */
    override fun findAll(): List<Adquisicion> = transaction {
        log.debug { "findAll()" }
        adquisicionDao.all().map { it.fromAdquisicionDaoToAdquisicion() }
    }

    /**
     * FindById()
     *
     * @param id Identificador de adquisicion
     * @return Adquisicion o Null
     */
    override fun findById(id: Long): Adquisicion? = transaction {
        log.debug { "findById($id)" }
        adquisicionDao.findById(id)?.fromAdquisicionDaoToAdquisicion()
    }

    /**
     * Save(), guarda o actualiza el entity
     *
     * @param entity Adquisicion
     * @return Adquisicion
     */
    override fun save(entity: Adquisicion): Adquisicion = transaction {
        val existe = adquisicionDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Insert(), se introduce el dato
     *
     * @param entity Adquisicion
     * @return Adquisicion
     */
    private fun insert(entity: Adquisicion): Adquisicion {
        log.debug { "save($entity) - creando" }
        return adquisicionDao.new(entity.id) {
            uuid = entity.uuid
            producto = entity.producto?.let { ProductoDao.findById(it.id) }
                ?: throw GenericException("No existe producto")
            descripcion = entity.descripcion
                ?: throw GenericException("Error con la descripcion")
            cantidad = entity.cantidad
            precio = entity.precio!! * cantidad
        }.fromAdquisicionDaoToAdquisicion()
    }

    /**
     * Update(), se actualiza el dato
     *
     * @param entity Adquisicion
     * @param existe AdquisicionDao
     * @return Adquisicion
     */
    private fun update(entity: Adquisicion, existe: AdquisicionDao): Adquisicion {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            producto = entity.producto?.let { ProductoDao.findById(it.id) }
                ?: throw GenericException("No existe producto")
            descripcion = entity.descripcion
                ?: throw GenericException("Error con la descripcion")
            cantidad = entity.cantidad
            precio = entity.precio!! * cantidad
        }.fromAdquisicionDaoToAdquisicion()
    }

    /**
     * Delete(), se elimina el dato
     *
     * @param entity Adquisicion
     * @return Boolean
     */
    override fun delete(entity: Adquisicion): Boolean = transaction {
        val existe = adquisicionDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity)- borrando" }
        existe.delete()
        true
    }

}