package mappers

import entities.UsuarioDao
import models.Usuario
import utils.Cifrador

fun UsuarioDao.fromUsuarioDaoToUsuario(): Usuario {
    return Usuario(
        id = id.value,
        uuid = uuid,
        nombre = nombre,
        apellido = apellido,
        email = email,
        password = Cifrador.encryptString(password),
        perfil = Usuario.Perfil.from(perfil)
    )
}