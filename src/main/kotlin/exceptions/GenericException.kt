package exceptions

import java.lang.RuntimeException

class GenericException(mensaje: String) : RuntimeException(mensaje) {
}