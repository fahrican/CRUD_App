package de.example.crudapp.data.networking

import de.example.crudapp.model.Product
import de.example.crudapp.model.ProductsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FlaskProductRestApi {

    @GET("products")
    suspend fun getProducts(): ProductsResponse

    @POST("product")
    suspend fun createProduct(@Body product: Product): Call<Product>

}