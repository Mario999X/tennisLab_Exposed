package repositories.usuario

import entities.usuario.EncargadoDao
import mappers.fromTrabajadorDaoToTrabajador
import mappers.usuario.fromEncargadoDaoToEncargado
import models.usuario.Encargado
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class EncargadoRepositoryImpl(private val encargadoDao: LongEntityClass<EncargadoDao>) : EncargadoRepository {
    override fun findAll(): List<Encargado> = transaction {
        log.debug { "findAll()" }
        encargadoDao.all().map { it.fromEncargadoDaoToEncargado() }
    }

    override fun findById(id: Long): Encargado? = transaction {
        log.debug { "findById($id)" }
        encargadoDao.findById(id)?.fromEncargadoDaoToEncargado()
    }

    override fun save(entity: Encargado): Encargado = transaction {
        val existe = encargadoDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

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

    override fun delete(entity: Encargado): Boolean = transaction {
        val existe = encargadoDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}