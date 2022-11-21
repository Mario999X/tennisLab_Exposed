package repositories.usuario

import models.Usuario
import repositories.CrudRepository

interface UsuariosRepository: CrudRepository<Usuario, Long> {
}