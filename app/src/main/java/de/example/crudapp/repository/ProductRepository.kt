package de.example.crudapp.repository

import de.example.crudapp.model.Product
import de.example.crudapp.model.ProductsResponse

interface ProductRepository {

    suspend fun fetchProducts(): ProductsResponse

    suspend fun createProduct(product: Product): Product

}