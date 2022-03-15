package com.r3d1r4ph.mobile_lab2_itindr.utils.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.r3d1r4ph.mobile_lab2_itindr.ui.onboarding.OnboardingViewModel
import com.r3d1r4ph.mobile_lab2_itindr.ui.profile.rate.RateProfileViewModel

class IntentViewModelFactory(
    private val application: Application,
    private val intent: Intent
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when(modelClass) {
        RateProfileViewModel::class.java -> RateProfileViewModel(application, intent)
        OnboardingViewModel::class.java -> OnboardingViewModel(application, intent)
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T

}