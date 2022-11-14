package mappers

import entities.ProductoDao
import models.Producto

fun ProductoDao.fromProductoDaoToProducto(): Producto {
    return Producto(
        id = id.value,
        uuid = uuid,
        tipo = Producto.Tipo.from(tipo),
        marca = marca,
        modelo = modelo,
        stock = stock,
        precio = precio
    )
}