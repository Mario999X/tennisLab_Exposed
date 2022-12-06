package controllerTest

import controllers.RaquetaController
import exceptions.GenericException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Raqueta
import models.usuario.Cliente
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.raqueta.RaquetaRepository

@ExtendWith(MockKExtension::class)
internal class RaquetaControllerTest {

    @MockK
    lateinit var raquetaRepository: RaquetaRepository

    @InjectMockKs
    lateinit var raquetaController: RaquetaController

    private val raqueta = Raqueta(
        id = 1L,
        marca = "Wilson",
        modelo = "Pure",
        cliente = Cliente(
            id = 5L,
            nombre = "Sandra",
            apellido = "Ortega",
            email = "email3@email.com",
            password = "4321"
        )
    )

    @Test
    fun getRaquetas() {
        every { raquetaRepository.findAll() } returns listOf(raqueta)

        val res = raquetaController.getRaquetas()

        assert(res == listOf(raqueta))

        verify(exactly = 1) { raquetaRepository.findAll() }
    }

    @Test
    fun getRaquetaById() {
        every { raquetaRepository.findById(raqueta.id) } returns raqueta

        val res = raquetaController.getRaquetaById(raqueta.id)

        assert(res == raqueta)

        verify(exactly = 1) { raquetaRepository.findById(raqueta.id) }
    }

    @Test
    fun getRaquetaByIdNotExists() {
        every { raquetaRepository.findById(raqueta.id) } returns null

        val res = assertThrows<GenericException> { raquetaController.getRaquetaById(raqueta.id) }

        assert(res.message == "Raqueta con id ${raqueta.id} no encontrada")

        verify(exactly = 1) { raquetaRepository.findById(raqueta.id) }
    }

    @Test
    fun updateRaqueta() {
        every { raquetaRepository.save(raqueta) } returns raqueta

        raqueta.let {
            raqueta.marca = "LOL"
            raquetaController.updateRaqueta(it)
        }

        val res = raqueta.marca

        assert(res == "LOL")

        verify(exactly = 1) { raquetaRepository.save(raqueta) }
    }

    @Test
    fun deleteRaqueta() {
        every { raquetaRepository.delete(raqueta) } returns true

        val res = raquetaController.deleteRaqueta(raqueta)

        assert(res)

        verify(exactly = 1) { raquetaRepository.delete(raqueta) }
    }

    @Test
    fun deleteRaquetaNotExists() {
        every { raquetaRepository.delete(raqueta) } returns false

        val res = assertThrows<GenericException> { raquetaController.deleteRaqueta(raqueta) }

        assert(res.message == "Raqueta con id ${raqueta.id} no encontrada")

        verify(exactly = 1) { raquetaRepository.delete(raqueta) }
    }

    @Test
    fun createRaqueta() {
        every { raquetaRepository.save(raqueta) } returns raqueta

        val res = raquetaController.createRaqueta(raqueta)

        assert(res == raqueta)

        verify(exactly = 1) { raquetaRepository.save(raqueta) }
    }
}