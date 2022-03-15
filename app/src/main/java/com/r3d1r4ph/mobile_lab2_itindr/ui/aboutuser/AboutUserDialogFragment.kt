package com.r3d1r4ph.mobile_lab2_itindr.ui.aboutuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.google.android.material.chip.Chip
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.DialogFragmentAboutUserBinding
import com.r3d1r4ph.mobile_lab2_itindr.domain.ProfileData
import com.r3d1r4ph.mobile_lab2_itindr.utils.extensions.setSystemMarginBottom
import com.r3d1r4ph.mobile_lab2_itindr.utils.extensions.setSystemMarginTop
import com.r3d1r4ph.mobile_lab2_itindr.utils.viewmodel.ArgumentsViewModelFactory


class AboutUserDialogFragment : DialogFragment() {

    companion object {
        val TAG: String = AboutUserDialogFragment::class.java.simpleName
        fun newInstance(userId: String) = AboutUserDialogFragment().apply {
            val bundle = Bundle()
            bundle.putSerializable(AboutUserViewModel.USER_ID_KEY, userId)
            arguments = bundle
        }
    }

    private val viewBinding by viewBinding(DialogFragmentAboutUserBinding::bind)
    private val viewModel by viewModels<AboutUserViewModel> {
        ArgumentsViewModelFactory(
            requireActivity().application,
            arguments
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.dialog_fragment_about_user,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureUI()
        observeUserProfile()
    }

    private fun configureUI() {
        configureWindow()
        configureBackArrow()
    }

    private fun configureWindow() {
        requireDialog().window?.setWindowAnimations(
            R.style.DialogAnimation
        )
        requireDialog().window?.let { WindowCompat.setDecorFitsSystemWindows(it, false) }

        requireDialog().window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.black)

        requireActivity().window?.let {
            it.decorView.systemUiVisibility =
                it.decorView.systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun configureBackArrow() =
        with(viewBinding.aboutUserBackImageButton) {
            setOnClickListener {
                requireActivity().window?.let {
                    it.decorView.systemUiVisibility =
                        it.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                dismiss()
            }
            setSystemMarginTop()
        }

    private fun observeUserProfile() {

        viewModel.userData.observe(this) { profile ->
            inflateViewByProfileInfo(profile)

            when {
                viewBinding.aboutUserChipGroup.visibility == View.VISIBLE -> {
                    viewBinding.aboutUserChipGroup.setSystemMarginBottom()
                }
                viewBinding.aboutUserDescriptionTextView.visibility == View.VISIBLE -> {
                    viewBinding.aboutUserDescriptionTextView.setSystemMarginBottom()
                }
                else -> {
                    viewBinding.aboutUserNameTextView.setSystemMarginBottom()
                }
            }
        }
    }

    private fun inflateViewByProfileInfo(profileData: ProfileData) { //TODO change
        viewBinding.aboutUserNameTextView.text = profileData.name
        if (profileData.avatar != null) {
            viewBinding.aboutUserBackgroundImageView.load(
                profileData.avatar
            )
        } else {
            viewBinding.aboutUserBackgroundImageView
                .setImageResource(R.drawable.bg_defaulf_avatar_fullscreen)
        }

        viewBinding.aboutUserDescriptionTextView.text =
            when (profileData.aboutMyself) {
                null -> {
                    viewBinding.aboutUserDescriptionTextView.visibility = View.GONE
                    ""
                }
                else -> profileData.aboutMyself
            }

        viewBinding.aboutUserNameTextView.text = profileData.name
        viewBinding.aboutUserChipGroup.removeAllViews()

        if (profileData.topics.isEmpty()) {
            viewBinding.aboutUserChipGroup.visibility = View.GONE
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
            viewBinding.aboutUserChipGroup.addView(chip)
        }
    }
}