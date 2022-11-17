package database

import models.*
import java.time.LocalDate

fun getUsuariosInit() = listOf(
    Usuario(
        id = 1L,
        nombre = "Sebastian",
        apellido = "Mendoza",
        email = "email@email.com",
        password = "1234",
        perfil = Usuario.Perfil.ADMIN
    ),
    Usuario(
        id = 2L,
        nombre = "Mario",
        apellido = "Resa",
        email = "email2@email.com",
        password = "5678",
        perfil = Usuario.Perfil.ENCORDADOR
    ),
    Usuario(
        id = 3L,
        nombre = "Sandra",
        apellido = "Ortega",
        email = "email3@email.com",
        password = "4321",
        perfil = Usuario.Perfil.TENISTA
    )
)

fun getProductoInit() = listOf(
    Producto(
        id = 1L,
        tipo = Producto.Tipo.RAQUETA,
        marca = "Babolat",
        modelo = "Pure Aero",
        stock = 3,
        precio = 279.95
    ),
    Producto(
        id = 2L,
        tipo = Producto.Tipo.COMPLEMENTO,
        marca = "Wilson",
        modelo = "Dazzle",
        stock = 5,
        precio = 7.90
    )
)

fun getEncordadorasInit() = listOf(
    Encordadora(
        id = 1L,
        marca = "Toshiba",
        modelo = "ABC",
        fechaAdquisicion = LocalDate.now(),
        numSerie = 120L,
        isManual = true,
        tensionMax = 23.2,
        tensionMin = 20.5
    )
)

fun getPersonalizadoras() = listOf(
    Personalizadora(
        id = 1L,
        marca = "Toshiba",
        modelo = "ABC",
        fechaAdquisicion = LocalDate.now(),
        numSerie = 120L,
        maniobrabilidad = true,
        balance = false,
        rigidez = false
    )
)

fun getTurno() = listOf(
    Turno(
        id = 1L,
        horario = Turno.TipoHorario.MAniANA
    )
)