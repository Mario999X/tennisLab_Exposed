package repositoryTest

import config.ConfigProject
import database.DataBaseManager
import entities.PedidoDao
import entities.usuario.ClienteDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Pedido
import models.usuario.Cliente
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.pedido.PedidoRepositoryImpl
import java.time.LocalDate
import java.util.*

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PedidoRepositoryImplTest {

    // LANZAR TEST DE 1 EN 1 !!
    @MockK
    lateinit var pedidoDao: LongEntityClass<PedidoDao>

    @InjectMockKs
    lateinit var pedidoRepository: PedidoRepositoryImpl

    private val cliente = Cliente(
        id = 5L,
        nombre = "Sandra",
        apellido = "Ortega",
        email = "email3@email.com",
        password = "4321"
    )

    private val pedido = Pedido(
        id = 5L,
        estado = Pedido.TipoEstado.RECIBIDO,
        fechaEntrada = LocalDate.now(),
        fechaProgramada = LocalDate.now().plusDays(1),
        fechaSalida = LocalDate.now().plusDays(2),
        cliente = cliente
    )

    private lateinit var daoItemPedido: PedidoDao
    private lateinit var daoItemCliente: ClienteDao

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
            daoItemPedido = PedidoDao.new(pedido.id) {
                uuid = pedido.uuid
                estado = pedido.estado.estado
                fechaEntrada = pedido.fechaEntrada
                fechaProgramada = pedido.fechaProgramada
                fechaSalida = pedido.fechaSalida
                cliente = ClienteDao.findById(pedido.cliente.id)!!
            }
        }
    }

    @Test
    fun findAll() {
        every { pedidoDao.all() } returns SizedCollection(listOf(daoItemPedido))
        val res = pedidoRepository.findAll()
        assertAll(
            { assert(1 == res.size) },
            { assert(res[0].uuid == pedido.uuid) }
        )
        verify { pedidoDao.all() }
    }

    @Test
    fun findById() {
        every { pedidoDao.findById(pedido.id) } returns daoItemPedido
        val res = pedidoRepository.findById(pedido.id)
        assert(res?.uuid == pedido.uuid)
        verify { pedidoDao.findById(pedido.id) }
    }

    @Test
    fun findByIdNotExists() {
        every { pedidoDao.findById(pedido.id) } returns null
        val res = pedidoRepository.findById(pedido.id)
        assert(res == null)
        verify { pedidoDao.findById(pedido.id) }
    }

    @Test
    fun save() {
        every { pedidoDao.findById(pedido.id) } returns daoItemPedido
        val res = pedidoRepository.save(pedido)
        assert(res.uuid == pedido.uuid)
        verify { pedidoDao.findById(pedido.id) }
    }

    @Test
    fun delete() {
        every { pedidoDao.findById(pedido.id) } returns daoItemPedido
        val res = pedidoRepository.delete(pedido)
        assert(res)
        verify { pedidoDao.findById(pedido.id) }
    }

    @Test
    fun deleteNotExists() {
        every { pedidoDao.findById(pedido.id) } returns null
        val res = pedidoRepository.delete(pedido)
        assert(!res)
        verify { pedidoDao.findById(pedido.id) }
    }
}
