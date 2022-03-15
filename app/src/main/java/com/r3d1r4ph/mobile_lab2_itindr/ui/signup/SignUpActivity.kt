package com.r3d1r4ph.mobile_lab2_itindr.ui.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.ActivitySignUpBinding
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.AuthParams
import com.r3d1r4ph.mobile_lab2_itindr.ui.BaseActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.profile.create.CreateProfileActivity
import com.r3d1r4ph.mobile_lab2_itindr.utils.SharedPreferencesUtils


class SignUpActivity : BaseActivity() {

    private val viewBinding by viewBinding(ActivitySignUpBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setButtonClickListeners()
        setObserver()
    }

    private fun setObserver() {
        viewModel.goToNextScreen.observe(this) {
            if (it) {
                val intent = Intent(this, CreateProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setButtonClickListeners() {
        viewBinding.signUpRegisterButton.setOnClickListener {
            viewModel.register(email = viewBinding.signUpEmailTextInputEditText.text.toString(),
                password = viewBinding.signUpPasswordTextInputEditText.text.toString(),
                confirmedPassword = viewBinding.signUpRepeatPasswordTextInputEditText.text.toString(),
                errorOutput = {
                    Toast.makeText(this, resources.getString(it), Toast.LENGTH_LONG).show()
                },
                onFailure = { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() })
        }

        viewBinding.signUpBackButton.setOnClickListener {
            finish()
        }
    }
}