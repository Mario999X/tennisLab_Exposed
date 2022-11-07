package controllers

import models.Usuario
import mu.KotlinLogging
import repositories.UsuariosRepository

private val log = KotlinLogging.logger { }

class UsuariosController(private val usuariosRepository: UsuariosRepository) {
    fun getUsuarios(): List<Usuario> {
        log.info { "Obteniendo usuarios" }
        return usuariosRepository.findAll()
    }

    fun getUsuarioById(id: Long): Usuario? {
        log.info { "Obteniendo usuario por ID $id" }
        return usuariosRepository.findById(id)
    }

    fun updateUsuario(usuario: Usuario) {
        log.info { "Actualizado usuario $usuario" }
        usuariosRepository.save(usuario)
    }

    fun deleteUsuario(it: Usuario): Boolean { // TODO Elegir si eliminar o suspender el usuario
        log.info { "Borrando usuario $it" }
        return usuariosRepository.delete(it)
    }

    fun createUsuario(usuario: Usuario): Usuario {
        log.info { "Insertando nuevo usuario $usuario" }
        usuariosRepository.save(usuario)
        return usuario
    }
}