package mappers

import entities.usuario.ClienteDao
import entities.usuario.EncargadoDao
import entities.usuario.TrabajadorDao
import models.usuario.Cliente
import models.usuario.Encargado
import models.usuario.Trabajador
import utils.Cifrador

fun ClienteDao.fromClienteDaoToCliente(): Cliente {
    return Cliente(
        id = id.value,
        uuid = uuid,
        nombre = nombre,
        apellido = apellido,
        email = email,
        password = Cifrador.encryptString(password)
    )
}

fun EncargadoDao.fromEncargadoDaoToEncargado(): Encargado {
    return Encargado(
        id = id.value,
        uuid = uuid,
        nombre = nombre,
        apellido = apellido,
        email = email,
        password = Cifrador.encryptString(password)
    )
}

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