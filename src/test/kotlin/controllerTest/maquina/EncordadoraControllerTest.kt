package controllerTest.maquina

import controllers.maquina.EncordadoraController
import exceptions.GenericException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.maquina.Encordadora
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.encordadora.EncordadoraRepository
import java.time.LocalDate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class EncordadoraControllerTest {

    @MockK
    lateinit var encordadoraRepository: EncordadoraRepository

    @InjectMockKs
    lateinit var encordadoraController: EncordadoraController

    private val encordadora = Encordadora(
        id = 5L,
        marca = "CBA",
        modelo = "ABC",
        fechaAdquisicion = LocalDate.now(),
        numSerie = 120L,
        isManual = true,
        tensionMax = 23.2,
        tensionMin = 20.5
    )

    @Test
    fun getEncordadoras() {
        every { encordadoraRepository.findAll() } returns listOf(encordadora)

        val res = encordadoraController.getEncordadoras()

        assert(res == listOf(encordadora))

        verify(exactly = 1) { encordadoraRepository.findAll() }
    }

    @Test
    fun getEncordadoraById() {
        every { encordadoraRepository.findById(encordadora.id) } returns encordadora

        val res = encordadoraController.getEncordadoraById(encordadora.id)

        assert(res == encordadora)

        verify { encordadoraRepository.findById(encordadora.id) }
    }

    @Test
    fun getEncordadoraByIdNotExists() {
        every { encordadoraRepository.findById(encordadora.id) } returns null

        val res = assertThrows<GenericException> { encordadoraController.getEncordadoraById(encordadora.id) }

        assert(res.message == "Encordadora con id ${encordadora.id} no encontrada")

        verify { encordadoraRepository.findById(encordadora.id) }
    }

    @Test
    fun updateEncordadora() {
        every { encordadoraRepository.save(encordadora) } returns encordadora

        encordadora.let {
            encordadora.isManual = false
            encordadoraController.updateEncordadora(encordadora)
        }

        val res = encordadora.isManual

        assert(res == encordadora.isManual)

        verify { encordadoraRepository.save(encordadora) }
    }

    @Test
    fun deleteEncordadora() {
        every { encordadoraRepository.delete(encordadora) } returns true

        val res = encordadoraController.deleteEncordadora(encordadora)

        assert(res)

        verify { encordadoraRepository.delete(encordadora) }
    }

    @Test
    fun deleteEncordadoraNotExists() {
        every { encordadoraRepository.delete(encordadora) } returns false

        val res = assertThrows<GenericException>{ encordadoraController.deleteEncordadora(encordadora) }

        assert(res.message == "Encordadora con id ${encordadora.id} no encontrada")

        verify { encordadoraRepository.delete(encordadora) }
    }

    @Test
    fun createEncordadora() {
        every { encordadoraRepository.save(encordadora)} returns encordadora

        val res = encordadoraController.createEncordadora(encordadora)

        assert(res == encordadora)

        verify { encordadoraRepository.save(encordadora) }
    }
}