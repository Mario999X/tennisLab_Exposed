package repositories.personalizadora

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.maquina.PersonalizadoraDao
import mappers.fromPersonalizadoraDaoToPersonalizadora
import models.maquina.Personalizadora
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

/**
 * PersonalizadoraRepositoryImpl, clase que realiza operaciones CRUD, personalizadora.
 *
 * @property personalizadoraDao PersonalizadoraMapper
 */
class PersonalizadoraRepositoryImpl(private val personalizadoraDao: LongEntityClass<PersonalizadoraDao>) :
    PersonalizadoraRepository {
    /**
     * FindAll()
     *
     * @return Lista de personalizadoras
     */
    override fun findAll(): List<Personalizadora> = transaction {
        log.debug { "findAll()" }
        personalizadoraDao.all().map { it.fromPersonalizadoraDaoToPersonalizadora() }
    }

    /**
     * FindById()
     *
     * @param id Identificador de personalizadora
     * @return Personalizadora o Null
     */
    override fun findById(id: Long): Personalizadora? = transaction {
        log.debug { "findById($id)" }
        personalizadoraDao.findById(id)?.fromPersonalizadoraDaoToPersonalizadora()
    }

    /**
     * Save(), guarda o actualiza el entity
     *
     * @param entity Personalizadora
     */
    override fun save(entity: Personalizadora) = transaction {
        val existe = personalizadoraDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Insert(), se introduce el dato
     *
     * @param entity Personalizadora
     * @return Personalizadora
     */
    private fun insert(entity: Personalizadora): Personalizadora {
        log.debug { "save($entity) - creando" }
        return personalizadoraDao.new(entity.id) {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            fechaAdquisicion = entity.fechaAdquisicion
            numSerie = entity.numSerie
            maniobrabilidad = entity.maniobrabilidad
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromPersonalizadoraDaoToPersonalizadora()
    }

    /**
     * Update(), se actualiza el dato
     *
     * @param entity Personalizadora
     * @param existe PersonalizadoraDao
     * @return Personalizadora
     */
    private fun update(entity: Personalizadora, existe: PersonalizadoraDao): Personalizadora {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            fechaAdquisicion = entity.fechaAdquisicion
            numSerie = entity.numSerie
            maniobrabilidad = entity.maniobrabilidad
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromPersonalizadoraDaoToPersonalizadora()
    }

    /**
     * Delete(), se elimina el dato
     *
     * @param entity Personalizadora
     * @return Personalizadora
     */
    override fun delete(entity: Personalizadora): Boolean = transaction {
        val existe = personalizadoraDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}