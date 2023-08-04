package com.example.viewmodellesson2.ui.login2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodellesson2.R
import com.example.viewmodellesson2.data.state.SecondLoginState
import com.example.viewmodellesson2.databinding.ActivitySecondLoginBinding
import com.example.viewmodellesson2.ui.product.ProductsActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SecondLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivitySecondLoginBinding
    private val viewModel:SecondLoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        observeLoginState()
        observeMessage()

        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.etEmail.text.toString(),binding.etPassword.text.toString())
        }

    }

    private fun observeMessage() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.message.collect{
                    AlertDialog.Builder(this@SecondLoginActivity).setMessage(it).create().show()
                }
            }
        }
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.loginState.collect{
                    when(it){
                        is SecondLoginState.Idle->{}
                        is SecondLoginState.Success->{
                            binding.progressBar.isVisible = false
                            binding.btnLogin.isVisible = true
                            val intent = Intent(this@SecondLoginActivity,ProductsActivity::class.java)
                            startActivity(intent)
                        }
                        is SecondLoginState.Loading->{
                            binding.progressBar.isVisible = true
                            binding.btnLogin.isVisible = false
                        }
                        is SecondLoginState.Error->{
                            binding.progressBar.isVisible = false
                            binding.btnLogin.isVisible = true

                            Toast.makeText(this@SecondLoginActivity, "Hata olustu",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}