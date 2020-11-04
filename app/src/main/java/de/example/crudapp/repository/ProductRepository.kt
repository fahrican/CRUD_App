package de.example.crudapp.repository

import de.example.crudapp.model.ProductsResponse

interface ProductRepository {

    suspend fun fetchProducts(): ProductsResponse

}