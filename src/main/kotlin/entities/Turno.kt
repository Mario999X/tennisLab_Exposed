package entities

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.maquina.EncordadoraDao
import entities.maquina.EncordadoraTable
import entities.maquina.PersonalizadoraDao
import entities.maquina.PersonalizadoraTable
import entities.usuario.TrabajadorDao
import entities.usuario.TrabajadorTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.ReferenceOption

/**
 * TurnoTable, clase objeto que genera una tabla
 */
object TurnoTable : LongIdTable("turnos") {
    val uuid = uuid("uuid")
    val horario = varchar("tipo_horario", 7)
    val encordadora = reference("encordadora_turno", EncordadoraTable, ReferenceOption.SET_NULL).nullable()
    val personalizadora = reference("personalizadora_turno", PersonalizadoraTable, ReferenceOption.SET_NULL).nullable()
    val trabajador = reference("trabajador_turno", TrabajadorTable)
}

/**
 * TurnoDao, clase de paso objeto a dato de la tabla
 *
 * @constructor ID
 *
 * @param id EntityID<Long>
 */
class TurnoDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<TurnoDao>(TurnoTable)

    var uuid by TurnoTable.uuid
    var horario by TurnoTable.horario
    var encordadora by EncordadoraDao optionalReferencedOn TurnoTable.encordadora
    var personalizadora by PersonalizadoraDao optionalReferencedOn  TurnoTable.personalizadora
    var trabajador by TrabajadorDao referencedOn TurnoTable.trabajador
}