package com.example.viewmodellesson2.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodellesson2.data.state.AdapterState
import com.example.viewmodellesson2.ui.adapter.UsersAdapter
import com.example.viewmodellesson2.databinding.ActivityUsersBinding
import kotlinx.coroutines.launch

class UsersActivity : AppCompatActivity() {


    lateinit var binding: ActivityUsersBinding
    private val viewModel: UsersViewModel by viewModels()


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
                viewModel.adapterState.collect{
                    when(it){
                        is AdapterState.Idle ->{}
                        is AdapterState.Remove ->{
                            adapter.notifyItemRemoved(it.index)
                        }
                        is AdapterState.Add ->{

                        }
                        else->{}
                    }
                }
            }
        }
    }

    private fun observeUserList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.userList.collect{users->
                    adapter = UsersAdapter(this@UsersActivity, users){user,position->
                        viewModel.removeItem(user,position)
                    }
                    binding.rvUser.adapter = adapter
                }
            }
        }
    }
}