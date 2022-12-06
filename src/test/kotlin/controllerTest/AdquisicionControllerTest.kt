package controllerTest

import controllers.AdquisicionController
import exceptions.GenericException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Adquisicion
import models.Producto
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.adquisicion.AdquisicionRepository

@ExtendWith(MockKExtension::class)
internal class AdquisicionControllerTest {

    @MockK
    lateinit var adquisicionRepository: AdquisicionRepository

    @InjectMockKs
    lateinit var adquisicionController: AdquisicionController

    private val producto =
        Producto(id = 1L, tipo = Producto.Tipo.COMPLEMENTO, marca = "Wilson", modelo = "Pure", stock = 3, precio = 17.9)
    private val adquisicion =
        Adquisicion(id = 1L, cantidad = 2, producto = producto)

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun getAdquisiciones() {
        every { adquisicionRepository.findAll() } returns listOf(adquisicion)
        val res = adquisicionController.getAdquisiciones()
        assert(res == listOf(adquisicion))
        verify(exactly = 1) { adquisicionRepository.findAll() }
    }

    @Test
    fun getAdquisicionById() {
        every { adquisicionRepository.findById(adquisicion.id) } returns adquisicion
        val res = adquisicionController.getAdquisicionById(adquisicion.id)
        assert(res == adquisicion)
        verify(exactly = 1) { adquisicionRepository.findById(adquisicion.id) }
    }

    @Test
    fun getAdquisicionByIdNoExiste() {
        every { adquisicionRepository.findById(adquisicion.id) } returns null
        val res = assertThrows<GenericException> { adquisicionController.getAdquisicionById(adquisicion.id) }
        assert(res.message == "Adquisicion con id ${adquisicion.id} no encontrada")
        verify(exactly = 1) { adquisicionRepository.findById(adquisicion.id) }
    }

    @Test
    fun updateAdquisicion() {
        every { adquisicionRepository.save(adquisicion) } returns adquisicion
        adquisicion?.let {
            it.cantidad += 1
            adquisicionController.updateAdquisicion(it)
        }
        val res = adquisicion.cantidad
        assert(res == 3)
        verify(exactly = 1) { adquisicionRepository.save(adquisicion) }
    }

    @Test
    fun deleteAdquisicion() {
        every { adquisicionRepository.delete(adquisicion) } returns true
        val res = adquisicionController.deleteAdquisicion(adquisicion)
        assertTrue(res)
        verify(exactly = 1) { adquisicionRepository.delete(adquisicion) }
    }

    @Test
    fun createAdquisicion() {
        every { adquisicionRepository.save(adquisicion) } returns adquisicion
        val res = adquisicionController.createAdquisicion(adquisicion)
        assert(res == adquisicion)
        verify(exactly = 1) { adquisicionRepository.save(adquisicion) }
    }
}