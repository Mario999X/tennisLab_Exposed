package repositories.usuario

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.usuario.EncargadoDao
import mappers.fromEncargadoDaoToEncargado
import models.usuario.Encargado
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

/**
 * EncargadoRepositoryImpl Clase que realiza operaciones CRUD, encargados.
 *
 * @property encargadoDao EncargadoMapper
 */
class EncargadoRepositoryImpl(private val encargadoDao: LongEntityClass<EncargadoDao>) : EncargadoRepository {
    /**
     * FindAll()
     *
     * @return Lista de encargados
     */
    override fun findAll(): List<Encargado> = transaction {
        log.debug { "findAll()" }
        encargadoDao.all().map { it.fromEncargadoDaoToEncargado() }
    }

    /**
     * FindById()
     *
     * @param id Identificador de encargado
     * @return Encargado o Null
     */
    override fun findById(id: Long): Encargado? = transaction {
        log.debug { "findById($id)" }
        encargadoDao.findById(id)?.fromEncargadoDaoToEncargado()
    }

    /**
     * Save(), guarda o actualiza el entity
     *
     * @param entity Encargado
     * @return Encargado
     */
    override fun save(entity: Encargado): Encargado = transaction {
        val existe = encargadoDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Insert(), se introduce el dato
     *
     * @param entity Encargado
     * @return Encargado
     */
    private fun insert(entity: Encargado): Encargado {
        log.debug { "save($entity) - creando" }
        return EncargadoDao.new(entity.id) {
            uuid = entity.uuid
            nombre = entity.nombre
            apellido = entity.apellido
            email = entity.email
            password = entity.password
            perfil = entity.perfil.rol
        }.fromEncargadoDaoToEncargado()
    }

    /**
     * Update(), se actualiza el dato
     *
     * @param entity Encargado
     * @param existe EncargadoDao
     * @return Encargado
     */
    private fun update(entity: Encargado, existe: EncargadoDao): Encargado {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            nombre = entity.nombre
            apellido = entity.apellido
            email = entity.email
            password = entity.password
            perfil = entity.perfil.rol
        }.fromEncargadoDaoToEncargado()
    }

    /**
     * Delete(), se elimina el dato
     *
     * @param entity Encargado
     * @return Boolean
     */
    override fun delete(entity: Encargado): Boolean = transaction {
        val existe = encargadoDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}