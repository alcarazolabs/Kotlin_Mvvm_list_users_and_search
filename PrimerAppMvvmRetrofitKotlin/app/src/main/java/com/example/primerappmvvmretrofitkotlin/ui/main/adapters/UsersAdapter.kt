package com.example.primerappmvvmretrofitkotlin.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo.core.BaseViewHolder
import com.example.primerappmvvmretrofitkotlin.data.model.User
import com.example.primerappmvvmretrofitkotlin.databinding.UserItemBinding

class UsersAdapter(
        private val usersList: List<User>,
        private val itemClickListener: OnUserClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnUserClickListener {
        fun onUserClick(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = UsersViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onUserClick(usersList[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is UsersViewHolder -> holder.bind(usersList[position])
        }
    }

    override fun getItemCount(): Int = usersList.size

    private inner class UsersViewHolder(val binding: UserItemBinding, val context: Context) : BaseViewHolder<User>(binding.root) {
        override fun bind(item: User) {
            binding.txtUserName.text="${item.name}"
            Glide.with(context).load("${item.image}").centerCrop().into(binding.imgUser)
            binding.txtUserEmail.text="Email: ${item.email}"
            binding.txtUserPhone.text="Phone: ${item.phone}"
        }
    }
}