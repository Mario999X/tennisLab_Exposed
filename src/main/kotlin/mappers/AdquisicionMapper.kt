package mappers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.AdquisicionDao
import models.Adquisicion

/**
 * FromAdquisicionDaoToAdquisicion(), funcion que obtiene el dato y lo pasa a objeto
 *
 * @return Adquisicion
 */
fun AdquisicionDao.fromAdquisicionDaoToAdquisicion(): Adquisicion {
    return Adquisicion(
        id = id.value,
        uuid = uuid,
        producto = producto?.fromProductoDaoToProducto(),
        descripcion = descripcion,
        cantidad = cantidad,
        precio = precio
    )
}