package repositoryTest

import config.ConfigProject
import database.DataBaseManager
import entities.AdquisicionDao
import entities.ProductoDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Adquisicion
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
import repositories.adquisicion.AdquisicionRepositoryImpl

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdquisicionRepositoryImplTest {

    @MockK
    lateinit var adquisicionDao: LongEntityClass<AdquisicionDao>

    @InjectMockKs
    lateinit var adquisicionRepository: AdquisicionRepositoryImpl

    private val producto =
        Producto(id = 1L, tipo = Producto.Tipo.COMPLEMENTO, marca = "Wilson", modelo = "Pure", stock = 3, precio = 17.9)
    private val adquisicion =
        Adquisicion(id = 1L, cantidad = 2, producto = producto)

    private lateinit var daoItemAdquisicion: AdquisicionDao
    private lateinit var daoItemProducto: ProductoDao

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
            daoItemProducto = ProductoDao.new(producto.id) {
                uuid = producto.uuid
                tipo = producto.tipo.item
                marca = producto.marca
                modelo = producto.modelo
                stock = producto.stock
                precio = producto.precio
            }
        }
        transaction {
            daoItemAdquisicion = AdquisicionDao.new(adquisicion.id) {
                uuid = adquisicion.uuid
                producto = adquisicion.producto?.let { ProductoDao.findById(it.id) }
                descripcion = adquisicion.descripcion!!
                cantidad = adquisicion.cantidad
                precio = adquisicion.precio!!
            }
        }
    }

    @Test
    fun findAll() {
        every { adquisicionDao.all() } returns SizedCollection(listOf(daoItemAdquisicion))
        val res = adquisicionRepository.findAll()
        assertAll(
            { assert(1 == res.size) },
            { assert(res[0].uuid == adquisicion.uuid) }
        )
        verify { adquisicionDao.all() }
    }

    @Test
    fun findById() {
        every { adquisicionDao.findById(adquisicion.id) } returns daoItemAdquisicion
        val res = adquisicionRepository.findById(adquisicion.id)
        assert(res?.uuid == adquisicion.uuid)
        verify { adquisicionDao.findById(adquisicion.id) }
    }

    @Test
    fun findByIdNoExiste() {
        every { adquisicionDao.findById(adquisicion.id) } returns null
        val res = adquisicionRepository.findById(adquisicion.id)
        assert(res == null)
        verify { adquisicionDao.findById(adquisicion.id) }
    }

    @Test
    fun save() {
        every { adquisicionDao.findById(adquisicion.id) } returns daoItemAdquisicion
        val res = adquisicionRepository.save(adquisicion)
        assert(res.id == adquisicion.id)
        verify { adquisicionDao.findById(adquisicion.id) }
    }

    @Test
    fun delete() {
        every { adquisicionDao.findById(adquisicion.id) } returns daoItemAdquisicion
        val res = adquisicionRepository.delete(adquisicion)
        assert(res)
        verify { adquisicionDao.findById(adquisicion.id) }
    }

    @Test
    fun deleteNoExiste() {
        every { adquisicionDao.findById(adquisicion.id) } returns null
        val res = adquisicionRepository.delete(adquisicion)
        assert(!res)
        verify { adquisicionDao.findById(adquisicion.id) }
    }
}