package mappers

import entities.usuario.ClienteDao
import models.usuario.Cliente
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