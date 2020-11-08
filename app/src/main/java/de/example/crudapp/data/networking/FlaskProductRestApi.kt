package de.example.crudapp.data.networking

import de.example.crudapp.model.Product
import de.example.crudapp.model.ProductsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface FlaskProductRestApi {

    @GET("products")
    suspend fun getProducts(): ProductsResponse

    @FormUrlEncoded
    @POST("product")
    suspend fun postProduct(
        @Field("name") name: String,
        @Field("description") description: String,
        @Field("price") price: Double,
        @Field("qty") qty: Int
    ): Response<Product>

    @DELETE("product/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Response<Unit>

}