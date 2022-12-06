package repositoryTest

import config.ConfigProject
import database.DataBaseManager
import entities.ProductoDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
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
import repositories.producto.ProductoRepositoryImpl

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ProductoRepositoryImplTest {

    @MockK
    lateinit var productoDao: LongEntityClass<ProductoDao>

    @InjectMockKs
    lateinit var productoRepository: ProductoRepositoryImpl

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
        every { productoDao.all() } returns SizedCollection(listOf(daoItem))
        val res = productoRepository.findAll()
        assertAll(
            { assert(1 == res.size) },
            { assert(res[0] == producto) }
        )
        verify { productoDao.all() }
    }

    @Test
    fun findById() {
        every { productoDao.findById(producto.id) } returns daoItem
        val res = productoRepository.findById(producto.id)
        assert(res == producto)
        verify { productoDao.findById(producto.id) }
    }

    @Test
    fun findByIdNoExiste() {
        every { productoDao.findById(producto.id) } returns null
        val res = productoRepository.findById(producto.id)
        assert(res == null)
        verify { productoDao.findById(producto.id) }
    }

    @Test
    fun save() {
        every { productoDao.findById(producto.id) } returns daoItem
        val res = productoRepository.save(producto)
        assert(res == producto)
        verify { productoDao.findById(producto.id) }
    }

    @Test
    fun delete() {
        every { productoDao.findById(producto.id) } returns daoItem
        val res = productoRepository.delete(producto)
        assert(res)
        verify { productoDao.findById(producto.id) }
    }

    @Test
    fun deleteNoExiste() {
        every { productoDao.findById(producto.id) } returns null
        val res = productoRepository.delete(producto)
        assert(!res)
        verify { productoDao.findById(producto.id) }
    }
}