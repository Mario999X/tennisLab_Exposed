package controllerTest.usuario

import controllers.usuario.EncargadoController
import exceptions.GenericException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.usuario.Encargado
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.usuario.EncargadoRepository
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class EncargadoControllerTest {

    @MockK
    lateinit var encargadoRepository: EncargadoRepository

    @InjectMockKs
    lateinit var encargadoController: EncargadoController

    private val encargado = Encargado(
        5L, UUID.randomUUID(), "Mencia", "Gomez", "aa@gmail.com",
        "1234"
    )

    @Test
    fun getEncargados() {
        every { encargadoRepository.findAll() } returns listOf(encargado)

        val res = encargadoController.getEncargados()

        assert(res == listOf(encargado))

        verify(exactly = 1) { encargadoRepository.findAll() }
    }

    @Test
    fun getEncargadoById() {
        every { encargadoRepository.findById(encargado.id) } returns encargado

        val res = encargadoController.getEncargadoById(encargado.id)

        assert(res == encargado)

        verify { encargadoRepository.findById(encargado.id) }
    }

    @Test
    fun getEncargadoByIdNotExists() {
        every { encargadoRepository.findById(encargado.id) } returns null

        val res = assertThrows<GenericException> { encargadoController.getEncargadoById(encargado.id)}

        assert(res.message == "Encargado con id ${encargado.id} no encontrado")

        verify { encargadoRepository.findById(encargado.id) }
    }

    @Test
    fun updateEncargado() {
        every { encargadoRepository.save(encargado) } returns encargado

        encargado.let {
            encargado.nombre = "Pepe"
            encargadoController.updateEncargado(it)
        }

        val res = encargado.nombre

        assert(res == encargado.nombre)

        verify { encargadoRepository.save(encargado) }
    }

    @Test
    fun deleteEncargado() {
        every { encargadoRepository.delete(encargado) } returns true

        val res = encargadoController.deleteEncargado(encargado)

        assert(res)

        verify { encargadoRepository.delete(encargado) }
    }

    @Test
    fun deleteEncargadoNotExists() {
        every { encargadoRepository.delete(encargado) } returns false

        val res = assertThrows<GenericException> { encargadoController.deleteEncargado(encargado) }

        assert(res.message == "Encargado con id ${encargado.id} no encontrado")

        verify { encargadoRepository.delete(encargado) }
    }

    @Test
    fun createEncargado() {
        every { encargadoRepository.save(encargado) } returns encargado

        val res = encargadoController.createEncargado(encargado)

        assert(res == encargado)

        verify { encargadoRepository.save(encargado) }
    }
}