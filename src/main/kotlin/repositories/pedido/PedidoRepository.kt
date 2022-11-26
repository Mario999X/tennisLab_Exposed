package repositories.pedido

import models.Pedido
import repositories.CrudRepository

interface PedidoRepository : CrudRepository<Pedido, Long> {
}