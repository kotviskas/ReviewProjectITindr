package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.people.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.ItemRecyclerUserBinding

class UserAdapter(private val listener: UserAdapterListener) :
    ListAdapter<UserItem, UserAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem) =
                oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_recycler_user, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRecyclerUserBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(userItem: UserItem) = with(binding) {
            itemPeopleNameTextView.text = userItem.name
            //todo
            if (userItem.avatar != null) {
                itemPeopleAvatarImageView.load(userItem.avatar)
                itemPeopleAvatarImageView.clipToOutline = true
            } else {
                itemPeopleAvatarImageView.load(R.drawable.ic_default_avatar)
            }
        }
    }

    interface UserAdapterListener {
        fun onItemClick(item: UserItem)
    }
}