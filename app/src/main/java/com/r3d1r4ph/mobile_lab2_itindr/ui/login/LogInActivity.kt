package com.r3d1r4ph.mobile_lab2_itindr.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.mobile_lab2_itindr.R
import com.r3d1r4ph.mobile_lab2_itindr.databinding.ActivityLogInBinding
import com.r3d1r4ph.mobile_lab2_itindr.ui.BaseActivity
import com.r3d1r4ph.mobile_lab2_itindr.ui.menu.MenuActivity

class LogInActivity : BaseActivity() {

    private val viewBinding by viewBinding(ActivityLogInBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<LogInViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        setButtonClickListeners()
        setObserver()
    }

    private fun setObserver() {
        viewModel.goToNextScreen.observe(this) {
            if (it) {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setButtonClickListeners() {
        viewBinding.logInEnterButton.setOnClickListener {
            viewModel.login(
                email = viewBinding.logInEmailTextInputEditText.text.toString(),
                password = viewBinding.logInPasswordTextInputEditText.text.toString(),
                { Toast.makeText(this, resources.getString(it), Toast.LENGTH_LONG).show() },
                { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
            )
            //todo
//            if (isValidationCompleted()) {
//                controller.login(
//                    AuthParams(
//                        email = viewBinding.logInEmailTextInputEditText.text.toString(),
//                        password = viewBinding.logInPasswordTextInputEditText.text.toString()
//                    ),
//                    onSuccess = {
//                        SharedPreferencesUtils.updateTokenInfo(it)
//                        val intent = Intent(this, MenuActivity::class.java)
//                        startActivity(intent)
//                    },
//                    onFailure = {
//                        Toast.makeText(
//                            this,
//                            it,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                )
//            }
        }
        viewBinding.logInBackButton.setOnClickListener {
            finish()
        }
    }

//    private fun isValidationCompleted(): Boolean {
//        if (viewBinding.logInEmailTextInputEditText.text.toString().isEmpty()) {
//            Toast.makeText(
//                this,
//                resources.getString(R.string.error_email_is_empty),
//                Toast.LENGTH_LONG
//            ).show()
//            return false
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(viewBinding.logInEmailTextInputEditText.text.toString())
//                .matches()
//        ) {
//            Toast.makeText(
//                this,
//                resources.getString(R.string.error_not_email_type),
//                Toast.LENGTH_LONG
//            ).show()
//            return false
//        }
//        if (viewBinding.logInPasswordTextInputEditText.text.toString().length < 8) {
//            Toast.makeText(
//                this,
//                resources.getString(R.string.error_small_password_length),
//                Toast.LENGTH_LONG
//            ).show()
//            return false
//        }
//        return true
//    }
}