package mappers

import entities.PersonalizarDao
import models.Personalizar

fun PersonalizarDao.fromPersonalizarDaoToPersonalizar(): Personalizar {
    return Personalizar(
        id = id.value,
        uuid = uuid,
        peso = peso,
        balance = balance,
        rigidez = rigidez,
        precio = precio
    )
}