package de.example.crudapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        val swipedProduct: Product? = intent.getParcelableExtra(MainActivity.UPDATE_PRODUCT)
        swipedProduct?.let {
            Log.v("intent", "${swipedProduct.description}")
        }
    }

    fun createProduct(v: View) {
        /*val product = Product(
            null,
            binding.pDescriptionInput.toString(),
            binding.pNameInput.toString(),
            binding.pPriceInput.toString().toDouble(),
            binding.pQtyInput.toString().toInt()
        )*/
        viewModel.createProduct("test1", "yoyoyo", 9.99, 2)
        startActivity(Intent(this, MainActivity::class.java))
    }

}