package database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import config.ConfigProject
import mu.KotlinLogging
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

object DataBaseManage {
    lateinit var config: ConfigProject
    fun init(config: ConfigProject) {
        this.config = config
        log.debug { "Inicializando la base de datos" }
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = config.jdbcUrl
            driverClassName = config.jdbcDriver
            maximumPoolSize = config.jdbcPool
        }
        val dataSource = HikariDataSource(hikariConfig)
        log.debug { "Base de datos inicializada exitosamente" }
        if (config.jdbcTablas) {
            crearTablas()
        }
    }

    private fun crearTablas() = transaction {
        log.debug { "Creando las tablas" }
        if (config.jdbcSQL)
            addLogger(StdOutSqlLogger)
        //SchemaUtils.create() ToDo agregar los nombres de las tablas
        log.debug { "Tablas creadas" }
    }
}

