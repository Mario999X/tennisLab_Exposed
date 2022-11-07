package mappers

import entities.UsuarioDao
import models.Usuario

fun UsuarioDao.fromUsuarioDaoToUsuario(): Usuario {
    return Usuario(
        id = id.value,
        uuid = uuid,
        nombre = nombre,
        apellido = apellido,
        email = email,
        password = password,
        perfil = Usuario.Perfil.from(perfil)
    )
}