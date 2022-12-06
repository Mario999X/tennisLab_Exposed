package repositoryTest.maquina

import config.ConfigProject
import database.DataBaseManager
import entities.maquina.EncordadoraDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.maquina.Encordadora
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.encordadora.EncordadoraRepositoryImpl
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class EncordadoraRepositoryImplTest {

    @MockK
    lateinit var encordadoraDao: LongEntityClass<EncordadoraDao>

    @InjectMockKs
    lateinit var encordadoraRepository: EncordadoraRepositoryImpl

    private val encordadora = Encordadora(
        id = 5L,
        marca = "Toshiba",
        modelo = "ABC",
        fechaAdquisicion = LocalDate.now(),
        numSerie = 120L,
        isManual = true,
        tensionMax = 23.2,
        tensionMin = 20.5
    )

    private lateinit var daoItem: EncordadoraDao

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
            daoItem = EncordadoraDao.new(encordadora.id) {
                uuid = encordadora.uuid
                marca = encordadora.marca
                modelo = encordadora.modelo
                fechaAdquisicion = encordadora.fechaAdquisicion
                numSerie = encordadora.numSerie
                isManual = encordadora.isManual
                tensionMax = encordadora.tensionMax
                tensionMin = encordadora.tensionMin
            }
        }
    }

    @Test
    fun findAll() {
        every { encordadoraDao.all() } returns SizedCollection(listOf(daoItem))
        val res = encordadoraRepository.findAll()
        Assertions.assertAll(
            { assert(1 == res.size) },
            { assert(res[0].uuid == encordadora.uuid) }
        )
        verify { encordadoraDao.all() }
    }

    @Test
    fun findById() {
        every { encordadoraDao.findById(encordadora.id) } returns daoItem
        val res = encordadoraDao.findById(encordadora.id)
        assert(res?.uuid == encordadora.uuid)
        verify { encordadoraDao.findById(encordadora.id) }
    }

    @Test
    fun findByIdNotExists() {
        every { encordadoraDao.findById(encordadora.id) } returns null
        val res = encordadoraDao.findById(encordadora.id)
        assert(res == null)
        verify { encordadoraDao.findById(encordadora.id) }
    }

    @Test
    fun save() {
        every { encordadoraDao.findById(encordadora.id) } returns daoItem
        val res = encordadoraRepository.save(encordadora)
        assert(res.uuid == encordadora.uuid)
        verify { encordadoraDao.findById(encordadora.id) }
    }

    @Test
    fun delete() {
        every { encordadoraDao.findById(encordadora.id) } returns daoItem
        val res = encordadoraRepository.delete(encordadora)
        assert(res)
        verify { encordadoraDao.findById(encordadora.id) }
    }

    @Test
    fun deleteNotExists() {
        every { encordadoraDao.findById(encordadora.id) } returns null
        val res = encordadoraRepository.delete(encordadora)
        assert(!res)
        verify { encordadoraDao.findById(encordadora.id) }
    }
}