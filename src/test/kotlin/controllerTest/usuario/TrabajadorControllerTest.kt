package controllerTest.usuario

import controllers.usuario.TrabajadorController
import exceptions.GenericException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.usuario.Trabajador
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.usuario.TrabajadorRepository

@ExtendWith(MockKExtension::class)
internal class TrabajadorControllerTest {

    @MockK
    lateinit var trabajadorRepository: TrabajadorRepository

    @InjectMockKs
    lateinit var trabajadorController: TrabajadorController

    private val trabajador = Trabajador(
        id = 5L,
        nombre = "BBBBB",
        apellido = "Garcia",
        email = "email@email.com",
        password = "1234"
    )

    @Test
    fun getTrabajadores() {
        every { trabajadorRepository.findAll() } returns listOf(trabajador)

        val res = trabajadorController.getTrabajadores()

        assert(res == listOf(trabajador))

        verify(exactly = 1) { trabajadorRepository.findAll() }
    }

    @Test
    fun getTrabajadorById() {
        every { trabajadorRepository.findById(trabajador.id) } returns trabajador

        val res = trabajadorController.getTrabajadorById(trabajador.id)

        assert(res == trabajador)

        verify(exactly = 1) { trabajadorRepository.findById(trabajador.id) }
    }

    @Test
    fun getTrabajadorByIdNotExists() {
        every { trabajadorRepository.findById(trabajador.id) } returns null

        val res = assertThrows<GenericException> { trabajadorController.getTrabajadorById(trabajador.id) }

        assert(res.message == "Trabajador con id ${trabajador.id} no encontrado")

        verify(exactly = 1) { trabajadorRepository.findById(trabajador.id) }
    }

    @Test
    fun updateTrabajador() {
        every { trabajadorRepository.save(trabajador) } returns trabajador

        trabajador.let {
            trabajador.nombre = "GameFreak"
            trabajadorController.updateTrabajador(it)
        }

        val res = trabajador.nombre

        assert(res == "GameFreak")

        verify(exactly = 1) { trabajadorRepository.save(trabajador) }
    }

    @Test
    fun deleteTrabajador() {
        every { trabajadorRepository.delete(trabajador) } returns true

        val res = trabajadorController.deleteTrabajador(trabajador)

        assert(res)

        verify(exactly = 1) { trabajadorRepository.delete(trabajador) }
    }

    @Test
    fun deleteTrabajadorNotExists() {
        every { trabajadorRepository.delete(trabajador) } returns false

        val res = assertThrows<GenericException> {trabajadorController.deleteTrabajador(trabajador)}

        assert(res.message == "Trabajador con id ${trabajador.id} no encontrado")

        verify(exactly = 1) { trabajadorRepository.delete(trabajador) }
    }

    @Test
    fun createTrabajador() {
        every { trabajadorRepository.save(trabajador) } returns trabajador

        val res = trabajadorController.createTrabajador(trabajador)

        assert(res == trabajador)

        verify(exactly = 1) { trabajadorRepository.save(trabajador) }
    }
}