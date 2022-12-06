package repositoryTest

import config.ConfigProject
import database.DataBaseManager
import entities.ProductoDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import models.Producto
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import repositories.producto.ProductoRepository

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ProductoRepositoryImplTest {

    @MockK
    lateinit var productoDao: LongEntityClass<ProductoDao>

    @InjectMockKs
    lateinit var productoRepository: ProductoRepository

    private val producto =
        Producto(id = 1L, tipo = Producto.Tipo.COMPLEMENTO, marca = "Wilson", modelo = "Pure", stock = 3, precio = 17.9)

    private lateinit var daoItem: ProductoDao

    init {
        MockKAnnotations.init(this)
    }

    @BeforeAll
    fun setUp() {
        DataBaseManager.init(ConfigProject.DEFAULT)
    }

    @AfterAll
    fun tearDown() {
        DataBaseManager.dropTablas()
    }

    @BeforeEach
    fun beforeEach() {
        DataBaseManager.clearTablas()
        transaction {
            daoItem = ProductoDao.new(producto.id) {
                uuid = producto.uuid
                tipo = producto.tipo.item
                marca = producto.marca
                modelo = producto.modelo
                stock = producto.stock
                precio = producto.precio
            }
        }
    }

    @Test
    fun findAll() {
        every { productoDao.all() }
    }

    @Test
    fun findById() {
    }

    @Test
    fun save() {
    }

    @Test
    fun delete() {
    }
}