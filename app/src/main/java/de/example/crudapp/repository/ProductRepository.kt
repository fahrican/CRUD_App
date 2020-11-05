package de.example.crudapp.repository

import de.example.crudapp.model.Product
import de.example.crudapp.model.ProductsResponse
import retrofit2.Call

interface ProductRepository {

    suspend fun fetchProducts(): ProductsResponse

    suspend fun createProduct(product: Product): Call<Product>

}