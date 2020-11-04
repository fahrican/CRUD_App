package de.example.crudapp.data.networking

import de.example.crudapp.model.ProductsResponse
import retrofit2.http.GET

interface FlaskProductRestApi {

    @GET("products")
    suspend fun getProducts(): ProductsResponse

}