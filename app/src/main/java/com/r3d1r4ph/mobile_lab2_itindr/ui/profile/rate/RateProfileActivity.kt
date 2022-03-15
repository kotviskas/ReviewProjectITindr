package com.r3d1r4ph.mobile_lab2_itindr.ui.profile.rate

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.LayoutProfileBinding
import com.r3d1r4ph.mobile_lab2_itindr.ui.BaseActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.match.MatchDialogFragment
import com.r3d1r4ph.mobile_lab2_itindr.utils.GeneralUtils
import com.r3d1r4ph.mobile_lab2_itindr.utils.viewmodel.IntentViewModelFactory

class RateProfileActivity : BaseActivity() {

    private val viewBinding by viewBinding(LayoutProfileBinding::bind, R.id.rootLayout)
    private var matchDialog: MatchDialogFragment? = null

    private val viewModel by viewModels<RateProfileViewModel> {
        IntentViewModelFactory(
            application,
            intent
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_profile)

        setObservers()
        configureUI()
        setOnClickListeners()
    }

    private fun setObservers() {
        observeProfileInclude()
        observeIsMutual()
    }

    private fun observeProfileInclude() {
        viewModel.userData.observe(this) {
            GeneralUtils.inflateProfileInfoInclude(
                viewBinding.profileProfileInfoInclude,
                it,
                layoutInflater
            )
        }
    }

    private fun observeIsMutual() {
        viewModel.likeIsMutual.observe(this) {
            if (it) {
                matchDialog =
                    MatchDialogFragment.newInstance(viewModel.userId)
                matchDialog?.show(
                    supportFragmentManager,
                    MatchDialogFragment.TAG
                )
            } else {
                finish()
            }
        }
    }

    private fun configureUI() {
        viewBinding.profileProfileInfoInclude.profileAvatarImageView.clipToOutline = true
        viewBinding.profileRateConstraintLayout.visibility = View.VISIBLE
        viewBinding.profileEditButton.visibility = View.GONE
        configureToolbar()
    }

    private fun configureToolbar() {
        setSupportActionBar(viewBinding.profileToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        viewBinding.profileToolbar.titleMarginStart =
            resources.getDimensionPixelSize(R.dimen.margin_32)
        viewBinding.profileToolbar.navigationIcon?.apply {
            colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                ContextCompat.getColor(
                    this@RateProfileActivity,
                    R.color.pink
                ), BlendModeCompat.SRC_ATOP
            )
        }
        viewBinding.profileToolbar.setNavigationOnClickListener { finish() }
    }

    private fun setOnClickListeners() {
        viewBinding.profileLikeButton.setOnClickListener {
            viewModel.likeUser(::makeToast)
        }

        viewBinding.profileCancelButton.setOnClickListener {
            viewModel.dislikeUser(::makeToast) {
                finish()
            }
        }
    }
}