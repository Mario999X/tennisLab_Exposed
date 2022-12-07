package controllerTest

import controllers.TareaController
import exceptions.GenericException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Pedido
import models.Tarea
import models.usuario.Cliente
import models.usuario.Trabajador
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.tarea.TareaRepository
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
internal class TareaControllerTest {

    @MockK
    lateinit var tareaRepository: TareaRepository

    @InjectMockKs
    lateinit var tareaController: TareaController

    private val cliente = Cliente(
        id = 1L,
        nombre = "Pepe",
        apellido = "Bordillo",
        email = "email2@email",
        password = "1234"
    )

    private val trabajador =
        Trabajador(id = 1L, nombre = "Miguel", apellido = "Alba", email = "email3@email", password = "4567")

    private val pedido = Pedido(
        id = 1L,
        estado = Pedido.TipoEstado.TERMINADO,
        fechaEntrada = LocalDate.now(),
        fechaProgramada = LocalDate.now().plusDays(2),
        fechaSalida = LocalDate.now().plusDays(1),
        cliente = cliente
    )
    private val tarea = Tarea(id = 1L, precio = 100.0, pedido = pedido, trabajador = trabajador)

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun getTareas() {
        every { tareaRepository.findAll() } returns listOf(tarea)
        val res = tareaController.getTareas()
        assert(res == listOf(tarea))
        verify(exactly = 1) { tareaRepository.findAll() }
    }

    @Test
    fun getTareaById() {
        every { tareaRepository.findById(tarea.id) } returns tarea
        val res = tareaController.getTareaById(tarea.id)
        assert(res == tarea)
        verify(exactly = 1) { tareaRepository.findById(tarea.id) }
    }

    @Test
    fun getTareaByIdNoExiste() {
        every { tareaRepository.findById(tarea.id) } returns null
        val res = assertThrows<GenericException> { tareaController.getTareaById(tarea.id) }
        assert(res.message == "Tarea con id ${tarea.id} no encontrada")
        verify(exactly = 1) { tareaRepository.findById(tarea.id) }
    }

    @Test
    fun updateTarea() {
        every { tareaRepository.save(tarea) } returns tarea
        tarea?.let {
            it.precio += 2.0
            tareaController.updateTarea(it)
        }
        val res = tarea.precio
        assert(res == 102.0)
        verify(exactly = 1) { tareaRepository.save(tarea) }
    }

    @Test
    fun deleteTarea() {
        every { tareaRepository.delete(tarea) } returns true
        val res = tareaController.deleteTarea(tarea)
        assertTrue(res)
        verify(exactly = 1) { tareaRepository.delete(tarea) }
    }

    @Test
    fun createTarea() {
        every { tareaRepository.save(tarea) } returns tarea
        val res = tareaController.createTarea(tarea)
        assert(res == tarea)
        verify(exactly = 1) { tareaRepository.save(tarea) }
    }
}