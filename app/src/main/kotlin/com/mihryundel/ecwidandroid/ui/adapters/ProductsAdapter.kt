package com.mihryundel.ecwidandroid.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mihryundel.ecwidandroid.R
import com.mihryundel.ecwidandroid.mvp.model.Product
import com.mihryundel.ecwidandroid.utils.formatDate

class ProductsAdapter(private val productsList: List<Product>) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ProductsAdapter.ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.product_item_layout, viewGroup, false)
        return ProductsAdapter.ViewHolder(v)
    }

    override
    fun onBindViewHolder(viewHolder: ProductsAdapter.ViewHolder, i: Int) {
        val product = productsList[i]
        viewHolder.productTitle.text = product.title
        viewHolder.productDate.text = formatDate(product.changedAt)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var productTitle: TextView = itemView.findViewById(R.id.tvItemProductTitle) as TextView
        var productDate: TextView = itemView.findViewById(R.id.tvItemProductDate) as TextView

    }

}
