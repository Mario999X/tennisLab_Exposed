package controllerTest

import controllers.TurnoController
import exceptions.GenericException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Turno
import models.usuario.Trabajador
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.turno.TurnoRepository

@ExtendWith(MockKExtension::class)
internal class TurnoControllerTest {

    @MockK
    lateinit var turnoRepository: TurnoRepository

    @InjectMockKs
    lateinit var turnoController: TurnoController

    private val turno = Turno(
        id = 5L,
        horario = Turno.TipoHorario.TEMPRANO,
        trabajador = Trabajador(
            id = 5L,
            nombre = "Camilo",
            apellido = "Echeverri",
            email = "email3@email.com",
            password = "4321"
        )
    )

    @Test
    fun getTurnos() {
        every { turnoRepository.findAll() } returns listOf(turno)

        val res = turnoController.getTurnos()

        assert(res == listOf(turno))

        verify(exactly = 1) { turnoRepository.findAll() }
    }

    @Test
    fun getTurnoById() {
        every { turnoRepository.findById(turno.id) } returns turno

        val res = turnoController.getTurnoById(turno.id)

        assert(res == turno)

        verify(exactly = 1) { turnoRepository.findById(turno.id) }
    }

    @Test
    fun getTurnoByIdNotExists() {
        every { turnoRepository.findById(turno.id) } returns null

        val res = assertThrows<GenericException> { turnoController.getTurnoById(turno.id) }

        assert(res.message == "Turno con id ${turno.id} no encontrado")

        verify(exactly = 1) { turnoRepository.findById(turno.id) }
    }

    @Test
    fun updateTurno() {
        every { turnoRepository.save(turno) } returns turno

        turno.let {
            turno.horario = Turno.TipoHorario.TARDE
            turnoController.updateTurno(it)
        }

        val res = turno.horario

        assert(res == Turno.TipoHorario.TARDE)

        verify(exactly = 1) { turnoRepository.save(turno) }
    }

    @Test
    fun deleteTurno() {
        every { turnoRepository.delete(turno) } returns true

        val res = turnoController.deleteTurno(turno)

        assert(res)

        verify(exactly = 1) { turnoRepository.delete(turno) }
    }

    @Test
    fun deleteTurnoNotExists() {
        every { turnoRepository.delete(turno) } returns false

        val res = assertThrows<GenericException> { turnoController.deleteTurno(turno) }

        assert(res.message == "Turno con id ${turno.id} no encontrado")

        verify(exactly = 1) { turnoRepository.delete(turno) }
    }

    @Test
    fun createTurno() {
        every { turnoRepository.save(turno) } returns turno

        val res = turnoController.createTurno(turno)

        assert(res == turno)

        verify(exactly = 1) { turnoRepository.save(turno) }
    }
}