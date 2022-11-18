package database

import models.*

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

fun getAdquisicionInit() = listOf(
    Adquisicion(
        id = 1L,
        cantidad = 1,
        uuidProducto = getProductoInit()[0],
        precio = getProductoInit()[0].precio
    )
)

fun getPersonalizacionInit() = listOf(
    Personalizar(
        id = 1L,
        peso = 1.2,
        balance = 1.3,
        rigidez = 2
    )
)

fun getTareaInit() = listOf(
    Tarea(
        id = 1L,
        uuidAdquisicion = getAdquisicionInit()[0],
        precio = getProductoInit()[0].precio
    )
)