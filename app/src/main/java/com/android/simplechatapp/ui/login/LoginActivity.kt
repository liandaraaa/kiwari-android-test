package com.android.simplechatapp.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.simplechatapp.R
import com.android.simplechatapp.datasource.FirebaseAuthDataSource
import com.android.simplechatapp.datasource.OnFirebaseAuthListener
import com.android.simplechatapp.ui.main.MainActivity
import com.android.simplechatapp.utils.gone
import com.android.simplechatapp.utils.showToast
import com.android.simplechatapp.utils.visible
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), OnFirebaseAuthListener {

    private val authDataSource: FirebaseAuthDataSource by lazy {
        FirebaseAuthDataSource(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            pbLogin.visible()
            val email = tilEmail.editText?.text.toString()
            val password = tilPassword.editText?.text.toString()
            authDataSource.signIn(email, password)
        }

    }

    override fun onLoginSuccess() {
        pbLogin.gone()
        MainActivity.start(this)
        finish()
    }

    override fun onLoginFailed(message: String) {
        pbLogin.gone()
        showToast(message)
    }
}
