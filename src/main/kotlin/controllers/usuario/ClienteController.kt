package controllers.usuario

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import exceptions.GenericException
import models.usuario.Cliente
import mu.KotlinLogging
import repositories.usuario.ClienteRepository

private val log = KotlinLogging.logger { }

/**
 * ClienteController, clase que usa los metodos del respectivo repositorio.
 *
 * @property clienteRepository ClienteRepository
 */
class ClienteController(private val clienteRepository: ClienteRepository) {
    fun getClientes(): List<Cliente> {
        log.info { "Obteniendo clientes" }
        return clienteRepository.findAll()
    }

    fun getClienteById(id: Long): Cliente {
        log.info { "Obteniendo cliente por ID $id" }
        return clienteRepository.findById(id) ?: throw GenericException("Cliente con id $id no encontrado")
    }

    fun updateCliente(cliente: Cliente) {
        log.info { "Actualizado cliente $cliente" }
        clienteRepository.save(cliente)
    }

    fun deleteCliente(it: Cliente): Boolean {
        log.info { "Borrando cliente $it" }
        return if (clienteRepository.delete(it))
            true
        else
            throw GenericException("Cliente con id ${it.id} no encontrado")
    }

    fun createCliente(cliente: Cliente): Cliente {
        log.info { "Insertando nuevo cliente $cliente" }
        clienteRepository.save(cliente)
        return cliente
    }
}