package com.mihryundel.ecwidandroid.mvp.model

import com.reactiveandroid.query.Delete
import com.reactiveandroid.query.Select
import java.util.*

class ProductDao {

    fun createProduct(): Product {
        val product = Product("New product", Date())
        product.save()
        return product
    }

    fun saveProduct(product: Product): Long = product.save()

    fun loadAllProducts(): MutableList<Product> = Select.from(Product::class.java).fetch()

    fun getProductById(productId: Long): Product? = Select.from(Product::class.java).where("id = ?", productId).fetchSingle()

    fun deleteAllProducts() = Delete.from(Product::class.java).execute()

    fun deleteProduct(product: Product) = product.delete()

}
