package com.rchyn.githubapp.adapter

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
) : ListAdapter<User, ListUserAdapter.ListUserViewHolder>(DiffCallback) {
    private lateinit var ctx: Context

    inner class ListUserViewHolder(
        binding: ItemRowUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val root = binding.root
        private val ivAvatar = binding.ivAvatar
        private val tvUsername = binding.tvUsername
        private val tvType = binding.tvType

        fun bind(user: User) {
            ivAvatar.load(user.avatar)
            tvUsername.text = user.login
            tvType.text = user.type

            root.setOnClickListener {
                onClickItem(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        ctx = parent.context
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(ctx), parent, false)
        return ListUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User) =
            oldItem.id == newItem.id
    }
}