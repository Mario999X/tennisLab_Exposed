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
import mu.KotlinLogging
import repositories.adquisicion.AdquisicionRepositoryImpl
import repositories.encordadora.EncordadoraRepositoryImpl
import repositories.encordar.EncordarRepositoryImpl
import repositories.pedido.PedidoRepositoryImpl
import repositories.personalizadora.PersonalizadoraRepositoryImpl
import repositories.personalizar.PersonalizarRepositoryImpl
import repositories.producto.ProductoRepositoryImpl
import repositories.tarea.TareaRepositoryImpl
import repositories.turno.TurnoRepositoryImpl
import repositories.usuario.ClienteRepositoryImpl
import repositories.usuario.EncargadoRepositoryImpl
import repositories.usuario.TrabajadorRepositoryImpl
import java.io.File
import java.nio.file.Paths

private val log = KotlinLogging.logger { }
fun main() {
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

    //Inserción de datos
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

    getTareaInit().forEach { tarea ->
        tareaController.createTarea(tarea)
    }

    getTurnosInit().forEach { turno ->
        turnoController.createTurno(turno)
    }

    //CRUD
    //Usuarios
    val clientes = clienteController.getClientes()
    clientes.forEach { println(it) }

    val trabajadores = trabajadorController.getTrabajadores()
    trabajadores.forEach { println(it) }

    val encargados = encargadoController.getEncargados()
    encargados.forEach { println(it) }

    //Productos
    val productos = productosController.getProductos()
    productos.forEach { println(it) }

    //Adquisiciones
    val adquisiciones = adquisicionController.getAdquisiciones()
    adquisiciones.forEach { println(it) }

    //Personalizaciones
    val personalizaciones = personalizarController.getPersonalizaciones()
    personalizaciones.forEach { println(it) }

    // Encordados
    val encordados = encordarController.getEncordados()
    encordados.forEach { println(it) }

    // Pedidos
    val pedidos = pedidosController.getPedidos()
    pedidos.forEach { println(it) }

    //Tareas
    val tareas = tareaController.getTareas()
    tareas.forEach { println(it) }

    // Turnos
    val turnos = turnoController.getTurnos()
    turnos.forEach { println(it) }

}

fun initDataBase() {
    val path =
        Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + "config.properties")
            .toString()
    val config = ConfigProject.fromProperties(path)
    println("Configuración: $config")

    DataBaseManager.init(config)
}