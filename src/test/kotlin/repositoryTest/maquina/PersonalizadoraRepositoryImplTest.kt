package repositoryTest.maquina

import config.ConfigProject
import database.DataBaseManager
import entities.maquina.PersonalizadoraDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.maquina.Personalizadora
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.personalizadora.PersonalizadoraRepositoryImpl
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PersonalizadoraRepositoryImplTest {

    @MockK
    lateinit var personalizadoraDao: LongEntityClass<PersonalizadoraDao>

    @InjectMockKs
    lateinit var personalizadoraRepository: PersonalizadoraRepositoryImpl

    private val personalizadora = Personalizadora(
        id = 5L,
        marca = "Toshiba",
        modelo = "ABC",
        fechaAdquisicion = LocalDate.now(),
        numSerie = 120L,
        maniobrabilidad = true,
        balance = false,
        rigidez = false
    )

    private lateinit var daoItem: PersonalizadoraDao

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
            daoItem = PersonalizadoraDao.new(personalizadora.id) {
                uuid = personalizadora.uuid
                marca = personalizadora.marca
                modelo = personalizadora.modelo
                fechaAdquisicion = personalizadora.fechaAdquisicion
                numSerie = personalizadora.numSerie
                maniobrabilidad = personalizadora.maniobrabilidad
                balance = personalizadora.balance
                rigidez = personalizadora.rigidez
            }
        }
    }

    @Test
    fun findAll() {
        every { personalizadoraDao.all() } returns SizedCollection(listOf(daoItem))
        val res = personalizadoraRepository.findAll()
        Assertions.assertAll(
            { assert(1 == res.size) },
            { assert(res[0].uuid == personalizadora.uuid) }
        )
        verify { personalizadoraDao.all() }
    }

    @Test
    fun findById() {
        every { personalizadoraDao.findById(personalizadora.id) } returns daoItem
        val res = personalizadoraDao.findById(personalizadora.id)
        assert(res?.uuid == personalizadora.uuid)
        verify { personalizadoraDao.findById(personalizadora.id) }
    }

    @Test
    fun findByIdNotExists() {
        every { personalizadoraDao.findById(personalizadora.id) } returns null
        val res = personalizadoraDao.findById(personalizadora.id)
        assert(res == null)
        verify { personalizadoraDao.findById(personalizadora.id) }
    }

    @Test
    fun save() {
        every { personalizadoraDao.findById(personalizadora.id) } returns daoItem
        val res = personalizadoraRepository.save(personalizadora)
        assert(res.uuid == personalizadora.uuid)
        verify { personalizadoraDao.findById(personalizadora.id) }
    }

    @Test
    fun delete() {
        every { personalizadoraDao.findById(personalizadora.id) } returns daoItem
        val res = personalizadoraRepository.delete(personalizadora)
        assert(res)
        verify { personalizadoraDao.findById(personalizadora.id)}
    }

    @Test
    fun deleteNotExists() {
        every { personalizadoraDao.findById(personalizadora.id) } returns null
        val res = personalizadoraRepository.delete(personalizadora)
        assert(!res)
        verify { personalizadoraDao.findById(personalizadora.id)}
    }
}