package models.usuario

import java.util.UUID

class Encargado(
    id: Long,
    uuid: UUID = UUID.randomUUID(),
    nombre: String,
    apellido: String,
    email: String,
    password: String,
    val perfil: Perfil = Perfil.ADMIN
) : Usuario(id, uuid, nombre, apellido, email, password) {
    override fun toString(): String {
        return "Encargado(uuid=$uuid, nombre='$nombre', apellido='$apellido', email='$email', password='$password')"
    }
}