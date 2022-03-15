package com.r3d1r4ph.mobile_lab2_itindr.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.database.Database
import com.r3d1r4ph.mobile_lab2_itindr.databinding.ActivityOnboardingBinding
import com.r3d1r4ph.mobile_lab2_itindr.repository.auth.AuthDBRepository
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileService
import com.r3d1r4ph.mobile_lab2_itindr.ui.BaseActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.login.LogInActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.MenuActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.signup.SignUpActivity
import com.r3d1r4ph.mobile_lab2_itindr.utils.SharedPreferencesUtils
import com.r3d1r4ph.mobile_lab2_itindr.utils.viewmodel.IntentViewModelFactory


class OnboardingActivity : BaseActivity() {

    private val viewBinding by viewBinding(ActivityOnboardingBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<OnboardingViewModel> {
        IntentViewModelFactory(
            application,
            intent
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        setButtonClickListeners()
        setObservers()
        viewModel.checkAuthorization {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setObservers() {
        viewModel.authorized.observe(this) {
            if (it) {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setButtonClickListeners() {
        viewBinding.onboardingSignUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        viewBinding.onboardingLogInButton.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
    }
}