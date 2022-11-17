import config.ConfigProject
import controllers.*
import database.*
import entities.*
import mu.KotlinLogging
import repositories.*
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

    val turnosController = TurnoController(TurnoRepositoryImpl(TurnoDao))

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

    getTurno().forEach {
        turnosController.createTurno(it)
    }

    val turno = turnosController.getTurnos()
    turno.forEach { println(it) }
}

fun initDataBase() {
    val path =
        Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + "config.properties")
            .toString()
    val config = ConfigProject.fromProperties(path)
    println("Configuración: $config")

    DataBaseManager.init(config)
}