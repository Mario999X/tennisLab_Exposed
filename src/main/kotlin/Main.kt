import config.ConfigProject
import controllers.*
import database.*
import entities.*
import mu.KotlinLogging
import repositories.adquisicion.AdquisicionRepositoryImpl
import repositories.personalizar.PersonalizarRepositoryImpl
import repositories.producto.ProductoRepositoryImpl
import repositories.tarea.TareaRepositoryImpl
import repositories.usuario.UsuariosRepositoryImpl
import java.io.File
import java.nio.file.Paths

private val log = KotlinLogging.logger { }
fun main() {
    log.info("TennisLab App")
    initDataBase()

    val usuariosController = UsuariosController(UsuariosRepositoryImpl(UsuarioDao))
    val productosController = ProductoController(ProductoRepositoryImpl(ProductoDao))
    val adquisicionController = AdquisicionController(AdquisicionRepositoryImpl(AdquisicionDao))
    val personalizarController = PersonalizarController(PersonalizarRepositoryImpl(PersonalizarDao))
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

    getPersonalizacionInit().forEach { personalizar ->
        personalizarController.createPersonalizacion(personalizar)
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

    val personalizaciones = personalizarController.getPersonalizaciones()
    personalizaciones.forEach { println(it) }

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