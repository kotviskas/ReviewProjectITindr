package com.r3d1r4ph.mobile_lab2_itindr.ui.chat.messagerecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.ItemRecyclerIncomingMessageBinding
import com.r3d1r4ph.mobile_lab2_itindr.databinding.ItemRecyclerOutgoingMessageBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(private val selfId: String) :
    ListAdapter<MessageItem, RecyclerView.ViewHolder>(DIFF) {

    private companion object {
        val DIFF = object : DiffUtil.ItemCallback<MessageItem>() {
            override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem) =
                oldItem.messageId == newItem.messageId


            override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem) =
                oldItem == newItem

        }
    }

    private val outgoingViewType = R.layout.item_recycler_outgoing_message
    private val incomingViewType = R.layout.item_recycler_incoming_message

    override fun getItemViewType(position: Int) =
        if (getItem(position).userId == selfId) {
            outgoingViewType
        } else {
            incomingViewType
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            outgoingViewType -> OutgoingViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
            else -> IncomingViewHolder(
                LayoutInflater.from(parent.context).inflate(viewType, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBind).bind(getItem(position))
    }

    inner class OutgoingViewHolder(view: View) : RecyclerView.ViewHolder(view), ViewHolderBind {
        private val binding = ItemRecyclerOutgoingMessageBinding.bind(view)

        override fun bind(item: MessageItem) {
            //todo
            item.avatar?.let {
                with(binding.outgoingAvatarImageView) {
                    load(it)
                    clipToOutline = true
                }
            }

            binding.outgoingDataTextView.text = getDateLocaleFromUTC(item.data)

            if (item.text != null) {
                with(binding.outgoingMessageTextView) {
                    text = item.text
                    visibility = View.VISIBLE
                }
            } else {
                binding.outgoingMessageTextView.visibility = View.GONE
            }

            if (item.attachments.isNotEmpty()) {
                with(binding.outgoingImageImageView) {
                    load(item.attachments[0])
                    clipToOutline = true
                    visibility = View.VISIBLE
                }
            } else {
                binding.outgoingImageImageView.visibility = View.GONE
            }
        }
    }

    inner class IncomingViewHolder(view: View) : RecyclerView.ViewHolder(view), ViewHolderBind {
        private val binding = ItemRecyclerIncomingMessageBinding.bind(view)

        override fun bind(item: MessageItem) {
            item.avatar?.let {
                with(binding.incomingAvatarImageView) {
                    load(it)
                    clipToOutline = true
                }
            }
            binding.incomingDataTextView.text = getDateLocaleFromUTC(item.data)

            if (item.text != null) {
                with(binding.incomingMessageTextView) {
                    text = item.text
                    visibility = View.VISIBLE
                }
            } else {
                binding.incomingMessageTextView.visibility = View.GONE
            }

            if (item.attachments.isNotEmpty()) {
                with(binding.incomingImageImageView) {
                    load(item.attachments[0])
                    clipToOutline = true
                    visibility = View.VISIBLE
                }
            } else {
                binding.incomingImageImageView.visibility = View.GONE
            }
        }
    }

    private fun getDateLocaleFromUTC(date: String): String {
        var time = ""
        val utcFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        utcFormatter.timeZone = TimeZone.getTimeZone("UTC")
        var gpsUTCDate: Date? = null
        try {
            gpsUTCDate = utcFormatter.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val localFormatter = SimpleDateFormat("HH:mm • dd MMMM yyyy", Locale("ru", "RU"))
        localFormatter.timeZone = TimeZone.getDefault()
        gpsUTCDate?.let {
            time = localFormatter.format(it.time)
        }
        return time
    }

    fun interface ViewHolderBind {
        fun bind(item: MessageItem)
    }
}