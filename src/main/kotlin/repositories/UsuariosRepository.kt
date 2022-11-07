package repositories

import models.Usuario

interface UsuariosRepository: CrudRepository<Usuario, Long> {
}