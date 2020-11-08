package de.example.crudapp.view.ui

import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import de.example.crudapp.R
import de.example.crudapp.databinding.ActivityMainBinding
import de.example.crudapp.view.adapter.ProductAdapter
import de.example.crudapp.viewmodel.ProductViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


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

        createNewProduct()

        val itemTouchHelper = ItemTouchHelper(setUpSimpleCallback())
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
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

    private fun setUpSimpleCallback(): ItemTouchHelper.SimpleCallback {
        return object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        //show alert dialog
                        Log.v("onSwiped", "ItemTouchHelper.LEFT ")
                        val dialog = setUpAlertDialog()
                        dialog.show()
                    }
                    ItemTouchHelper.RIGHT -> {
                        Log.v("onSwiped", "ItemTouchHelper.RIGHT ")
                        Toast.makeText(
                            this@MainActivity,
                            "Item PATCH request",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> return
                }
                productAdapter.notifyItemChanged(viewHolder.adapterPosition);
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.swipeDelete
                        )
                    )
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .setSwipeLeftLabelColor(R.color.labelColor)
                    .addSwipeLeftLabel("Delete Product")
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.swipeEdit
                        )
                    )
                    .addSwipeRightActionIcon(R.drawable.ic_edit)
                    .setSwipeRightLabelColor(R.color.labelColor)
                    .addSwipeRightLabel("Edit Product")
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
    }

    private fun createNewProduct() {
        binding.fabPostProduct.setOnClickListener {
            val intent = Intent(this, ProductActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setUpAlertDialog(): AlertDialog {
        return AlertDialog.Builder(this@MainActivity)
            .setTitle("Delete Product")
            .setMessage("Do wou really want to delete this item?")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteProduct(5)
                Toast.makeText(
                    this@MainActivity,
                    "Item DELETE request",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton("No") { _, _ ->
            }.create()
    }

}