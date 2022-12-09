package controllers

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import exceptions.GenericException
import models.Producto
import mu.KotlinLogging
import repositories.producto.ProductoRepository

private val log = KotlinLogging.logger { }

/**
 * ProductoController, clase que usa los metodos del respectivo repositorio
 *
 * @property productoRepository ProductoRepository
 */
class ProductoController(private val productoRepository: ProductoRepository) {
    fun getProductos(): List<Producto> {
        log.info("Obteniendo productos")
        return productoRepository.findAll()
    }

    fun getProductoById(id: Long): Producto {
        log.info("Obteniendo producto por ID $id")
        return productoRepository.findById(id) ?: throw GenericException("Producto con id $id no encontrado")
    }

    fun updateProducto(producto: Producto) {
        log.info("Actualizado producto $producto")
        productoRepository.save(producto)
    }

    fun deleteProducto(it: Producto): Boolean {
        log.info("Borrando producto $it")
        return if (productoRepository.delete(it))
            true
        else
            throw GenericException("Producto con id ${it.id} no encontrado")
    }

    fun createProducto(producto: Producto): Producto {
        log.info { "Insertando nuevo producto $producto" }
        productoRepository.save(producto)
        return producto
    }
}