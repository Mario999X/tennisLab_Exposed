package mappers

import entities.RaquetaDao
import models.Raqueta

fun RaquetaDao.fromRaquetaDaoToRaqueta(): Raqueta {
    return Raqueta(
        id = id.value,
        uuid = uuid,
        marca = marca,
        modelo = modelo,
        cliente = cliente.fromClienteDaoToCliente(),
    )
}