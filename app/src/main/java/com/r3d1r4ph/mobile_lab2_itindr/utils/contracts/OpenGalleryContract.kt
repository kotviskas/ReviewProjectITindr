package com.r3d1r4ph.mobile_lab2_itindr.utils.contracts

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract

class OpenGalleryContract : ActivityResultContract<String, Uri?>() {

    override fun createIntent(context: Context, input: String): Intent {
        return Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        ).setType(input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (intent == null || intent.data == null) return null
        return intent.data
    }
}