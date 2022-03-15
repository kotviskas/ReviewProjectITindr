package com.r3d1r4ph.mobile_lab2_itindr.ui.chat

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.ActivityChatBinding
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat.message.MessageController
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat.message.MessageResponse
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileService
import com.r3d1r4ph.mobile_lab2_itindr.ui.BaseActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.chat.messagerecycler.EndlessRecyclerViewScrollListener
import com.r3d1r4ph.mobile_lab2_itindr.ui.chat.messagerecycler.MessageAdapter
import com.r3d1r4ph.mobile_lab2_itindr.ui.chat.messagerecycler.MessageItem
import com.r3d1r4ph.mobile_lab2_itindr.ui.chat.messagerecycler.MessageItemDecorator
import com.r3d1r4ph.mobile_lab2_itindr.utils.GeneralUtils
import com.r3d1r4ph.mobile_lab2_itindr.utils.ImagePicker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody


class ChatActivity : BaseActivity() {

    private companion object {
        private const val MESSAGE_QUANTITY_PER_REQUEST = 20
    }

    private var chatId: String = ""
    private val viewBinding by viewBinding(ActivityChatBinding::bind, R.id.rootLayout)
    private var messageAdapter: MessageAdapter? = null
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    private val messageController = MessageController()
    private val profileController = ProfileService()
    private var messageHistory = listOf<MessageItem>()

    private var pinnedImageUri: Uri? = null
    private lateinit var imagePicker: ImagePicker

