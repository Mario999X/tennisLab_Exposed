package repositories.personalizar

import entities.PersonalizarDao
import mappers.fromPersonalizarDaoToPersonalizar
import models.Personalizar
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class PersonalizarRepositoryImpl(private val personalizarDao: LongEntityClass<PersonalizarDao>) :
    PersonalizarRepository {
    override fun findAll(): List<Personalizar> = transaction {
        log.debug { "findAll()" }
        personalizarDao.all().map { it.fromPersonalizarDaoToPersonalizar() }
    }

    override fun findById(id: Long): Personalizar? = transaction {
        log.debug { "findById($id)" }
        personalizarDao.findById(id)?.fromPersonalizarDaoToPersonalizar()
    }

    override fun save(entity: Personalizar): Personalizar = transaction {
        val existe = personalizarDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun insert(entity: Personalizar): Personalizar {
        log.debug { "save($entity) - creando" }
        return personalizarDao.new(entity.id) {
            uuid = entity.uuid
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
            precio = entity.precio
        }.fromPersonalizarDaoToPersonalizar()
    }

    private fun update(entity: Personalizar, existe: PersonalizarDao): Personalizar {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            peso = entity.peso
            balance = entity.balance
            rigidez = entity.rigidez
            precio = entity.precio
        }.fromPersonalizarDaoToPersonalizar()
    }

    override fun delete(entity: Personalizar): Boolean = transaction {
        val existe = personalizarDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }

}