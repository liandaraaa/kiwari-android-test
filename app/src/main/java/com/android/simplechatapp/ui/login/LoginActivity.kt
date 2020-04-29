package com.android.simplechatapp.ui.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.simplechatapp.R
import com.android.simplechatapp.datasource.FirebaseAuthDataSource
import com.android.simplechatapp.datasource.OnFirebaseAuthListener
import com.android.simplechatapp.preference.SimpleChatPreference
import com.android.simplechatapp.ui.main.MainActivity
import com.android.simplechatapp.utils.Constans
import com.android.simplechatapp.utils.gone
import com.android.simplechatapp.utils.showToast
import com.android.simplechatapp.utils.visible
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity(), OnFirebaseAuthListener {

    companion object {
        fun start(context: Context) {
            context.startActivity<LoginActivity>()
        }
    }

    private val authDataSource: FirebaseAuthDataSource by lazy {
        FirebaseAuthDataSource(this)
    }

    private val preference: SimpleChatPreference by lazy {
        SimpleChatPreference(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (isLoggedIn()) {
            toMainActivtiy()
        }

        btnLogin.setOnClickListener {
            pbLogin.visible()
            doLogin()
        }

    }

    private fun toMainActivtiy() {
        MainActivity.start(this)
        finish()
    }

    private fun isLoggedIn(): Boolean {
        return preference.getBoolean(Constans.KEY_IS_LOGGED_IN)
    }

    private fun doLogin() {
        val email = tilEmail.editText?.text.toString()
        val password = tilPassword.editText?.text.toString()
        authDataSource.signIn(email, password)
    }

    override fun onLoginSuccess(userId: String) {
        preference.saveBoolean(Constans.KEY_IS_LOGGED_IN, true)
        preference.saveString(Constans.KEY_USER_ID, userId)
        pbLogin.gone()
        MainActivity.start(this)
        finish()
    }

    override fun onLoginFailed(message: String) {
        pbLogin.gone()
        showToast(message)
    }
}
