import config.ConfigProject
import controllers.*
import controllers.maquina.EncordadoraController
import controllers.maquina.PersonalizadoraController
import controllers.usuario.ClienteController
import controllers.usuario.EncargadoController
import controllers.usuario.TrabajadorController
import database.*
import entities.*
import entities.maquina.EncordadoraDao
import entities.maquina.PersonalizadoraDao
import entities.usuario.ClienteDao
import entities.usuario.EncargadoDao
import entities.usuario.TrabajadorDao
import models.Pedido
import mu.KotlinLogging
import repositories.adquisicion.AdquisicionRepositoryImpl
import repositories.encordadora.EncordadoraRepositoryImpl
import repositories.encordar.EncordarRepositoryImpl
import repositories.pedido.PedidoRepositoryImpl
import repositories.personalizadora.PersonalizadoraRepositoryImpl
import repositories.personalizar.PersonalizarRepositoryImpl
import repositories.producto.ProductoRepositoryImpl
import repositories.raqueta.RaquetaRepositoryImpl
import repositories.tarea.TareaRepositoryImpl
import repositories.turno.TurnoRepositoryImpl
import repositories.usuario.ClienteRepositoryImpl
import repositories.usuario.EncargadoRepositoryImpl
import repositories.usuario.TrabajadorRepositoryImpl
import java.io.File
import java.nio.file.Paths

/**
 * @author Sebastian Mendoza y Mario Resa
 */
private val log = KotlinLogging.logger { }

/**
 * Main() Funcion principal del programa
 *
 * En el se cargan los controllers de los componentes de la aplicacion, se introducen los datos de Data y se
 * operan con ellos / Operaciones CRUD.
 *
 * @param args
 */
