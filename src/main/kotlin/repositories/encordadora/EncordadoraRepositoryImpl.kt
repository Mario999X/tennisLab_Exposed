package repositories.encordadora

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.maquina.EncordadoraDao
import mappers.fromEncordadoraDaoToEncordadora
import models.maquina.Encordadora
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

/**
 * EncordadoraRepositoryImpl, Clase que realiza operaciones CRUD, encordadoras.
 *
 * @property encordadoraDao EncordadoraMapper
 */
class EncordadoraRepositoryImpl(private val encordadoraDao: LongEntityClass<EncordadoraDao>) : EncordadoraRepository {

    /**
     * FindAll()
     *
     * @return Lista de encordadoras
     */
    override fun findAll(): List<Encordadora> = transaction {
        log.debug { "findAll()" }
        encordadoraDao.all().map { it.fromEncordadoraDaoToEncordadora() }
    }

    /**
     * FindById()
     *
     * @param id Identificador de encordadora
     * @return Encordadora o Null
     */
    override fun findById(id: Long): Encordadora? = transaction {
        log.debug { "findById($id)" }
        encordadoraDao.findById(id)?.fromEncordadoraDaoToEncordadora()
    }

    /**
     * Save(), guarda o actualiza el entity
     *
     * @param entity Encordadora
     */
    override fun save(entity: Encordadora) = transaction {
        val existe = encordadoraDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Insert(), se introduce el dato
     *
     * @param entity Encordadora
     * @return Encordadora
     */
    private fun insert(entity: Encordadora): Encordadora {
        log.debug { "save($entity) - creando" }
        return encordadoraDao.new(entity.id) {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            fechaAdquisicion = entity.fechaAdquisicion
            numSerie = entity.numSerie
            isManual = entity.isManual
            tensionMax = entity.tensionMax
            tensionMin = entity.tensionMin
        }.fromEncordadoraDaoToEncordadora()
    }

    /**
     * Update(), se actualiza el dato
     *
     * @param entity Encordadora
     * @param existe EncordadoraDao
     * @return Encordadora
     */
    private fun update(entity: Encordadora, existe: EncordadoraDao): Encordadora {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            fechaAdquisicion = entity.fechaAdquisicion
            numSerie = entity.numSerie
            isManual = entity.isManual
            tensionMax = entity.tensionMax
            tensionMin = entity.tensionMin
        }.fromEncordadoraDaoToEncordadora()
    }

    /**
     * Delete(), se elimina el dato
     *
     * @param entity Encordadora
     * @return Boolean
     */
    override fun delete(entity: Encordadora): Boolean = transaction {
        val existe = encordadoraDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}