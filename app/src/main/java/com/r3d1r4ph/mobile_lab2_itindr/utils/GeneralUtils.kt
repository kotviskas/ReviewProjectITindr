package com.r3d1r4ph.mobile_lab2_itindr.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.ResourcesCompat
import coil.load
import com.google.android.material.chip.Chip
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.IncludeProfileInfoBinding
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

object GeneralUtils {

    fun inflateProfileInfoIncludeWithPlaceholder(
        binding: IncludeProfileInfoBinding,
        layoutInflater: LayoutInflater
    ) {
        inflateProfileInfoInclude(
            binding,
            ProfileData(
                userId = "",
                name = layoutInflater.context.resources.getString(R.string.profile_placeholder_username),
                aboutMyself = layoutInflater.context.resources.getString(R.string.profile_placeholder_about_myself),
                avatar = null,
                topics = emptyList()
            ),
            layoutInflater
        )
    }

    fun inflateProfileInfoInclude(
        binding: IncludeProfileInfoBinding,
        profileData: ProfileData,
        layoutInflater: LayoutInflater
    ) {
        if (profileData.avatar != null) {
            binding.profileAvatarImageView.load(
                profileData.avatar
            )
        } else {
            binding.profileAvatarImageView
                .setImageResource(R.drawable.ic_default_avatar)
        }

        binding.aboutUserTextView.text =
            when (profileData.aboutMyself) {
                null -> ""
                else -> profileData.aboutMyself
            }

        binding.profileNameTextView.text = profileData.name
        binding.profileTopicsChipGroup.removeAllViews()

        if (profileData.topics.isEmpty()) {
            binding.profileTopicsChipGroup.visibility = View.GONE
        } else {
            binding.profileTopicsChipGroup.visibility = View.VISIBLE
        }
        for (topic in profileData.topics) {
            val chip = layoutInflater.inflate(R.layout.chip_template, null) as Chip
            chip.tag = topic.id
            chip.text = topic.title
            chip.isChecked = true
            chip.isCheckable = false
            chip.isClickable = false
            chip.isFocusable = false
            chip.typeface =
                ResourcesCompat.getFont(layoutInflater.context, R.font.montserrat_bold_700)
            binding.profileTopicsChipGroup.addView(chip)
        }
    }

    fun multipartBodyPartFromUri(context: Context, avatarUri: Uri): MultipartBody.Part {
        val bitmap = try {
            BitmapFactory.decodeStream(
                context.contentResolver.openInputStream(avatarUri)
            )
        } catch (e: Exception) {
            null
        }

        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val byteArray = stream.toByteArray()
        val reqFile = byteArray.toRequestBody("image/*".toMediaType())

//            var multipartBody: MultipartBody.Part? = null
//            requireContext().contentResolver.openInputStream(avatarUri)?.use { inputStream ->
//                multipartBody = MultipartBody.Part.createFormData(
//                    "avatar", getAbsoluteFileName(avatarUri),
//                    inputStream.readBytes().toRequestBody("image/*".toMediaType())
//                )
//            }

        return MultipartBody.Part.createFormData(
            "avatar",
            getAbsoluteFileName(context, avatarUri),
            reqFile
        )
    }

    private fun getAbsoluteFileName(context: Context, uri: Uri): String {
        var fileName = ""
        context.contentResolver.query(
            uri,
            arrayOf(MediaStore.MediaColumns.DISPLAY_NAME),
            null,
            null,
            null
        )
            ?.use { metaCursor ->
                if (metaCursor.moveToFirst()) {
                    fileName = metaCursor.getString(0)
                }
            }
        return fileName
    }
}