fun main(args: Array<String>) {
    log.info("TennisLab App")
    initDataBase()

    //Controladores
    val clienteController = ClienteController(ClienteRepositoryImpl(ClienteDao))
    val trabajadorController = TrabajadorController(TrabajadorRepositoryImpl(TrabajadorDao))
    val encargadoController = EncargadoController(EncargadoRepositoryImpl(EncargadoDao))
    val productosController = ProductoController(ProductoRepositoryImpl(ProductoDao))
    val adquisicionController = AdquisicionController(AdquisicionRepositoryImpl(AdquisicionDao))
    val personalizarController = PersonalizarController(PersonalizarRepositoryImpl(PersonalizarDao))
    val encordarController = EncordarController(EncordarRepositoryImpl(EncordarDao))
    val encordadorasController = EncordadoraController(EncordadoraRepositoryImpl(EncordadoraDao))
    val personalizadorasController = PersonalizadoraController(PersonalizadoraRepositoryImpl(PersonalizadoraDao))
    val tareaController = TareaController(TareaRepositoryImpl(TareaDao))
    val turnoController = TurnoController(TurnoRepositoryImpl(TurnoDao))
    val pedidosController = PedidoController(PedidoRepositoryImpl(PedidoDao))
    val raquetaController = RaquetaController(RaquetaRepositoryImpl(RaquetaDao))


    // --- INSERCION DE DATOS ---

    getClientesInit().forEach { cliente ->
        clienteController.createCliente(cliente)
    }

    getTrabajadorInit().forEach { trabajador ->
        trabajadorController.createTrabajador(trabajador)
    }

    getEncargadoInit().forEach { encargado ->
        encargadoController.createEncargado(encargado)
    }

    getProductoInit().forEach { producto ->
        productosController.createProducto(producto)
    }

    getAdquisicionInit().forEach { adquisicion ->
        adquisicionController.createAdquisicion(adquisicion)
    }

    getPersonalizacionInit().forEach { personalizar ->
        personalizarController.createPersonalizacion(personalizar)
    }

    getEncordadorasInit().forEach { encordadora ->
        encordadorasController.createEncordadora(encordadora)
    }

    getPersonalizadorasInit().forEach { personalizadora ->
        personalizadorasController.createPersonalizadora(personalizadora)
    }
    getEncordadoInit().forEach { encordado ->
        encordarController.createEncordado(encordado)
    }

    getPedidosInit().forEach { pedidos ->
        pedidosController.createPedido(pedidos)
    }

    getRaquetasInit().forEach { raqueta ->
        raquetaController.createRaqueta(raqueta)
    }

    getTareaInit().forEach { tarea ->
        tareaController.createTarea(tarea)
    }

    getTurnosInit().forEach { turno ->
        turnoController.createTurno(turno)
    }

    // --- CRUD ---

    // -- CLIENTES --
    // FindAll
    val clientes = clienteController.getClientes()
    clientes.forEach { println(it) }

    // FindById
    val cliente = clienteController.getClienteById(1)
    println(cliente)

    // Update
    cliente.let {
        cliente.nombre = "Kratos"
        clienteController.updateCliente(it)
    }
    println(cliente)
    // - Delete de un cliente - NO ELIMINACION -

    // -- TRABAJADORES --
    // FindAll
    val trabajadores = trabajadorController.getTrabajadores()
    trabajadores.forEach { println(it) }

    // FindById
    val trabajador = trabajadorController.getTrabajadorById(1)
    println(trabajador)

    // Update
    trabajador.let {
        trabajador.nombre = "L"
        trabajadorController.updateTrabajador(it)
    }
    println(trabajador)
    // Delete - NO ELIMINACION -

    // -- ENCARGADOS --
    // FindAll
    val encargados = encargadoController.getEncargados()
    encargados.forEach { println(it) }

    // Update
    val encargado = encargadoController.getEncargadoById(1)
    encargado.let {
        it.nombre = "Jesus"
        encargadoController.updateEncargado(it)
    }

    // FindById
    println(encargadoController.getEncargadoById(1))

    // Delete
    encargado.let { if (encargadoController.deleteEncargado(it)) println(it) }
    println(encargadoController.getEncargados())

    // -- PRODUCTOS --
    // FindAll
    val productos = productosController.getProductos()
    productos.forEach { println(it) }

    // Update
    val producto = productosController.getProductoById(1)
    producto.let {
        it.precio += 10.05
        productosController.updateProducto(it)
    }

    // FindById
    productosController.getProductoById(1).let { println(it) }

    // Delete
    producto.let { if (productosController.deleteProducto(it)) println("Producto eliminado") }
    println(productosController.getProductos())

    // -- ADQUISICIONES --
    // FindAll
    val adquisiciones = adquisicionController.getAdquisiciones()
    adquisiciones.forEach { println(it) }

    // Update
    val adquisicion = adquisicionController.getAdquisicionById(2)
    adquisicion.let {
        it.cantidad += 1
        adquisicionController.updateAdquisicion(it)
    }

    // FindById
    println(adquisicionController.getAdquisicionById(2))

    // Delete
    adquisicion.let { if (adquisicionController.deleteAdquisicion(it)) println(it) }
    println(adquisicionController.getAdquisiciones())

    // -- PERSONALIZACIONES --
    // FindAll
    val personalizaciones = personalizarController.getPersonalizaciones()
    personalizaciones.forEach { println(it) }

    // Update
    val personalizacion = personalizarController.getPersonalizadoById(1)
    personalizacion.let {
        it.peso += 0.43
        personalizarController.updatePersonalizado(it)
    }

    // FindById
    println(personalizarController.getPersonalizadoById(1))

    // Delete
    personalizacion.let { if (personalizarController.deletePersonalizado(it)) println(it) }
    println(personalizarController.getPersonalizaciones())

    // -- ENCORDADOS --
    // FindAll
    val encordados = encordarController.getEncordados()
    encordados.forEach { println(it) }

    // Update
    val encordado = encordarController.getEncordadoById(1)
    encordado.let {
        it.nudos -= 1
        encordarController.updateEncordado(it)
    }

    // FindById
    println(encordarController.getEncordadoById(1))

    // Delete
    encordado.let { if (encordarController.deleteEncordado(it)) println(it) }
    println(encordarController.getEncordados())

    // -- PEDIDOS --
    // FindAll
    val pedidos = pedidosController.getPedidos()
    pedidos.forEach { println(it) }

    // Update
    val pedido = pedidosController.getPedidoById(2)
    pedido.let {
        it.estado = Pedido.TipoEstado.TERMINADO
        pedidosController.updatePedido(it)
    }

    // FindById
    println(pedidosController.getPedidoById(2))

    // Delete
    pedido.let { if (pedidosController.deletePedido(it)) println(it) }
    println(pedidosController.getPedidos())


    // -- TAREAS --
    // FindAll
    val tareas = tareaController.getTareas()
    tareas.forEach { println(it) }

    // Update
    val tarea = tareaController.getTareaById(1)
    tarea.let {
        it.precio += 100.0
        tareaController.updateTarea(it)
    }

    // FindById
    println(tareaController.getTareaById(2))

    // Delete
    tarea.let { if (tareaController.deleteTarea(it)) println(it) }
    println(tareaController.getTareas())

    // -- ENCORDADORAS --
    // FindAll
    val encordadoras = encordadorasController.getEncordadoras()
    encordadoras.forEach { println(it) }

    // FindById
    val encordadora = encordadorasController.getEncordadoraById(1) // encordadoras[0].id
    println(encordadora)

    // Update
    encordadora.let {
        it.marca = "MGS"
        encordadorasController.updateEncordadora(it)
    }
    println(encordadora)

    // Delete - NO turno
    encordadora.let {
        encordadorasController.deleteEncordadora(it)
    }
    println(encordadorasController.getEncordadoras())


    // -- PERSONALIZADORAS --
    // FindAll
    val personalizadoras = personalizadorasController.getPersonalizadoras()
    personalizadoras.forEach { println(it) }

    // FindById
    val personalizadora = personalizadorasController.getPersonalizadoraById(1) // personalizadoras[0].id
    println(personalizadora)

    // Update
    personalizadora.let {
        it.marca = "KOTO"
        personalizadorasController.updatePersonalizadora(it)
    }
    println(personalizadora)

    // Delete - SI turno
    personalizadora.let {
        personalizadorasController.deletePersonalizadora(it)
    }
    println(personalizadorasController.getPersonalizadoras())

    // -- TURNOS --
    // FindAll
    val turnos = turnoController.getTurnos()
    turnos.forEach { println(it) }

    // FindById
    val turno = turnoController.getTurnoById(1)
    println(turno)

    // Update
    turno.let {
        it.trabajador = trabajadorController.getTrabajadores()[2]
        turnoController.updateTurno(it)
    }
    println(turno)

    // Delete
    turno.let {
        turnoController.deleteTurno(it)
    }
    println(turnoController.getTurnos())


    // -- RAQUETAS --
    // FindAll
    val raquetas = raquetaController.getRaquetas()
    raquetas.forEach { println(it) }

    // FindById
    val raqueta = raquetaController.getRaquetaById(1)
    println(raqueta)

    // Update
    raqueta.let {
        it.modelo = "Paldea"
        raquetaController.updateRaqueta(it)
    }
    println(raqueta)

    // Delete
    raqueta.let {
        raquetaController.deleteRaqueta(it)
    }
    println(raquetaController.getRaquetas())

}


/**
 * InitDataBase(), Funcion que se encarga de cargar la configuracion de la BBDD
 *
 */
fun initDataBase() {
    val path =
        Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + "config.properties")
            .toString()
    val config = ConfigProject.fromProperties(path)
    println("Configuración: $config")

    DataBaseManager.init(config)
}