package mappers

import entities.maquina.EncordadoraDao
import entities.maquina.PersonalizadoraDao
import models.maquina.Encordadora
import models.maquina.Personalizadora

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