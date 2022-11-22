package repositories.tarea

import entities.AdquisicionDao
import entities.EncordarDao
import entities.PersonalizarDao
import entities.TareaDao
import mappers.fromTareaDaoToTarea
import models.Tarea
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class TareaRepositoryImpl(private val tareaDao: LongEntityClass<TareaDao>) : TareaRepository {
    override fun findAll(): List<Tarea> = transaction {
        log.debug { "findAll()" }
        tareaDao.all().map { it.fromTareaDaoToTarea() }
    }

    override fun findById(id: Long): Tarea? = transaction {
        log.debug { "findById($id)" }
        tareaDao.findById(id)?.fromTareaDaoToTarea()
    }

    override fun save(entity: Tarea): Tarea = transaction {
        val existe = tareaDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun insert(entity: Tarea): Tarea {
        log.debug { "save($entity) - creando" }
        return tareaDao.new(entity.id) {
            uuid = entity.uuid
            adquisicion = AdquisicionDao.findById(entity.id)!!
            personalizar = PersonalizarDao.findById(entity.id)!!
            encordar = EncordarDao.findById(entity.id)!!
            precio = entity.precio
        }.fromTareaDaoToTarea()
    }

    private fun update(entity: Tarea, existe: TareaDao): Tarea {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            adquisicion = AdquisicionDao(id)
            personalizar = PersonalizarDao(id)
            encordar = EncordarDao(id)
            precio = entity.precio
        }.fromTareaDaoToTarea()
    }

    override fun delete(entity: Tarea): Boolean = transaction {
        val existe = TareaDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }

}