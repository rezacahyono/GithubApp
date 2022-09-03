package com.rchyn.githubapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rchyn.githubapp.databinding.ItemRowUserBinding
import com.rchyn.githubapp.model.User

class ListUserAdapter(
    private val onClickItem: (user: User) -> Unit
) : RecyclerView.Adapter<ListUserAdapter.ListUserViewHolder>() {
    private lateinit var ctx: Context
    private var listUser: ArrayList<User> = arrayListOf()

    inner class ListUserViewHolder(
        binding: ItemRowUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val root = binding.root
        private val ivAvatar = binding.ivAvatar
        private val tvUsername = binding.tvUsername
        private val tvType = binding.tvType

        fun bind(user: User) {
            ivAvatar.load(user.avatar) {
                crossfade(true)
            }
            tvUsername.text = user.login
            tvType.text = user.type

            root.setOnClickListener {
                onClickItem(user)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(users: List<User>?) {
        if (users.isNullOrEmpty()) return
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        ctx = parent.context
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(ctx), parent, false)
        return ListUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        val data = listUser[position]
        holder.bind(data)
    }

    override fun getItemCount() = listUser.size

}