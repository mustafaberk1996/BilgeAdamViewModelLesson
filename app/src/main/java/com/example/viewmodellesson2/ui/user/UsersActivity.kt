package com.example.viewmodellesson2.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.viewmodellesson2.data.model.User
import com.example.viewmodellesson2.data.state.AdapterState
import com.example.viewmodellesson2.ui.adapter.UsersAdapter
import com.example.viewmodellesson2.databinding.ActivityUsersBinding
import com.example.viewmodellesson2.ui.UserDetailActivity
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
                    adapter = UsersAdapter(this@UsersActivity, users, this@UsersActivity::onClick){user,position->
                        viewModel.removeItem(user)
                    }
                    binding.rvUser.adapter = adapter
                }
            }
        }
    }

    companion object{
        const val REQUEST_CODE_FOR_DELETE = 1
        const val USER = "user"
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_FOR_DELETE && resultCode == RESULT_OK){
            data?.getParcelableExtra<User>(USER)?.let {removedUser->
                viewModel.removeItem(removedUser)
            }
        }
    }
    private fun onClick(user: User){
       val intent =  Intent(this, UserDetailActivity::class.java)
        intent.putExtra(USER,user)
        startActivityForResult(intent, REQUEST_CODE_FOR_DELETE)
    }
}