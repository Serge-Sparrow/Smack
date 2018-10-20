package com.example.serhiivorobiov.smack.Controller

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.LoginService
import com.example.serhiivorobiov.smack.Services.findUserByEmailService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun onLogActLoginButtonClicked(view: View) {

        val email = login_email_text.text.toString()
        val password = login_password_text.text.toString()

        LoginService.userLogin(this,email,password){loginSuccess ->
            if(loginSuccess){
                findUserByEmailService.findUser(this) {findSuccess ->
                    if(findSuccess)finish()
                }

            }
        }

    }

    fun onUserCreateButtonClicked(view: View) {

        val createUserIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()
    }
}
