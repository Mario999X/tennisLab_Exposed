package entities

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

/**
 * PersonalizarTable, clase objeto que genera una tabla
 */
object PersonalizarTable : LongIdTable("personalizaciones") {
    val uuid = uuid("uuid")
    val peso = double("peso")
    val balance = double("balance")
    val rigidez = integer("rigidez")
    val precio = double("precio")
}

/**
 * PersonalizarDao, clase de paso objeto a dato de la tabla
 *
 * @constructor ID
 *
 * @param id EntityID<Long>
 */
class PersonalizarDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PersonalizarDao>(PersonalizarTable)

    var uuid by PersonalizarTable.uuid
    var peso by PersonalizarTable.peso
    var balance by PersonalizarTable.balance
    var rigidez by PersonalizarTable.rigidez
    var precio by PersonalizarTable.precio
}
