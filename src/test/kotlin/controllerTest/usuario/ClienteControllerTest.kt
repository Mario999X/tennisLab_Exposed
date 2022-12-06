package controllerTest.usuario

import controllers.usuario.ClienteController
import exceptions.GenericException
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.usuario.Cliente
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.usuario.ClienteRepository

@ExtendWith(MockKExtension::class)
internal class ClienteControllerTest {

    @MockK
    lateinit var clienteRepository: ClienteRepository

    @InjectMockKs
    lateinit var clienteController: ClienteController

    private val cliente = Cliente(
        id = 5L,
        nombre = "Obelisco",
        apellido = "aaaaa",
        email = "email3@email.com",
        password = "4321"
    )

    @Test
    fun getClientes() {
        every { clienteRepository.findAll() } returns listOf(cliente)

        val res = clienteController.getClientes()

        assert(res == listOf(cliente))

        verify(exactly = 1) { clienteRepository.findAll() }
    }

    @Test
    fun getClienteById() {
        every { clienteRepository.findById(cliente.id) } returns cliente

        val res = clienteController.getClienteById(cliente.id)

        assert(res == cliente)

        verify(exactly = 1) { clienteRepository.findById(cliente.id) }
    }

    @Test
    fun getClienteByIdNotExists() {
        every { clienteRepository.findById(cliente.id) } returns null

        val res = assertThrows<GenericException> { clienteController.getClienteById(cliente.id) }

        assert(res.message == "Cliente con id ${cliente.id} no encontrado")

        verify(exactly = 1) { clienteRepository.findById(cliente.id) }
    }

    @Test
    fun updateCliente() {
        every { clienteRepository.save(cliente) } returns cliente

        cliente.let {
            cliente.nombre = "AAA"
            clienteController.updateCliente(it)
        }

        val res = cliente.nombre

        assert(res == "AAA")

        verify(exactly = 1) { clienteRepository.save(cliente) }
    }

    @Test
    fun deleteCliente() {
        every { clienteRepository.delete(cliente) } returns true

        val res = clienteController.deleteCliente(cliente)

        assert(res)

        verify(exactly = 1) { clienteRepository.delete(cliente) }
    }

    @Test
    fun deleteClienteNotExists() {
        every { clienteRepository.delete(cliente) } returns false

        val res = assertThrows<GenericException> { clienteController.deleteCliente(cliente) }

        assert(res.message == "Cliente con id ${cliente.id} no encontrado")

        verify(exactly = 1) { clienteRepository.delete(cliente) }
    }

    @Test
    fun createCliente() {
        every { clienteRepository.save(cliente) } returns cliente

        val res = clienteController.createCliente(cliente)

        assert(res == cliente)

        verify(exactly = 1) { clienteRepository.save(cliente) }
    }
}