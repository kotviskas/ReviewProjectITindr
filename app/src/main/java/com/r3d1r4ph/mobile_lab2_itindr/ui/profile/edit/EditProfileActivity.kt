package com.r3d1r4ph.mobile_lab2_itindr.ui.profile.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.ActivityEditProfileBinding
import com.r3d1r4ph.mobile_lab2_itindr.ui.BaseActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.profile.ProfileInfoFragment
import com.r3d1r4ph.mobile_lab2_itindr.utils.interfaces.SaveButtonOnClick

class EditProfileActivity : BaseActivity(), SaveButtonOnClick {

    private val viewBinding by viewBinding(ActivityEditProfileBinding::bind, R.id.rootLayout)
    private lateinit var saveButtonOnClick: () -> Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        supportFragmentManager.commit {
            replace(
                R.id.editProfileFragmentContainer,
                ProfileInfoFragment.newInstance(ProfileInfoFragment.PROFILE_EDIT_MODE),
                ProfileInfoFragment.TAG
            )
        }
        configureToolbar()
        viewBinding.editProfileSaveButton.setOnClickListener {
            saveButtonOnClick.invoke()
        }
    }

    private fun configureToolbar() {
        setSupportActionBar(viewBinding.editProfileToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewBinding.editProfileToolbar.navigationIcon?.apply {
            colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(
                    this@EditProfileActivity,
                    R.color.pink
                ), BlendModeCompat.SRC_ATOP
            )
        }
        viewBinding.editProfileToolbar.setNavigationOnClickListener { finish() }
    }

    override fun setSaveButtonOnClick(onClick: () -> Unit) {
        saveButtonOnClick = onClick
    }
}