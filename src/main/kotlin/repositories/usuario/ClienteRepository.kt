package repositories.usuario

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import models.usuario.Cliente
import repositories.CrudRepository

interface ClienteRepository: CrudRepository<Cliente, Long> {
}