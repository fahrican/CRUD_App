package de.example.crudapp.repository

import de.example.crudapp.data.networking.FlaskProductRestApi
import de.example.crudapp.di.DaggerAppComponent
import de.example.crudapp.model.Product
import de.example.crudapp.model.ProductsResponse
import retrofit2.Response
import javax.inject.Inject

class ProductRepositoryImpl : ProductRepository {

    @Inject
    lateinit var flaskApi: FlaskProductRestApi

    init {
        DaggerAppComponent.create().inject(this)
    }

    override suspend fun fetchProducts(): ProductsResponse {
        return flaskApi.getProducts()
    }

    override suspend fun createProduct(
        name: String,
        description: String,
        price: Double,
        qty: Int
    ): Response<Product> = flaskApi.postProduct(name, description, price, qty)


    override suspend fun deleteProduct(id: Int): Response<Unit> {
        return flaskApi.deleteProduct(id)
    }

    override suspend fun updateProduct(
        id: Int,
        name: String,
        description: String,
        price: Double,
        qty: Int
    ): Response<Product> = flaskApi.putProduct(id, name, description, price, qty)

}