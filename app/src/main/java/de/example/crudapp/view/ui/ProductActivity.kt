package de.example.crudapp.view.ui

import android.content.Intent
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
    private var swipedProduct: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        checkForProduct()
    }

    private fun checkForProduct() {
        swipedProduct = intent.getParcelableExtra(MainActivity.UPDATE_PRODUCT)
        if (swipedProduct != null) {
            supportActionBar?.title = "Update ${swipedProduct?.name}"
            binding.pNameInput.setText(swipedProduct?.name)
            binding.pDescriptionInput.setText(swipedProduct?.description)
            binding.pPriceInput.setText(swipedProduct?.price.toString())
            binding.pQtyInput.setText(swipedProduct?.qty.toString())
            binding.btnPostProduct.visibility = View.GONE
            binding.btnPutProduct.visibility = View.VISIBLE
        } else {
            supportActionBar?.title = "Create a new product"
            binding.btnPostProduct.visibility = View.VISIBLE
            binding.btnPutProduct.visibility = View.GONE
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
        viewModel.createProduct("test1", "yoyoyo", 9.99f, 2)
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun updateProduct(v: View) {
        swipedProduct?.let {
            viewModel.updateProduct(
                it.id ?: 0,
                it.name ?: "",
                it.description ?: "",
                it.price ?: 0.00f,
                it.qty ?: 0
            )
        }
        startActivity(Intent(this, MainActivity::class.java))
    }

}