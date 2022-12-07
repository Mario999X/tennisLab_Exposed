package repositories.encordadora

import entities.maquina.EncordadoraDao
import mappers.fromEncordadoraDaoToEncordadora
import models.maquina.Encordadora
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class EncordadoraRepositoryImpl(private val encordadoraDao: LongEntityClass<EncordadoraDao>) : EncordadoraRepository {

    override fun findAll(): List<Encordadora> = transaction {
        log.debug { "findAll()" }
        encordadoraDao.all().map { it.fromEncordadoraDaoToEncordadora() }
    }

    override fun findById(id: Long): Encordadora? = transaction {
        log.debug { "findById($id)" }
        encordadoraDao.findById(id)?.fromEncordadoraDaoToEncordadora()
    }

    override fun save(entity: Encordadora) = transaction {
        val existe = encordadoraDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

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

    override fun delete(entity: Encordadora): Boolean = transaction {
        val existe = encordadoraDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}