    private val openGalleryHandler = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGrantedCheck(
            granted,
            { imagePicker.pickGalleryImage() },
            Manifest.permission.READ_EXTERNAL_STORAGE,
            R.string.permission_denied_gallery
        )
    }

    private val openCameraHandler = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGrantedCheck(
            granted,
            { imagePicker.takeCameraPhoto(this) },
            Manifest.permission.CAMERA,
            R.string.permission_denied_camera
        )
    }

    private fun permissionGrantedCheck(
        granted: Boolean,
        pickImage: () -> Unit,
        permission: String,
        permissionDenied: Int
    ) {
        when {
            granted -> {
                pickImage()
            }
            else -> {
                permissionDeniedOutput(
                    permission,
                    permissionDenied
                )
            }
        }
    }

    private fun permissionDeniedOutput(permission: String, permissionDenied: Int) {
        if (!shouldShowRequestPermissionRationale(permission)) {
            Toast.makeText(
                this,
                getString(R.string.permission_denied_with_check_mark),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                getString(permissionDenied),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        initializeImagePicker()
        configureUI()
        setOnClickListeners()
        intent.getStringExtra(ChatViewModel.CHAT_ID_KEY)?.let {
            chatId = it
            //doRequestsToServer(it)
        }

        lifecycleScope.launch {
            while (true) {
                delay(30000)
                updateMessageHistory(chatId, true)
            }
        }
    }

    private fun initializeImagePicker() {
        imagePicker = ImagePicker(
            activityResultRegistry,
            this,
            { result ->
                if (result) {
                    pinImageByUri(ImagePicker.currentAvatarUri)
                }
            },
            { imageUri ->
                imageUri?.let { uri ->
                    pinImageByUri(uri)
                }
            })
    }

    private fun configureUI() {
        with(viewBinding.chatPinnedMessageImageView) {
            visibility = View.GONE
        }

        configureToolbar()
        configureRecyclerView()
    }

    private fun configureToolbar() {
        setSupportActionBar(viewBinding.chatToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

//        viewBinding.chatToolbarTitleTextView.text =
//            intent.getStringExtra(ChatViewModel.CHAT_USERNAME_KEY)
//
//        intent.getStringExtra(ChatViewModel.CHAT_AVATAR_KEY)
//            ?.let {
//                with(viewBinding.chatToolbarAvatarImageView) {
//                    load(it)
//                    clipToOutline = true
//                }
//            }

        viewBinding.chatToolbar.navigationIcon?.apply {
            colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(
                    this@ChatActivity,
                    R.color.pink
                ), BlendModeCompat.SRC_ATOP
            )
        }
        viewBinding.chatToolbar.setNavigationOnClickListener { finish() }
    }

    private fun configureRecyclerView() = with(viewBinding) {
        chatMessagesRecyclerView.apply {
            val linearLayoutManager =
                LinearLayoutManager(this@ChatActivity, LinearLayoutManager.VERTICAL, true)
            layoutManager = linearLayoutManager
            scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    updateMessageHistory(chatId, false, offset = messageHistory.size)
                }
            }

            addItemDecoration(MessageItemDecorator())
            addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)
        }
    }

    private fun setOnClickListeners() {

        viewBinding.chatMessageSendImageButton.setOnClickListener {
            if (!viewBinding.chatMessageFieldTextInputEditText.text.isNullOrBlank() || pinnedImageUri != null) {

                val messageText =
                    if (!viewBinding.chatMessageFieldTextInputEditText.text.isNullOrBlank()) {
                        MultipartBody.Part.createFormData(
                            "messageText",
                            viewBinding.chatMessageFieldTextInputEditText.text.toString()
                        )
                    } else {
                        null
                    }

                var attachments: MultipartBody.Part? = null
                pinnedImageUri?.let {
                    attachments = GeneralUtils.multipartBodyPartFromUri(this, it)
                }

                viewBinding.chatMessageFieldTextInputEditText.setText("")
                messageController.postMessage(
                    chatId = chatId,
                    messageText = messageText,
                    attachments = attachments,
                    onSuccess = {
                        updateMessageHistory(chatId, true)
                        unpinImage()
                    },
                    onFailure = {
                        Toast.makeText(
                            this,
                            it,
                            Toast.LENGTH_SHORT
                        ).show()
                    })
            }
        }

        viewBinding.chatPhotoAttachImageButton.setOnClickListener {
            if (pinnedImageUri == null) {
                createDialogToPickImage()
            } else {
                unpinImage()
            }
        }
    }

    private fun pinImageByUri(uri: Uri) {
        pinnedImageUri = uri
        with(viewBinding.chatPinnedMessageImageView) {
            load(uri)
            visibility = View.VISIBLE
        }
        viewBinding.chatPhotoAttachImageButton.load(R.drawable.ic_unpin_image)
    }

    private fun unpinImage() {
        pinnedImageUri = null
        viewBinding.chatPinnedMessageImageView.visibility = View.GONE
        viewBinding.chatPhotoAttachImageButton.load(R.drawable.ic_send_photo)
    }

    private fun createDialogToPickImage() {
        val options = arrayOf(
            getString(R.string.dialog_take_photo),
            getString(R.string.dialog_open_photo),
            getString(R.string.dialog_dismiss)
        )

        val builder = AlertDialog.Builder(this)

        builder.setItems(options) { dialog, item ->
            when (options[item]) {
                getString(R.string.dialog_take_photo) -> {
                    openCameraHandler.launch(Manifest.permission.CAMERA)
                }
                getString(R.string.dialog_open_photo) -> {
                    openGalleryHandler.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                getString(R.string.dialog_dismiss) -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

//    private fun doRequestsToServer(chatId: String) {
//        profileController.getProfile(onSuccess = {
//            messageAdapter = MessageAdapter(selfId = it.userId)
//            viewBinding.chatMessagesRecyclerView.adapter = messageAdapter
//            updateMessageHistory(chatId = chatId, true)
//        }, onFailure = {
//            Toast.makeText(
//                this,
//                it,
//                Toast.LENGTH_SHORT
//            ).show()
//        })
//    }

    @Synchronized
    private fun updateMessageHistory(chatId: String, showNewMessages: Boolean, offset: Int = 0) {
        messageController.getMessage(
            chatId = chatId,
            limit = MESSAGE_QUANTITY_PER_REQUEST,
            offset = if (showNewMessages) 0 else offset,
            onSuccess = { messageList ->
                if (showNewMessages) {
                    showMessages(messageList)
                } else {
                    showPreviousMessages(messageList)
                }
            },
            onFailure = {
                Toast.makeText(
                    this,
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }

    private fun showPreviousMessages(messageList: List<MessageResponse>) {
        val messageItems = mutableListOf<MessageItem>()
        if (messageHistory.isNotEmpty()) {
            for (message in messageList) {
                if (message.id != messageHistory.last().messageId) {
                    messageItems.add(createMessageItem(message))
                } else {
                    messageItems.clear()
                    break
                }
            }
        } else {
            for (message in messageList) {
                messageItems.add(createMessageItem(message))
            }
        }
        messageHistory = messageHistory.plus(messageItems)
        messageAdapter?.submitList(messageHistory)
    }

    private fun showMessages(messageList: List<MessageResponse>) {
        val messageItems = mutableListOf<MessageItem>()
        if (messageHistory.isNotEmpty()) {
            for (message in messageList) {
                if (message.id != messageHistory[0].messageId) {
                    messageItems.add(createMessageItem(message))
                } else {
                    break
                }
            }
        } else {
            for (message in messageList) {
                messageItems.add(createMessageItem(message))
            }
        }
        messageHistory = messageItems.plus(messageHistory)
        messageAdapter?.submitList(messageHistory)
    }

    private fun createMessageItem(message: MessageResponse) = MessageItem(
        messageId = message.id,
        userId = message.user.userId,
        avatar = message.user.avatar,
        text = message.text,
        data = message.createdAt,
        attachments = message.attachments
    )
}