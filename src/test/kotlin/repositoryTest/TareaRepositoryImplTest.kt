package repositoryTest

import config.ConfigProject
import database.*
import entities.*
import entities.usuario.ClienteDao
import entities.usuario.TrabajadorDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Pedido
import models.Tarea
import models.usuario.Cliente
import models.usuario.Trabajador
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.tarea.TareaRepositoryImpl
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TareaRepositoryImplTest {

    // Test no muy funcionales
    // Individualmente funcionan menos Delete y DeleteNotExists
    @MockK
    lateinit var tareaDao: LongEntityClass<TareaDao>

    @InjectMockKs
    lateinit var tareaRepository: TareaRepositoryImpl

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

    private val trabajador = Trabajador(
        id = 5L,
        nombre = "Camila",
        apellido = "Echeverri",
        email = "email3@email.com",
        password = "4321"
    )

    private val tarea = Tarea(
        id = 5L,
        precio = 10.0,
        pedido = pedido,
        trabajador = trabajador
    )

    private lateinit var daoItemCliente: ClienteDao
    private lateinit var daoItemPedido: PedidoDao
    private lateinit var daoItemTrabajador: TrabajadorDao
    private lateinit var daoItemTarea: TareaDao

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
        transaction {
            daoItemTrabajador = TrabajadorDao.new(trabajador.id) {
                uuid = trabajador.uuid
                nombre = trabajador.nombre
                apellido = trabajador.apellido
                email = trabajador.email
                password = trabajador.password
                perfil = trabajador.perfil.rol
            }
        }
        transaction {
            daoItemTarea = TareaDao.new(tarea.id) {
                uuid = tarea.uuid
                adquisicion = tarea.adquisicion?.let { AdquisicionDao.findById(it.id) }
                personalizar = tarea.personalizar?.let { PersonalizarDao.findById(it.id) }
                encordar = tarea.encordar?.let { EncordarDao.findById(it.id) }
                raqueta = tarea.raqueta?.let { RaquetaDao.findById(it.id) }
                precio = tarea.precio
                pedidoId = PedidoDao.findById(tarea.pedido.id)!!
                trabajadorId = TrabajadorDao.findById(tarea.trabajador.id)!!
            }
        }
    }

    @Test
    fun findAll() {
        every { tareaDao.all() } returns SizedCollection(listOf(daoItemTarea))
        val res = tareaRepository.findAll()
        assertAll(
            { assert(1 == res.size) },
            { assert(res[0].uuid == tarea.uuid) }
        )
        verify { tareaDao.all() }
    }

    @Test
    fun findById() {
        every { tareaDao.findById(tarea.id) } returns daoItemTarea
        val res = tareaRepository.findById(tarea.id)
        assert(res?.uuid == tarea.uuid)
        verify { tareaDao.findById(tarea.id) }
    }

    @Test
    fun findByIdNotExists() {
        every { tareaDao.findById(tarea.id) } returns null
        val res = tareaRepository.findById(tarea.id)
        assert(res == null)
        verify { tareaDao.findById(tarea.id) }
    }

    @Test
    fun save() {
        every { tareaDao.findById(tarea.id) } returns daoItemTarea
        val res = tareaRepository.save(tarea)
        assert(res.uuid == tarea.uuid)
        verify { tareaDao.findById(tarea.id) }
    }

    @Test
    fun delete() {
        every { tareaDao.findById(tarea.id) } returns daoItemTarea
        val res = tareaRepository.delete(tarea)
        assert(res)
        verify { tareaDao.findById(tarea.id) }
    }

    @Test
    fun deleteNotExists() {
        every { tareaDao.findById(tarea.id) } returns null
        val res = tareaRepository.delete(tarea)
        assert(!res)
        verify { tareaDao.findById(tarea.id) }
    }
}