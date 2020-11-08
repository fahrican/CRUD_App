package de.example.crudapp.data.networking

import de.example.crudapp.model.Product
import de.example.crudapp.model.ProductsResponse
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
        @Field("price") price: Float,
        @Field("qty") qty: Int
    ): Response<Product>

    @DELETE("product/{id}")
    suspend fun deleteProduct(@Path("id") id: Int): Response<Unit>

    @FormUrlEncoded
    @PUT("product/{id}")
    suspend fun putProduct(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("description") description: String,
        @Field("price") price: Float,
        @Field("qty") qty: Int
    ): Response<Product>

}