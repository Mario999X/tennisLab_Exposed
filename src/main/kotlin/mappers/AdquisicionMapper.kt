package mappers

import entities.AdquisicionDao
import models.Adquisicion

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