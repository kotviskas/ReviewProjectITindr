package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.people.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.ItemRecyclerUserBinding
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData

class UserPagingAdapter(private val listener: UserAdapterListener) :
    PagingDataAdapter<ProfileData, UserPagingAdapter.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<ProfileData>() {
            override fun areItemsTheSame(
                oldItem: ProfileData,
                newItem: ProfileData
            ) = oldItem.userId == newItem.userId

            override fun areContentsTheSame(
                oldItem: ProfileData,
                newItem: ProfileData
            ) = oldItem == newItem
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
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private  val binding = ItemRecyclerUserBinding.bind(view)

        init {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { it1 -> listener.onItemClick(it1) }
            }
        }

        fun bind(userItem: ProfileData) = with(binding) {
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
        fun onItemClick(item: ProfileData)
    }
}