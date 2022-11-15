import config.ConfigProject
import controllers.EncordadoraController
import controllers.PersonalizadoraController
import controllers.ProductoController
import controllers.UsuariosController
import database.*
import entities.EncordadoraDao
import entities.PersonalizadoraDao
import entities.ProductoDao
import entities.UsuarioDao
import mu.KotlinLogging
import repositories.EncordadoraRepositoryImpl
import repositories.PersonalizadoraRepositoryImpl
import repositories.ProductoRepositoryImpl
import repositories.UsuariosRepositoryImpl
import java.io.File
import java.nio.file.Paths

private val log = KotlinLogging.logger { }
fun main() {
    log.info("TennisLab App")
    initDataBase()

    val usuariosController = UsuariosController(UsuariosRepositoryImpl(UsuarioDao))
    val productosController = ProductoController(ProductoRepositoryImpl(ProductoDao))

    val encordadorasController = EncordadoraController(EncordadoraRepositoryImpl(EncordadoraDao))
    val personalizadorasController = PersonalizadoraController(PersonalizadoraRepositoryImpl(PersonalizadoraDao))

    getUsuariosInit().forEach { usuario ->
        usuariosController.createUsuario(usuario)
    }

    getProductoInit().forEach { producto ->
        productosController.createProducto(producto)
    }

    val usuarios = usuariosController.getUsuarios()
    usuarios.forEach { println(it) }

    val productos = productosController.getProductos()
    productos.forEach { println(it) }

    getEncordadorasInit().forEach { m ->
        encordadorasController.createEncordadora(m)
    }

    getPersonalizadoras().forEach { m ->
        personalizadorasController.createPersonalizadora(m)
    }

    val maquinasEncordadora = encordadorasController.getEncordadoras()
    maquinasEncordadora.forEach { println(it) }

    val maquinasPersonalizadora = personalizadorasController.getPersonalizadoras()
    maquinasPersonalizadora.forEach { println(it) }

}

fun initDataBase() {
    val path =
        Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + "config.properties")
            .toString()
    val config = ConfigProject.fromProperties(path)
    println("Configuración: $config")

    DataBaseManager.init(config)
}