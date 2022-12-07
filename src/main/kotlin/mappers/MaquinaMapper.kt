package mappers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.maquina.EncordadoraDao
import entities.maquina.PersonalizadoraDao
import models.maquina.Encordadora
import models.maquina.Personalizadora

/**
 * FromEncordadoraDaoToEncordadora(), funcion que obtiene el dato y lo pasa a objeto
 *
 * @return Encordadora
 */
fun EncordadoraDao.fromEncordadoraDaoToEncordadora(): Encordadora {
    return Encordadora(
        id = id.value,
        uuid = uuid,
        marca = marca,
        modelo = modelo,
        fechaAdquisicion = fechaAdquisicion,
        fechaJson = fechaAdquisicion.toString(),
        numSerie = numSerie,
        isManual = isManual,
        tensionMax = tensionMax,
        tensionMin = tensionMin,
    )
}

/**
 * FromPersonalizadoraDaoToPersonalizadora(), funcion que obtiene el dato y lo pasa a objeto
 *
 * @return Personalizadora
 */
fun PersonalizadoraDao.fromPersonalizadoraDaoToPersonalizadora(): Personalizadora {
    return Personalizadora(
        id = id.value,
        uuid = uuid,
        marca = marca,
        modelo = modelo,
        fechaAdquisicion = fechaAdquisicion,
        fechaJson = fechaAdquisicion.toString(),
        numSerie = numSerie,
        maniobrabilidad = maniobrabilidad,
        balance = balance,
        rigidez = rigidez,
    )
}