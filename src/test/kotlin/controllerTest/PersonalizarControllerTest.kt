package controllerTest

import controllers.PersonalizarController
import exceptions.GenericException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Personalizar
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.personalizar.PersonalizarRepository

@ExtendWith(MockKExtension::class)
internal class PersonalizarControllerTest {

    @MockK
    lateinit var personalizarRepository: PersonalizarRepository

    @InjectMockKs
    lateinit var personalizarController: PersonalizarController

    private val personalizar = Personalizar(id = 1L, peso = 1.3, balance = 1.4, rigidez = 2)

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun getPersonalizaciones() {
        every { personalizarRepository.findAll() } returns listOf(personalizar)
        val res = personalizarController.getPersonalizaciones()
        assert(res == listOf(personalizar))
        verify(exactly = 1) { personalizarRepository.findAll() }
    }

    @Test
    fun getPersonalizadoById() {
        every { personalizarRepository.findById(personalizar.id) } returns personalizar
        val res = personalizarController.getPersonalizadoById(personalizar.id)
        assert(res == personalizar)
        verify(exactly = 1) { personalizarRepository.findById(personalizar.id) }
    }

    @Test
    fun getPersonalizadoByIdNoExiste() {
        every { personalizarRepository.findById(personalizar.id) } returns null
        val res = assertThrows<GenericException> { personalizarController.getPersonalizadoById(personalizar.id) }
        assert(res.message == "Personalizacion con id ${personalizar.id} no encontrada")
        verify(exactly = 1) { personalizarRepository.findById(personalizar.id) }
    }

    @Test
    fun updatePersonalizado() {
        every { personalizarRepository.save(personalizar) } returns personalizar
        personalizar?.let {
            it.peso += 0.5
            personalizarController.updatePersonalizado(it)
        }
        val res = personalizar.peso
        assert(res == 1.8)
        verify(exactly = 1) { personalizarRepository.save(personalizar) }
    }

    @Test
    fun deletePersonalizado() {
        every { personalizarRepository.delete(personalizar) } returns true
        val res = personalizarController.deletePersonalizado(personalizar)
        assertTrue(res)
        verify(exactly = 1) { personalizarRepository.delete(personalizar) }
    }

    @Test
    fun createPersonalizacion() {
        every { personalizarRepository.save(personalizar) } returns personalizar
        val res = personalizarController.createPersonalizacion(personalizar)
        assert(res == personalizar)
        verify(exactly = 1) { personalizarRepository.save(personalizar) }
    }
}