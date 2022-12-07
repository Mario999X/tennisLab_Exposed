package repositories.pedido

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import models.Pedido
import repositories.CrudRepository

interface PedidoRepository : CrudRepository<Pedido, Long> {
}