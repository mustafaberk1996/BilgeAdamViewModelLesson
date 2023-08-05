package com.example.viewmodellesson2.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.viewmodellesson2.data.model.User
import com.example.viewmodellesson2.databinding.UserListItemBinding

class UsersAdapter(private val context:Context, private val users:List<User>,val onClick:(user:User)->Unit , val onRemove:(user: User, position:Int)->Unit):RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    class MyViewHolder(binding:UserListItemBinding):RecyclerView.ViewHolder(binding.root){
        val tvUserFullName = binding.tvUserFullName
        val ivUser = binding.ivUser
        val ivRemove = binding.ivRemove
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            UserListItemBinding.inflate(LayoutInflater.from(context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val user = users[position]
        holder.tvUserFullName.text = "$position, ${user.name} ${user.surname}"
        holder.ivUser.load(user.profileImageUrl)


        holder.itemView.setOnClickListener {
            onClick(user)
        }

        holder.ivRemove.setOnClickListener {
            println("adapterden gelen  position: $position")
            onRemove(user,position)
        }
    }
}