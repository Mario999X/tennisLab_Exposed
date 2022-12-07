package repositories.producto

/**
 * @author Sebastian Mendoza y Mario Resa
 */
import entities.ProductoDao
import mappers.fromProductoDaoToProducto
import models.Producto
import mu.KotlinLogging
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.sql.transactions.transaction

private val log = KotlinLogging.logger { }

/**
 * ProductoRepositoryImpl Clase que realiza operaciones CRUD, productos.
 *
 * @property productoDao ProductoMapper
 */
class ProductoRepositoryImpl(private val productoDao: LongEntityClass<ProductoDao>) : ProductoRepository {
    /**
     * FindAll()
     *
     * @return Lista de productos
     */
    override fun findAll(): List<Producto> = transaction {
        log.debug { "findAll()" }
        productoDao.all().map { it.fromProductoDaoToProducto() }
    }

    /**
     * FindById()
     *
     * @param id Identificador de producto
     * @return Producto o Null
     */
    override fun findById(id: Long): Producto? = transaction {
        log.debug { "findById($id)" }
        productoDao.findById(id)?.fromProductoDaoToProducto()
    }

    /**
     * Save(), guarda o actualiza el entity
     *
     * @param entity Producto
     * @return Producto
     */
    override fun save(entity: Producto): Producto = transaction {
        val existe = productoDao.findById(entity.id)
        existe?.let {
            update(entity, existe)
        } ?: run {
            insert(entity)
        }
    }

    /**
     * Insert(), Se introduce el dato
     *
     * @param entity Producto
     * @return Producto
     */
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

    /**
     * Update(), se actualiza el dato
     *
     * @param entity Producto
     * @param existe ProductoDao
     * @return Producto
     */
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

    /**
     * Delete(), Se elimina el dato
     *
     * @param entity Producto
     * @return Boolean
     */
    override fun delete(entity: Producto): Boolean = transaction {
        val existe = productoDao.findById(entity.id) ?: return@transaction false
        log.debug { "delete($entity) - borrando" }
        existe.delete()
        true
    }
}