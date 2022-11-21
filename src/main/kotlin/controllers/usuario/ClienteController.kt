package controllers.usuario

import models.usuario.Cliente
import mu.KotlinLogging
import repositories.usuario.ClienteRepository

private val log = KotlinLogging.logger { }

class ClienteController(private val clienteRepository: ClienteRepository) {
    fun getClientes(): List<Cliente> {
        log.info { "Obteniendo clientes" }
        return clienteRepository.findAll()
    }

    fun getClienteById(id: Long): Cliente? {
        log.info { "Obteniendo cliente por ID $id" }
        return clienteRepository.findById(id)
    }

    fun updateCliente(cliente: Cliente) {
        log.info { "Actualizado cliente $cliente" }
        clienteRepository.save(cliente)
    }

    fun deleteCliente(it: Cliente): Boolean {
        log.info { "Borrando cliente $it" }
        return clienteRepository.delete(it)
    }

    fun createCliente(cliente: Cliente): Cliente {
        log.info { "Insertando nuevo cliente $cliente" }
        clienteRepository.save(cliente)
        return cliente
    }
}