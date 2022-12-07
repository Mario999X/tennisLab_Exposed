package repositories.producto

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import models.Producto
import repositories.CrudRepository

interface ProductoRepository : CrudRepository<Producto, Long> {
}