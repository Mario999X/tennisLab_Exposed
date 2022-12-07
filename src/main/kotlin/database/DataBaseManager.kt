package database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import config.ConfigProject
import entities.*
import entities.maquina.EncordadoraTable
import entities.maquina.PersonalizadoraTable
import entities.usuario.ClienteTable
import entities.usuario.EncargadoTable
import entities.usuario.TrabajadorTable
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

/**
 * DataBaseManager, clase objeto que maneja la BBDD
 */
object DataBaseManager {
    lateinit var config: ConfigProject

    /**
     * Conecta la BBDD
     *
     * @param config
     */
    fun init(config: ConfigProject) {
        this.config = config
        log.debug { "Inicializando la base de datos" }
        val hikariConfig = HikariConfig().apply {
            jdbcUrl = config.jdbcUrl
            driverClassName = config.jdbcDriver
            maximumPoolSize = config.jdbcPool
        }
        val dataSource = HikariDataSource(hikariConfig)
        Database.connect(dataSource)
        log.debug { "Base de datos inicializada exitosamente" }
        if (config.jdbcTablas) {
            crearTablas()
        }
    }

    /**
     * crearTablas(), inicia las tablas
     *
     */
    private fun crearTablas() = transaction {
        log.debug { "Creando las tablas" }
        if (config.jdbcSQL)
            addLogger(StdOutSqlLogger)
        SchemaUtils.create(
            RaquetaTable,
            ClienteTable,
            ProductoTable,
            AdquisicionTable,
            PersonalizarTable,
            EncordarTable,
            TareaTable,
            TrabajadorTable,
            EncargadoTable,
            EncordadoraTable,
            PersonalizadoraTable,
            TurnoTable,
            PedidoTable
        )
        log.debug { "Tablas creadas" }
    }

    /**
     * DropTablas(), borra las tablas
     *
     */
    fun dropTablas() = transaction {
        log.debug { "Eliminando las tablas" }
        if (config.jdbcSQL)
            addLogger(StdOutSqlLogger)
        SchemaUtils.drop(
            RaquetaTable,
            ClienteTable,
            ProductoTable,
            AdquisicionTable,
            PersonalizarTable,
            EncordarTable,
            TareaTable,
            TrabajadorTable,
            EncargadoTable,
            EncordadoraTable,
            PersonalizadoraTable,
            TurnoTable,
            PedidoTable
        )
        log.debug { "Tablas eliminadas" }
    }

    /**
     * ClearTablas(), limpia las tablas
     *
     */
    fun clearTablas() = transaction {
        log.debug { "Limpiando tablas" }
        if (config.jdbcSQL)
            addLogger(StdOutSqlLogger)
        val tablas = arrayOf(
            RaquetaTable,
            ClienteTable,
            ProductoTable,
            AdquisicionTable,
            PersonalizarTable,
            EncordarTable,
            TareaTable,
            TrabajadorTable,
            EncargadoTable,
            EncordadoraTable,
            PersonalizadoraTable,
            TurnoTable,
            PedidoTable
        )

        tablas.forEach {
            it.deleteAll()
        }
        log.debug { "Tablas limpias" }
    }

}

