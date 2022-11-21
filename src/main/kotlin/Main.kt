import config.ConfigProject
import controllers.*
import controllers.usuario.ClienteController
import controllers.usuario.EncargadoController
import controllers.usuario.TrabajadorController
import database.*
import entities.*
import entities.usuario.ClienteDao
import entities.usuario.EncargadoDao
import entities.usuario.TrabajadorDao
import mu.KotlinLogging
import repositories.adquisicion.AdquisicionRepositoryImpl
import repositories.personalizar.PersonalizarRepositoryImpl
import repositories.producto.ProductoRepositoryImpl
import repositories.tarea.TareaRepositoryImpl
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
    val tareaController = TareaController(TareaRepositoryImpl(TareaDao))

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

    getTareaInit().forEach { tarea ->
        tareaController.createTarea(tarea)
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

    //Tareas: Adquisiciones, personalizaciones y encordados
    val tareas = tareaController.getTareas()
    tareas.forEach { println(it) }

    val adquisiciones = adquisicionController.getAdquisiciones()
    adquisiciones.forEach { println(it) }

    val personalizaciones = personalizarController.getPersonalizaciones()
    personalizaciones.forEach { println(it) }

}

fun initDataBase() {
    val path =
        Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + "config.properties")
            .toString()
    val config = ConfigProject.fromProperties(path)
    println("Configuración: $config")

    DataBaseManager.init(config)
}