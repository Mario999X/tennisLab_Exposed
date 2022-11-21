package entities

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object PersonalizarTable : LongIdTable("personalizaciones") {
    val uuid = uuid("uuid")
    val peso = double("peso")
    val balance = double("balance")
    val rigidez = integer("rigidez")
    val precio = double("precio")
}

class PersonalizarDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<PersonalizarDao>(PersonalizarTable)

    var uuid by PersonalizarTable.uuid
    var peso by PersonalizarTable.peso
    var balance by PersonalizarTable.balance
    var rigidez by PersonalizarTable.rigidez
    var precio by PersonalizarTable.precio
}
