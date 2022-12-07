package repositoryTest.usuario

import config.ConfigProject
import database.DataBaseManager
import entities.usuario.ClienteDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.usuario.Cliente
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
import repositories.usuario.ClienteRepositoryImpl

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ClienteRepositoryImplTest {

    @MockK
    lateinit var clienteDao: LongEntityClass<ClienteDao>

    @InjectMockKs
    lateinit var clienteRepository: ClienteRepositoryImpl

    private val cliente = Cliente(
        id = 5L,
        nombre = "Mario",
        apellido = "Resa",
        email = "email2@email.com",
        password = "5678"
    )

    private lateinit var daoItem: ClienteDao

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
            daoItem = ClienteDao.new(cliente.id) {
                uuid = cliente.uuid
                nombre = cliente.nombre
                apellido = cliente.apellido
                email = cliente.email
                password = cliente.password
                perfil = cliente.perfil.rol
            }
        }
    }

    @Test
    fun findAll() {
        every { clienteDao.all() } returns SizedCollection(listOf(daoItem))
        val res = clienteRepository.findAll()
        assertAll(
            { assert(1 == res.size) },
            { assert(res[0].uuid == cliente.uuid) }
        )
        verify { clienteDao.all() }
    }

    @Test
    fun findById() {
        every { clienteDao.findById(cliente.id) } returns daoItem
        val res = clienteRepository.findById(cliente.id)
        assert(res?.uuid == cliente.uuid)
        verify { clienteDao.findById(cliente.id) }
    }

    @Test
    fun findByIdNotExists() {
        every { clienteDao.findById(cliente.id) } returns null
        val res = clienteRepository.findById(cliente.id)
        assert(res == null)
        verify { clienteDao.findById(cliente.id) }
    }

    @Test
    fun save() {
        every { clienteDao.findById(cliente.id) } returns daoItem
        val res = clienteRepository.save(cliente)
        assert(res.uuid == cliente.uuid)
        verify { clienteDao.findById(cliente.id) }
    }

    @Test
    fun delete() {
        every { clienteDao.findById(cliente.id) } returns daoItem
        val res = clienteRepository.delete(cliente)
        assert(res)
        verify { clienteDao.findById(cliente.id) }
    }

    @Test
    fun deleteNotExists() {
        every { clienteDao.findById(cliente.id) } returns null
        val res = clienteRepository.delete(cliente)
        assert(!res)
        verify { clienteDao.findById(cliente.id) }
    }
}