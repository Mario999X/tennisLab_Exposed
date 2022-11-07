import config.ConfigProject
import controllers.UsuariosController
import database.DataBaseManage
import database.getUsuariosInit
import entities.UsuarioDao
import mu.KotlinLogging
import repositories.UsuariosRepositoryImpl
import java.io.File
import java.nio.file.Paths

private val log = KotlinLogging.logger { }
fun main() {
    log.info("TennisLab App")
    initDataBase()

    val controller = UsuariosController(UsuariosRepositoryImpl(UsuarioDao))

    getUsuariosInit().forEach { usuario ->
        controller.createUsuario(usuario)
    }

    val usuarios = controller.getUsuarios()
    usuarios.forEach { println(it) }
}

fun initDataBase() {
    val path =
        Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + "config.properties")
            .toString()
    val config = ConfigProject.fromProperties(path)
    println("Configuración: $config")

    DataBaseManage.init(config)
}