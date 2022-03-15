package com.r3d1r4ph.mobile_lab2_itindr.ui.profile.create

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.ActivityCreateProfileBinding
import com.r3d1r4ph.mobile_lab2_itindr.ui.BaseActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.profile.ProfileInfoFragment
import com.r3d1r4ph.mobile_lab2_itindr.utils.interfaces.SaveButtonOnClick


class CreateProfileActivity : BaseActivity(), SaveButtonOnClick {

    private val viewBinding by viewBinding(ActivityCreateProfileBinding::bind, R.id.rootLayout)
    private lateinit var saveButtonOnClick: () -> Unit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_profile)

        supportFragmentManager.commit {
            replace(
                R.id.createProfileFragmentContainer,
                ProfileInfoFragment.newInstance(ProfileInfoFragment.PROFILE_CREATE_MODE),
                ProfileInfoFragment.TAG
            )
        }
        viewBinding.createProfileSaveButton.setOnClickListener {
            saveButtonOnClick.invoke()
        }
    }

    override fun setSaveButtonOnClick(onClick: () -> Unit) {
        saveButtonOnClick = onClick
    }
}