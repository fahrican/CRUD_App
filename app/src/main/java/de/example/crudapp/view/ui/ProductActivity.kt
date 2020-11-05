package de.example.crudapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import de.example.crudapp.databinding.ActivityProductBinding
import de.example.crudapp.model.Product
import de.example.crudapp.viewmodel.ProductViewModel

class ProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductBinding
    private val viewModel: ProductViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.title = "Create a new product"
    }

    fun createProduct(v: View) {
        /*val product = Product(
            null,
            binding.pDescriptionInput.toString(),
            binding.pNameInput.toString(),
            binding.pPriceInput.toString().toDouble(),
            binding.pQtyInput.toString().toInt()
        )*/
        val product = Product(null, "a", "a", 1.2, 1)
        viewModel.createProduct(product)
    }

}