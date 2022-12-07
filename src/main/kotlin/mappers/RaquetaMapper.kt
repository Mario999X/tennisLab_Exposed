package mappers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.RaquetaDao
import models.Raqueta

/**
 * FromRaquetaDaotoRaqueta(), funcion que obtiene el dato y lo pasa a objeto
 *
 * @return Raqueta
 */
fun RaquetaDao.fromRaquetaDaoToRaqueta(): Raqueta {
    return Raqueta(
        id = id.value,
        uuid = uuid,
        marca = marca,
        modelo = modelo,
        cliente = cliente.fromClienteDaoToCliente(),
    )
}