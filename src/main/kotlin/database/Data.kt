package database

import models.*
import java.time.LocalDate
import models.maquina.Encordadora
import models.maquina.Personalizadora
import models.usuario.Cliente
import models.usuario.Encargado
import models.usuario.Trabajador

fun getClientesInit() = listOf(
    Cliente(
        id = 1L,
        nombre = "Sebastian",
        apellido = "Mendoza",
        email = "email@email.com",
        password = "1234"
    ),
    Cliente(
        id = 2L,
        nombre = "Mario",
        apellido = "Resa",
        email = "email2@email.com",
        password = "5678"
    ),
    Cliente(
        id = 3L,
        nombre = "Sandra",
        apellido = "Ortega",
        email = "email3@email.com",
        password = "4321"
    )
)

fun getTrabajadorInit() = listOf(
    Trabajador(
        id = 1L,
        nombre = "Adrian",
        apellido = "Garcia",
        email = "email@email.com",
        password = "1234"
    ),
    Trabajador(
        id = 2L,
        nombre = "Julian",
        apellido = "Estrada",
        email = "email2@email.com",
        password = "5678"
    ),
    Trabajador(
        id = 3L,
        nombre = "Camila",
        apellido = "Echeverri",
        email = "email3@email.com",
        password = "4321"
    )
)

fun getEncargadoInit() = listOf(
    Encargado(
        id = 1L,
        nombre = "Alberto",
        apellido = "Muñoz",
        email = "email@email.com",
        password = "1234"
    ),
    Encargado(
        id = 2L,
        nombre = "Elena",
        apellido = "Gonzalez",
        email = "email2@email.com",
        password = "5678"
    ),
    Encargado(
        id = 3L,
        nombre = "Elizabeth",
        apellido = "Merino",
        email = "email3@email.com",
        password = "4321"
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

fun getEncordadoInit() = listOf(
    Encordar(
        id = 1L,
        tensionCuerdasHorizontales = 2.0,
        cordajeHorizontal = "Dato 1",
        tensionCuerdasVerticales = 3.2,
        cordajeVertical = "Dato 2",
        nudos = 2
    )
)

fun getTareaInit() = listOf(
    Tarea(
        id = 1L,
        uuidAdquisicion = getAdquisicionInit()[0],
        uuidEncordar = getEncordadoInit()[0],
        precio = getAdquisicionInit()[0].precio + getEncordadoInit()[0].precio
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

fun getPersonalizadorasInit() = listOf(
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

fun getTurnosInit() = listOf(
    Turno(
        id = 1L,
        horario = Turno.TipoHorario.TEMPRANO,
        personalizadora = getPersonalizadorasInit()[0],
        trabajador = getTrabajadorInit()[0]
    )
)