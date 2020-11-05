package de.example.crudapp.repository

import de.example.crudapp.model.Product
import de.example.crudapp.model.ProductsResponse
import retrofit2.Call
import retrofit2.http.Field

interface ProductRepository {

    suspend fun fetchProducts(): ProductsResponse

    suspend fun createProduct(product: Product): Call<Product>

    suspend fun createProduct(
        name: String,
        description: String,
        price: Float,
        qty: Int
    ): Call<Product>

}