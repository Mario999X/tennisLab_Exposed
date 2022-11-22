package repositories.encordar

import entities.EncordarDao
import mappers.fromEncordarDaoToEncordar
import models.Encordar
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class EncordarRepositoryImpl(private val encordarDao: LongEntityClass<EncordarDao>) : EncordarRepository {
    override fun findAll(): List<Encordar> = transaction {
        log.debug { "findAll()" }
        encordarDao.all().map { it.fromEncordarDaoToEncordar() }
    }

    override fun findById(id: Long): Encordar? = transaction {
        log.debug { "findById($id)" }
        encordarDao.findById(id)?.fromEncordarDaoToEncordar()
    }

    override fun save(entity: Encordar): Encordar = transaction {
        val existe = encordarDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

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

    override fun delete(entity: Encordar): Boolean = transaction {
        val existe = encordarDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}