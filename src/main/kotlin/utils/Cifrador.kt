package utils

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Cifrador clase objeto donde se cifra el password de los distintos usuarios de la BBDD
 */
object Cifrador {
    /**
     * encryptString() Funcion que cifra un String usando el algoritmo SHA-512
     *
     * @param input Password del usuario en String
     * @return Password cifrada.
     */
    fun encryptString(input: String): String {
        return try {
            val md = MessageDigest.getInstance("SHA-512")
            val messageDigest = md.digest(input.toByteArray())
            val no = BigInteger(1, messageDigest)
            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            hashtext
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }
}