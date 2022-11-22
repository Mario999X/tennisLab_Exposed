package entities

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

object EncordarTable : LongIdTable("encordados") {
    val uuid = uuid("uuid")
    val tenCuHori = double("tension_horizontal")
    val cordajeHorizontal = varchar("cordaje_horizontal", 50)
    val tenCuVerti = double("tension_vertical")
    val cordajeVertical = varchar("cordaje_vertical", 50)
    val nudos = integer("nudos")
    val precio = double("precio")
}

class EncordarDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<EncordarDao>(EncordarTable)

    var uuid by EncordarTable.uuid
    var tenCuHori by EncordarTable.tenCuHori
    var cordajeHorizontal by EncordarTable.cordajeHorizontal
    var tenCuVerti by EncordarTable.tenCuVerti
    var cordajeVertical by EncordarTable.cordajeVertical
    var nudos by EncordarTable.nudos
    var precio by EncordarTable.precio
}