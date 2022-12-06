package controllers

import exceptions.GenericException
import models.Producto
import mu.KotlinLogging
import repositories.producto.ProductoRepository

private val log = KotlinLogging.logger { }

class ProductoController(private val productoRepository: ProductoRepository) {
    fun getProductos(): List<Producto> {
        log.info("Obteniendo productos")
        return productoRepository.findAll()
    }

    fun getProductoById(id: Long): Producto? {
        log.info("Obteniendo producto por ID $id")
        return productoRepository.findById(id) ?: throw GenericException("Producto con id $id no encontrado")
    }

    fun updateProducto(producto: Producto) {
        log.info("Actualizado producto $producto")
        productoRepository.save(producto)
    }

    fun deleteProducto(it: Producto): Boolean {
        log.info("Borrando usuario $it")
        return productoRepository.delete(it)
    }

    fun createProducto(producto: Producto): Producto {
        log.info { "Insertando nuevo producto $producto" }
        productoRepository.save(producto)
        return producto
    }
}