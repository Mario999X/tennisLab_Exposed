package database

import models.*
import java.time.LocalDate
import models.maquina.Encordadora
import models.maquina.Personalizadora
import models.usuario.Cliente
import models.usuario.Encargado
import models.usuario.Trabajador
import java.util.*

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
        producto = getProductoInit()[0],
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
        adquisicion = getAdquisicionInit()[0],
        encordar = getEncordadoInit()[0],
        precio = getAdquisicionInit()[0].precio + getEncordadoInit()[0].precio,
        pedidoId = getPedidosInit()[0]

    ),
    Tarea(
        id = 2L,
        encordar = getEncordadoInit()[0],
        raqueta = getRaquetasInit()[0],
        precio = getEncordadoInit()[0].precio,
        pedidoId = getPedidosInit()[0]
    ),
    Tarea(
        id = 3L,
        personalizar = getPersonalizacionInit()[0],
        raqueta = getRaquetasInit()[1],
        precio = getPersonalizacionInit()[0].precio,
        pedidoId = getPedidosInit()[0]
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


fun getPedidosInit() = listOf(
    Pedido(
        id = 1L,
        UUID.randomUUID(),
        estado = Pedido.TipoEstado.RECIBIDO,
        fechaEntrada = LocalDate.now(),
        fechaProgramada = LocalDate.now().plusDays(1),
        fechaSalida = LocalDate.now().plusDays(2),
        total = 100.0
    )
)

fun getRaquetasInit() = listOf(
    Raqueta(
        id = 1L,
        marca = "Wilson",
        modelo = "Pure",
        cliente = getClientesInit()[0]
    ),
    Raqueta(
        id = 2L,
        marca = "Prueba",
        modelo = "Air",
        cliente = getClientesInit()[0]
    )
)