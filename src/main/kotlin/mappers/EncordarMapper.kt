package mappers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.EncordarDao
import models.Encordar

/**
 * FromEncordarDaoToEncordar(), funcion que obtiene el dato y lo pasa a objeto
 *
 * @return Encordar
 */
fun EncordarDao.fromEncordarDaoToEncordar(): Encordar {
    return Encordar(
        id = id.value,
        uuid = uuid,
        tensionCuerdasHorizontales = tenCuHori,
        cordajeHorizontal = cordajeHorizontal,
        tensionCuerdasVerticales = tenCuVerti,
        cordajeVertical = cordajeVertical,
        nudos = nudos,
        precio = precio
    )
}