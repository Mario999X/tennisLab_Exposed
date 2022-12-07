package controllerTest

import controllers.ProductoController
import exceptions.GenericException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import models.Producto
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import repositories.producto.ProductoRepository
import java.util.*

@ExtendWith(MockKExtension::class)
internal class ProductoControllerTest {

    @MockK
    lateinit var productoRepository: ProductoRepository

    @InjectMockKs
    lateinit var productoController: ProductoController

    private val producto =
        Producto(id = 1L, tipo = Producto.Tipo.COMPLEMENTO, marca = "Wilson", modelo = "Pure", stock = 3, precio = 17.9)

    init {
        MockKAnnotations.init(this)
    }

    @Test
    fun getProductos() {
        every { productoRepository.findAll() } returns listOf(producto)
        val res = productoController.getProductos()
        assert(res == listOf(producto))
        verify(exactly = 1) { productoRepository.findAll() }
    }

    @Test
    fun getProductoById() {
        every { productoRepository.findById(producto.id) } returns producto
        val res = productoController.getProductoById(producto.id)
        assert(res == producto)
        verify(exactly = 1) { productoRepository.findById(producto.id) }
    }

    @Test
    fun getProductoByIdNoExiste() {
        every { productoRepository.findById(producto.id) } returns null
        val res = assertThrows<GenericException> { productoController.getProductoById(producto.id) }
        assert(res.message == "Producto con id ${producto.id} no encontrado")
        verify(exactly = 1) { productoRepository.findById(producto.id) }
    }

    @Test
    fun updateProducto() {
        every { productoRepository.save(producto) } returns producto
        producto?.let {
            it.modelo = "Prueba"
            productoController.updateProducto(it)
        }
        val res = producto.modelo
        assert(res == "Prueba")
        verify(exactly = 1) { productoRepository.save(producto) }
    }

    @Test
    fun deleteProducto() {
        every { productoRepository.delete(producto) } returns true
        val res = productoController.deleteProducto(producto)
        assertTrue(res)
        verify(exactly = 1) { productoRepository.delete(producto) }
    }

    @Test
    fun createProducto() {
        every { productoRepository.save(producto) } returns producto
        val res = productoController.createProducto(producto)
        assert(res == producto)
        verify(exactly = 1) { productoRepository.save(producto) }
    }
}