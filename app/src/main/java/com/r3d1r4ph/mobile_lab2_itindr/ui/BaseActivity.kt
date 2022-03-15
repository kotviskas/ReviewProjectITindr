package com.r3d1r4ph.mobile_lab2_itindr.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Database
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.repository.auth.AuthDBRepository
import com.r3d1r4ph.mobile_lab2_itindr.ui.onboarding.OnboardingActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.onboarding.OnboardingViewModel

abstract class BaseActivity : AppCompatActivity() {
    private val authDBRepository by lazy { AuthDBRepository(application) }
    private var authorized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar)
        window.decorView.systemUiVisibility =
            window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        observeTokens()
    }

    protected fun makeToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun observeTokens() {
        authDBRepository.getAuthParams().observe(this) { list ->
            if (list.isNotEmpty()) {
                authorized = true
            }
            if (list.isEmpty() && this !is OnboardingActivity && authorized) {
                val intent = Intent(this, OnboardingActivity::class.java)
                    .putExtra(OnboardingViewModel.CHECK_AUTHORIZATION, false)
                startActivity(intent)
            }
        }
    }
}