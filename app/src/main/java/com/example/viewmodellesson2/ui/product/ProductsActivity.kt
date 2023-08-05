package com.example.viewmodellesson2.ui.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodellesson2.data.state.AdapterState
import com.example.viewmodellesson2.data.state.ProductListState
import com.example.viewmodellesson2.ui.adapter.ProductsAdapter
import com.example.viewmodellesson2.R
import com.example.viewmodellesson2.data.model.Product
import com.example.viewmodellesson2.data.model.User
import com.example.viewmodellesson2.databinding.ActivityProductsBinding
import com.example.viewmodellesson2.ui.UserDetailActivity
import com.example.viewmodellesson2.ui.user.UsersActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ProductsActivity : AppCompatActivity() {

    lateinit var binding:ActivityProductsBinding

    private val viewModel: ProductsViewModel by viewModels()
    private var adapter: ProductsAdapter?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        observeProductListState()
        observeAdapterState()
        observeFavState()
        observeUnDoFavState()


        binding.btnGetProducts.setOnClickListener {
            viewModel.getProducts()
        }

        binding.btnExit.setOnClickListener {
            finish()
        }


    }

    private fun observeUnDoFavState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.undoFavState.collect{
                    adapter?.undoFavorite(it)
                }
            }
        }
    }

    private fun observeFavState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.favState.collect{product->
                    val message = if (product.favorite) getString(R.string.a_product_is_added_to_the_favorites) else getString(
                        R.string.a_product_is_removed_to_the_favorites
                    )
                    Snackbar.make(binding.btnGetProducts,message,Snackbar.LENGTH_LONG).setAction(getString(
                        R.string.undo
                    )){
                        viewModel.undoFavorite(product)
                    }.show()
                }
            }
        }
    }

    private fun observeAdapterState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.adapterState.collect{
                    drawAdapterState(it)
                }
            }
        }
    }

    private fun drawAdapterState(adapterState: AdapterState) {
        when(adapterState){
            is AdapterState.Idle ->{}
            is AdapterState.Changed ->{
                adapter?.notifyItemChanged(adapterState.index)
            }
           is AdapterState.Remove->{
                adapter?.notifyItemRemoved(adapterState.index)
           }
            else->{}
        }
    }

    private fun observeProductListState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.productListState.collect{
                    drawProductListState(it)
                }
            }
        }
    }

    private fun drawProductListState(it: ProductListState) {
        when(it){
            is ProductListState.Idle ->{}
            is ProductListState.Loading ->{
                binding.rvProducts.isVisible = false
                binding.progressBar.root.isVisible = true

            }
            is ProductListState.Result ->{
                binding.rvProducts.isVisible = true
                binding.progressBar.root.isVisible = false

                adapter = ProductsAdapter(this,it.products,this::onClick){product,index->
                    viewModel.addOrRemoveFavorite(product,index)
                }
                binding.rvProducts.adapter = adapter
            }
            is ProductListState.Error ->{
                binding.rvProducts.isVisible = false
                binding.progressBar.root.isVisible = false

                AlertDialog.Builder(this).setMessage("There is an error happened!").create().show()
            }
        }
    }


    companion object{
        const val REQUEST_CODE_FOR_DELETE = 1
        const val PRODUCT = "PRODUCT"
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_FOR_DELETE && resultCode == RESULT_OK){
            data?.getParcelableExtra<Product>(PRODUCT)?.let { removedProduct->
                viewModel.removeItem(removedProduct)
            }
        }
    }
    private fun onClick(product: Product){
        val intent =  Intent(this, UserDetailActivity::class.java)
        intent.putExtra(PRODUCT,product)
        startActivityForResult(intent, REQUEST_CODE_FOR_DELETE)
    }
}