package com.r3d1r4ph.mobile_lab2_itindr.ui.menu.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.LayoutProfileBinding
import com.r3d1r4ph.mobile_lab2_itindr.ui.profile.edit.EditProfileActivity
import com.r3d1r4ph.mobile_lab2_itindr.utils.GeneralUtils

class ProfileFragment : Fragment(R.layout.layout_profile) {

    companion object {
        val TAG: String = ProfileFragment::class.java.simpleName
        fun newInstance() = ProfileFragment()
    }

    private val viewBinding by viewBinding(LayoutProfileBinding::bind)
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.profileProfileInfoInclude.profileAvatarImageView.clipToOutline = true
        setOnClickListeners()
        setObserver()
    }

    private fun setOnClickListeners() {
        viewBinding.profileEditButton.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setObserver() {
        viewModel.profileData.observe(this) {
            GeneralUtils.inflateProfileInfoInclude(
                viewBinding.profileProfileInfoInclude,
                it,
                layoutInflater
            )
        }
    }
}