package repositories

import entities.UsuarioDao
import mappers.fromUsuarioDaoToUsuario
import models.Usuario
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class UsuariosRepositoryImpl(private val usuarioDao: LongEntityClass<UsuarioDao>) : UsuariosRepository {
    override fun findAll(): List<Usuario> = transaction {
        log.debug { "findAll()" }
        usuarioDao.all().map { it.fromUsuarioDaoToUsuario() }
    }

    override fun findById(id: Long): Usuario? = transaction {
        log.debug { "findById($id)" }
        usuarioDao.findById(id)?.fromUsuarioDaoToUsuario()
    }

    override fun save(entity: Usuario): Usuario = transaction {
        val existe = usuarioDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun insert(entity: Usuario): Usuario {
        log.debug { "save($entity) - creando" }
        return usuarioDao.new(entity.id) {
            uuid = entity.uuid
            nombre = entity.nombre
            apellido = entity.apellido
            email = entity.email
            password = entity.password
            perfil = entity.perfil.rol
        }.fromUsuarioDaoToUsuario()
    }

    private fun update(entity: Usuario, existe: UsuarioDao): Usuario {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            nombre = entity.nombre
            apellido = entity.apellido
            email = entity.email
            password = entity.password
            perfil = entity.perfil.rol
        }.fromUsuarioDaoToUsuario()
    }

    override fun delete(entity: Usuario): Boolean = transaction {
        val existe = usuarioDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }


}