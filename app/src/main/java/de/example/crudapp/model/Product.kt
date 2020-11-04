package de.example.crudapp.model

data class Product(
    val description: String,
    val id: Int,
    val name: String,
    val price: Double,
    val qty: Int
) //todo: make nullable