package mappers

import entities.EncordadoraDao
import entities.PersonalizadoraDao
import models.Encordadora
import models.Personalizadora

fun EncordadoraDao.fromEncordadoraDaoToEncordadora(): Encordadora {
    return Encordadora(
        id = id.value,
        uuid = uuid,
        marca = marca,
        modelo = modelo,
        fechaAdquisicion = fechaAdquisicion,
        numSerie = numSerie,
        isManual = isManual,
        tensionMax = tensionMax,
        tensionMin = tensionMin,
    )
}

fun PersonalizadoraDao.fromPersonalizadoraDaoToPersonalizar(): Personalizadora {
    return Personalizadora(
        id = id.value,
        uuid = uuid,
        marca = marca,
        modelo = modelo,
        fechaAdquisicion = fechaAdquisicion,
        numSerie = numSerie,
        maniobrabilidad = maniobrabilidad,
        balance = balance,
        rigidez = rigidez,
    )
}