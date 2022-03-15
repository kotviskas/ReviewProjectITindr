package com.r3d1r4ph.mobile_lab2_itindr.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.LifecycleOwner
import com.r3d1r4ph.mobile_lab2_itindr.BuildConfig
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.utils.contracts.OpenGalleryContract
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ImagePicker(
    activityResultRegistry: ActivityResultRegistry,
    lifecycleOwner: LifecycleOwner,
    fromCameraCallback: (result: Boolean) -> Unit,
    fromGalleryCallback: (imageUri: Uri?) -> Unit
) {
    companion object {
        private const val MIMETYPE_IMAGES = "image/*"
        private const val CAMERA_KEY = "Camera key"
        private const val GALLERY_KEY = "Gallery key"
        var currentAvatarUri: Uri = Uri.EMPTY
    }

    private val pickGalleryImageLauncher: ActivityResultLauncher<String> =
        activityResultRegistry.register(
            GALLERY_KEY,
            lifecycleOwner,
            OpenGalleryContract(),
            fromGalleryCallback
        )

    private val takeCameraPictureLauncher = activityResultRegistry.register(
        CAMERA_KEY,
        lifecycleOwner,
        ActivityResultContracts.TakePicture(),
        fromCameraCallback
    )

    fun pickGalleryImage() {
        pickGalleryImageLauncher.launch(MIMETYPE_IMAGES)
    }

    fun takeCameraPhoto(context: Context) {
        dispatchTakePicture(context)
    }

    private fun dispatchTakePicture(context: Context) {
        val photoFile: File? = try {
            createImageFile(context)
        } catch (ex: IOException) {
            Toast.makeText(
                context,
                context.getString(R.string.taking_photo_error),
                Toast.LENGTH_SHORT
            )
                .show()
            null
        }

        photoFile?.let {
            currentAvatarUri = FileProvider.getUriForFile(
                context,
                "${BuildConfig.APPLICATION_ID}.provider",
                it
            )
            takeCameraPictureLauncher.launch(currentAvatarUri)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(context: Context): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = context.cacheDir
        return File.createTempFile(
            "JPEG_Avatar${timeStamp}_",
            ".jpg",
            storageDir
        )
    }
}