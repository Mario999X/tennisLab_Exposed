package controllerTest

import controllers.PedidoController
import exceptions.GenericException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Pedido
import models.usuario.Cliente
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.pedido.PedidoRepository
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
internal class PedidoControllerTest {

    @MockK
    lateinit var pedidoRepository: PedidoRepository

    @InjectMockKs
    lateinit var pedidoController: PedidoController

    private val cliente = Cliente(
        id = 1L,
        nombre = "Pepe",
        apellido = "Bordillo",
        email = "email2@email",
        password = "1234"
    )
    private val pedido = Pedido(
        id = 1L,
        estado = Pedido.TipoEstado.TERMINADO,
        fechaEntrada = LocalDate.now(),
        fechaProgramada = LocalDate.now().plusDays(2),
        fechaSalida = LocalDate.now().plusDays(1),
        cliente = cliente
    )

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun getPedidos() {
        every { pedidoRepository.findAll() } returns listOf(pedido)
        val res = pedidoController.getPedidos()
        assert(res == listOf(pedido))
        verify(exactly = 1) { pedidoRepository.findAll() }
    }

    @Test
    fun getPedidoById() {
        every { pedidoRepository.findById(pedido.id) } returns pedido
        val res = pedidoController.getPedidoById(pedido.id)
        assert(res == pedido)
        verify(exactly = 1) { pedidoRepository.findById(pedido.id) }
    }

    @Test
    fun getPedidoByIdNoExiste() {
        every { pedidoRepository.findById(pedido.id) } returns null
        val res = assertThrows<GenericException> { pedidoController.getPedidoById(pedido.id) }
        assert(res.message == "Pedido con id ${pedido.id} no encontrado")
        verify(exactly = 1) { pedidoRepository.findById(pedido.id) }
    }

    @Test
    fun updatePedido() {
        every { pedidoRepository.save(pedido) } returns pedido
        pedido?.let {
            it.estado = Pedido.TipoEstado.PROCESANDO
            pedidoController.updatePedido(it)
        }
        val res = pedido.estado
        assert(res == Pedido.TipoEstado.PROCESANDO)
        verify(exactly = 1) { pedidoRepository.save(pedido) }
    }

    @Test
    fun deletePedido() {
        every { pedidoRepository.delete(pedido) } returns true
        val res = pedidoController.deletePedido(pedido)
        assertTrue(res)
        verify(exactly = 1) { pedidoRepository.delete(pedido) }
    }

    @Test
    fun createPedido() {
        every { pedidoRepository.save(pedido) } returns pedido
        val res = pedidoController.createPedido(pedido)
        assert(res == pedido)
        verify(exactly = 1) { pedidoRepository.save(pedido) }
    }
}