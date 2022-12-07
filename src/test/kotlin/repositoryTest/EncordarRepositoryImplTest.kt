package repositoryTest

import config.ConfigProject
import database.DataBaseManager
import entities.EncordarDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Encordar
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.encordar.EncordarRepositoryImpl

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EncordarRepositoryImplTest {

    @MockK
    lateinit var encordadoDao: LongEntityClass<EncordarDao>

    @InjectMockKs
    lateinit var encordadoRepository: EncordarRepositoryImpl

    private val encordado = Encordar(
        id = 5L,
        tensionCuerdasHorizontales = 2.0,
        cordajeHorizontal = "Dato 1",
        tensionCuerdasVerticales = 3.2,
        cordajeVertical = "Dato 2",
        nudos = 2
    )

    private lateinit var daoItem: EncordarDao

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
            daoItem = EncordarDao.new(encordado.id) {
                uuid = encordado.uuid
                tenCuHori = encordado.tensionCuerdasHorizontales
                cordajeHorizontal = encordado.cordajeHorizontal
                tenCuVerti = encordado.tensionCuerdasVerticales
                cordajeVertical = encordado.cordajeVertical
                nudos = encordado.nudos
                precio = encordado.precio
            }
        }
    }

    @Test
    fun findAll() {
        every { encordadoDao.all() } returns SizedCollection(listOf(daoItem))
        val res = encordadoRepository.findAll()
        assertAll(
            { assert(1 == res.size) },
            { assert(res[0].uuid == encordado.uuid) }
        )
        verify { encordadoDao.all() }
    }

    @Test
    fun findById() {
        every { encordadoDao.findById(encordado.id) } returns daoItem
        val res = encordadoRepository.findById(encordado.id)
        assert(res?.uuid == encordado.uuid)
        verify { encordadoDao.findById(encordado.id) }
    }

    @Test
    fun findByIdNotExists() {
        every { encordadoDao.findById(encordado.id) } returns null
        val res = encordadoRepository.findById(encordado.id)
        assert(res == null)
        verify { encordadoDao.findById(encordado.id) }
    }

    @Test
    fun save() {
        every { encordadoDao.findById(encordado.id) } returns daoItem
        val res = encordadoRepository.save(encordado)
        assert(res.uuid == encordado.uuid)
        verify { encordadoDao.findById(encordado.id) }
    }

    @Test
    fun delete() {
        every { encordadoDao.findById(encordado.id) } returns daoItem
        val res = encordadoRepository.delete(encordado)
        assert(res)
        verify { encordadoDao.findById(encordado.id) }
    }

    @Test
    fun deleteNotExists() {
        every { encordadoDao.findById(encordado.id) } returns null
        val res = encordadoRepository.delete(encordado)
        assert(!res)
        verify { encordadoDao.findById(encordado.id) }
    }
}