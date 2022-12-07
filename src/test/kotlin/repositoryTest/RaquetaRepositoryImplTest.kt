package repositoryTest

import config.ConfigProject
import database.DataBaseManager
import entities.RaquetaDao
import entities.usuario.ClienteDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Raqueta
import models.usuario.Cliente
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.raqueta.RaquetaRepositoryImpl

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RaquetaRepositoryImplTest {

    @MockK
    lateinit var raquetaDao: LongEntityClass<RaquetaDao>

    @InjectMockKs
    lateinit var raquetaRepository: RaquetaRepositoryImpl

    private val cliente = Cliente(
        id = 5L,
        nombre = "Sandra",
        apellido = "Ortega",
        email = "email3@email.com",
        password = "4321"
    )

    private val raqueta = Raqueta(
        id = 5L,
        marca = "Wilson",
        modelo = "Pure",
        cliente = cliente
    )

    private lateinit var daoItemCliente: ClienteDao
    private lateinit var daoItemRaqueta: RaquetaDao

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
            daoItemCliente = ClienteDao.new(cliente.id) {
                uuid = cliente.uuid
                nombre = cliente.nombre
                apellido = cliente.apellido
                email = cliente.email
                password = cliente.password
                perfil = cliente.perfil.rol
            }
        }
        transaction {
            daoItemRaqueta = RaquetaDao.new(raqueta.id) {
                uuid = raqueta.uuid
                marca = raqueta.marca
                modelo = raqueta.modelo
                cliente = ClienteDao.findById(raqueta.cliente.id)!!
            }
        }
    }

    @Test
    fun findAll() {
        every { raquetaDao.all() } returns SizedCollection(listOf(daoItemRaqueta))
        val res = raquetaRepository.findAll()
        assertAll(
            { assert(1 == res.size) },
            { assert(res[0].uuid == raqueta.uuid) }
        )
        verify { raquetaDao.all() }
    }

    @Test
    fun findById() {
        every { raquetaDao.findById(raqueta.id) } returns daoItemRaqueta
        val res = raquetaRepository.findById(raqueta.id)
        assert(res?.uuid == raqueta.uuid)
        verify { raquetaDao.findById(raqueta.id) }
    }

    @Test
    fun findByIdNotExists() {
        every { raquetaDao.findById(raqueta.id) } returns null
        val res = raquetaRepository.findById(raqueta.id)
        assert(res == null)
        verify { raquetaDao.findById(raqueta.id) }
    }

    @Test
    fun save() {
        every { raquetaDao.findById(raqueta.id) } returns daoItemRaqueta
        val res = raquetaRepository.save(raqueta)
        assert(res.uuid == raqueta.uuid)
        verify { raquetaDao.findById(raqueta.id) }
    }

    @Test
    fun delete() {
        every { raquetaDao.findById(raqueta.id) } returns daoItemRaqueta
        val res = raquetaRepository.delete(raqueta)
        assert(res)
        verify { raquetaDao.findById(raqueta.id) }
    }

    @Test
    fun deleteNotExists() {
        every { raquetaDao.findById(raqueta.id) } returns null
        val res = raquetaRepository.delete(raqueta)
        assert(!res)
        verify { raquetaDao.findById(raqueta.id) }
    }
}