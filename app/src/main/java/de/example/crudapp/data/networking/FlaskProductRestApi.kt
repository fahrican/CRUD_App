package de.example.crudapp.data.networking

import de.example.crudapp.model.Product
import de.example.crudapp.model.ProductsResponse
import retrofit2.Call
import retrofit2.http.*

interface FlaskProductRestApi {

    @GET("products")
    suspend fun getProducts(): ProductsResponse

    @POST("product")
    suspend fun createProduct(@Body product: Product): Call<Product>

    @FormUrlEncoded
    @POST("product")
    suspend fun createProduct(
        @Field("name") name: String,
        @Field("description") description: String,
        @Field("price") price: Float,
        @Field("qty") qty: Int
    ): Call<Product>

}