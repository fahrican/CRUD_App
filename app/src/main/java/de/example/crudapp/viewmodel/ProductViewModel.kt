package de.example.crudapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.example.crudapp.di.DaggerAppComponent
import de.example.crudapp.model.Product
import de.example.crudapp.repository.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel : ViewModel() {

    @Inject
    lateinit var repository: ProductRepository

    private val _isInProgress by lazy { MutableLiveData<Boolean>() }
    val isInProgress: LiveData<Boolean>
        get() = _isInProgress

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>>
        get() = _products

    private val _exceptionText = MutableLiveData<String>()
    val exceptionText: LiveData<String>
        get() = _exceptionText

    init {
        DaggerAppComponent.create().inject(this)
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _isInProgress.postValue(true)
            val result = runCatching { repository.fetchProducts() }
            result.onSuccess {
                _products.value = it.products
            }.onFailure {
                _exceptionText.value = it.message
            }
            _isInProgress.postValue(false)
        }
    }

    fun createProduct(product: Product) {
        viewModelScope.launch {
            _isInProgress.postValue(true)
            val post = runCatching { repository.createProduct(product) }
            post.onSuccess {
                if (it.id != null) {
                    Log.d("createProduct:", "$it")
                }
            }.onFailure {
                Log.d("createProduct:", "${it.message}")
            }
            _isInProgress.postValue(false)
        }
    }

}