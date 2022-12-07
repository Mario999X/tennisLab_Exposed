package repositoryTest.usuario

import config.ConfigProject
import database.DataBaseManager
import entities.usuario.EncargadoDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.usuario.Encargado
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.usuario.EncargadoRepositoryImpl

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EncargadoRepositoryImplTest {

    @MockK
    lateinit var encargadoDao: LongEntityClass<EncargadoDao>

    @InjectMockKs
    lateinit var encargadoRepository: EncargadoRepositoryImpl

    private val encargado =
        Encargado(
            id = 1L,
            nombre = "Alberto",
            apellido = "Muñoz",
            email = "email@email.com",
            password = "1234"
        )

    private lateinit var daoItem: EncargadoDao

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
            daoItem = EncargadoDao.new(encargado.id) {
                uuid = encargado.uuid
                nombre = encargado.nombre
                apellido = encargado.apellido
                email = encargado.email
                password = encargado.password
                perfil = encargado.perfil.rol
            }
        }
    }

    @Test
    fun findAll() {
        every { encargadoDao.all() } returns SizedCollection(listOf(daoItem))
        val res = encargadoRepository.findAll()
        Assertions.assertAll(
            { assert(1 == res.size) },
            { assert(res[0].uuid == encargado.uuid) }
        )
        verify { encargadoDao.all() }
    }

    @Test
    fun findById() {
        every { encargadoDao.findById(encargado.id) } returns daoItem
        val res = encargadoRepository.findById(encargado.id)
        assert(res?.uuid == encargado.uuid)
        verify { encargadoDao.findById(encargado.id) }
    }

    @Test
    fun findByIdNotExists() {
        every { encargadoDao.findById(encargado.id) } returns null
        val res = encargadoRepository.findById(encargado.id)
        assert(res == null)
        verify { encargadoDao.findById(encargado.id) }
    }

    @Test
    fun save() {
        every { encargadoDao.findById(encargado.id) } returns daoItem
        val res = encargadoRepository.save(encargado)
        assert(res.uuid == encargado.uuid)
        verify { encargadoDao.findById(encargado.id) }
    }

    @Test
    fun delete() {
        every { encargadoDao.findById(encargado.id) } returns daoItem
        val res = encargadoRepository.delete(encargado)
        assert(res)
        verify { encargadoDao.findById(encargado.id) }
    }

    @Test
    fun deleteNotExists() {
        every { encargadoDao.findById(encargado.id) } returns null
        val res = encargadoRepository.delete(encargado)
        assert(!res)
        verify { encargadoDao.findById(encargado.id) }
    }
}