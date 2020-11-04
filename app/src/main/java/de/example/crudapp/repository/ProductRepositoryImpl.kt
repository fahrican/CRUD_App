package de.example.crudapp.repository

import de.example.crudapp.data.networking.FlaskProductRestApi
import de.example.crudapp.di.DaggerAppComponent
import de.example.crudapp.model.ProductsResponse
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
}