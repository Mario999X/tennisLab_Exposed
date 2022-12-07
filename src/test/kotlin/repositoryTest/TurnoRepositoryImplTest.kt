package repositoryTest

import config.ConfigProject
import database.DataBaseManager
import entities.TurnoDao
import entities.maquina.EncordadoraDao
import entities.maquina.PersonalizadoraDao
import entities.usuario.TrabajadorDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Turno
import models.usuario.Trabajador
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.turno.TurnoRepositoryImpl

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TurnoRepositoryImplTest {

    // LANZAR TEST DE 1 EN 1 !!
    @MockK
    lateinit var turnoDao: LongEntityClass<TurnoDao>

    @InjectMockKs
    lateinit var turnoRepository: TurnoRepositoryImpl

    private val trabajador = Trabajador(
        id = 5L,
        nombre = "Julian",
        apellido = "Estrada",
        email = "email2@email.com",
        password = "5678"
    )

    private val turno = Turno(
        id = 5L,
        horario = Turno.TipoHorario.TEMPRANO,
        trabajador = trabajador
    )

    private lateinit var daoItemTrabajador: TrabajadorDao
    private lateinit var daoItemTurno: TurnoDao

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
            daoItemTurno = TurnoDao.new(turno.id) {
                uuid = turno.uuid
                horario = turno.horario.horario
                encordadora = turno.encordadora?.let { EncordadoraDao.findById(it.id) }
                personalizadora = turno.personalizadora?.let { PersonalizadoraDao.findById(it.id) }
                trabajador = TrabajadorDao.findById(turno.trabajador.id)!!
            }
        }
    }

    @Test
    fun findAll() {
        every { turnoDao.all() } returns SizedCollection(listOf(daoItemTurno))
        val res = turnoRepository.findAll()
        assertAll(
            { assert(1 == res.size) },
            { assert(res[0].uuid == turno.uuid) }
        )
        verify { turnoDao.all() }
    }

    @Test
    fun findById() {
        every { turnoDao.findById(turno.id) } returns daoItemTurno
        val res = turnoRepository.findById(turno.id)
        assert(res?.uuid == turno.uuid)
        verify { turnoDao.findById(turno.id) }
    }

    @Test
    fun findByIdNotExists() {
        every { turnoDao.findById(turno.id) } returns null
        val res = turnoRepository.findById(turno.id)
        assert(res == null)
        verify { turnoDao.findById(turno.id) }
    }

    @Test
    fun save() {
        every { turnoDao.findById(turno.id) } returns daoItemTurno
        val res = turnoRepository.save(turno)
        assert(res.uuid == turno.uuid)
        verify { turnoDao.findById(turno.id) }
    }

    @Test
    fun delete() {
        every { turnoDao.findById(turno.id) } returns daoItemTurno
        val res = turnoRepository.delete(turno)
        assert(res)
        verify { turnoDao.findById(turno.id) }
    }

    @Test
    fun deleteNotExists() {
        every { turnoDao.findById(turno.id) } returns null
        val res = turnoRepository.delete(turno)
        assert(!res)
        verify { turnoDao.findById(turno.id) }
    }
}