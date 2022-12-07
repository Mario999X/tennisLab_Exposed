package mappers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.usuario.ClienteDao
import entities.usuario.EncargadoDao
import entities.usuario.TrabajadorDao
import models.usuario.Cliente
import models.usuario.Encargado
import models.usuario.Trabajador
import utils.Cifrador

/**
 * FromClienteDaoToCliente(), funcion que obtiene el dato y lo pasa a objeto
 *
 * @return Cliente
 */
fun ClienteDao.fromClienteDaoToCliente(): Cliente {
    return Cliente(
        id = id.value,
        uuid = uuid,
        nombre = nombre,
        apellido = apellido,
        email = email,
        password = Cifrador.encryptString(password),
    )
}

/**
 * FromEncargadoDaoToEncargado(), funcion que obtiene el dato y lo pasa a objeto
 *
 * @return Encargado
 */
fun EncargadoDao.fromEncargadoDaoToEncargado(): Encargado {
    return Encargado(
        id = id.value,
        uuid = uuid,
        nombre = nombre,
        apellido = apellido,
        email = email,
        password = Cifrador.encryptString(password)
    )
}

/**
 * FromTrabajadorDaoToTrabajador(), funcion que obtiene el dato y lo pasa a objeto
 *
 * @return Trabajador
 */
fun TrabajadorDao.fromTrabajadorDaoToTrabajador(): Trabajador {
    return Trabajador(
        id = id.value,
        uuid = uuid,
        nombre = nombre,
        apellido = apellido,
        email = email,
        password = Cifrador.encryptString(password)
    )
}