package repositories

import models.Producto

interface ProductoRepository : CrudRepository<Producto, Long> {
}