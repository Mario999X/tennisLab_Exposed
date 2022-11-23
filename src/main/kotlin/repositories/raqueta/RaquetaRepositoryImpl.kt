package repositories.raqueta

import entities.RaquetaDao
import entities.usuario.ClienteDao
import mappers.fromRaquetaDaoToRaqueta
import models.Raqueta
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class RaquetaRepositoryImpl(private val raquetaDao: LongEntityClass<RaquetaDao>) : RaquetaRepository {
    override fun findAll(): List<Raqueta> = transaction {
        log.debug { "findAll()" }
        raquetaDao.all().map { it.fromRaquetaDaoToRaqueta() }
    }

    override fun findById(id: Long): Raqueta? = transaction {
        log.debug { "findById($id)" }
        raquetaDao.findById(id)?.fromRaquetaDaoToRaqueta()
    }

    override fun save(entity: Raqueta): Raqueta = transaction {
        val existe = raquetaDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun insert(entity: Raqueta): Raqueta {
        log.debug { "save($entity) - creando" }
        return raquetaDao.new(entity.id) {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            cliente = ClienteDao.findById(entity.cliente.id)!!
        }.fromRaquetaDaoToRaqueta()
    }

    private fun update(entity: Raqueta, existe: RaquetaDao): Raqueta {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            cliente = ClienteDao.findById(entity.cliente.id)!!
        }.fromRaquetaDaoToRaqueta()
    }

    override fun delete(entity: Raqueta): Boolean = transaction {
        val existe = raquetaDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}