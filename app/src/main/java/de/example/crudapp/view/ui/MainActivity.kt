package de.example.crudapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import de.example.crudapp.R
import de.example.crudapp.databinding.ActivityMainBinding
import de.example.crudapp.view.adapter.ProductAdapter
import de.example.crudapp.viewmodel.ProductViewModel


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductViewModel by viewModels()
    private val productAdapter by lazy { ProductAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpSwipeRefresh()

        setUpRecyclerView()

        observeLiveData()
    }

    override fun onRefresh() {
        viewModel.fetchProducts()
    }

    private fun setUpSwipeRefresh() {
        binding.swipeRefresh.let {
            it.setOnRefreshListener(this)
            it.setColorSchemeResources(R.color.colorAccent)
            it.setOnRefreshListener {
                it.isRefreshing = false
                viewModel.fetchProducts()
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.let {
            it.setHasFixedSize(true)
            it.itemAnimator = DefaultItemAnimator()
            it.adapter = productAdapter
        }
    }

    private fun observeLiveData() {
        observeInProgress()
        observeIsError()
        observeProducts()
    }

    private fun observeInProgress() {
        viewModel.isInProgress.observe(this) { isLoading ->
            isLoading.let {
                if (it) {
                    binding.emptyText.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.fetchProgress.visibility = View.VISIBLE
                } else {
                    binding.fetchProgress.visibility = View.GONE
                }
            }
        }
    }

    private fun observeIsError() {
        viewModel.exceptionText.observe(this) { isError ->
            isError.let {
                if (it.isNotEmpty()) {
                    disableViewsOnError()
                } else {
                    binding.emptyText.visibility = View.GONE
                    binding.fetchProgress.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun disableViewsOnError() {
        binding.fetchProgress.visibility = View.VISIBLE
        binding.emptyText.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        productAdapter.submitList(emptyList())
        binding.fetchProgress.visibility = View.GONE
    }


    private fun observeProducts() {
        viewModel.products.observe(this) { products ->
            products.let {
                if (it != null && it.isNotEmpty()) {
                    binding.fetchProgress.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.VISIBLE
                    productAdapter.submitList(it)
                    binding.emptyText.visibility = View.GONE
                    binding.fetchProgress.visibility = View.GONE
                } else {
                    disableViewsOnError()
                }
            }
        }
    }

}