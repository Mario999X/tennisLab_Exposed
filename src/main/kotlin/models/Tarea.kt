package models

import java.util.UUID

open class Tarea(
    val id: Long,
    val uuid: UUID = UUID.randomUUID(),
    val precio: Double
){
    override fun toString(): String {
        return "Tarea(id=$id, uuid=$uuid, precio=$precio)"
    }
}