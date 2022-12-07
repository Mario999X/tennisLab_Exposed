package exceptions

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import java.lang.RuntimeException

/**
 * GenericException, clase dedicada a la genereacion de excepciones personalizadas.
 *
 * @constructor Mensaje: String
 *
 * @param mensaje mensaje que se mostrar al saltar una excepcion.
 */
class GenericException(mensaje: String) : RuntimeException(mensaje) {
}