package controllerTest

import controllers.EncordarController
import exceptions.GenericException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Encordar
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.encordar.EncordarRepository

@ExtendWith(MockKExtension::class)
internal class EncordarControllerTest {

    @MockK
    lateinit var encordarRepository: EncordarRepository

    @InjectMockKs
    lateinit var encordarController: EncordarController

    private val encordado = Encordar(
        id = 1L,
        tensionCuerdasHorizontales = 2.3,
        cordajeHorizontal = "Dato1",
        tensionCuerdasVerticales = 1.2,
        cordajeVertical = "Dato2",
        nudos = 2
    )

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun getEncordados() {
        every { encordarRepository.findAll() } returns listOf(encordado)
        val res = encordarController.getEncordados()
        assert(res == listOf(encordado))
        verify(exactly = 1) { encordarRepository.findAll() }
    }

    @Test
    fun getEncordadoById() {
        every { encordarRepository.findById(encordado.id) } returns encordado
        val res = encordarController.getEncordadoById(encordado.id)
        assert(res == encordado)
        verify(exactly = 1) { encordarRepository.findById(encordado.id) }
    }

    @Test
    fun getEncordadoByIdNoExiste() {
        every { encordarRepository.findById(encordado.id) } returns null
        val res = assertThrows<GenericException> { encordarController.getEncordadoById(encordado.id) }
        assert(res.message == "Encordado con id ${encordado.id} no encontrado")
        verify(exactly = 1) { encordarRepository.findById(encordado.id) }
    }

    @Test
    fun updateEncordado() {
        every { encordarRepository.save(encordado) } returns encordado
        encordado?.let {
            it.nudos += 2
            encordarController.updateEncordado(it)
        }
        val res = encordado.nudos
        assert(res == 4)
        verify(exactly = 1) { encordarRepository.save(encordado) }
    }

    @Test
    fun deleteEncordado() {
        every { encordarRepository.delete(encordado) } returns true
        val res = encordarController.deleteEncordado(encordado)
        assertTrue(res)
        verify(exactly = 1) { encordarRepository.delete(encordado) }
    }

    @Test
    fun createEncordado() {
        every { encordarRepository.save(encordado) } returns encordado
        val res = encordarController.createEncordado(encordado)
        assert(res == encordado)
        verify(exactly = 1) { encordarRepository.save(encordado) }
    }
}