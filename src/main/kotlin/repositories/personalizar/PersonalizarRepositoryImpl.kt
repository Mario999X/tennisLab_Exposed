package repositories.personalizar

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.PersonalizarDao
import mappers.fromPersonalizarDaoToPersonalizar
import models.Personalizar
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

/**
 * PersonalizarRepositoryImpl, Clase que realiza operaciones CRUD, personalizaciones.
 *
 * @property personalizarDao
 */
class PersonalizarRepositoryImpl(private val personalizarDao: LongEntityClass<PersonalizarDao>) :
    PersonalizarRepository {
    /**
     * FindAll()
     *
     * @return Lista de personalizaciones
     */
    override fun findAll(): List<Personalizar> = transaction {
        log.debug { "findAll()" }
        personalizarDao.all().map { it.fromPersonalizarDaoToPersonalizar() }
    }

    /**
     * FindById()
     *
     * @param id Identificador de personalizar
     * @return Personalizar o Null
     */
    override fun findById(id: Long): Personalizar? = transaction {
        log.debug { "findById($id)" }
        personalizarDao.findById(id)?.fromPersonalizarDaoToPersonalizar()
    }

    /**
     * Save(), guarda o actualiza el entity
     *
     * @param entity Personalizar
     * @return Personalizar
     */
    override fun save(entity: Personalizar): Personalizar = transaction {
        val existe = personalizarDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Insert(), se introduce el dato
     *
     * @param entity Personalizar
     * @return Personalizar
     */
    private fun insert(entity: Personalizar): Personalizar {
        log.debug { "save($entity) - creando" }
        return personalizarDao.new(entity.id) {
            uuid = entity.uuid
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
            precio = entity.precio
        }.fromPersonalizarDaoToPersonalizar()
    }

    /**
     * Update(), se actualiza el dato
     *
     * @param entity Personalizar
     * @param existe PersonalizarDao
     * @return Personalizar
     */
    private fun update(entity: Personalizar, existe: PersonalizarDao): Personalizar {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
            precio = entity.precio
        }.fromPersonalizarDaoToPersonalizar()
    }

    /**
     * Delete(), se elimina el dato
     *
     * @param entity Personalizar
     * @return Personalizar
     */
    override fun delete(entity: Personalizar): Boolean = transaction {
        val existe = personalizarDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }

}