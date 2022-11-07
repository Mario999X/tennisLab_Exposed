package utils

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Cifrador {//TODO: Tenemos que averiguar dónde usar esto
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