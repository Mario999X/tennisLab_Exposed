package entities

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.date

object EncordadoraTable : LongIdTable("encordadoras") {
    val uuid = uuid("uuid")
    val marca = varchar("marca", 100)
    val modelo = varchar("modelo", 50)
    val fechaAdquisicion = date("fecha_adquisicion")
    val numSerie = long("num_serie")
    val isManual = bool("is_manual")
    val tensionMax = double("tension_max")
    val tensionMin = double("tension_min")
}

class EncordadoraDao(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<EncordadoraDao>(EncordadoraTable)

    var uuid by EncordadoraTable.uuid
    var marca by EncordadoraTable.marca
    var modelo by EncordadoraTable.modelo
    var fechaAdquisicion by EncordadoraTable.fechaAdquisicion
    var numSerie by EncordadoraTable.numSerie
    var isManual by EncordadoraTable.isManual
    var tensionMax by EncordadoraTable.tensionMax
    var tensionMin by EncordadoraTable.tensionMin
}