package config

import mu.KotlinLogging
import java.io.FileInputStream
import java.util.Properties

private val log = KotlinLogging.logger { }

data class ConfigProject(
    val nombre: String,
    val version: String,
    val jdbcUrl: String,
    val jdbcDriver: String,
    val jdbcPool: Int = 10,
    val jdbcTablas: Boolean = true,
    val jdbcSQL: Boolean = true
) {
    companion object {
        val DEFAULT = ConfigProject(
            nombre = "app",
            version = "1.0.0",
            jdbcUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;",
            jdbcDriver = "org.h2.Driver",
            jdbcPool = 10,
            jdbcTablas = true,
            jdbcSQL = true
        )

        fun fromProperties(file: String): ConfigProject {
            log.debug { "Cargando las propiedades del archivo $file" }
            val properties = Properties()
            properties.load(FileInputStream(file))
            return ConfigProject(
                nombre = properties.getProperty("nombre"),
                version = properties.getProperty("version"),
                jdbcUrl = properties.getProperty("jdbc.url"),
                jdbcDriver = properties.getProperty("jdbc.driver"),
                jdbcPool = properties.getProperty("jdbc.maxPoolSize").toInt(),
                jdbcTablas = properties.getProperty("jdbc.createTables").toBoolean(),
                jdbcSQL = properties.getProperty("jdbc.showSQL").toBoolean()
            )
        }
    }
}