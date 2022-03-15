package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.chats.chatrecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.ItemRecyclerChatBinding

class ChatAdapter(private val listener: ChatAdapterListener) :
    ListAdapter<ChatItem, ChatAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<ChatItem>() {
            override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem) =
                oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_recycler_chat, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemRecyclerChatBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                listener.onItemClick(getItem(bindingAdapterPosition))
            }
        }

        fun bind(chatItem: ChatItem) = with(binding) {
            itemChatsUsernameTextView.text = chatItem.username
            itemChatsMessageTextView.text = chatItem.lastMessage ?: ""

            if (chatItem.avatar != null) {
                itemChatsAvatarImageView.load(chatItem.avatar)
            } else {
                itemChatsAvatarImageView.setImageResource(R.drawable.ic_default_avatar)
            }
            itemChatsAvatarImageView.clipToOutline = true
        }
    }

    interface ChatAdapterListener {
        fun onItemClick(item: ChatItem)
    }
}