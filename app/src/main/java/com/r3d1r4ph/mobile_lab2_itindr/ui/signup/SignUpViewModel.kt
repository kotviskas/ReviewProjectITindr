package com.r3d1r4ph.mobile_lab2_itindr.ui.signup

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.ui.login.AuthRepository
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.ResultWrapper
import kotlinx.coroutines.launch

class SignUpViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = AuthRepository(app)

    private val _goToNextScreen = MutableLiveData<Boolean>()
    val goToNextScreen: LiveData<Boolean>
        get() = _goToNextScreen

    fun register(
        email: String,
        password: String,
        confirmedPassword: String,
        errorOutput: (stringId: Int) -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        if (!isValidationCompleted(email, password, confirmedPassword, errorOutput)) return
        viewModelScope.launch {
            when (val response = repository.register(email, password)) {
                is ResultWrapper.Success -> {
                    response.value?.let {
                        repository.resetTokens(it.toEntity())
                        when (val profile = repository.getProfile()) {
                            is ResultWrapper.Success -> {
                                profile.value?.let { resProfile ->
                                    repository.addProfile(resProfile)
                                }
                            }
                            is ResultWrapper.Error -> profile.error?.let(onFailure)
                        }
                        _goToNextScreen.value = true
                    }
                }
                is ResultWrapper.Error -> response.error?.let(onFailure)
            }
        }
    }

    private fun isValidationCompleted(
        email: String,
        password: String,
        confirmedPassword: String,
        errorOutput: (stringId: Int) -> Unit
    ): Boolean {
        if (email.isEmpty()) {
            errorOutput.invoke(R.string.error_email_is_empty)
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) {
            errorOutput.invoke(R.string.error_not_email_type)
            return false
        }
        if (password.length < 8) {
            errorOutput.invoke(R.string.error_small_password_length)
            return false
        }
        if (password
            != confirmedPassword
        ) {
            errorOutput.invoke(R.string.error_different_passwords)
            return false
        }
        return true
    }
}