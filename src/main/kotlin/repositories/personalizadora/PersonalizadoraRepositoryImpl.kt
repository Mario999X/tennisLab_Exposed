package repositories.personalizadora

import entities.maquina.PersonalizadoraDao
import mappers.fromPersonalizadoraDaoToPersonalizar
import models.maquina.Personalizadora
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class PersonalizadoraRepositoryImpl(private val personalizadoraDao: LongEntityClass<PersonalizadoraDao>) :
    PersonalizadoraRepository {
    override fun findAll(): List<Personalizadora> = transaction {
        log.debug { "findAll()" }
        personalizadoraDao.all().map { it.fromPersonalizadoraDaoToPersonalizar() }
    }

    override fun findById(id: Long): Personalizadora? = transaction {
        log.debug { "findById($id)" }
        personalizadoraDao.findById(id)?.fromPersonalizadoraDaoToPersonalizar()
    }

    override fun save(entity: Personalizadora) = transaction {
        val existe = personalizadoraDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    override fun delete(entity: Personalizadora): Boolean = transaction {
        val existe = personalizadoraDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
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
        }.fromPersonalizadoraDaoToPersonalizar()
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
        }.fromPersonalizadoraDaoToPersonalizar()
    }

}