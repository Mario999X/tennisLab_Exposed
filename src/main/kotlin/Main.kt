import config.ConfigProject
import controllers.AdquisicionController
import controllers.ProductoController
import controllers.TareaController
import controllers.UsuariosController
import database.*
import entities.AdquisicionDao
import entities.ProductoDao
import entities.TareaDao
import entities.UsuarioDao
import mu.KotlinLogging
import repositories.AdquisicionRepositoryImpl
import repositories.ProductoRepositoryImpl
import repositories.TareaRepositoryImpl
import repositories.UsuariosRepositoryImpl
import java.io.File
import java.nio.file.Paths

private val log = KotlinLogging.logger { }
fun main() {
    log.info("TennisLab App")
    initDataBase()

    val usuariosController = UsuariosController(UsuariosRepositoryImpl(UsuarioDao))
    val productosController = ProductoController(ProductoRepositoryImpl(ProductoDao))
    val adquisicionController = AdquisicionController(AdquisicionRepositoryImpl(AdquisicionDao))
    val tareaController = TareaController(TareaRepositoryImpl(TareaDao))

    getUsuariosInit().forEach { usuario ->
        usuariosController.createUsuario(usuario)
    }

    getProductoInit().forEach { producto ->
        productosController.createProducto(producto)
    }

    getAdquisicionInit().forEach { adquisicion ->
        adquisicionController.createAdquisicion(adquisicion)
    }

    getTareaInit().forEach { tarea ->
        tareaController.createTarea(tarea)
    }


    val usuarios = usuariosController.getUsuarios()
    usuarios.forEach { println(it) }

    val productos = productosController.getProductos()
    productos.forEach { println(it) }

    val adquisiciones = adquisicionController.getAdquisiciones()
    adquisiciones.forEach { println(it) }

    val tareas = tareaController.getTareas()
    tareas.forEach { println(it) }
}

fun initDataBase() {
    val path =
        Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + "config.properties")
            .toString()
    val config = ConfigProject.fromProperties(path)
    println("Configuración: $config")

    DataBaseManager.init(config)
}