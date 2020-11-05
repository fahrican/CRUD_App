package de.example.crudapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import de.example.crudapp.R

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        supportActionBar?.title = "Create a new product"
    }

}