package repositories.personalizadora

import entities.maquina.PersonalizadoraDao
import mappers.fromPersonalizadoraDaoToPersonalizadora
import models.maquina.Personalizadora
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class PersonalizadoraRepositoryImpl(private val personalizadoraDao: LongEntityClass<PersonalizadoraDao>) :
    PersonalizadoraRepository {
    override fun findAll(): List<Personalizadora> = transaction {
        log.debug { "findAll()" }
        personalizadoraDao.all().map { it.fromPersonalizadoraDaoToPersonalizadora() }
    }

    override fun findById(id: Long): Personalizadora? = transaction {
        log.debug { "findById($id)" }
        personalizadoraDao.findById(id)?.fromPersonalizadoraDaoToPersonalizadora()
    }

    override fun save(entity: Personalizadora) = transaction {
        val existe = personalizadoraDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun insert(entity: Personalizadora): Personalizadora {
        log.debug { "save($entity) - creando" }
        return personalizadoraDao.new(entity.id) {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            fechaAdquisicion = entity.fechaAdquisicion
            numSerie = entity.numSerie
            maniobrabilidad = entity.maniobrabilidad
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromPersonalizadoraDaoToPersonalizadora()
    }

    private fun update(entity: Personalizadora, existe: PersonalizadoraDao): Personalizadora {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            marca = entity.marca
            modelo = entity.modelo
            fechaAdquisicion = entity.fechaAdquisicion
            numSerie = entity.numSerie
            maniobrabilidad = entity.maniobrabilidad
            balance = entity.balance
            rigidez = entity.rigidez
        }.fromPersonalizadoraDaoToPersonalizadora()
    }

    override fun delete(entity: Personalizadora): Boolean = transaction {
        val existe = personalizadoraDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}