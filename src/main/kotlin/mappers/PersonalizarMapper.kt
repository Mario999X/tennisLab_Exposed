package mappers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.PersonalizarDao
import models.Personalizar

/**
 * FromPersonalizarDaoToPersonalizar(), funcion que obtiene el dato y lo pasa a objeto
 *
 * @return Personalizar
 */
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