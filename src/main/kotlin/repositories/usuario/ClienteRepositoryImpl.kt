package repositories.usuario

import entities.usuario.ClienteDao
import mappers.fromClienteDaoToCliente
import models.usuario.Cliente
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class ClienteRepositoryImpl(private val clienteDao: LongEntityClass<ClienteDao>) : ClienteRepository {
    override fun findAll(): List<Cliente> = transaction {
        log.debug { "findAll()" }
        clienteDao.all().map { it.fromClienteDaoToCliente() }
    }

    override fun findById(id: Long): Cliente? = transaction {
        log.debug { "findById($id)" }
        clienteDao.findById(id)?.fromClienteDaoToCliente()
    }

    override fun save(entity: Cliente): Cliente = transaction {
        val existe = clienteDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun insert(entity: Cliente): Cliente {
        log.debug { "save($entity) - creando" }
        return clienteDao.new(entity.id) {
            uuid = entity.uuid
            nombre = entity.nombre
            apellido = entity.apellido
            email = entity.email
            password = entity.password
            perfil = entity.perfil.rol
        }.fromClienteDaoToCliente()
    }

    private fun update(entity: Cliente, existe: ClienteDao): Cliente {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            nombre = entity.nombre
            apellido = entity.apellido
            email = entity.email
            password = entity.password
            perfil = entity.perfil.rol
        }.fromClienteDaoToCliente()
    }

    override fun delete(entity: Cliente): Boolean = transaction {
        val existe = clienteDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}