package repositories.turno

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.TurnoDao
import entities.maquina.EncordadoraDao
import entities.maquina.PersonalizadoraDao
import entities.usuario.TrabajadorDao
import exceptions.GenericException
import mappers.fromTurnoDaoToTurno
import models.Turno
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

/**
 * TurnoRepositoryImpl, Clase que realiza operaciones CRUD, turnos.
 *
 * @property turnoDao TurnoMapper
 */
class TurnoRepositoryImpl(private val turnoDao: LongEntityClass<TurnoDao>) : TurnoRepository {
    /**
     * FindAll()
     *
     * @return Lista de turnos
     */
    override fun findAll(): List<Turno> = transaction {
        log.debug { "findAll()" }
        turnoDao.all().map { it.fromTurnoDaoToTurno() }
    }

    /**
     * FindById()
     *
     * @param id Identificador de turno
     * @return Turno o Null
     */
    override fun findById(id: Long): Turno? = transaction {
        log.debug { "findById($id)" }
        turnoDao.findById(id)?.fromTurnoDaoToTurno()
    }

    /**
     * Save(), guarda o actualiza el entity
     *
     * @param entity Turno
     */
    override fun save(entity: Turno) = transaction {
        val existe = turnoDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Insert(), guarda el dato
     *
     * @param entity Turno
     * @return Turno
     */
    private fun insert(entity: Turno): Turno {
        log.debug { "save($entity) - creando" }
        return turnoDao.new(entity.id) {
            uuid = entity.uuid
            horario = entity.horario.horario
            encordadora = entity.encordadora?.let { EncordadoraDao.findById(it.id) }
            personalizadora = entity.personalizadora?.let { PersonalizadoraDao.findById(it.id) }
            trabajador = TrabajadorDao.findById(entity.trabajador.id)
                ?: throw GenericException("Trabajador no encontrado")
        }.fromTurnoDaoToTurno()
    }

    /**
     * Update(), se actualiza el dato
     *
     * @param entity Turno
     * @param existe TurnoDao
     * @return Turno
     */
    private fun update(entity: Turno, existe: TurnoDao): Turno {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            horario = entity.horario.horario
            encordadora = entity.encordadora?.let { EncordadoraDao.findById(it.id) }
            personalizadora = entity.personalizadora?.let { PersonalizadoraDao.findById(it.id) }
            trabajador = TrabajadorDao.findById(entity.trabajador.id)
                ?: throw GenericException("Trabajador no encontrado")
        }.fromTurnoDaoToTurno()
    }

    /**
     * Delete(), se elimina el dato
     *
     * @param entity Turno
     * @return Boolean
     */
    override fun delete(entity: Turno): Boolean = transaction {
        val existe = turnoDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }

}