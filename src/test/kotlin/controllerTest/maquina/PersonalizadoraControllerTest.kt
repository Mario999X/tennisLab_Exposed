package controllerTest.maquina

import controllers.maquina.PersonalizadoraController
import exceptions.GenericException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.maquina.Personalizadora
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.personalizadora.PersonalizadoraRepository
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
internal class PersonalizadoraControllerTest {

    @MockK
    lateinit var personalizadoraRepository: PersonalizadoraRepository

    @InjectMockKs
    lateinit var personalizadoraController: PersonalizadoraController

    private val personalizadora = Personalizadora(
        id = 5L,
        marca = "CBA",
        modelo = "ABC",
        fechaAdquisicion = LocalDate.now(),
        numSerie = 120L,
        maniobrabilidad = true,
        balance = false,
        rigidez = false
    )

    @Test
    fun getPersonalizadoras() {
        every { personalizadoraRepository.findAll() } returns listOf(personalizadora)

        val res = personalizadoraController.getPersonalizadoras()

        assert(res == listOf(personalizadora))

        verify(exactly = 1) { personalizadoraRepository.findAll() }
    }

    @Test
    fun getPersonalizadoraById() {
        every { personalizadoraRepository.findById(personalizadora.id) } returns personalizadora

        val res = personalizadoraController.getPersonalizadoraById(personalizadora.id)

        assert(res == personalizadora)

        verify(exactly = 1) { personalizadoraRepository.findById(personalizadora.id) }
    }

    @Test
    fun getPersonalizadoraByIdNotExists() {
        every { personalizadoraRepository.findById(personalizadora.id) } returns null

        val res =
            assertThrows<GenericException> { personalizadoraController.getPersonalizadoraById(personalizadora.id) }

        assert(res.message == "Personalizadora con id ${personalizadora.id} no encontrada")

        verify(exactly = 1) { personalizadoraRepository.findById(personalizadora.id) }
    }

    @Test
    fun updatePersonalizadora() {
        every { personalizadoraRepository.save(personalizadora) } returns personalizadora

        personalizadora.let {
            personalizadora.balance = false
            personalizadoraController.updatePersonalizadora(it)
        }

        val res = personalizadora.balance

        assert(!res)

        verify(exactly = 1) { personalizadoraRepository.save(personalizadora) }
    }

    @Test
    fun deletePersonalizadora() {
        every { personalizadoraRepository.delete(personalizadora) } returns true

        val res = personalizadoraController.deletePersonalizadora(personalizadora)

        assert(res)

        verify(exactly = 1) { personalizadoraRepository.delete(personalizadora) }
    }

    @Test
    fun deletePersonalizadoraNotExists() {
        every { personalizadoraRepository.delete(personalizadora) } returns false

        val res = assertThrows<GenericException> { personalizadoraController.deletePersonalizadora(personalizadora) }

        assert(res.message == "Personalizadora con id ${personalizadora.id} no encontrada")

        verify(exactly = 1) { personalizadoraRepository.delete(personalizadora) }
    }

    @Test
    fun createPersonalizadora() {
        every { personalizadoraRepository.save(personalizadora) } returns personalizadora

        val res = personalizadoraController.createPersonalizadora(personalizadora)

        assert(res == personalizadora)

        verify(exactly = 1) { personalizadoraRepository.save(personalizadora) }
    }
}