package entities.maquina

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.date

/**
 * PersonalizadoraTable, clase objeto que genera una tabla
 */
object PersonalizadoraTable : LongIdTable("personalizadoras") {
    val uuid = uuid("uuid")
    val marca = varchar("marca", 100)
    val modelo = varchar("modelo", 50)
    val fechaAdquisicion = date("fecha_adquisicion")
    val numSerie = long("num_serie")
    val maniobrabilidad = bool("maniobrabilidad")
    val balance = bool("balance")
    val rigidez = bool("rigidez")
}

/**
 * PersonalizadoraDao, clase de paso objeto a dato de la tabla
 *
 * @constructor ID
 *
 * @param id EntityID<Long>
 */
class PersonalizadoraDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PersonalizadoraDao>(PersonalizadoraTable)

    var uuid by PersonalizadoraTable.uuid
    var marca by PersonalizadoraTable.marca
    var modelo by PersonalizadoraTable.modelo
    var fechaAdquisicion by PersonalizadoraTable.fechaAdquisicion
    var numSerie by PersonalizadoraTable.numSerie
    var maniobrabilidad by PersonalizadoraTable.maniobrabilidad
    var balance by PersonalizadoraTable.balance
    var rigidez by PersonalizadoraTable.rigidez
}