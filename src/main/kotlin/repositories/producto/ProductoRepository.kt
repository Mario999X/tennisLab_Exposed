package repositories.producto

import models.Producto
import repositories.CrudRepository

interface ProductoRepository : CrudRepository<Producto, Long> {
}