package com.r3d1r4ph.mobile_lab2_itindr.ui.match

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.DialogFragmentMatchBinding
import com.r3d1r4ph.mobile_lab2_itindr.ui.chat.ChatActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.chat.ChatViewModel
import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.chats.ChatsFragment
import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.chats.ChatsViewModel
import com.r3d1r4ph.mobile_lab2_itindr.utils.extensions.setSystemMarginBottom
import com.r3d1r4ph.mobile_lab2_itindr.utils.viewmodel.ArgumentsViewModelFactory

class MatchDialogFragment : DialogFragment() {

    companion object {
        val TAG: String = MatchDialogFragment::class.java.simpleName
        fun newInstance(userId: String) = MatchDialogFragment().apply {
            val bundle = Bundle()
            bundle.putString(MatchViewModel.USER_ID_KEY, userId)
            arguments = bundle
        }
    }

    private val viewBinding by viewBinding(DialogFragmentMatchBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<MatchViewModel> {
        ArgumentsViewModelFactory(
            requireActivity().application,
            arguments
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.Theme_App_Dialog_FullScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.dialog_fragment_match,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireDialog().window?.setWindowAnimations(
            R.style.DialogAnimation
        )
        requireDialog().window?.let { WindowCompat.setDecorFitsSystemWindows(it, false) }

        configureUI()
        setOnClickListener()
        setObservers()
    }

    private fun configureUI() {
        viewBinding.matchWhiteMessageButton.setSystemMarginBottom()
    }

    private fun setOnClickListener() {
        viewBinding.matchWhiteMessageButton.setOnClickListener {
            viewModel.createNewChat {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setObservers() {
        viewModel.goToChatScreen.observe(this) {
            if (it) {
                val intent = Intent(requireContext(), ChatActivity::class.java)
                    .putExtra(
                        ChatViewModel.CHAT_ID_KEY,
                        viewModel.newChatId
                    )
                startActivity(intent)
                dismiss()
            }
        }
    }
}