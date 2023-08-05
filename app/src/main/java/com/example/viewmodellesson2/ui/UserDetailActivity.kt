package com.example.viewmodellesson2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.viewmodellesson2.R
import com.example.viewmodellesson2.data.model.Product
import com.example.viewmodellesson2.data.model.User
import com.example.viewmodellesson2.databinding.ActivityUserDetailBinding
import com.example.viewmodellesson2.ui.product.ProductsActivity.Companion.PRODUCT
import com.example.viewmodellesson2.ui.user.UsersActivity

class UserDetailActivity : AppCompatActivity() {


    private lateinit var binding:ActivityUserDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val user = intent.getParcelableExtra<Product>(PRODUCT)

        binding.btnDeleteUser.setOnClickListener {

            user?.let {

                val intent = Intent()
                intent.putExtra(PRODUCT, user)
                setResult(RESULT_OK, intent)
                finish()

            }?: kotlin.run {
                Toast.makeText(this,"user null",Toast.LENGTH_SHORT).show()
                finish()
            }




        }










    }
}