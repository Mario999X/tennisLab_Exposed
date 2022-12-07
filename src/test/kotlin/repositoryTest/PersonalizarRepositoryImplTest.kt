package repositoryTest

import config.ConfigProject
import database.DataBaseManager
import entities.PersonalizarDao
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Personalizar
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import repositories.personalizar.PersonalizarRepositoryImpl

@ExtendWith(MockKExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PersonalizarRepositoryImplTest {

    @MockK
    lateinit var personalizarDao: LongEntityClass<PersonalizarDao>

    @InjectMockKs
    lateinit var personalizarRepository: PersonalizarRepositoryImpl

    private val personalizacion = Personalizar(
        id = 1L,
        peso = 1.2,
        balance = 1.3,
        rigidez = 2
    )

    private lateinit var daoItem: PersonalizarDao

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
            daoItem = PersonalizarDao.new(personalizacion.id) {
                uuid = personalizacion.uuid
                peso = personalizacion.peso
                balance = personalizacion.balance
                rigidez = personalizacion.rigidez
                precio = personalizacion.precio
            }
        }
    }

    @Test
    fun findAll() {
        every { personalizarDao.all() } returns SizedCollection(listOf(daoItem))
        val res = personalizarRepository.findAll()
        assertAll(
            { assert(1 == res.size) },
            { assert(res[0].uuid == personalizacion.uuid) }
        )
        verify { personalizarDao.all() }
    }

    @Test
    fun findById() {
        every { personalizarDao.findById(personalizacion.id) } returns daoItem
        val res = personalizarRepository.findById(personalizacion.id)
        assert(res?.uuid == personalizacion.uuid)
        verify { personalizarDao.findById(personalizacion.id) }
    }

    @Test
    fun findByIdNotExists() {
        every { personalizarDao.findById(personalizacion.id) } returns null
        val res = personalizarRepository.findById(personalizacion.id)
        assert(res == null)
        verify { personalizarDao.findById(personalizacion.id) }
    }

    @Test
    fun save() {
        every { personalizarDao.findById(personalizacion.id) } returns daoItem
        val res = personalizarRepository.save(personalizacion)
        assert(res.uuid == personalizacion.uuid)
        verify { personalizarDao.findById(personalizacion.id) }
    }

    @Test
    fun delete() {
        every { personalizarDao.findById(personalizacion.id) } returns daoItem
        val res = personalizarRepository.delete(personalizacion)
        assert(res)
        verify { personalizarDao.findById(personalizacion.id) }
    }

    @Test
    fun deleteNotExists() {
        every { personalizarDao.findById(personalizacion.id) } returns null
        val res = personalizarRepository.delete(personalizacion)
        assert(!res)
        verify { personalizarDao.findById(personalizacion.id) }
    }
}