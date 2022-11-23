package repositories.adquisicion

import entities.AdquisicionDao
import entities.ProductoDao
import mappers.fromAdquisicionDaoToAdquisicion
import models.Adquisicion
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class AdquisicionRepositoryImpl(private val adquisicionDao: LongEntityClass<AdquisicionDao>) : AdquisicionRepository {
    override fun findAll(): List<Adquisicion> = transaction {
        log.debug { "findAll()" }
        adquisicionDao.all().map { it.fromAdquisicionDaoToAdquisicion() }
    }

    override fun findById(id: Long): Adquisicion? = transaction {
        log.debug { "findById($id)" }
        adquisicionDao.findById(id)?.fromAdquisicionDaoToAdquisicion()
    }

    override fun save(entity: Adquisicion): Adquisicion = transaction {
        val existe = adquisicionDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun insert(entity: Adquisicion): Adquisicion {
        log.debug { "save($entity) - creando" }
        return adquisicionDao.new(entity.id) {
            uuid = entity.uuid
            producto = ProductoDao.findById(entity.uuidProducto.id)!!
            cantidad = entity.cantidad
            precio = entity.precio

        }.fromAdquisicionDaoToAdquisicion()
    }

    private fun update(entity: Adquisicion, existe: AdquisicionDao): Adquisicion {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            producto = ProductoDao.findById(entity.uuidProducto.id)!!
            cantidad = entity.cantidad
            precio = entity.precio
        }.fromAdquisicionDaoToAdquisicion()
    }

    override fun delete(entity: Adquisicion): Boolean = transaction {
        val existe = adquisicionDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity)- borrando" }
        existe.delete()
        true
    }

}