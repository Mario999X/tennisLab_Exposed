package repositories

import entities.ProductoDao
import mappers.fromProductoDaoToProducto
import models.Producto
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

class ProductoRepositoryImpl(private val productoDao: LongEntityClass<ProductoDao>) : ProductoRepository {
    override fun findAll(): List<Producto> = transaction {
        log.debug { "findAll()" }
        productoDao.all().map { it.fromProductoDaoToProducto() }
    }

    override fun findById(id: Long): Producto? = transaction {
        log.debug { "findById($id)" }
        productoDao.findById(id)?.fromProductoDaoToProducto()
    }

    override fun save(entity: Producto): Producto = transaction {
        val existe = productoDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    private fun insert(entity: Producto): Producto {
        log.debug { "save($entity) - creando" }
        return productoDao.new(entity.id) {
            uuid = entity.uuid
            tipo = entity.tipo.item
            marca = entity.marca
            modelo = entity.modelo
            stock = entity.stock
            precio = entity.precio
        }.fromProductoDaoToProducto()
    }

    private fun update(entity: Producto, existe: ProductoDao): Producto {
        log.debug { "save($entity) - actualizando" }
        return existe.apply {
            uuid = entity.uuid
            tipo = entity.tipo.item
            marca = entity.marca
            modelo = entity.modelo
            stock = entity.stock
            precio = entity.precio
        }.fromProductoDaoToProducto()
    }

    override fun delete(entity: Producto): Boolean = transaction {
        val existe = productoDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}