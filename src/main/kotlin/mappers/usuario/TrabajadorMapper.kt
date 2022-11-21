package mappers

import entities.usuario.TrabajadorDao
import models.usuario.Trabajador
import utils.Cifrador

fun TrabajadorDao.fromTrabajadorDaoToTrabajador(): Trabajador {
    return Trabajador(
        id = id.value,
        uuid = uuid,
        nombre = nombre,
        apellido = apellido,
        email = email,
        password = Cifrador.encryptString(password)
    )
}