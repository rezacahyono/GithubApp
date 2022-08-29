package com.rchyn.githubapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
        private val tvName = binding.tvName
        private val tvUsername = binding.tvUsername
        private val tvLocation = binding.tvLocation

        fun bind(user: User) {
            val imageRes = ctx.resources.getIdentifier(user.avatar, null, ctx.packageName)
            ivAvatar.setImageResource(imageRes)
            tvName.text = user.name
            tvUsername.text = user.username
            tvLocation.text = user.location

            root.setOnClickListener {
                onClickItem(user)
            }
        }
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.name == newItem.name
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
}