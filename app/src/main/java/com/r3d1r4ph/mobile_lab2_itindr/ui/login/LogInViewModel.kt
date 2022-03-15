package com.r3d1r4ph.mobile_lab2_itindr.ui.login

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.*
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.ResultWrapper
import kotlinx.coroutines.launch

class LogInViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = AuthRepository(app)

    private val _goToNextScreen = MutableLiveData<Boolean>()
    val goToNextScreen: LiveData<Boolean>
        get() = _goToNextScreen

    fun login(
        email: String,
        password: String,
        errorOutput: (stringId: Int) -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        if (!isValidationCompleted(email, password, errorOutput)) return
            viewModelScope.launch {
                when (val response = repository.login(email, password)) {
                    is ResultWrapper.Success -> {
                        //todo
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
        return true
    }
}