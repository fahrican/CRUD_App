package de.example.crudapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import de.example.crudapp.R
import de.example.crudapp.databinding.ItemProductBinding
import de.example.crudapp.model.Product

class ProductAdapter : ListAdapter<Product, ProductViewHolder>(ProductDiffUtil()) {

    private lateinit var viewGroupContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        viewGroupContext = parent.context
        val itemProductBinding: ItemProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_product,
            parent,
            false
        )
        return ProductViewHolder(itemProductBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.itemProductBinding.product = getItem(position)
        holder.itemProductBinding.productCard.setOnClickListener {
            setIntentForNewsDetail(getItem(position))
        }
    }

    private fun setIntentForNewsDetail(product: Product) {
        //todo: implement rest
    }

}

class ProductViewHolder(
    val itemProductBinding: ItemProductBinding
) : RecyclerView.ViewHolder(itemProductBinding.root)

class ProductDiffUtil : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}