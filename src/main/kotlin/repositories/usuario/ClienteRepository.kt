package repositories.usuario

import models.usuario.Cliente
import repositories.CrudRepository

interface ClienteRepository: CrudRepository<Cliente, Long> {
}