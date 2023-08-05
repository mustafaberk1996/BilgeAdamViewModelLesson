package com.example.viewmodellesson2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.viewmodellesson2.R
import com.example.viewmodellesson2.data.model.Product
import com.example.viewmodellesson2.databinding.ProductListItemBinding

class ProductsAdapter(private val context:Context, private val products:List<Product>,
                      private val onClick:(product:Product)->Unit,private val onAddRemoveFavorite:(product: Product, index:Int)->Unit):RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

    class MyViewHolder(binding: ProductListItemBinding):ViewHolder(binding.root){
        val tvName = binding.tvName
        val ivProduct = binding.ivProduct
        val tvPrice = binding.tvPrice
        val ivAddRemoveFavorite = binding.ivAddRemoveFavorite
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ProductListItemBinding.inflate(LayoutInflater.from(context),parent,false)
        )
    }

    override fun getItemCount(): Int {
       return products.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       products[position].apply {
           holder.ivProduct.load(imageUrl)
           holder.tvName.text = name
           holder.tvPrice.text = price.toString()
           holder.itemView.setOnClickListener {
               onClick(this)
           }
           holder.ivAddRemoveFavorite.setImageResource(
               if (favorite) R.drawable.baseline_star_rate_24 else R.drawable.baseline_star_border_24
           )
           holder.ivAddRemoveFavorite.setOnClickListener {
               val index = products.indexOf(this)
               this.favorite = !this.favorite
               onAddRemoveFavorite(this,index)
           }
       }
    }

    fun undoFavorite(product: Product) {
        val index = products.indexOf(product)
        products[index].favorite = !product.favorite
        onAddRemoveFavorite(products[index], index)

    }
}