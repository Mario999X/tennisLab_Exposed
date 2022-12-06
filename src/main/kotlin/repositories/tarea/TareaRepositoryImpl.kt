package repositories.tarea

import entities.*
import entities.usuario.TrabajadorDao
import exceptions.GenericException
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
            adquisicion = entity.adquisicion?.let { AdquisicionDao.findById(it.id) }
            personalizar = entity.personalizar?.let { PersonalizarDao.findById(it.id) }
            encordar = entity.encordar?.let { EncordarDao.findById(it.id) }
            raqueta = entity.raqueta?.let { RaquetaDao.findById(it.id) }
            precio = entity.precio
            pedidoId = PedidoDao.findById(entity.pedido.id)
                ?: throw GenericException("Pedido no encontrado")
            trabajadorId = TrabajadorDao.findById(entity.trabajador.id)
                ?: throw GenericException("Trabajador no encontrado")
        }.fromTareaDaoToTarea()
    }

    private fun update(entity: Tarea, existe: TareaDao): Tarea {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            adquisicion = entity.adquisicion?.let { AdquisicionDao.findById(it.id) }
            personalizar = entity.personalizar?.let { PersonalizarDao.findById(it.id) }
            encordar = entity.encordar?.let { EncordarDao.findById(it.id) }
            raqueta = entity.raqueta?.let { RaquetaDao.findById(it.id) }
            precio = entity.precio
            pedidoId = PedidoDao.findById(entity.pedido.id)
                ?: throw GenericException("Pedido no encontrado")
            trabajadorId = TrabajadorDao.findById(entity.trabajador.id)
                ?: throw GenericException("Trabajador no encontrado")
        }.fromTareaDaoToTarea()
    }

    override fun delete(entity: Tarea): Boolean = transaction {
        val existe = TareaDao.findById(entity.id) ?: return@transaction false
        existe.delete()
        true
    }

}