package com.example.viewmodellesson2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodellesson2.databinding.ActivityUsersBinding
import kotlinx.coroutines.launch

class UsersActivity : AppCompatActivity() {


    lateinit var binding: ActivityUsersBinding
    private val viewModel:UsersViewModel by viewModels()


    lateinit var adapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)


        observeUserList()
        observeAdapterState()


    }

    private fun observeAdapterState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.adapter.collect{
                    when(it){
                        is UsersViewModel.Adapter.Idle->{}
                        is UsersViewModel.Adapter.Remove->{
                            //adaptere silme islemini notiy et (haber ver)
                            println("removed Index: ${it.position}")
                            //adapter.notifyItemRemoved(it.position)
                            adapter.notifyDataSetChanged()
                        }
                        is UsersViewModel.Adapter.Add->{
                            //ekleme islemini notify

                        }
                    }
                }
            }
        }
    }

    private fun observeUserList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.userList.collect{users->
                    adapter = UsersAdapter(this@UsersActivity, users){position->
                        viewModel.removeItem(position)
                    }
                    binding.rvUser.adapter = adapter
                }
            }
        }
    }
}