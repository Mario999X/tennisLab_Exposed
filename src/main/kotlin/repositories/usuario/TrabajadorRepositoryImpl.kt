package repositories.usuario

import entities.usuario.TrabajadorDao
import mappers.fromTrabajadorDaoToTrabajador
import models.usuario.Trabajador
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class TrabajadorRepositoryImpl(private val trabajadorDao: LongEntityClass<TrabajadorDao>) : TrabajadorRepository {
    override fun findAll(): List<Trabajador> = transaction {
        log.debug { "findAll()" }
        trabajadorDao.all().map { it.fromTrabajadorDaoToTrabajador() }
    }

    override fun findById(id: Long): Trabajador? = transaction {
        log.debug { "findById($id)" }
        trabajadorDao.findById(id)?.fromTrabajadorDaoToTrabajador()
    }

    override fun save(entity: Trabajador): Trabajador = transaction {
        val existe = trabajadorDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

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

    override fun delete(entity: Trabajador): Boolean = transaction {
        val existe = trabajadorDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}