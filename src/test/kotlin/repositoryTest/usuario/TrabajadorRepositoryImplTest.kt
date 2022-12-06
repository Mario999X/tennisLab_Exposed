package repositoryTest.usuario

import config.ConfigProject
import database.DataBaseManager
import entities.usuario.TrabajadorDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.usuario.Trabajador
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.usuario.TrabajadorRepositoryImpl

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TrabajadorRepositoryImplTest {

    @MockK
    lateinit var trabajadorDao: LongEntityClass<TrabajadorDao>

    @InjectMockKs
    lateinit var trabajadorRepository: TrabajadorRepositoryImpl

    private val trabajador = Trabajador(
        id = 5L,
        nombre = "Adriano",
        apellido = "Garcia",
        email = "email@email.com",
        password = "1234"
    )

    private lateinit var daoItem: TrabajadorDao

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
            daoItem = TrabajadorDao.new(trabajador.id) {
               uuid = trabajador.uuid
               nombre = trabajador.nombre
               apellido = trabajador.apellido
               email = trabajador.email
               password = trabajador.password
               perfil = trabajador.perfil.rol
            }
        }
    }

    @Test
    fun findAll() {
        every { trabajadorDao.all() } returns SizedCollection(listOf(daoItem))
        val res = trabajadorRepository.findAll()
        Assertions.assertAll(
            { assert(1 == res.size)},
            { assert(res[0].uuid == trabajador.uuid) }
        )
        verify { trabajadorDao.all() }
    }

    @Test
    fun findById() {
        every { trabajadorDao.findById(trabajador.id) } returns daoItem
        val res = trabajadorRepository.findById(trabajador.id)
        assert(res?.uuid == trabajador.uuid)
        verify { trabajadorDao.findById(trabajador.id) }
    }

    @Test
    fun findByIdNotExists() {
        every { trabajadorDao.findById(trabajador.id) } returns null
        val res = trabajadorRepository.findById(trabajador.id)
        assert(res == null)
        verify { trabajadorDao.findById(trabajador.id) }
    }

    @Test
    fun save() {
        every { trabajadorDao.findById(trabajador.id) } returns daoItem
        val res = trabajadorRepository.save(trabajador)
        assert(res.uuid == trabajador.uuid)
        verify { trabajadorDao.findById(trabajador.id)}
    }

    @Test
    fun delete() {
        every { trabajadorDao.findById(trabajador.id) } returns daoItem
        val res = trabajadorRepository.delete(trabajador)
        assert(res)
        verify { trabajadorDao.findById(trabajador.id) }
    }

    @Test
    fun deleteNotExists() {
        every { trabajadorDao.findById(trabajador.id) } returns null
        val res = trabajadorRepository.delete(trabajador)
        assert(!res)
        verify { trabajadorDao.findById(trabajador.id) }
    }
}