package mappers.usuario

import entities.usuario.EncargadoDao
import models.usuario.Encargado
import utils.Cifrador

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