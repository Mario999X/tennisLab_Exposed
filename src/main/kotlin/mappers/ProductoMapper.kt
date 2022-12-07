package mappers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.ProductoDao
import models.Producto

/**
 * FromProductoDaoToProducto(), funcion que obtiene el dato y lo pasa a objeto
 *
 * @return Producto
 */
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