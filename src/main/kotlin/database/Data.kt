package database

import models.Usuario
import utils.Cifrador

fun getUsuariosInit() = listOf(
    Usuario(
        id = 1L,
        nombre = "Sebastian",
        apellido = "Mendoza",
        email = "email@email.com",
        password = Cifrador.encryptString("1234"),//TODO: Así funcionaría el SHA512 clavándoselo por aquí. Otra cosa es hacerlo mediante un insert
        perfil = Usuario.Perfil.ADMIN
    ),
    Usuario(
        id = 2L,
        nombre = "Mario",
        apellido = "Resa",
        email = "email2@email.com",
        password = "1234",
        perfil = Usuario.Perfil.ENCORDADOR
    ),
    Usuario(
        id = 3L,
        nombre = "Sandra",
        apellido = "Ortega",
        email = "email3@email.com",
        password = "1234",
        perfil = Usuario.Perfil.TENISTA
    )
)