package com.example.serhiivorobiov.smack.Controller

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.serhiivorobiov.smack.R
import com.example.serhiivorobiov.smack.Services.LoginService
import com.example.serhiivorobiov.smack.Services.findUserByEmailService
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login_spinner.visibility = View.INVISIBLE
    }

    fun onLogActLoginButtonClicked(view: View) {
        enableSpinner(true)
        val email = login_email_text.text.toString()
        val password = login_password_text.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            hideKeyboard()
            LoginService.userLogin(email, password) { loginSuccess ->
                if (loginSuccess) {
                    findUserByEmailService.findUser(this) { findSuccess ->
                        if (findSuccess) finish() else errorToast()
                    }
                } else errorToast()
            }
        }else{
            Toast.makeText(this, "Please, make sure email and/or password are filled in!",
                Toast.LENGTH_LONG).show()
            enableSpinner(false)
        }
    }

    fun onUserCreateButtonClicked(view: View) {

        val createUserIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()
    }

//    fun onCrashClicked(view: View): Unit = throw RuntimeException("This is a crash")

    fun enableSpinner(enable: Boolean){

        if (enable){
           login_spinner.visibility = View.VISIBLE
        } else{
            login_spinner.visibility = View.INVISIBLE
        }
        log_act_login_btn.isEnabled = !enable
        user_create_btn.isEnabled = !enable
    }

    fun errorToast() {
        Toast.makeText(this, "Something goes wrong, please try again.", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if(inputManager.isAcceptingText){
            inputManager.hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }
    }
}
