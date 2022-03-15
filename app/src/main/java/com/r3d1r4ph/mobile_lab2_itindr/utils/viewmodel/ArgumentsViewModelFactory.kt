package com.r3d1r4ph.mobile_lab2_itindr.utils.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.r3d1r4ph.mobile_lab2_itindr.ui.aboutuser.AboutUserViewModel
import com.r3d1r4ph.mobile_lab2_itindr.ui.match.MatchViewModel

class ArgumentsViewModelFactory(
    private val application: Application,
    private val arguments: Bundle?
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when(modelClass) {
        AboutUserViewModel::class.java -> AboutUserViewModel(application, arguments)
        MatchViewModel::class.java -> MatchViewModel(application, arguments)
        else -> throw IllegalArgumentException("Unknown ViewModel class")
    } as T

}