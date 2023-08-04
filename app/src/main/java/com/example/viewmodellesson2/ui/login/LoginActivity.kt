package com.example.viewmodellesson2.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodellesson2.data.state.LoginState
import com.example.viewmodellesson2.R
import com.example.viewmodellesson2.data.state.ReactionState
import com.example.viewmodellesson2.databinding.ActivityLoginBinding
import com.example.viewmodellesson2.ui.product.ProductsActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        listeners()

        observeReaction()
        observeLoginState()

    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.loginState.collect{
                    when(it){
                        is LoginState.Idle ->{}
                        is LoginState.Success ->{
                            startActivity(
                                Intent(this@LoginActivity, ProductsActivity::class.java)
                            )
                        }
                        is LoginState.Error ->{
                            AlertDialog.Builder(this@LoginActivity).setTitle("Uyari").setMessage("Bilinmeyen bir hata olustu").create().show()
                        }
                    }
                }
            }
        }
    }

    private fun observeReaction() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.reactionState.collect{
                    when(it){
                        is ReactionState.Idle ->{binding.ivReaction.setImageResource(R.drawable.ic_glued_mute_silent_icon)}
                        is ReactionState.Suspicious ->{binding.ivReaction.setImageResource(R.drawable.ic_suspicious_icon)}
                        is ReactionState.Happy ->{binding.ivReaction.setImageResource(R.drawable.ic_happy_laugh)}
                        is ReactionState.Shocked ->{binding.ivReaction.setImageResource(R.drawable.ic_shocked_wonder)}
                    }
                }
            }

        }
    }

    private fun listeners() {


        binding.btnLogin.setOnClickListener {
            viewModel.loginButtonClicked(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }

        binding.etEmail.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.emailTextChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.etPassword.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.passwordTextChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }
}