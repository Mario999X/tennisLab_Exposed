package mappers

import entities.AdquisicionDao
import models.Adquisicion

fun AdquisicionDao.fromAdquisicionDaoToAdquisicion(): Adquisicion{
    return Adquisicion(
        id = id.value,
        uuid = uuid,
        uuidProducto = producto.fromProductoDaoToProducto(),
        cantidad = cantidad,
        precio = precio
    )
}