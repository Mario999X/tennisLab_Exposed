package repositories.usuario

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.usuario.TrabajadorDao
import mappers.fromTrabajadorDaoToTrabajador
import models.usuario.Trabajador
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

/**
 * TrabajadorRepositoryImpl, Clase que realiza operaciones CRUD, trabajador.
 *
 * @property trabajadorDao TrabajadorMapper
 */
class TrabajadorRepositoryImpl(private val trabajadorDao: LongEntityClass<TrabajadorDao>) : TrabajadorRepository {
    /**
     * FindAll()
     *
     * @return Lista de trabajadores
     */
    override fun findAll(): List<Trabajador> = transaction {
        log.debug { "findAll()" }
        trabajadorDao.all().map { it.fromTrabajadorDaoToTrabajador() }
    }

    /**
     * FindById()
     *
     * @param id Identificador de trabajador
     * @return Trabajador o Null
     */
    override fun findById(id: Long): Trabajador? = transaction {
        log.debug { "findById($id)" }
        trabajadorDao.findById(id)?.fromTrabajadorDaoToTrabajador()
    }

    /**
     * Save(), guarda o actualiza el entity
     *
     * @param entity Trabajador
     * @return Trabajador
     */
    override fun save(entity: Trabajador): Trabajador = transaction {
        val existe = trabajadorDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Insert(), Se introduce el dato
     *
     * @param entity Trabajador
     * @return Trabajador
     */
    private fun insert(entity: Trabajador): Trabajador {
        log.debug { "save($entity) - creando" }
        return trabajadorDao.new(entity.id) {
            uuid = entity.uuid
            nombre = entity.nombre
            apellido = entity.apellido
            email = entity.email
            password = entity.password
            perfil = entity.perfil.rol
        }.fromTrabajadorDaoToTrabajador()
    }

    /**
     * Update(), se actualiza el dato
     *
     * @param entity Trabajador
     * @param existe TrabajadorDao
     * @return Trabajador
     */
    private fun update(entity: Trabajador, existe: TrabajadorDao): Trabajador {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            nombre = entity.nombre
            apellido = entity.apellido
            email = entity.email
            password = entity.password
            perfil = entity.perfil.rol
        }.fromTrabajadorDaoToTrabajador()
    }

    /**
     * Delete(), se elimina el dato
     *
     * @param entity Trabajador
     * @return Boolean
     */
    override fun delete(entity: Trabajador): Boolean = transaction {
        val existe = trabajadorDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}