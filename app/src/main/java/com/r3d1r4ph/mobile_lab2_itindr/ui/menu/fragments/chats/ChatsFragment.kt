package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.chats

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.FragmentChatsBinding
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat.ChatService
import com.r3d1r4ph.mobile_lab2_itindr.ui.chat.ChatActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.chat.ChatViewModel
import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.chats.chatrecycler.ChatAdapter
import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.chats.chatrecycler.ChatItem

class ChatsFragment : Fragment(R.layout.fragment_chats) {

    companion object {
        val TAG: String = ChatsFragment::class.java.simpleName
        fun newInstance() = ChatsFragment()
    }

    private val viewBinding by viewBinding(FragmentChatsBinding::bind)
    private val viewModel by viewModels<ChatsViewModel>()

    private val chatAdapterListener = object : ChatAdapter.ChatAdapterListener {
        override fun onItemClick(item: ChatItem) {
            val intent = Intent(
                requireContext(),
                ChatActivity::class.java
            )
                .putExtra(ChatViewModel.CHAT_ID_KEY, item.id)
            startActivity(intent)
        }
    }
    private val chatAdapter = ChatAdapter(chatAdapterListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()
        //doRequestsToServer()
        setObservers()
        //loadChats()
    }

    private fun configureRecyclerView() = with(viewBinding) {
        chatsRecyclerView.apply {
            adapter = chatAdapter
        }
    }

    private fun loadChats() {
        viewModel.loadChats { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
        //todo
//        chatController.getChat(onSuccess = { chatList ->
//            view?.let {
//                val chatItems = chatList.map { chat ->
//                    ChatItem(
//                        id = chat.chat.id,
//                        username = chat.chat.title,
//                        lastMessage = chat.lastMessage?.text,
//                        avatar = chat.chat.avatar
//                    )
//                }
//                chatAdapter.submitList(chatItems.reversed())
//            }
//        }, onFailure = {
//            view?.let { _ ->
//                Toast.makeText(
//                    requireContext(),
//                    it,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        })
    }

    private fun setObservers() {
        viewModel.chats.observe(this) { list ->
            chatAdapter.submitList(list.map { it.toChatItem() }.reversed())
        }
    }

    override fun onResume() {
        super.onResume()
        loadChats()
    }
}